package com.marklogic.analyser;

import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.resources.ErrorLogMap;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void processErrorLog(ErrorLog el) {

        Map<String, List<String>> keywordOccurrences = new HashMap<String, List<String>>();
        Map<String, List<String>> otherMessages = new HashMap<String, List<String>>();
        Map<String, List<String>> traceEvents = new HashMap<String, List<String>>();
        List<String> restarts = new ArrayList<String>();
        List<String> lines = el.getErrorLogTxt();

        for (String l : lines) {
            // Ignore some verbose level logging:
            if (l.contains("Fine: ") || l.contains("Finer: ") || l.contains("Finest: ")) {
                // || l.contains("Debug: " ?
                // Nothing to list here: do no further checking
            } else {
                if (l.contains("Starting MarkLogic")) {
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
                // Gather and sort all Audit and trace events found in the ErrorLog
                if (l.contains("AuditEvent")) {
                    //LOG.info(l);
                    // TODO - may want to break these down further
                    checkAndAddItem(otherMessages, "AuditEvent", l);
                } else if (l.contains("Event:")) {
                    String temp = l.split("Event:id=")[1];
                    String evtType = temp.substring(0, temp.indexOf(']'));
                    LOG.debug(MessageFormat.format("Trace Event Found: {0}", evtType));
                    checkAndAddItem(traceEvents, evtType, l);
                } else if (l.contains("Warning: ") || l.contains("Notice: ") || l.contains("Critical: ")) {
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

        otherMessages.put("Restarts", restarts);
        el.setOtherMessages(otherMessages);
        el.setOccurrenceMap(keywordOccurrences);
        el.setTraceEventMessages(traceEvents);
        ErrorLogMap.getInstance().put(el.getName(), el);

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
