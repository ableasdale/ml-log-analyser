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
        el.setErrorLogTxt( IOUtils.readLines(new InputStreamReader(is, Charset.forName("UTF-8"))));
        processErrorLog(el);

    }

    public void processLog(File file) throws IOException {
        LOG.info(MessageFormat.format("Processing ErrorLog file: {0}", file.getName()));

        String fileStr = readFile(file.getPath(), StandardCharsets.UTF_8);
        ErrorLog el = new ErrorLog();
        el.setName(file.getName());
        el.setErrorLogTxt(IOUtils.readLines(new StringReader(fileStr)));
        processErrorLog(el);

    }



    private void processErrorLog(ErrorLog el) {

        Map<String, Integer> keywordOccurrences = new HashMap<String, Integer>();
        List<String> lines = el.getErrorLogTxt();

        for (String l : lines)  {
            if (l.contains("Starting MarkLogic")) {
                int start = lines.indexOf(l);
                int idx;
                LOG.info(MessageFormat.format("Array index for restart message: {0}", String.valueOf(start)));
                LOG.debug(MessageFormat.format("Restart detected - displaying the following {0} lines after restart and lines before..", Consts.RESTART_TOTAL_LINES));

                if (start >= 4) {
                    idx = start - 3;
                }  else {
                    idx = 1;
                }

                for (int i = 0; i < Consts.RESTART_TOTAL_LINES; i++){
                    LOG.debug(lines.get(idx));
                    idx++;
                }
            }

            // TODO:: below
            if(l.contains("Event:")) {
                LOG.debug("* Event * : "+l);
            }
            if (l.contains("Warning:")) {
                LOG.debug("* Warning * : "+l);
            }
            if (l.contains("* Critical * : "+l)) {
                LOG.debug("Critical");
            }
            // Exception keywords
            for (String j : Consts.KEYWORDS) {
                if(l.contains(j)){
                    if (keywordOccurrences.containsKey(j)){
                        keywordOccurrences.put(j, (keywordOccurrences.get(j) + 1));
                    } else {
                        keywordOccurrences.put(j, 1);
                    }
                   // LOG.debug(l);
                }
            }
        }

        el.setOccurrenceMap(keywordOccurrences);
        ErrorLogMap.getInstance().put(el.getName(), el);

        for (String s : el.getOccurrenceMap().keySet())
            LOG.info(MessageFormat.format("Total number of {0} messages reported: {1}", s, String.valueOf(keywordOccurrences.get(s))));
    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
