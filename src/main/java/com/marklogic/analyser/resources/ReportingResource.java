package com.marklogic.analyser.resources;

import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.sun.jersey.api.view.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ableasdale
 * Date: 5/13/15
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/report")
public class ReportingResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingResource.class);
    private static Map<String, Integer> aggregateValues = new HashMap<String, Integer>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDashboard() {
        StringBuilder sb = new StringBuilder();
        for (String s : ErrorLogMap.getInstance().keySet()  ){
            LOG.info("Creating Report for..."+s);
            ErrorLog e = ErrorLogMap.getInstance().get(s);
            sb.append("Filename\t:\t"+s+"\n");
            sb.append("Total Restarts\t:\t"+e.getTotalRestarts()+"\n");
            appendStringBuilderWithMapItems(sb, e.getOtherMessages());
            appendStringBuilderWithMapItems(sb, e.getOccurrenceMap());
            aggregateOccurrences(e.getOccurrenceMap());
            sb.append("------------\n");
        }
        sb.append("Aggregate report for all ErrorLog files\n");
        sb.append("------------\n");
        for (String t : aggregateValues.keySet()) {
            sb.append(t+"\t:\t"+ aggregateValues.get(t)+"\n");
        }
        return sb.toString();
    }

    private void aggregateOccurrences(Map<String, List<String>> map){
        for (String t : map.keySet()) {
            if(aggregateValues.containsKey(t)){
                aggregateValues.put(t, (aggregateValues.get(t) + map.get(t).size()));
            } else {
                aggregateValues.put(t, map.get(t).size());
            }
        }
    }

    private StringBuilder appendStringBuilderWithMapItems(StringBuilder sb, Map<String, List<String>> map){
        for (String t : map.keySet()) {
            sb.append(t+"\t:\t"+ map.get(t).size()+"\n");
        }
        return sb;
    }



}
