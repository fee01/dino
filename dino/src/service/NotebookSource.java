package service;

import java.util.ArrayList;
import java.util.List;

import implementation.Directory;
import implementation.Note;
import implementation.Notebook;
import implementation.NotebookList;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import utilities.NoteBookException;
import utilities.NotebookAlreadyExistsException;
import utilities.NotebookExceptionMapper;





@Path("")
public class NotebookSource 
{
	
	private Directory db;
	
	public NotebookSource()
	{
		this.db = Directory.getDatabase();
	}
	
	

	
	@GET
	@Path("")
	@Produces("text/xml")
	public Response getAllNoteBooksFromServer()
	{
		NotebookList nbList = new NotebookList();
		nbList.setNotebooks(db.getAllNotebooks());
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
		NotebookList nbList = new NotebookList();
		nbList.setNotebooks(db.getAllNotebooks());
		return Response.ok(nbList).build();
	}
	
	@GET
	@Path("/notebook/{notebookId}")
	@Produces("text/xml")
	public Response getNoteBookFromId(@PathParam("notebookId") String id)
	{	
				
		Notebook nb = new Notebook();
		nb = db.getNotebook(id);		
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
		Notebook nb = db.getNotebook(nbId);
		try
		{
			for(Note note : nb.getNotes())
			{
				if(note.getId() == Integer.parseInt(noteId))
				{
					return Response.ok(note).build();
				}
			}
			return Response.status(Response.Status.NOT_FOUND)
			.entity("Request failed: no note with that Id").type("text/plain").build();
							
		}
		catch(NumberFormatException nnfex)
		{
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("request failed: " + nnfex.getMessage()).type("text/plain").build();
		}
		catch(NullPointerException npex)
		{
			return Response.status(Response.Status.NOT_FOUND)
					.entity("request failed: " + npex.getMessage()).type("text/plain").build();
		}
		
	}
	
	
	
	@POST
	@Path("/notebook")
	@Consumes("text/xml")
	public Response createNotebook(String title)
	{
		try
		{
			
			db.createNotebook(title);
			return Response.ok("Notebook: " + title + ", was added to the Database").build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}
		
		
	}
	
	//adds notebook using xml notebook at path 8080/dino/
	@POST
	@Consumes("text/xml")
	public Response addNotebook(Notebook nb)
	{
		try
		{
			//String id =
			db.createNotebook(nb.getTitle());
			//Notebook nb = directoryManager.findById(id);
			//return Response.ok("Notebook: " + nb.getTitle() + ", was added to the Database").build();
			return Response.ok("Notebook: " + nb.getTitle() + ", was added to the Database").build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}
	}

	

}
