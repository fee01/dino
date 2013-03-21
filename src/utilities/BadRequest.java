package utilities;

public class BadRequest extends NoteBookException 
{
	
	
	private static final long serialVersionUID = -7216697577838687982L;

	public BadRequest()
	{
		super();
	}
	
	public BadRequest(String message, Throwable fault)
	{
		super(message, fault);
	}
	
	public BadRequest(String message)
	{
		super(message);
	}
	
	public BadRequest(Throwable fault)
	{
		super(fault);
	}
}


