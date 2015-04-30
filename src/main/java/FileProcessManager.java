import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    Logger LOG = LoggerFactory.getLogger("FileProcessManager");

    public void processLog(File file) throws IOException {
        LOG.info("Processing ErrorLog file: " + file.getName());
        String fileStr = readFile(file.getPath(), StandardCharsets.UTF_8);
        //System.out.println(fileStr);
        List<String> lines = IOUtils.readLines(new StringReader(fileStr));

        Map<String, Integer> keywordOccurrences = new HashMap<String, Integer>();

        for (String l : lines)  {
            if (l.contains("Starting MarkLogic")) {

                int start = lines.indexOf(l);
                int idx;

                LOG.info("INDEX: " + String.valueOf(start));

                LOG.info("Restart detected - displaying the following "+ Consts.RESTART_TOTAL_LINES + " lines after restart and lines before..");
                if (start < 2) {

                    idx = start - 3;
                }   else {
                    idx = 1;
                }

                for (int i = 0; i < Consts.RESTART_TOTAL_LINES; i++){
                    LOG.info(lines.get(idx));
                    idx++;
                }
            }

            // Exception keywords
            for (String j : Consts.KEYWORDS) {
                if(l.contains(j)){
                    if (keywordOccurrences.containsKey(j)){

                        keywordOccurrences.put(j, (keywordOccurrences.get(j) + 1));
                    } else {
                        keywordOccurrences.put(j, 1);
                    }
                   // LOG.info(l);
                }
            }
        }

        //System.out.println("debug");
        for (String s : keywordOccurrences.keySet()) {
            LOG.info("Total number of " + s + " messages reported: " + String.valueOf(keywordOccurrences.get(s)) );
        }

    }

    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
