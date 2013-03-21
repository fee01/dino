package service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.sun.jersey.spi.resource.Singleton;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.serviceEndpointInterfaceMappingType;



import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import resource.ConfigResource;
import utilities.EJBLocator;
/**
 *Derived from dino
 * senthil ramasamy
 * https://bitbucket.org/senthil/team2/overview
 * @author splassman
 *
 */

@Singleton
@Path("/config")
public class ConfigSource implements ConfigResource {
	
	private static String url = null;
	private static String hostNameAndPort = null;
	
	public static String contextPath = "";
	
	public static String getServiceUrl(UriInfo uriI, ServletContext sContext)
	{
		String localhost;
		if (url == null)
		{
			
			try
			{
				localhost = InetAddress.getLocalHost().getHostName();
				
			}
			catch(UnknownHostException uhex1)
			{
				localhost = "localhost";
			}
		
		if(uriI != null)
		{
			try{
				localhost = uriI.getRequestUri().getHost();
				hostNameAndPort = localhost + ":" + uriI.getRequestUri().getPort();
				contextPath = sContext.getContextPath();
				String path = "http://" + hostNameAndPort + sContext.getContextPath();
				if(localhost.equalsIgnoreCase("localhost") || localhost.equalsIgnoreCase("127.0.0.1"))
				{
					return path;
				}
				else
				{
					url = path;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		else
		{
			hostNameAndPort = localhost + ":8080";
			url = "http://" + hostNameAndPort + "/plass_fee_team";
		}
		}
		return url;
	}
	public static String getHostNameAndPort(UriInfo uriI, ServletContext sContext)
	{
		getServiceUrl(uriI, sContext);
		return hostNameAndPort;
	}
	
	
	@GET
	@Path("/self")
	public Response getConfig(@Context UriInfo uriI,
			@Context ServletContext sContext) {
		String rtv = getHostNameAndPort(uriI, sContext);
		return Response.ok(rtv, MediaType.TEXT_PLAIN).build();
		
		
	}

	
	@GET
	@Path("/jndi")
	public Response getJndiConfig(@Context UriInfo uriI,
			@Context ServletContext sContext) {
		
		String rtv = EJBLocator.getJndiHost();
		String rtvPort = EJBLocator.getJndiPort();
		if(rtvPort != null)
		{
			rtv += ":" + rtvPort;
		}
		return Response.ok(rtv, MediaType.TEXT_PLAIN).build();
	}

	
	@POST
	@Path("/self/{hostport}")
	public Response setConfigPost(@PathParam("hostport") String hostport,
			@Context UriInfo uriInfo, @Context ServletContext servletContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@POST
	@Path("/jndi/{hostport}")
	public Response setJndiConfigPost(@PathParam("hostport") String hostport,
			@Context UriInfo uriInfo, @Context ServletContext servletContext) {
		return setJndiConfig(hostport, uriInfo, servletContext);
	}

	
	@PUT
	@Path("/self/{hostport}")
	public Response setConfig(@PathParam("hostport") String hostport,
			@Context UriInfo uriInfo, @Context ServletContext servletContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@PUT
	@Path("/jndi/{hostport}")
	public Response setJndiConfig(@PathParam("hostport") String hostport,
			@Context UriInfo uriInfo, @Context ServletContext servletContext) {
		EJBLocator.setJndiHostPort(hostport);
		return Response.ok().build();
	}

}
