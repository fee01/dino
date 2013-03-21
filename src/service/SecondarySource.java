package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import implementation.BackupDirectory;
import implementation.Directory;
//import implementation.Note;
import implementation.Notebook;
import implementation.NotebookList;
import resource.SecondaryResource;
import utilities.BadRequest;
import utilities.NoteBookException;
//import utilities.NoteNotFoundException;
import utilities.NotebookExceptionMapper;

@Path("/secondary")
public class SecondarySource implements SecondaryResource
{
	/*
	
	private Directory db;
	private BackupDirectory backup;
	
	public SecondarySource()
	{
		this.db = Directory.getDatabase();
		this.backup = BackupDirectory.getDatabase();
	}
	

	@GET
	@Path("")
	@Produces("text/xml")
	public Response getAllNoteBooksFromServer()
	{
		NotebookList nbList = new NotebookList();
		nbList.setNotebooks(backup.getAllNotebooks());
		return Response.ok(nbList).build();
	}
		
	@GET
	@Path("/{notebookId}")
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
			nb = backup.getNotebook(id);	
			return Response.ok(nb).build();
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}
			
				
	
		
	}
	
	
	//Below deals with creation Posts/Puts
	
	@POST
	@Path("/{notebookId")
	@Consumes("text/xml")
	public Response createNotebook(@PathParam("notebookId") String id)
	{
		if(id.length() < 1 || id == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			Notebook nb = db.findById(id);
			backup.createNotebook(nb);
			return Response.ok("Notebook: " + nb.getTitle() + ", was added to the Database").build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}
		
		
	}
	
	
	
	@DELETE
	@Path("/{notebookId}")
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
			backup.deleteNotebook(id);	
			return Response.ok().build();
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}
		
	}
	*/
	
}
