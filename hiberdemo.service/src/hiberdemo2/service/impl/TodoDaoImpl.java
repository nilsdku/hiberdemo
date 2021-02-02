package hiberdemo2.service.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import hiberdemo.model.Group;
import hiberdemo.model.Todo;
import hiberdemo2.service.ITodoDao;

public class TodoDaoImpl implements ITodoDao
{

	private EntityManager m_em;

	public TodoDaoImpl(EntityManager a_em)
	{
		m_em = Objects.requireNonNull(a_em);
	}
	
	@Override
	public List<Todo> getTodos() 
	{
//		TypedQuery<Todo> query = a_em.createQuery("select t from Todo t", Todo.class);
		
		CriteriaBuilder cb = m_em.getCriteriaBuilder();
		CriteriaQuery<Todo> cq = cb.createQuery(Todo.class);
		Root<Todo> rootTodo = cq.from(Todo.class);
		CriteriaQuery<Todo> allTodos = cq.select(rootTodo);
		TypedQuery<Todo> query = m_em.createQuery(allTodos);
		
		return query.getResultList();
	}

	@Override
	public void saveTodos(Todo... todos)
	{
		m_em.getTransaction().begin();
		
		for(Todo todo : todos)
			m_em.persist(todo);
		
		m_em.getTransaction().commit();
	}

	public void saveGroups(Group...groups)
	{
		m_em.getTransaction().begin();
		
		for(Group group : groups)
			m_em.persist(group);
		
		m_em.getTransaction().commit();
	}

	@Override
	public List<Group> getGroups()
	{
		return m_em.createQuery("select g from Group g", Group.class).getResultList();
	}
	
}
