package resource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



import implementation.NotebookXML;
import implementation.XmlSecondary;

import com.sun.jersey.api.NotFoundException;

import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;




public interface NotebookResource {

	@GET
	public Response getAllNoteBooksFromServer();
	
	@GET
	@Path("/{notebookId}")
	@Produces("text/xml")
	public Response getNotebookByID(@PathParam("notebookId") String nbid, @Context UriInfo uriInfo, @Context HttpServletResponse response, @Context ServletContext servletContext) throws NotFoundException, NotebookNotFoundException;
	
	@POST
	@Consumes
	public Response createNotebook(NotebookXML notebook, @Context UriInfo uriInfo, @Context ServletContext servletContext) throws NotebookAlreadyExistsException;

	@POST
	@Path("/secondary/{notebookId}")
	@Consumes
	public Response updateNotebookSecondary(@PathParam("notebookId") String notebookId, XmlSecondary secondary, @Context UriInfo uriInfo, @Context ServletContext servletContext, @Context HttpServletResponse response) throws NotebookAlreadyExistsException, NotebookNotFoundException;

//	@DELETE
//	@Path("/secondary/{notebookId}")
//	@Consumes
//	public Response deleteNotebookSecondary(@PathParam("notebookId") String notebookId, XmlSecondary secondary, @Context UriInfo uriInfo, @Context ServletContext servletContext, @Context HttpServletResponse response) throws NotebookAlreadyExistsException, NotebookNotFoundException;
	
	@POST
	@Path ("/sync/{notebookId}")
	public Response sync(@PathParam("notebookId") String notebookId, @Context HttpServletRequest request) throws NotebookAlreadyExistsException;
	
	@PUT
	@Consumes
	public Response createNotebookPut(NotebookXML notebook, @Context UriInfo uriInfo, @Context ServletContext servletContext) throws NotebookAlreadyExistsException;
	

	@DELETE
	@Path("/{notebookId}")
	public Response deleteNotebookByID(@PathParam("notebookId") String nbid, @Context UriInfo uriInfo, @Context ServletContext servletContext, @Context HttpServletResponse response) throws NotFoundException, NotebookNotFoundException;

}

