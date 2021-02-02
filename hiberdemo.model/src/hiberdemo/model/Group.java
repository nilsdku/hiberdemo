package hiberdemo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUP")
public class Group
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int			id;

	private String		name;

	@OneToMany
	private List<Todo>	todos	= new ArrayList<>();

	public Group()
	{
	}

	public Group(String a_name)
	{
		name = a_name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int a_id)
	{
		id = a_id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String a_name)
	{
		name = a_name;
	}

	public List<Todo> getTodos()
	{
		return todos;
	}

	@Override
	public String toString()
	{
		return "Group [id=" + id + ", name=" + name + "]";
	}
}
