package implementation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;




/**
 * This is a note implementation for the DiNo project. It has a unique integer id and contains content in the form of a String. 
 * @author  JFee
 */
@XmlRootElement (name="note")
@XmlType ( name="note", propOrder={"id", "content"})
public class Note implements Serializable
{
	private static final long serialVersionUID = 6079846718306792555L;
	private String content;
	private int id;
	
	@XmlElement (name="content")
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	
	@XmlElement (name="id")
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	

}
