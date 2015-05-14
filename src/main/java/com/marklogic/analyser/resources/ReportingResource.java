package com.marklogic.analyser.resources;

import com.marklogic.analyser.beans.ErrorLog;
import com.marklogic.analyser.beans.ErrorLogMap;
import com.marklogic.analyser.util.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

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

    // map.put("title", "Dashboard and Overview");


    private static Map<String, Integer> sortMapByIntegerSize(Map<String, Integer> map, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDashboard() {
        StringBuilder sb = new StringBuilder();
        for (String s : ErrorLogMap.getInstance().keySet()) {
            LOG.debug("Creating Report for..." + s);
            ErrorLog e = ErrorLogMap.getInstance().get(s);
            sb.append(formattedLine("Filename", s));
            sb.append(formattedLine("Total Restarts", Integer.toString(e.getTotalRestarts())));
            appendStringBuilderWithMapItems(sb, e.getOtherMessages());
            appendStringBuilderWithMapItems(sb, e.getOccurrenceMap());
            aggregateOccurrences(e.getOccurrenceMap());
            sb.append("------------\n");
        }
        sb.append("Aggregate report for all ErrorLog files\n");
        sb.append("------------\n");
        for (String t : sortMapByIntegerSize(aggregateValues, false).keySet()) {
            sb.append(formattedLine(t,Integer.toString(aggregateValues.get(t))));
        }
        return sb.toString();
    }

    private void aggregateOccurrences(Map<String, List<String>> map) {
        for (String t : map.keySet()) {
            if (aggregateValues.containsKey(t)) {
                aggregateValues.put(t, (aggregateValues.get(t) + map.get(t).size()));
            } else {
                aggregateValues.put(t, map.get(t).size());
            }
        }
    }

    private StringBuilder appendStringBuilderWithMapItems(StringBuilder sb, Map<String, List<String>> map) {
        SortedSet<String> keys = new TreeSet<String>(map.keySet());
        for (String t : keys) {
            sb.append(formattedLine(t,Integer.toString(map.get(t).size())));
        }
        return sb;
    }

    private String formattedLine(String key, String value){
        return String.format(Consts.FORMAT, key, value);
    }

}
