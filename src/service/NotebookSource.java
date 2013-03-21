package service;

//import java.util.ArrayList;
//import java.util.List;

import implementation.Directory;
import implementation.Note;
import implementation.Notebook;
import implementation.NotebookList;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import utilities.BadRequest;
import utilities.NoteBookException;
import utilities.NoteNotFoundException;
//import utilities.NotebookAlreadyExistsException;
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
		if(id.isEmpty() || id.trim().isEmpty() || id == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);			
		}
		
		Notebook nb = new Notebook();
		
		try 
		{
			nb = db.getNotebook(id);	
			return Response.ok(nb).build();
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}
			
				
	
		
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
		Notebook nb = null;
		try 
		{  
			nb = db.getNotebook(nbId); 
	     
			try
			{
				for(Note note : nb.getNotes())
				{
					if(note.getId().equals(noteId))
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
		catch(NoteBookException exception)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
	}
	
	
	
	//Below deals with creation Posts/Puts
	
	@POST
	@Path("/notebook")
	@Consumes("text/xml")
	public Response createNotebook(Notebook nb)
	{
		if(nb.getTitle().length() < 1 || nb.getTitle() == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			
			db.createNotebook(nb.getTitle());
			return Response.status(Status.CREATED).build();
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
		if(nb.getTitle().length() < 1 || nb.getTitle() == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			//String id =
			db.createNotebook(nb.getTitle());
			//Notebook nb = directoryManager.findById(id);
			//return Response.ok("Notebook: " + nb.getTitle() + ", was added to the Database").build();
			return Response.status(Status.CREATED).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}
	}

	/*
	//Instead of just taking in String of content use XML note creation 
	@POST
	@Path("/notes/{notebookId}")
	@Consumes("text/xml")
	@Produces(MediaType.TEXT_XML)
	public Response addNoteToNotebook(@PathParam("notebookId") String nbId, String noteContent)
	{
		if(noteContent == null || noteContent.trim().isEmpty() || noteContent.isEmpty())
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			Notebook nb = db.getNotebook(nbId);			
			Note nt = new Note();
			nt.setContent(noteContent);
			nb.addNote(nt);
			db.updateNotebookDatabase(nb.getId(), nb);
			return Response.ok(nb).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}		
	}
	*/
	
	@POST
	@Path("/notes/{notebookId}")
	@Consumes("text/xml")
	@Produces(MediaType.TEXT_XML)
	public Response addNoteToNotebook(@PathParam("notebookId") String nbId, Note note)
	{
		
		String noteContent = note.getContent();
		if(noteContent == null || noteContent.trim().isEmpty() || noteContent.isEmpty())
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			Notebook nb = db.getNotebook(nbId);			
			nb.addNote(note);
			db.updateNotebookDatabase(nb.getId(), nb);
			return Response.status(Status.CREATED).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}		
	}
	
	
	@PUT
	@Path("/notes/{notebookId}/{noteId}")
	@Consumes("text/xml")
	@Produces(MediaType.TEXT_XML)
	public Response putNoteToNotebook(@PathParam("notebookId") String nbId, @PathParam("noteId") String nId, Note note)
	{
		
		String noteContent = note.getContent();
		if(noteContent == null || noteContent.trim().isEmpty() || noteContent.isEmpty())
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			Notebook nb = db.getNotebook(nbId);			
			Note found = nb.getNote(nId);
			found.setContent(note.getContent());
			db.updateNotebookDatabase(nb.getId(), nb);
			return Response.ok(nb).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}		
	}
	
	
	
	//Below deals with deleting items
	
	@DELETE
	@Path("/notebook/{notebookId}")
	@Produces("text/xml")
	public Response deleteNotebookWithId(@PathParam("notebookId") String id)
	{
		if(id.isEmpty() || id.trim().isEmpty() || id == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);			
		}
		
		try 
		{
			db.deleteNotebook(id);	
			return Response.ok().build();
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}
		
	}
	
	
	@DELETE
	@Path("/notes/{notebookId}/{noteId}")
	@Produces("text/xml")
	public Response deleteNoteFromId(@PathParam("notebookId") String nbId, @PathParam("noteId") String noteId)
	{		
		if(noteId == null || noteId.trim().isEmpty() || noteId.isEmpty())
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(new BadRequest());
		}
		Notebook nb = null;
		try 
		{
			nb = db.getNotebook(nbId); 
			
			if(nb.deleteNote(noteId))
			{
				db.updateNotebookDatabase(nb.getId(), nb);
				return Response.ok("Note: " + noteId + 
						", was deleted from Notebook " + nb.getId()).build();
			}
			
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(new NoteNotFoundException());
		}
		catch(NoteBookException exception)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
	}

	

	
}