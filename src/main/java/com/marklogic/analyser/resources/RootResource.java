package com.marklogic.analyser.resources;


import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.marklogic.analyser.util.Consts;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * The "Root" dashboard resource
 * <p/>
 * User: ableasdale Date: 2/1/14 Time: 6:40 PM
 */

@Path("/")
public class RootResource extends BaseResource {

    //@Context
    //SecurityContext securityContext;

    //@Context
    //private ThreadLocal<Request> request;

    //@GET
    //public String getUserPrincipal() {
     //   return securityContext.getUserPrincipal().getName();
    //}

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");

        if (ErrorLogMap.getInstance().size() > 0) {
            LOG.debug(MessageFormat.format("ErrorLog contains {0} items", ErrorLogMap.getInstance().size()));
            if (ErrorLogMap.getInstance().containsKey(id)) {
                LOG.debug(MessageFormat.format("Key is {0} contains key? {1}", id, ErrorLogMap.getInstance().containsKey(id)));
                map.put("errorlog", ErrorLogMap.getInstance().get(id));
            } else {
                LOG.debug("Putting first item from map in.. ");
                ConcurrentNavigableMap m = ErrorLogMap.getInstance();
                String s = ErrorLogMap.getInstance().firstKey();
                map.put("errorlog", ErrorLogMap.getInstance().get(s));
            }

        } else {
            LOG.debug(MessageFormat.format("Dashboard - no ErrorLog to display - size is: {0}", ErrorLogMap.getInstance().size()));
        }


        map.put("errorlogs", ErrorLogMap.getInstance());
        map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
        return map;
    }
                            //@Context SecurityContext sc
    @POST
    @Produces(MediaType.TEXT_HTML)
    public Viewable doPost() {
        return getDashboard();
    }
                                 //@Context SecurityContext sc
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {

        //LOG.info("Is Secure: " + sc.isSecure());
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/dashboard", createModel("ErrorLog.txt"));
    }

    @GET
    @Path("/view/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDetails(@PathParam("id") String id) {
        LOG.debug(id);
        return new Viewable("/dashboard", createModel(id));
    }

    @GET
    @Path("/clear")
    @Produces(MediaType.TEXT_HTML)
    public Viewable clearAndGetDashboard() {
        LOG.debug("Clearing ErrorLog...");
        ErrorLogMap.setInstance(new ConcurrentSkipListMap<String, ErrorLog>());
        // renders the URI using "src/main/resources/freemarker/dashboard.ftl"
        return new Viewable("/dashboard", createModel("ErrorLog.txt"));
    }
}