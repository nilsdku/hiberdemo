package hiberdemo2.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import hiberdemo2.service.impl.TodoDaoImpl;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator
{

	// The plug-in ID
	public static final String			PLUGIN_ID	= "hiberdemo2";	//$NON-NLS-1$

	private static EntityManagerFactory	m_emf;
	private static EntityManager		m_em;

	private static ITodoDao	m_dao;

	@Override
	public void start(BundleContext a_context) throws Exception
	{
		createEntityManagerFactory(a_context);

		m_dao = new TodoDaoImpl(getEntityManager());
	}

	private void createEntityManagerFactory(BundleContext a_context)
	{
		ServiceReference<PersistenceProvider> service_reference = a_context
				.getServiceReference(PersistenceProvider.class);

		PersistenceProvider provider = a_context.getService(service_reference);

		m_emf = provider.createEntityManagerFactory("h2-hibernate", null);
	}

	@Override
	public void stop(BundleContext a_context) throws Exception
	{
		if(m_em != null)
			m_em.close();

		if(m_emf != null)
			m_emf.close();
	}

	public static EntityManager getEntityManager()
	{
		if(m_em == null && m_emf != null)
			m_em = m_emf.createEntityManager();

		return m_em;
	}

	public static ITodoDao getTodoDao()
	{
		return m_dao;
	}
}
