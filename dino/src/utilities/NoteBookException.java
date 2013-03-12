package utilities;

public class NoteBookException extends Exception {
	private static final long serialVersionUID = -1379331016337229897L;
	
	public NoteBookException()
	{
		super();		
	}
	public NoteBookException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteBookException(String message) {
		super(message);
	}

	public NoteBookException(Throwable cause) {
		super(cause);
	}
	

}
