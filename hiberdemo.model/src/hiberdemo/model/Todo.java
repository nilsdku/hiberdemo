package hiberdemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TODO")
public class Todo
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int		id;

	private String	text;

	@ManyToOne
	private Group	group;

	public int getId()
	{
		return id;
	}

	public void setId(int a_id)
	{
		id = a_id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String a_text)
	{
		text = a_text;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group a_group)
	{
		this.group = a_group;
	}

	@Override
	public String toString()
	{
		return "Todo [id=" + getId() + ", text=`" + getText() + "`]";
	}
}
