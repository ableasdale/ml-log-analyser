package com.marklogic.analyser.resources;

import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ableasdale
 * Date: 5/13/15
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/search")
public class SearchResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(SearchResource.class);
    private static Map<String, List<String>> logs;

    // data model for freemarker .ftl template
    private Map<String, Object> createModel(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Search");
        map.put("errorlogs", ErrorLogMap.getInstance());
        map.put("term", id);
        map.put("searchResults", logs);

        //map.put("errorlog", ErrorLogMap.getInstance().get(id));
        //map.put("errorlogs", ErrorLogMap.getInstance());
        //map.put("lines", Consts.MAX_LINES_FOR_LOG_PREVIEW);
        return map;
    }

    @GET
    @Path("/{term}")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getDetails(@PathParam("term") String searchTerm) {
        LOG.debug("Search :: GET :: searched for: " + searchTerm);
        doSearch(searchTerm);
        return new Viewable("/search", createModel(searchTerm));
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Viewable doGet(@FormParam("term") String searchTerm) {
        LOG.debug("Search :: POST :: searched for: " + searchTerm);
        doSearch(searchTerm);
        // renders the URI using "src/main/resources/freemarker/search.ftl"
        return new Viewable("/search", createModel(searchTerm));
    }

    private void doSearch(String searchTerm){
        logs = new HashMap<String, List<String>>();

        for (String s : ErrorLogMap.getInstance().keySet()){
            ErrorLog el = ErrorLogMap.getInstance().get(s);

            List<String> logTxt = el.getErrorLogTxt();
            List<String> searchResults = new ArrayList<String>();

            for (String t : logTxt){
                if(t.contains(searchTerm)){
                    searchResults.add(t);
                }
            }
            logs.put(s, searchResults);
        }
    }

}
