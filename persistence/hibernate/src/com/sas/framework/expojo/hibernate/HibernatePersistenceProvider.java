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
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.expojo.ModelExposerFactory;

// -[KeepBeforeClass]-
import org.hibernate.*;


// -[Class]-

/**
 * Class Name : HibernatePersistenceProvider
 * Diagram    : exPOJO Hibernate specific classes
 * Project    : Untitled Project
 * Type       : concrete
 * Provides persistence using Hibernate.
 * 
 * @author Chris Colman
 */
public 
class HibernatePersistenceProvider extends PersistenceProvider
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Hibernate session.
 */
protected transient Session session;



/**
 * Hibernate session factory.
 */
protected transient SessionFactory sessionFactory;



/**
 * The current transaction for the session, null if no transaction is currently associated
 * with the session.
 */
protected transient Transaction transaction;


// -[Methods]-

/**
 * Returns a detached copy of the given persistent object.
 */
public Object detachCopy(Object po)
{
	// Doesn't seem to be a detach in Hibernate. 
	// Objects become detached automatically when their session is closed
	return po;
}

/**
 * Reattaches the given unattached object to the PersistenceProvider
 */
public Object attach(Object object)
{
	return session.merge(object);
}

/**
 * Flushes all object modifications to SQL (as yet uncommitted) that makes newly created
 * objects and changes available to DB queries within the current transaction.
 */
public void flush()
{
	session.flush();
}

/**
 * Refresh all transactional instances - only has effect if within the context of an
 * open transaction.
 * WARNING: Could not find a method that performs a refresh on all objects in Hibernate
 * so this method currently does nothing. If there is one please let us know.
 */
public void refreshAll()
{
	//session.refresh(); - can't find a refreshAll equivalent in Hibernate
}




/**
 * Refresh the given object - only has effect if within the context of an open transaction.
 */
public void refresh(Object o)
{
	session.refresh(o);
}

/**
 * Evicts all objects from the PersistenceManager's cache.
 */
public void evictAll()
{
	session.clear();
}

/**
 * Evicts all objects from the PersistenceProvider's (Session) cache.
 */
public void evict(Object po)
{
	session.evict(po);
}




/**
 * Returns true if the PersistenceProvider has an active transaction.
 */
public boolean hasActiveTx()
{
	return transaction != null;
}

/**
 * Sets transaction
 */
public void setTransaction(Transaction transaction)
{
    this.transaction = transaction;
}

/**
 * Returns transaction
 */
public Transaction getTransaction()
{
    return transaction;
}

/**
 * Sets session
 */
public void setSession(Session session)
{
    this.session = session;
}

/**
 * Returns session
 */
public Session getSession()
{
    return session;
}

/**
 * Sets sessionFactory
 */
public void setSessionFactory(SessionFactory sessionFactory)
{
    this.sessionFactory = sessionFactory;
}

/**
 * Returns sessionFactory
 */
public SessionFactory getSessionFactory()
{
    return sessionFactory;
}

/**
 * Opens a persistence provider.
 */
public void open()
{
	session = sessionFactory.openSession();
}

/**
 * Processes an exception thrown while in a transaction. The exception could be relevent
 * to the persistence technology or a generic RuntimeException. If it's an optimistic
 * verification exception (optimistic locking check failed) then no exception is thrown
 * and the persistence technology may or may not need clean. In the case of JDO a rollback
 * as already been performed.
 */
public RuntimeException processException(RuntimeException e)
{
	if ( e instanceof TransactionException )
	{
		// Not quite sure if this is the right transaction thrown
		// when an optimistic lock is performed. From the doco it looks
		// like it might also be thrown for other transaction problems
		// as well
		System.out.println("HibernatePersistenceProvider: Rolling back and retrying optimistic txn.");
		e.printStackTrace();

		rollbackTx();

		return (TransactionException)e;
	}	
	else 
	{
		System.out.println("HibernatePersistenceManager Exception caught: " + e);
		e.printStackTrace();
		
		rollbackTx();
		
		// no other exception can be handled here so rethrow it
		throw e;
	}
}

/**
 * Closes the persistence provider. It can no longer be used after being closed.
 */
public void close()
{
	if ( depth != 0 )
	{
		System.out.println("close PersistenceProvider but depth is not 0. depth=" + depth + ", Ending transaction");
	}

	if ( session != null )
	{
		session.close();
		session = null;
	}
}

/**
 * Deletes the persistent object from the datastore.
 */
public void delete(Object object)
{
	// only delete already saved objects otherwise there could be a problem
	// if ( !session.isSaved(object) ) <-- only implemented in SessionImpl - is it always
	// save to downcast to a SessionImpl object or not?
		session.delete(object);
}




/**
 * Makes the given object persistent.
 */
public Object persist(Object object)
{
	// make object persistent
	session.save(object);	
	return object;
}

/**
 * Rolls back a transaction.
 */
public void rollbackTx()
{
	if ( modelExposer.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____rollbackTx____" + "Tx active: " + (transaction != null));

	if ( --depth < 0 )
	{
		System.out.println("Rollback: Illegal depth " + depth);
		Thread.currentThread().dumpStack();
	}

	if ( depth == 0 )
	{
		if ( transaction != null  )
		{
			transaction.rollback();
			transaction = null;
		}
	}
}

/**
 * Begins a transaction.
 */
public void beginTx()
{
	if ( modelExposer.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____beginTx____ Tx active: " + (transaction != null));

	if ( depth++ == 0 )
	{
		transaction = session.beginTransaction();
	}
}

/**
 * Commits a transaction.
 */
public void commitTx()
{
	if ( modelExposer.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____commitTx____" + "Tx active: " + (transaction != null));

	if ( depth < 1 )
	{
		System.out.println("Commit: Illegal depth " + depth);
		Thread.currentThread().dumpStack();
	}
		
	if ( depth == 1 )
	{
		if ( transaction != null  )
		{
			transaction.commit();
			transaction = null;
		}
	}
	
	// only decrement AFTER the commit in case an exception is thrown - the rollback
	// will then do the decrement
	depth--;
}

/**
 * Constructs the object giving it a SessionFactory so that it can make it's own Session(s).
 */
public HibernatePersistenceProvider(SessionFactory iSessionFactory)
{
	sessionFactory = iSessionFactory;
	session = null;
	transaction = null;
}

}


