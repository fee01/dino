package utilities;

import implementation.Note;

public class NotebookAlreadyExistsException extends NoteBookException
{

	private static final long serialVersionUID = -5039124568710291971L;

	public NotebookAlreadyExistsException()
	{
		super();
	}
	
	public NotebookAlreadyExistsException(String message, Throwable fault)
	{
		super(message, fault);
	}
	
	public NotebookAlreadyExistsException(String message)
	{
		super(message);
	}
	
	public NotebookAlreadyExistsException(Throwable fault)
	{
		super(fault);
	}
}
