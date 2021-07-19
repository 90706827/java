package com.jimmy.jersey;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class JerseyApplication {


    public ApiApplication apiApplication(){
        return new ApiApplication();

    }

    @Bean
    public Server httpServer() throws URISyntaxException {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.registerInstances(new RestResource());
//        resourceConfig.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
//        resourceConfig.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.PAYLOAD_ANY);
        URI uri = new URI("http://0.0.0.0:34545");
        return JettyHttpContainerFactory.createServer(uri, resourceConfig);
    }

	public static void main(String[] args) {
        SpringApplication.run(JerseyApplication.class, args);
	}
}
