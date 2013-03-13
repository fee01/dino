package service;

import implementation.Note;
import implementation.Notebook;
import resource.NoteResource;

public class NoteSource implements NoteResource
{
	private Notebook nb;
	private int idSetter = 0;
	
	public NoteSource(Notebook nb)
	{
		this.nb = nb;
	}
	
	public Notebook addNote(String content)
	{
		Integer id = getNoteIdIncrement();
		Note note = new Note();
		note.setContent(content);
		note.setId(id);
		nb.getNotes().add(note);		
		return nb;
		
		
	}
	
	private Integer getNoteIdIncrement()
	{
		
		if(!nb.getNotes().isEmpty())
		{
			Note lastNote = nb.getNotes().get(nb.getNotes().size() - 1);
			return lastNote.getId() + 1;
		}
		else
		{
			return 1;
		}
		
	}
	
}
