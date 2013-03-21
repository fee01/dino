package resource;

import javax.servlet.ServletContext;
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

import utilities.NotebookAlreadyExistsException;
import utilities.NotebookNotFoundException;

import implementation.Notebook;

import com.sun.jersey.api.NotFoundException;

public interface NotebookResource
{
	@GET
    public Response getAllNoteBooksFromServer();
    
    
}
