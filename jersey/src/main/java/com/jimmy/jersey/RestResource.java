package com.jimmy.jersey;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * @ClassName RestResource
 * @Description 入口
 * @Author Mr.jimmy
 * @Date 2019/3/19 12:56
 * @Version 1.0
 **/
@Path("test")
public class RestResource {

    @Path("type")
    @POST
    @Consumes(value = MediaType.TEXT_XML)
    @Produces(value = MediaType.TEXT_XML)
    public void post(String context,
                     @HeaderParam("type") String type,
                     @Suspended AsyncResponse asyncResponse) {
//       logger.info(context);
        asyncResponse.resume("<xml><root><code>00</code><desc>成功</desc></root></xml>");

    }
}
