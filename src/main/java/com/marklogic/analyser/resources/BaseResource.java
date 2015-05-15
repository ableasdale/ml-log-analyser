package com.marklogic.analyser.resources;

import com.marklogic.analyser.FileProcessManager;
import com.marklogic.analyser.beans.ConfigParams;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.marklogic.analyser.util.Consts;
import com.marklogic.analyser.util.Os;
import com.sun.jersey.api.view.Viewable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

import static java.text.MessageFormat.format;

/**
 * Created with IntelliJ IDEA. User: ableasdale Date: 2/1/14 Time: 6:10 PM
 */

public class BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResource.class);

    public List<File> files = new ArrayList<File>();

    public BaseResource() {
        // TODO - this should detect WHETHER ML is installed and if not, do no further analysis - just open the app with no data!
        //ResourceConfig rc = ServletContainer.getConfiguration();
        // is this a first run?
        if (ErrorLogMap.getInstance().size() == 0 && ConfigParams.getInstance().isFirstRun()) {
            LOG.info(MessageFormat.format("Base Resource :: Constructor - First Run :: OS detected: {0}", Consts.HOST_OS));
            if (Os.isWindows()) {
                analysePath(Consts.DIRECTORY_PATH_WINDOWS);
            } else if (Os.isLinux()) {
                analysePath(Consts.DIRECTORY_PATH_LINUX);
            } else if (Os.isMac()) {
                analysePath(Consts.DIRECTORY_PATH_OSX);
            }
            // TODO - Add Solaris support one day?
            ConfigParams cp = ConfigParams.getInstance();
            cp.setFirstRun(false);
            ConfigParams.setInstance(cp);
        }
    }


//	public List<PStackFrame> pstacks = PStackMovies.getInstance();

    /**
     * Attempt to make sure input is an integer.
     *
     * @param text the value passed to the method from the resource (a URI
     *             segment)
     * @return true or false
     */
    protected boolean canBeParsedAsInteger(String text) {
        try {
            new Integer(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Freemarker template parameter map for the HTTP Exception Page template
     * (exception.ftl)
     *
     * @param statusCode
     * @param message
     * @return
     */
    protected Map<String, Object> createExceptionModel(int statusCode,
                                                       String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", String.valueOf(statusCode));
        map.put("message", message);
        return map;
    }

    protected Response generalExceptionCheck(String id, int arrayBoundary) {
        Response r = null;
        // non-string check
        if (!canBeParsedAsInteger(id)) {
            r = wrapViewableExceptionResponse(format(
                    "<em>{0}</em> does not appear to be a valid integer.", id));
        } else {
            // bounds check
            if (Integer.parseInt(id) < 0) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) smaller than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
            if (Integer.parseInt(id) > arrayBoundary) {
                r = wrapViewableExceptionResponse(format(
                        "Bounds check failed; It looks like you have requested a value ({0}) larger than the number of stacks available ({1})",
                        id, arrayBoundary));
            }
        }
        return r;
    }


    /**
     * General handler for exceptions in the request made to the resource This
     * is how you do a custom com.marklogic.analyser.Server Exception with the Freemarker template
     *
     * @param message
     * @return
     */
    protected Response wrapViewableExceptionResponse(String message) {
        LOG.error(MessageFormat.format("Exception encountered: {0}", message));
        return Response
                .status(500)
                .entity(new Viewable("/exception", createExceptionModel(500,
                        message))).build();
    }

    /**
     * Wraps an 'OK' response with the Viewable (freemarker) template
     *
     * @param templateName
     * @param model
     * @return
     */
    protected Response wrapViewableResponse(String templateName,
                                            Map<String, Object> model) {
        return Response.status(Response.Status.OK)
                .entity(new Viewable(templateName, model)).build();
    }


    protected void analysePath(String path) {
        LOG.info("Working with file path: " + path);
        // This should be a singleton!
        FileProcessManager fpm = new FileProcessManager();
        File file = new File(path);
        Collection<File> files = FileUtils.listFiles(file, null, true);
        for (File file2 : files) {
            // TODO: Note - deliberately restricting it to just parsing the most recent ErrorLog so app starts faster - make this configurable
            if (file2.getName().startsWith("ErrorLog.txt")) {
                LOG.debug(file2.getName());
                try {
                    fpm.processLog(file2);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}