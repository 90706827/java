package com.jangni.socket_server;

import com.jangni.socket_server.netty.DateListener;
import com.jangni.socket_server.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    @Bean
    public NettyServer nettyServer(){
        return new NettyServer(new DateListener());
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
