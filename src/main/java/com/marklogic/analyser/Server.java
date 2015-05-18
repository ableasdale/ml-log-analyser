package com.marklogic.analyser;

import com.marklogic.analyser.util.Consts;
import com.sun.jersey.api.container.filter.LoggingFilter;

import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
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

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    public static final URI BASE_URI = getBaseURI();

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0")
                .port(Consts.GRIZZLY_HTTP_PORT).path("/").build();
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
        LOG.info("Starting Grizzly Web Container.");

        WebappContext context = new WebappContext("context");
        ServletRegistration registration =
                context.addServlet("ServletContainer", ServletContainer.class);
        registration.setInitParameter("com.sun.jersey.config.property.packages", Server.class.getPackage().getName());
        registration.addMapping("/*");

        /*
        // Add Security Request filter layer (to handle http authentication) - implemented in SimpleSecurityFilter and intended for demonstration only!
        registration.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS,
                "com.marklogic.analyser.auth.SimpleSecurityFilter;com.sun.jersey.api.container.filter.LoggingFilter");

        // Add logging Response filter (for debugging)
        registration.setInitParameter(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS,
                LoggingFilter.class.getName());
        */

        // Add Freemarker template mapping
        registration.setInitParameter(FreemarkerViewProcessor.FREEMARKER_TEMPLATES_BASE_PATH,
                "freemarker");

        // Create the container
        final HttpServer server = GrizzlyWebContainerFactory.create(BASE_URI);

        // Deploy the application into the container
        context.deploy(server);

        // Register shutdown hook for container
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("Stopping server..");
                server.shutdownNow();
            }
        }, "shutdownHook"));

        try {
            server.start();
            LOG.info("Server Ready... Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("Exception starting HTTP server: ", e);
        }
    }
}
