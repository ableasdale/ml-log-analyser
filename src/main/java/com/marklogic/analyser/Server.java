package com.marklogic.analyser;

import com.marklogic.analyser.resources.BaseResource;
import com.marklogic.analyser.util.Consts;
import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.freemarker.FreemarkerViewProcessor;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
/*import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;*/

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

        WebappContext context = new WebappContext("context");
        ServletRegistration registration =
                context.addServlet("ServletContainer", ServletContainer.class);
        registration.setInitParameter("com.sun.jersey.config.property.packages",
                "com.marklogic.analyser.resources;com.marklogic.analyser.auth");
        registration.addMapping("/*");

        // add security filter (which handles http basic authentication)
        registration.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                "com.marklogic.analyser.auth.SecurityFilter;com.sun.jersey.api.container.filter.LoggingFilter");
        registration.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                LoggingFilter.class.getName());

        // add freemarker init mapping
        registration.setInitParameter(FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");
        //registration.setInitParameter(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);

        ResourceConfig rc = new PackagesResourceConfig(BaseResource.class.getPackage().getName());
        rc.getFeatures().put(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, true);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("com.sun.jersey.freemarker.templateBasePath", Consts.FREEMARKER_TEMPLATE_PATH);
        rc.setPropertiesAndFeatures(params);


        final HttpServer server = GrizzlyServerFactory.createHttpServer(BASE_URI);
        context.deploy(server);

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

/*




    try {

        webServer = GrizzlyServerFactory.createHttpServer(
                getBaseURI()
        );

        // start Grizzly embedded server //
        System.out.println("Jersey app started. Try out " + BASE_URI + "\nHit CTRL + C to stop it...");
        context.deploy(webServer);
        webServer.start();

    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }

   */