package com.jimmy.jersey;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.json.JacksonJsonParser;

/**
 * @ClassName ApiApplication
 * @Description api
 * @Author Mr.jimmy
 * @Date 2018/10/16 11:49
 * @Version 1.0
 **/
public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        register(HelloResource.class);
        register(JacksonJsonParser.class);
        property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL, "ERROR");
        property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.PAYLOAD_ANY);
    }


}
