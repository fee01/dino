package utilities;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;



@Provider
public class NotebookExceptionMapper 
{

		public Response toResponse(NoteBookException me) 
		{
			String summary = "unspecified problem";
			Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
			if( me instanceof NotebookNotFoundException ) 
			{
				summary = "not found";
				status = Response.Status.NOT_FOUND;
			} 
			else if( me instanceof NotebookAlreadyExistsException ) 
			{
				summary = "Notebook already exists";
				status = Response.Status.CONFLICT;
			}
			else if( me instanceof BadRequest)
			{
				summary = "Bad Request";
				status = Response.Status.BAD_REQUEST;
			}
			else if( me instanceof NoteNotFoundException)
			{
				summary = "not found";
				status = Response.Status.NOT_FOUND;
			}
			return Response.status(status)
					.entity("request failed: " + summary + ": " + me.getMessage()).type("text/plain").build();
		}
}


