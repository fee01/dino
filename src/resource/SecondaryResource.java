package resource;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;




public interface SecondaryResource {

	@POST
	@Path ("/{notebookId}")
	public Response createSecondaryNotebook(@PathParam("notebookId") String nbid, @Context UriInfo uriInfo, @Context ServletContext servletContext) throws NotebookNotFoundException, NotebookAlreadyExistsException; 

//	@PUT
//	@Path ("/{notebookId}")
//	@Consumes
//	public Response createSecondaryNotebookPut(@PathParam("notebookId") String nbid)throws ParsingException {
//		return createSecondaryNotebook(nbid);
//	}
	
	@DELETE
	@Path ("/{notebookId}")
	public Response deleteSecondaryNotebook(@PathParam("notebookId") String nbid, @Context UriInfo uriInfo, @Context ServletContext servletContext) throws NotebookNotFoundException, NotebookAlreadyExistsException; 

}
