package service;

import java.util.ArrayList;
import java.util.List;

import factory.DirectoryManager;
import factory.Factory;
import implementation.Directory;
import implementation.Note;
import implementation.Notebook;
import implementation.NotebookList;


import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import utilities.NotebookNotFoundException;





//@Path("notebook")

@Path("")
public class NotebookSource 
{
	DirectoryManager directoryManager;
	
	private DirectoryManager db;
	
	public NotebookSource(@Context ServletConfig config)
	{
		directoryManager = new Factory(config).getDirectoryManager();
		
		//this.db = Directory.getDatabase();
	}
	
	

	
	@GET
	@Produces("text/xml")
	public Response getAllNoteBooksFromServer()
	{
		NotebookList nbList = new NotebookList();
		nbList.setNotebooks(directoryManager.getAllNotebooks());
		//nbList.setNotebooks(db.getAllNotebooks());
		return Response.ok(nbList).build();
	}
	
	@GET
	@Path("/all")
	@Produces("text/xml")
	public Response getAllNoteBooksFromAllServers()
	{
		return getAllNoteBooksFromServer();
	}
	
	@GET
	@Path("/notebook")
	@Produces("text/xml")
	public Response getAllNoteBooksOnCurrentServer()
	{
		return getAllNoteBooksFromServer();
	}
	
	@GET
	@Path("/notebook/{notebookId}")
	@Produces("text/xml")
	public Response getNoteBookFromId(@PathParam("notebookId") String id)
	{	
		NotebookList nbList = new NotebookList();		
		Notebook nb = new Notebook();
		nb = directoryManager.getNotebook(id);		
		//nbList.setNotebooks(db.getAllNotebooks());
		return Response.ok(nb).build();
	}
	
	@GET
	@Path("/notes/{notebookId}")
	@Produces("text/xml")
	public Response getNotesFromNoteBookId(@PathParam("notebookId") String id)
	{			
		return getNoteBookFromId(id);
	}
	
	@GET
	@Path("/notes/{notebookId}/{noteId}")
	@Produces("text/xml")
	public Response getNoteFromId(@PathParam("notebookId") String nbId, @PathParam("noteId") String noteId)
	{	
		Note note;
		Notebook nb = directoryManager.getNotebook(nbId);
		try
		{			
				return Response.ok(nb.getNoteById(noteId)).build();			
		}
		catch(NotebookNotFoundException nnfex)
		{
			return Response.status(Response.Status.NOT_FOUND)
					.entity("request failed: " + nnfex.getMessage()).type("text/plain").build();
		}
		catch(NullPointerException npex)
		{
			return Response.status(Response.Status.NOT_FOUND)
					.entity("request failed: " + npex.getMessage()).type("text/plain").build();
		}
		
	}
	
	

	

}
