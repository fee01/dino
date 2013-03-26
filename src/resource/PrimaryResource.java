package resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface PrimaryResource {

	@GET
	@Path ("/all")
	@Produces("text/xml")
	public Response getAllNotebooks();
	
	@DELETE
	@Path ("/all")
	public Response deleteAllNotebooks();

}
