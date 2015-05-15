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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
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
    public static void main(String[] args) throws Exception {
        new Server().startServer();
    }

    public static void startServer() throws IOException {
        LOG.info("Starting Grizzly (HTTP Service).");
        ResourceConfig rc = new PackagesResourceConfig(BaseResource.class.getPackage().getName());
        rc.getProperties().put(
                FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");
        rc.getFeatures().put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("com.sun.jersey.freemarker.templateBasePath", Consts.FREEMARKER_TEMPLATE_PATH);
        rc.setPropertiesAndFeatures(params);

        final HttpServer server = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);

        // register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("Stopping server..");
                server.stop();
            }
        }, "shutdownHook"));

        try {
            server.start();
            LOG.info(String.format("HTTP Application com.marklogic.analyser.Server Ready: %s", BASE_URI));
            LOG.info(MessageFormat.format("WADL Definition available at: {0}/application.wadl", BASE_URI));
            LOG.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error(
                    "There was an error while starting Grizzly HTTP server.", e);
        }
    }
}