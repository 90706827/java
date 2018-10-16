package com.jangni.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @ClassName HelloResource
 * @Description Hello
 * @Author Mr.Jangni
 * @Date 2018/10/16 11:45
 * @Version 1.0
 **/
@Path("hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return " say hello";
    }

}
