package com.jangni.socket;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.jangni.socket.client.MatchActor;
import com.jangni.socket.client.NettyClient;
import com.jangni.socket.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SocketClientApplication {
    @Bean
    ActorSystem actorSystem() {
        return ActorSystem.create("system");
    }

    @Bean
    ActorRef matchActor(ActorSystem actorSystem) {
        return actorSystem.actorOf(Props.create(MatchActor.class));
    }

    @Bean
    NettyClient nettyClient() {
       return  new NettyClient("127.0.0.1", 8889, 60000);
    }

    @Bean
    NettyServer nettyServer(NettyClient nettyClient){
        return new NettyServer(8888,60,new SocketDataListener(nettyClient));
    }

    public static void main(String[] args) {
        SpringApplication.run(SocketClientApplication.class, args);
    }
}
