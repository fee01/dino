package utilities;

public class NotebookNotFoundException extends Exception
{
	private static final long serialVersionUID = -3946465825945565956L;

	public NotebookNotFoundException()
	{
		super();
	}
	
	public NotebookNotFoundException(String message, Throwable fault)
	{
		super(message, fault);
	}
	
	public NotebookNotFoundException(String message)
	{
		super(message);
	}
	
	public NotebookNotFoundException(Throwable fault)
	{
		super(fault);
	}
}
