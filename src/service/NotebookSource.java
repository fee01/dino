package service;

//import java.util.ArrayList;
//import java.util.List;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import implementation.Note;
import implementation.NotebookXML;
import implementation.NotebookList;
import implementation.XmlSecondary;


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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import proxy.ClientProxy;
import resource.NotebookResource;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.resource.Singleton;

import db.NotebookServerDatabase;

import utilities.BadAddressException;
import utilities.BadRequest;
import utilities.EJBLocator;
import utilities.NoteBookException;
import utilities.NoteNotFoundException;

//import utilities.NotebookAlreadyExistsException;
import utilities.NotebookExceptionMapper;


import dino.api.Directory;
import dino.api.Notebook;
import dino.api.NotebookAlreadyExistsException;
import dino.api.NotebookNotFoundException;








@Singleton
@Path("/notebook")
public class NotebookSource extends BaseResponseBuilder implements NotebookResource
{
	/*
	private NotebookServerDatabase db;
	
	public NotebookSource()
	{
		db = NotebookServerDatabase.getDatabase();
	}
	*/
	
	/*
	private Directory directory;
	
	public NotebookSource()
	{
		this.directory = EJBLocator.getInstance().getDirectoryService();
		//this.db = Directory.getDatabase();
	}*/
	
	
	
	@Override
	public Response getAllNoteBooksFromServer() 
	{
		
		NotebookServerDatabase db = NotebookServerDatabase.getDatabase();
		List<NotebookXML> all = db.findAll();
		
		List<NotebookXML> result = new ArrayList<NotebookXML>();
		for (NotebookXML xmlNotebook : all) {
			NotebookXML nb = new NotebookXML();
			nb.setId(xmlNotebook.getId());
			nb.setTitle(xmlNotebook.getTitle());
			result.add(nb);
		}

		NotebookList notebookList = new NotebookList();
		notebookList.setNotebooks(result);
		
		return Response.ok(notebookList, "text/xml").build();
	}
	
