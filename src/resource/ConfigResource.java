package resource;

/**
 * Derived from dino
 * senthil ramasamy
 * https://bitbucket.org/senthil/team2/overview
 * 
 */

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public interface ConfigResource {

        @GET
        @Path ("/self")
        public Response getConfig(@Context UriInfo uriInfo, @Context ServletContext servletContext);
        
        @GET
        @Path ("/jndi")
        public Response getJndiConfig(@Context UriInfo uriInfo, @Context ServletContext servletContext);

        @POST
        @Path ("/self/{hostport}")
        public Response setConfigPost(@PathParam("hostport") String hostport, @Context UriInfo uriInfo, @Context ServletContext servletContext);
        
        @POST
        @Path ("/jndi/{hostport}")
        public Response setJndiConfigPost(@PathParam("hostport") String hostport, @Context UriInfo uriInfo, @Context ServletContext servletContext);
        
        @PUT
        @Path ("/self/{hostport}")
        public Response setConfig(@PathParam("hostport") String hostport, @Context UriInfo uriInfo, @Context ServletContext servletContext);
        
        @PUT
        @Path ("/jndi/{hostport}")
        public Response setJndiConfig(@PathParam("hostport") String hostport, @Context UriInfo uriInfo, @Context ServletContext servletContext);
        
}
