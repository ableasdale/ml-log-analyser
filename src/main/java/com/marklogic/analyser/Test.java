package com.marklogic.analyser;

import com.marklogic.analyser.util.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ableasdale
 * Date: 4/29/15
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws IOException {

        Logger LOG = LoggerFactory.getLogger("com.marklogic.analyser.Test");
        FileProcessManager fpm = new FileProcessManager();

        // traverse a directory
        LOG.info("Traversing Directory: " + Consts.DIRECTORY_PATH);

        File file = new File(Consts.DIRECTORY_PATH);
        Collection<File> files = FileUtils.listFiles(file, null, true);
        for (File file2 : files){
            if(file2.getName().startsWith("ErrorLog")){
                LOG.info(file2.getName());
                fpm.processLog(file2);
            }
        }
    }
}