	/*
	@GET
	@Path("")
	@Produces("text/xml")
	public Response getAllNoteBooksFromServer()
	{
		NotebookList nbList = new NotebookList();
		List<NotebookXML> listNBXML = new ArrayList<NotebookXML>();
		//ArrayList<Notebook> dnotes = ;
		for(Notebook nbs : directory.getAllNotebooks())
		{
			listNBXML.add(new NotebookXML(nbs));
			//NotebookXML nbXML = 
		}
		nbList.setNotebooks(listNBXML);
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
		nbList.setNotebooks(NotebookServerDatabase.getDatabase().findAll());
		/*List<NotebookXML> listNBXML = new ArrayList<NotebookXML>();
		//ArrayList<Notebook> dnotes = ;
		for(Notebook nbs : directory.getAllNotebooks())
		{
			listNBXML.add(new NotebookXML(nbs));
			//NotebookXML nbXML = 
		}
		nbList.setNotebooks(listNBXML);
		return Response.ok(nbList).build();
	}*/
	
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
		
		
		NotebookXML nb = this.findLocalNotebook(id);
		if(nb == null)
		{
			NoteBookException exception = new NoteNotFoundException();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		else
		{
			return Response.ok(nb).build();
		}
		
		
		/*try 
		{
			
			//nb = directory.getNotebook(id);
			
			
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}*/
			
				
	
		
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
		NotebookXML nb = null;
		 
			nb = this.findLocalNotebook(nbId);
			//nb = directory.getNotebook(nbId); 
	     
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
		
		
	
	
	
	
	//Below deals with creation Posts/Puts
	
	@Override
	public Response createNotebook(NotebookXML nbXML, UriInfo uriInfo, ServletContext servletContext) throws NotebookAlreadyExistsException
	{		

		try {
			NotebookServerDatabase db = NotebookServerDatabase.getDatabase();
			if (db.findByTitle(nbXML.getTitle()) != null) {
				throw new NotebookAlreadyExistsException();
			}

			Directory directory = EJBLocator.getInstance().getDirectoryService();
			String primaryUrl = ConfigSource.getServiceUrl(uriInfo, servletContext);
			String id = directory.createNotebook(nbXML.getTitle(), primaryUrl);
			nbXML.setId(id);
			nbXML.setPrimaryURL(primaryUrl);
			nbXML.setSecondaryNotebookUrl(new ArrayList<String>());

			db.add(id, nbXML);
		} catch (Exception e) {
			// no op
		}
		

		return Response.ok(nbXML, "text/xml").build();
			  
	}
	/*
	public Response createNotebook(NotebookXML nb)
	{
		
		try {
			NotebookServerDatabase db = NotebookServerDatabase.getDatabase();
			if (db.findByTitle(NotebookXML.getTitle()) != null) {
				throw new NotebookAlreadyExistsException();
			}

			Directory directory = EJBLocator.getInstance().getDirectoryService();
			String primaryUrl = ConfigSource.getServiceUrl(uriInfo, servletContext);
			String id = directory.createNotebook(xmlNotebook.getTitle(), primaryUrl);
			xmlNotebook.setId(id);
			xmlNotebook.setPrimaryNotebookUrl(primaryUrl);
			xmlNotebook.setSecondaryNotebookUrl(new ArrayList<String>());

			db.add(id, xmlNotebook);
		} catch (BadAddressException e) {
			logger.error(e);
		}

		return Response.ok(xmlNotebook, "text/xml").build();
		
	
	}*/
	
	/*
	//adds notebook using xml notebook at path 8080/dino/
	@POST
	@Consumes("text/xml")
	public Response addNotebook(NotebookXML nb)
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
			directory.createNotebook(nb.getTitle());
			//Notebook nb = directoryManager.findById(id);
			//return Response.ok("Notebook: " + nb.getTitle() + ", was added to the Database").build();
			return Response.status(Status.CREATED).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}
	}*/

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
	public Response addNoteToNotebook(@PathParam("notebookId") String nbId, Note note, UriInfo uriInfo, HttpServletResponse response, ServletContext servletContext)
	{
		
		NotebookServerDatabase db = NotebookServerDatabase.getDatabase();
		NotebookXML nbXML = this.findLocalNotebook(nbId);
		
		if (nbXML == null) {
			Notebook notebook = EJBLocator.getInstance().getDirectoryService().getNotebook(nbId);
			if (notebook == null) {
				try {
					throw new NotebookNotFoundException();
				} catch (NotebookNotFoundException e) {
					
					NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
					return neMapper.toResponse(new NoteBookException(e));
				}
			}
			nbXML = new NotebookXML(notebook);
		}

		if (!nbXML.getPrimaryURL().equalsIgnoreCase(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
			StringBuffer urlString = new StringBuffer(ClientProxy.getUrlString(nbXML.getPrimaryURL(), "notes"));
			urlString.append("/");
			urlString.append(nbId);
			
			try {
				Client client = Client.create();
				WebResource resource = client.resource(urlString.toString());
				Note newNote = resource.entity(note).type("text/xml").post(Note.class);
				return Response.ok(newNote, "text/xml").build();
			} catch (UniformInterfaceException e) {
				
				return Response.status(e.getResponse().getStatus()).build();
			}

		}
		else {
			synchronized (nbXML) {
				String noteid = db.getNextId();
				
	
				note.setId(noteid);
				if (nbXML.getNotes() == null) {
					nbXML.setNotes(new ArrayList<Note>());
				}
				nbXML.getNotes().add(note);
			}
			this.processSecondary(false, nbXML, response);
			return Response.ok(note, "text/xml").build();
		}
	}
		/*
		String noteContent = note.getContent();
		if(noteContent == null || noteContent.trim().isEmpty() || noteContent.isEmpty())
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			NotebookXML nb = this.findLocalNotebook(nbId);			
			nb.addNote(note);
			
			//directory.updateNotebookDatabase(nb.getId(), nb);
			return Response.status(Status.CREATED).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}		*/
	
	
	
	@PUT
	@Path("/notes/{notebookId}/{noteId}")
	@Consumes("text/xml")
	@Produces(MediaType.TEXT_XML)
	public Response putNoteToNotebook(@PathParam("notebookId") String nbId, @PathParam("noteId") String nId, Note note, UriInfo uriInfo, HttpServletResponse response,
			ServletContext servletContext)
	{
		
		
		NotebookXML xmlNotebook = this.findLocalNotebook(nbId);
		if (xmlNotebook == null) {
			Notebook notebook = EJBLocator.getInstance().getDirectoryService().getNotebook(nbId);
			if (notebook == null) {
				try {
					throw new NotebookNotFoundException();
				} catch (NotebookNotFoundException e) {
					NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
					return neMapper.toResponse(new NoteBookException(e));
				}
			}
			xmlNotebook = new NotebookXML(notebook);
		}

		if (!xmlNotebook.getPrimaryURL().equalsIgnoreCase(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
			StringBuffer urlString = new StringBuffer(ClientProxy.getUrlString(xmlNotebook.getPrimaryURL(), "notes"));
			urlString.append("/");
			urlString.append(nbId);
			urlString.append("/");
			urlString.append(nId);

			
			Client client = Client.create();
			WebResource resource = client.resource(urlString.toString());
			try {
				resource.entity(note).type("text/xml").put();
				return Response.ok().build();
			} catch (UniformInterfaceException e) {
				return Response.status(e.getResponse().getStatus()).build();
			}
		} else {
			ArrayList<Note> notes = xmlNotebook.getNotes();
			for (Note xmlNote : notes) {
				if (xmlNote.getId().equalsIgnoreCase(nId)) {
					xmlNote.setContent(note.getContent());
					this.processSecondary(false, xmlNotebook, response);
					return Response.ok().build();
				}
			}
			try
			{
			throw new NotebookNotFoundException();
			}
			catch(NotebookNotFoundException x)
			{
				NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
				return neMapper.toResponse(new NoteBookException(x));
			}
		}
		
		/*
		String noteContent = note.getContent();
		if(noteContent == null || noteContent.trim().isEmpty() || noteContent.isEmpty())
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);
		}
		
		try
		{
			NotebookXML nb = directory.getNotebook(nbId);			
			Note found = nb.getNote(nId);
			found.setContent(note.getContent());
			directory.updateNotebookDatabase(nb.getId(), nb);
			return Response.ok(nb).build();
		}
		catch(NoteBookException ex)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
					
		}	*/	
	}
	
	
	
	//Below deals with deleting items
	
	@DELETE
	@Path("/notebook/{notebookId}")
	@Produces("text/xml")
	public Response deleteNotebookWithId(@PathParam("notebookId") String id, UriInfo uriInfo, ServletContext servletContext, HttpServletResponse response) throws dino.api.NotebookNotFoundException
	{
		try
		{
		NotebookXML xmlNotebook = findLocalNotebook(id);
		if (xmlNotebook == null) {
			
			Notebook notebook = EJBLocator.getInstance().getDirectoryService().getNotebook(id);
			if (notebook == null) {
				
				throw new NotebookNotFoundException();
			}
			xmlNotebook = new NotebookXML(notebook);
		}

		if (!xmlNotebook.isPrimary(ConfigSource.getServiceUrl(uriInfo, servletContext))) {
			
			StringBuffer urlString = new StringBuffer(ClientProxy.getUrlString(xmlNotebook.getPrimaryURL(), "notebook"));
			urlString.append("/");
			urlString.append(id);
			int rc = ClientProxy.processRequest(urlString.toString(), "DELETE", null, response);
			return Response.status(rc).build();
		}

		

		Directory directory = EJBLocator.getInstance().getDirectoryService();
		directory.deleteNotebook(id);

		NotebookServerDatabase.getDatabase().remove(id);

		processSecondary(true, xmlNotebook, response);
		
		
		return Response.ok().build();
		}
		catch(NotebookNotFoundException x)
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(new NoteBookException(x));
		} 
		}
		
		/*
		if(id.isEmpty() || id.trim().isEmpty() || id == null)
		{
			NoteBookException exception = new BadRequest();
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(exception);			
		}
		
		try 
		{
			directory.deleteNotebook(id);	
			return Response.ok().build();
		} 
		catch (NoteBookException ex) 
		{
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(ex);
		}*/
		
	
	
	
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
		NotebookXML nb = null;
		
			//nb = directory.getNotebook(nbId); 
			
			if(nb.deleteNote(noteId))
			{
				//directory.updateNotebookDatabase(nb.getId(), nb);
				return Response.ok("Note: " + noteId + 
						", was deleted from Notebook " + nb.getId()).build();
			}
			
			NotebookExceptionMapper neMapper = new NotebookExceptionMapper();
			return neMapper.toResponse(new NoteNotFoundException());
		}
		



	//@Override
	@GET
	@Path("/{notebookId}")
	@Produces("text/xml")
	public Response getNotebookByID(@PathParam("notebookId") String nbid,
			@Context UriInfo uriInfo, @Context HttpServletResponse response,
			@Context ServletContext servletContext) throws NotFoundException,
			NotebookNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	//@Override
	@POST
	@Path("/secondary/{notebookId}")
	@Consumes
	public Response updateNotebookSecondary(
			@PathParam("notebookId") String notebookId, XmlSecondary secondary,
			@Context UriInfo uriInfo, @Context ServletContext servletContext,
			@Context HttpServletResponse response)
			throws NotebookAlreadyExistsException, NotebookNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	public Response createNotebookPut(NotebookXML xmlNotebook, UriInfo uriInfo, ServletContext servletContext)
			throws NotebookAlreadyExistsException {
		
		return createNotebook(xmlNotebook, uriInfo, servletContext);
	}


	//@Override
	@DELETE
	@Path("/{notebookId}")
	public Response deleteNotebookByID(@PathParam("notebookId") String nbid,
			@Context UriInfo uriInfo, @Context ServletContext servletContext,
			@Context HttpServletResponse response) throws NotFoundException,
			NotebookNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

			
	//find notebook in notebookserver with its id
	public NotebookXML findLocalNotebook(String id) 
	{
		
		NotebookServerDatabase db = NotebookServerDatabase.getDatabase();
		return db.findById(id);
	}
	
	
	
	public void processSecondary(boolean delete, NotebookXML notebookXML, HttpServletResponse response) 
	{
		for (String secondary : notebookXML.getSecondaryNotebookUrl()) 
		{
			
			ClientProxy.sync(secondary, delete, notebookXML.getId(), notebookXML, null);
		}
	}

	
	
	public Response sync(String notebookId, HttpServletRequest request) throws NotebookAlreadyExistsException {
		
		NotebookXML notebookXML;
		try {

			NotebookServerDatabase db = NotebookServerDatabase.getDatabase();

			if (request.getContentLength() == 0) {
				db.remove(notebookId);
			}
			else {
			
				ObjectInputStream oips = new ObjectInputStream(request.getInputStream());
				notebookXML = (NotebookXML) oips.readObject();
				
				db.update(notebookXML.getId(), notebookXML);
				
			}

			
		} catch (IOException e) {
			
			return Response.serverError().build();
		} catch (ClassNotFoundException e) {
			
			return Response.serverError().build();
		} catch (NotebookNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok().build();
	}

	
}
