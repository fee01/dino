package implementation;

import java.io.Serializable;
import java.util.ArrayList;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;




/**
 * Notebook with unique String id, unique String title and Notes associated with it. 
 * @author  JFee
 */
@XmlRootElement (name="notebook")
@XmlType (name="notebook", propOrder={"id", "title", "notes"})
public class Notebook implements Serializable
{
	private static final long serialVersionUID = 8018457530676416928L;
	private String id;
	private ArrayList<Note> notes;
	private String title;
	private String primaryURL;
	private String secondaryURL;
	
	
	
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
	public void setNotes(ArrayList<Note> notes)
	{
		this.notes = notes;
	}
	
	
	@XmlElement (name="title")
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
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
	
	

}
