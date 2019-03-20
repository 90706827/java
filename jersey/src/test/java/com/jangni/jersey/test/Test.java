package com.jangni.jersey.test;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @ClassName Test
 * @Description
 * @Author Mr.Jangni
 * @Date 2019/3/19 13:38
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        Response r = client
                .target("http://127.0.0.1:34545")
                .request()
                .accept(MediaType.TEXT_XML)
                .header("type", "1001")
                .post(Entity.entity("<xml><root>abcd</root></xml>", "text/xml; charset=utf-8"));

        System.out.println(r);
        if (r.getStatus() == 200) {
            String body = r.readEntity(String.class);
            System.out.println(body);
        }
        client.close();
    }
}
