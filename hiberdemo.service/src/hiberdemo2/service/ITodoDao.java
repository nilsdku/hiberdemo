package hiberdemo2.service;

import hiberdemo.model.Group;
import hiberdemo.model.Todo;
import java.util.List;

public interface ITodoDao
{
	void saveTodos(Todo... todos);

	void saveGroups(Group... groups);

	List<Todo> getTodos();

	List<Group> getGroups();
}
