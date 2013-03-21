package implementation;

import java.io.Serializable;
import java.util.ArrayList;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import utilities.BadRequest;




/**
 * Notebook with unique String id, unique String title and Notes associated with it. 
 * @author  JFee
 */
@XmlRootElement (name="notebook")
@XmlType (name="notebook", propOrder={"id", "title", "notes"})
public class Notebook implements Serializable
{
	private static final long serialVersionUID = 8018457530676416928L;
	private String id = "";
	private ArrayList<Note> notes = new ArrayList<Note>();
	private String title = "";
	private String primaryURL = "";
	private String secondaryURL = "";
	
	private int noteIdSetter = 0;
	
	@XmlElement (name="id")
	public String getId()
	{
		return id;
	}	
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	@XmlElement (name="note")
	public ArrayList<Note> getNotes()
	{
		return notes;
	}
	public void setNotes(ArrayList<Note> noteList)
	{
		if(noteList == null)
		{
			return;
		}
		
		//reset list and idSetter
		notes = new ArrayList<Note>();
		noteIdSetter = 0;
		
		for(Note n: noteList)
		{
			this.addNote(n);
		}
		
	}
	public void addNote(Note n) 
	{
		
		if(n.getContent().isEmpty())
		{
			return;
			
		}
		
		n.setId(this.setNoteId());
		this.notes.add(n);
		
	}
	public Note getNote(String id)
	{
		
		int location = this.findNote(id);

		return notes.get(location);
	}
	public boolean deleteNote(Note n) 
	{
		int location = this.findNote(n.getId());
		if(location >= 0)
		{
			notes.remove(location);
			return true;
		}
		
			
		return false;	
	}
	public boolean deleteNote(String id)
	{
		int location = this.findNote(id);
		if(location >= 0)
		{
			notes.remove(location);
			return true;
		}
		
		return false;
	}
	
	public int findNote(String id)
	{
		for(int i = 0; i < notes.size(); i++)
		{
			if(notes.get(i).getId().equals(id))
			{
				return i;
			}
			
		}
		
		return -1;
		
		
	}
	
	
	
	@XmlElement (name="title")
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		if(title.charAt(0) == ' ')
		{
			return;
		}
		this.title = title;
	}
	
	@XmlTransient
	public String getPrimaryURL()
	{
		return primaryURL;
	}
	public void setPrimaryURL(String primaryURL)
	{
		this.primaryURL = primaryURL;
	}
	
	@XmlTransient
	public String getSecondaryURL()
	{
		return secondaryURL;
	}
	public void setSecondaryURL(String secondaryURL)
	{
		this.secondaryURL = secondaryURL;
	}
	
	
	private String setNoteId()
	{
		noteIdSetter++;
		return "" + noteIdSetter;
		
		
	}
	

}

	
	

