package com.marklogic.analyser.resources;


import com.marklogic.analyser.beans.ErrorLogMap;
import com.marklogic.analyser.util.Consts;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * The "Root" dashboard resource
 * <p/>
 * User: ableasdale Date: 2/1/14 Time: 6:40 PM
 */

@Path("/")
public class RootResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(RootResource.class);

    /* data model for freemarker .ftl template
    private Map<String, Object> createModel() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
        map.put("errorlog", ErrorLogMap.getInstance().get("ErrorLog.txt"));
        //  map.put("path", PropertiesMap.getInstance().get("path"));
        map.put("errorlogs", ErrorLogMap.getInstance());
        //  map.put("stacksCarried", stackRecords);
        return map;
    } */

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Dashboard and Overview");
        map.put("errorlog", ErrorLogMap.getInstance().get(id));
        map.put("errorlogs", ErrorLogMap.getInstance());
        map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
        return map;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Viewable doPost() {
        return getDashboard();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDashboard() {
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

}