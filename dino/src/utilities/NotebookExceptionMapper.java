package utilities;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class NotebookExceptionMapper {

		public Response toResponse(NoteBookException me) {
			String summary = "unspecified problem";
			Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
			if( me instanceof NotebookNotFoundException ) {
				summary = "not found";
				status = Response.Status.NOT_FOUND;
			} else if( me instanceof NotebookAlreadyExistsException ) {
				summary = "Notebook already exists";
				status = Response.Status.BAD_REQUEST;
			}
			return Response.status(status)
					.entity("request failed: " + summary + ": " + me.getMessage()).type("text/plain").build();
		}
}

