// -[KeepHeading]-


// -[Copyright]-

/**
 * 
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.expojo.hibernate;

import java.lang.*;
import com.sas.framework.expojo.ModelExposerFactory;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.hibernate.HibernatePersistenceProvider;


// -[KeepBeforeClass]-
import org.hibernate.*;
import org.hibernate.cfg.*;



// -[Class]-

/**
 * Class Name : HibernatePersistenceProviderFactory
 * Diagram    : exPOJO Hibernate specific classes
 * Project    : Untitled Project
 * Type       : abstract
 * Factory to supply HibernatePersistenceProvider instances as required.
 * 
 * @author Chris Colman
 */
public abstract 
class HibernatePersistenceProviderFactory extends ModelExposerFactory
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * It is expensive to create these so only do it once.
 */
protected transient SessionFactory sessionFactory;


// -[Methods]-

/**
 * Describe here
 */
public void close()
{
	if (sessionFactory != null)
	{
		sessionFactory.close();
		sessionFactory = null;
	}
}

/**
 * Overridden in derived classes to provide PersistenceProvider appropriate to the persistence
 * technology used.
 */
public PersistenceProvider createPersistenceProvider()
{
	if ( sessionFactory == null )
	{
		try
		{
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable ex)
		{
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	return new HibernatePersistenceProvider(sessionFactory);
}

}


