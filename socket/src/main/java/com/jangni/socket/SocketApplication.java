package com.jangni.socket;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.jangni.socket.netty.MatchActor;
import com.jangni.socket.netty.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.jangni")
public class SocketApplication {
    @Bean
    ActorSystem actorSystem() {
        return ActorSystem.create("system");
    }

    @Bean
    ActorRef matchActor(ActorSystem actorSystem) {
        return actorSystem.actorOf(Props.create(MatchActor.class));
    }

    @Bean(initMethod = "connect")
    NettyClient nettyClient() {
        NettyClient nettyClient =  new NettyClient();
        return nettyClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }
}
