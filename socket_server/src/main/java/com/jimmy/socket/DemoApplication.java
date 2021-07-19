package com.jimmy.socket;

import com.jimmy.socket.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    @Bean
    public NettyServer nettyServer() {
        return new NettyServer(8889,60,new DateListener());
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
