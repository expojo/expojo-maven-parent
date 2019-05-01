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
package com.sas.framework.expojo.jdo;

import java.lang.*;
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;
    
import com.sas.framework.expojo.ExpojoContext;
    
import com.sas.framework.expojo.ExpojoContextFactory;

// -[KeepBeforeClass]-
import javax.jdo.*;
import java.util.*;

import org.datanucleus.enhancement.Persistable;


// -[Class]-

/**
 * Class Name : JdoPersistenceProvider
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * Provides facilities for accessing transparent persistence via a JDO compliant persistence
 * engine.
 * 
 * @author Chris Colman
 */
public 
class JdoPersistenceProvider extends PersistenceProvider
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Stores a reference to the persistence manager.
 */
protected transient PersistenceManager pm;



/**
 * JDO persistence manager factory object. Uses this in 'open' to create a persistence
 * manager.
 */
protected PersistenceManagerFactory pmf;



/**
 * A count of the number of JdoPersistenceProviders that have been instantiated but not
 * yet closed.
 */
public static int count = 0;




// -[Methods]-

/**
 * Returns true if the given object is of a class that has been configured to be 'able
 * to be persisted' by whatever the current ORM implementation requires.
 */
public boolean isPersistenceCapable(Object object)
{
	return object != null && object instanceof Persistable;
}

/**
 * Deletes the persistent objects contained in the collection.
 */
public void deleteAll(Collection toDelete)
{
	// Need to make a copy because the provided collection might be the collection implementing a 
	// relationship. As items are deleted they will/might be deleted (depending on how JDO works!)
	// and so we can miss every second item!
	List<Object> list = new ArrayList<>(toDelete);
	
	pm.deletePersistentAll(list);
	
	
}

/**
 * Detects if the given object related directly or indirectly to any attached objects.
 * Typically used when re-detaching an supposedly already detached object graph to ensure
 * that there are no references to attached objects.
 */
public void detectAttachedRelationships(Object o)
{
	if 
	(
		o instanceof 
		org.datanucleus.enhancement.Persistable
//		javax.jdo.spi.PersistenceCapable		
	)
	{
		if (JDOHelper.isDetached(o))
		{
			// Final opportunity to detect attached objects in the object graph that may cause database duplication
			AttachedRelationshipDetector detector = new AttachedRelationshipDetector();
			detector.checkObject
			(
				(
					org.datanucleus.enhancement.Persistable
//					javax.jdo.spi.PersistenceCapable				
				)o
			);
		}
	}
	else
		ExpojoFoundation.sLogWarn("Calling detach on object that doesn't implement Persistable.");
}




/**
 * Static method that returns the numeric portion of the OID for the given object.
 */
public static long _getObjectId(Object o)
{
	Long aLong;

/*	
	org.datanucleus.identity.OIDImpl oid = (org.datanucleus.identity.OIDImpl)JDOHelper.getObjectId(o);
	
	if ( oid != null )
		aLong = (Long)oid.oid;
	else
		aLong = new Long(-1);
*/

	org.datanucleus.identity.DatastoreId oid = (org.datanucleus.identity.DatastoreId)JDOHelper.getObjectId(o);
	
	if (oid != null)
		aLong = (Long)oid.getKeyAsObject();
	else
		aLong = (long)-1;

	return aLong.longValue();
}

/**
 * Returns the object of the given class and oid.
 */
public Object getObject(Class cls, long oid)
{
	String oidString = "" + oid + "[OID]"	+ cls.getName();
//	return pm.getObjectById(new org.datanucleus.identity.OIDImpl(oidString));
	return pm.getObjectById(new org.datanucleus.identity.DatastoreIdImpl(oidString));
}

/**
 * Overriden method that calls a static method to return the numeric portion of the OID
 * for the given object.
 */
public long getObjectId(Object o)
{
	return _getObjectId(o);
}

/**
 * Returns a summary of the statistics regarding the PersistenceProvider. Typically this
 * will be a count of the number of objects it is currently managing in it's L1 cache.
 */
public String getStatistics()
{
	Set set = pm.getManagedObjects();
	
	return "PersistenceProvider: Mgd Objs: " + ((set != null) ? ""+set.size() : "none");
}

/**
 * Returns a detached copy of the given persistent object.
 */
public Object detachCopy(Object po)
{
	if ( JDOHelper.getObjectId(po) != null && !JDOHelper.isDetached(po) && !JDOHelper.isDeleted(po) )
	{
		//System.out.println("Detaching: " + JDOHelper.getObjectId(po));
		return pm.detachCopy(po);
	}
	return po;
}

/**
 * Reattaches the given unattached object to the PersistenceProvider
 */
public Object attach(Object object)
{
	if ( object != null )
	{
		// Not make non persistent objects persistent because user may be editing a new object
		// that they don't want to be persisted just yet
		if ( JDOHelper.getObjectId(object) != null && JDOHelper.isDetached(object) )
		{
			//System.out.println("Attaching: " + JDOHelper.getObjectId(object) + " persistent");
			return pm.makePersistent(object);
		}
	}
	return object;
}

