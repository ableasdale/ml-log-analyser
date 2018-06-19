package com.marklogic.analyser;

import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.marklogic.analyser.util.Consts;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created with IntelliJ IDEA.
 * User: ableasdale
 * Date: 4/29/15
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileProcessManager {

    Logger LOG = LoggerFactory.getLogger(FileProcessManager.class);

    public void processUploadedFile(InputStream is, String filename) throws IOException {
        LOG.info(MessageFormat.format("Processing Uploaded ErrorLog file: {0}", filename));
        ErrorLog el = new ErrorLog();
        el.setName(filename);
        el.setErrorLogTxt(IOUtils.readLines(new InputStreamReader(is, Charset.forName("UTF-8"))));
        processErrorLog(el);
        LOG.info(MessageFormat.format("Completed processing ErrorLog file: {0}", filename));
    }

    public void processLog(File file) throws IOException {
        LOG.info(MessageFormat.format("Processing ErrorLog file: {0}", file.getName()));
        String fileStr = readFile(file.getPath(), StandardCharsets.UTF_8);
        ErrorLog el = new ErrorLog();
        el.setName(file.getName());
        el.setErrorLogTxt(IOUtils.readLines(new StringReader(fileStr)));
        processErrorLog(el);
        LOG.info(MessageFormat.format("Completed processing ErrorLog file: {0}", file.getName()));
    }

    // What kind of file is it? Is this an ML ErrorLog file?  Is it a support dump or is it a kernel messages file?
                /* if messages
                Apr  8 21:15:04
                if ErrorLog:
                2015-05-08 08:48:31.694
                */
    private String matchDateRepresentation(String line) {
        if(line.contains("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")){
            LOG.info("File is a support dump");
            return("SUPPORT_DUMP");
        }


        LOG.info("Attempting to parse: "+line);
        // is this an ErrorLog?
        SimpleDateFormat ft = new SimpleDateFormat(Consts.DATE_FMT_ML_ERRORLOG);
        Date t = new Date();
        try {
            t = ft.parse(line);
            LOG.info("We have a date formatted for a MarkLogic ErrorLog: "+t.toString());
            return("ERROR_LOG");
        } catch (ParseException e) {
            LOG.info("Unable to parse file as ErrorLog.txt " + e.getMessage());
        }

        // Is it a Linux kernel messages file?
        SimpleDateFormat ft2 = new SimpleDateFormat(Consts.DATE_FMT_LINUX_MESSAGES);
        Date t2 = new Date();
        try {
            t2 = ft2.parse(line);
            LOG.info("We have a date formatted for a Linux Messages file: "+t.toString());
            return("MESSAGES");
        } catch (ParseException e) {
            LOG.info("Unable to parse file as /var/log/messages " + e.getMessage());
        }

        // No idea ....
        return "UNIDENTIFIED";
    }

    private void processErrorLog(ErrorLog el) {

        Map<String, List<String>> keywordOccurrences = new HashMap<String, List<String>>();
        Map<String, List<String>> otherMessages = new HashMap<String, List<String>>();
        Map<String, List<String>> traceEvents = new HashMap<String, List<String>>();
        int totalRestarts = 0;
        List<String> restarts = new ArrayList<String>();
        List<String> lines = el.getErrorLogTxt();
        List<String> fragmentMessages = new ArrayList<String>();

        //String mode = matchDateRepresentation(lines.get(0));

        for (String l : lines) {
            // Ignore some verbose level logging:
            if (l.contains("Fine: ") || l.contains("Finer: ") || l.contains("Finest: ") || l.contains("Debug: ")  ) {
                // || l.contains("Debug: " ?
                // Nothing to list here: do no further checking
            } else {
                if (l.contains("MarkLogic: Forest") && l.contains("has") && l.contains("fragments.")) {
                    int start = lines.indexOf(l);
                    int idx;
                    LOG.debug(MessageFormat.format("Messages :: \"Forest has XXXXX fragments\" message found at : {0}", String.valueOf(start)));
                    if (start >= 5) {
                        idx = start - 4;
                    } else {
                        idx = 0;
                    }
                    for (int i = 0; i < Consts.RESTART_TOTAL_LINES; i++) {
                        fragmentMessages.add(lines.get(idx));
                        idx++;
                    }
                    fragmentMessages.add("------------");
                }
                if (l.contains("Starting MarkLogic")) {
                    totalRestarts += 1;
                    int start = lines.indexOf(l);
                    int idx;
                    LOG.debug(MessageFormat.format("Array index for restart message: {0}", String.valueOf(start)));
                    LOG.debug(MessageFormat.format("Restart detected - adding the following {0} lines after restart and lines before..", Consts.RESTART_TOTAL_LINES));

                    if (start >= 4) {
                        idx = start - 3;
                    } else {
                        idx = 0;
                    }

                    for (int i = 0; i < Consts.RESTART_TOTAL_LINES; i++) {
                        restarts.add(lines.get(idx));
                        idx++;
                    }
                    restarts.add("------------");
                }

                // Find evidence of memory information logged
                if (l.contains("Info: Memory")) {
                    LOG.debug("Memory utilisation details found %s", l);
                    checkAndAddItem(otherMessages, "Memory", l);
                    // Find evidence of a stack trace
                } else if (l.contains("#") && l.contains(" 0x") && l.contains(" in ")) {// !(l.contains("&#") || l.contains("&amp;#") ) ) {
                    LOG.debug(String.format("Crash dump found: %s", l));
                    checkAndAddItem(otherMessages, "Crash", l);
                } else if (l.contains("AuditEvent")) {
                    // Gather and sort all Audit and trace events found in the ErrorLog
                    // TODO - may want to break these down further
                    checkAndAddItem(otherMessages, "AuditEvent", l);
                } else if (l.contains("Event:")) {
                    LOG.info(String.format("Event: %s", l));
                    String temp = l.split("Event:id=")[1];
                    String evtType = temp.substring(0, temp.indexOf(']'));
                    LOG.debug(MessageFormat.format("Trace Event Found: {0}", evtType));
                    checkAndAddItem(traceEvents, evtType, l);
                } else if (l.contains("Warning: ") || l.contains("Critical: ")) {
                    // Removed these as exceptions and restarts are already tracked anyway || l.contains("Notice: ")
                    String msgType = l.split(" ")[2];
                    msgType = msgType.substring(0, msgType.length() - 1);
                    LOG.debug(MessageFormat.format("Important {0} level ErrorLog message found", msgType));
                    checkAndAddItem(otherMessages, msgType, l);
                }
                // Check for Exception keywords
                for (String j : Consts.KEYWORDS) {
                    if (l.contains(j)) {
                        checkAndAddItem(keywordOccurrences, j, l);
                    }
                }
            }
        }

        el.setErrorLogRestartTxt(restarts);
        el.setOtherMessages(otherMessages);
        el.setTotalRestarts(totalRestarts);
        el.setOccurrenceMap(keywordOccurrences);
        el.setFragmentMessages(fragmentMessages);
        el.setTraceEventMessages(traceEvents);
        ConcurrentNavigableMap<String, ErrorLog> elm = ErrorLogMap.getInstance();
        elm.put(el.getName(), el);
        ErrorLogMap.setInstance(elm);
        // .getInstance().put(el.getName(), el);

       /* for (String s : el.getOccurrenceMap().keySet())
            LOG.info(MessageFormat.format("Total number of {0} messages reported: {1}", s, String.valueOf(keywordOccurrences.get(s))));   */
    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void checkAndAddItem(Map<String, List<String>> map, String key, String value) {
        if (map.containsKey(key)) {
            List<String> lst = map.get(key);
            lst.add(value);
            map.put(key, lst);
        } else {
            List<String> lst = new ArrayList<String>();
            lst.add(value);
            map.put(key, lst);
        }
    }
}
