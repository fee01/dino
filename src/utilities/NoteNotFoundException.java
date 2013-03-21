package utilities;

public class NoteNotFoundException extends NoteBookException
{
	
	private static final long serialVersionUID = 213422030797062209L;

	public NoteNotFoundException()
	{
		super();
	}
	
	public NoteNotFoundException(String message, Throwable fault)
	{
		super(message, fault);
	}
	
	public NoteNotFoundException(String message)
	{
		super(message);
	}
	
	public NoteNotFoundException(Throwable fault)
	{
		super(fault);
	}

}