/**
 * Flushes all object modifications to SQL (as yet uncommitted) that makes newly created
 * objects and changes available to DB queries within the current transaction.
 */
public void flush()
{
	pm.flush();
}

/**
 * Refresh all transactional instances - only has effect if within the context of an
 * open transaction.
 */
public void refreshAll()
{
	pm.refreshAll();
}

/**
 * Refresh the given object.
 */
public void refresh(Object o)
{
	pm.refresh(o);
}

/**
 * Evict the given object from the PersistenceManager's cache.
 */
public void evict(Object po)
{
	pm.evict(po);
}

/**
 * Evicts all objects from the PersistenceManager's cache.
 */
public void evictAll()
{
	pm.evictAll();
}




/**
 * Returns true if the PersistenceProvider has an active transaction.
 */
public boolean hasActiveTx()
{
	return pm.currentTransaction().isActive();
}

/**
 * Opens a persistence provider.
 */
public void open()
{
	if ( pm == null )
		pm = pmf.getPersistenceManager();
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
	if ( e instanceof JDOOptimisticVerificationException )
	{
		// According to the latest JDO 2 spec:		
		// If any instance fails the verification, a JDOOptimisticVerificationException
		// is thrown which contains an array of JDOOptimisticVerificationException,
		// one for each instance that failed the verification. The optimistic transaction is failed, and
		// the transaction is rolled back. The definition of 'changed instance' is a JDO
		// implementation choice, but it is required that a field that has been changed to different
		// values in different transactions results in one of the transactions failing.
		System.out.println("JdoPersistenceProvider: Rolling back and retrying optimistic txn.");
		e.printStackTrace();

		// Clears transaction depth back to 0
		clearTx();


		return (JDOOptimisticVerificationException)e;
	}	
	else 
	{
		System.out.println("PersistenceManager Exception caught: " + e);
		e.printStackTrace();
		
		rollbackTx();
		
		// no other exception can be handled here so rethrow it
		throw e;
	}
}

/**
 * Closes the persistence provider. It can no longer be used after being
 * closed.
 */
public void close()
{
	if ( depth != 0 )
	{
		System.out.println("close PersistenceProvider but depth is not 0. depth=" + depth + ", Ending transaction");
		
	}

	if ( pm != null )
	{
		pm.close();
		pm = null;
	}
}

/**
 * Deletes the persistent object from the datastore.
 */
public void delete(Object object)
{
	Object oid = JDOHelper.getObjectId(object);

	// only delete if object is actually persistent - only objects that have been made persistent 
	// return a non null oid
	if ( oid != null )
		pm.deletePersistent(object);
	else
		ExpojoFoundation.sLogError("Calling delete on object that doesn't implement Persistable.");
}

/**
 * Makes the given object persistent.
 */
public Object persist(Object object)
{
	return pm.makePersistent(object);
}

/**
 * Constructs the object.
 */
public JdoPersistenceProvider(PersistenceManagerFactory iPmf)
{
	pmf = iPmf;
	pm = null;
}

/**
 * Sets pm
 */
public void setPm(PersistenceManager pm)
{
    this.pm = pm;
}

/**
 * Returns pm
 */
public PersistenceManager getPm()
{
    return pm;
}

/**
 * Rolls back a transaction.
 */
public void rollbackTx()
{
	if ( expojoContext.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____rollbackTx____ Tx active: " + pm.currentTransaction().isActive());

	if ( --depth < 0 )
	{
		System.out.println("Rollback: Illegal depth " + depth);
		Thread.currentThread().dumpStack();
	}

	if ( depth == 0 )
	{
		if ( pm.currentTransaction().isActive() )
		{
			System.out.println("Tx Rollback: depth " + depth);
			pm.currentTransaction().rollback();
		}
	}
}

/**
 * Commits a transaction.
 */
public void commitTx()
{
	if ( expojoContext.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____commitTx____ Tx active: " + pm.currentTransaction().isActive());

	if ( depth < 1 )
	{
		System.out.println("Commit: Illegal depth " + depth);
		Thread.currentThread().dumpStack();
	}
		
	if ( depth == 1 )
	{
		if ( pm.currentTransaction().isActive() )
		{
			pm.currentTransaction().commit();
		}
	}
	
	// only decrement AFTER the commit in case an exception is thrown - the rollback
	// will then do the decrement
	depth--;
}

/**
 * Begins a transaction.
 */
public void beginTx()
{
	if ( expojoContext.getTraceTxDepth() )
		System.out.println("Depth: " + depth + "____beginTx____ Tx active: " + pm.currentTransaction().isActive());

	if ( depth++ == 0 )
	{
		pm.currentTransaction().begin();
	}
}

}


