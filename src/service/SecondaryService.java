package service;

import implementation.NotebookXML;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import proxy.ClientProxy;

import resource.SecondaryResource;
import utilities.EJBLocator;


import com.sun.jersey.spi.resource.Singleton;
import dino.api.Notebook;
import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;
import db.NotebookServerDatabase;



@Singleton
@Path("/secondary")
public class SecondaryService implements SecondaryResource {
	
	private static final NotebookSource notebookService = new NotebookSource();
	
	//@Override
	public Response createSecondaryNotebook(String nbid, UriInfo uriInfo, ServletContext servletContext) throws NotebookNotFoundException, NotebookAlreadyExistsException {
		

		NotebookXML xmlNotebook = notebookService.findLocalNotebook(nbid);
		if (xmlNotebook == null) {
			Notebook notebook = EJBLocator.getInstance().getDirectoryService().getNotebook(nbid);
			if (notebook == null ) {
				throw new NotebookNotFoundException();
			}
			
			xmlNotebook = new NotebookXML(notebook);
		}
		
		if (xmlNotebook.isPrimary(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
			throw new NotebookAlreadyExistsException();
		}
		
		for (String url : xmlNotebook.getSecondaryNotebookUrl()) {
			if (url.equalsIgnoreCase(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
				
				throw new NotebookAlreadyExistsException();
			}
		}

		ClientProxy.updateSecondaryForNotebook(nbid, xmlNotebook.getPrimaryURL(), ConfigSource.getServiceUrl(uriInfo, servletContext));
		return Response.ok(xmlNotebook, "text/xml").build();
	}

	//@Override
	public Response deleteSecondaryNotebook(String nbid, UriInfo uriInfo, ServletContext servletContext)
			throws NotebookNotFoundException, NotebookAlreadyExistsException {
		


		NotebookXML xmlNotebook = notebookService.findLocalNotebook(nbid);
		if (xmlNotebook == null) {
			throw new NotebookNotFoundException();
		}
		
		if (xmlNotebook.isPrimary(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
			throw new NotebookAlreadyExistsException();
		}
		
		NotebookServerDatabase.getDatabase().remove(nbid);
		ClientProxy.deleteSecondaryForNotebook(nbid, xmlNotebook.getPrimaryURL(), ConfigSource.getServiceUrl(uriInfo, servletContext));
		return Response.ok(xmlNotebook, "text/xml").build();
	}

}
