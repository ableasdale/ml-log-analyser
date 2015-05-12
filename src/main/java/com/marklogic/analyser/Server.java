package com.marklogic.analyser;

import com.marklogic.analyser.resources.BaseResource;
import com.marklogic.analyser.util.Consts;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
/*import com.xmlmachines.pstack.resources.com.marklogic.analyser.resources.BaseResource;
import com.xmlmachines.pstack.resources.PropertiesMap;
import com.xmlmachines.pstack.util.com.marklogic.analyser.util.Consts;*/

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private static String path;

    public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0")
                .port(Consts.GRIZZLY_HTTP_PORT).build();
    }

    /**
     * Start the Jersey FreeMarker application.
     *
     * @param args does not matter.
     * @throws IOException in case the application could not be started.
     */
    public static void main(String[] args) throws IOException {

        // PropertiesMap.getInstance().put("path", args[0]);
        // HTTP server invocation code below
        HttpServer httpServer = startServer();
        LOG.info("HTTP Application com.marklogic.analyser.Server Ready: " + BASE_URI);
        LOG.info("WADL Definition available at: " + BASE_URI
                + "application.wadl");
        LOG.info("Press enter to stop the application server...");
        System.in.read();
        httpServer.stop();
    }


    protected static HttpServer startServer() throws IOException {
        LOG.info("Starting Grizzly (HTTP Service).");
        ResourceConfig rc = new PackagesResourceConfig(BaseResource.class
                .getPackage().getName());
        rc.getProperties().put(
                FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");
        //rc.getProperties().put(com.marklogic.analyser.util.Consts.SOURCE_FILE_TO_PROCESS, path);
        rc.getFeatures().put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);
        // TODO - not sure if both template base paths need to be "put" but this
        // works for now :)
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("com.sun.jersey.freemarker.templateBasePath",
                Consts.FREEMARKER_TEMPLATE_PATH);
        rc.setPropertiesAndFeatures(params);
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }
}