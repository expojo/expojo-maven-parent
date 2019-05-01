// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2007, 2015. Step Ahead Software Pty Ltd.
 * This software is released under the Apache 2 License, a copy of which can be found
 * at:
 * http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.expojo;

import java.lang.*;
    
import com.sas.framework.expojo.ExpojoContext;
    
import com.sas.framework.expojo.ExpojoContextFactory;

// -[KeepBeforeClass]-
import java.util.Collection;


// -[Class]-

/**
 * Class Name : PersistenceProvider
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : abstract
 * Superclass for all persistence providers. This class is extended to provide persistence
 * engine specific implementations. eg., HibernatePersistenceProvider and JdoPersistenceProvider.
 * These objects are typically kept 'open' throughout multiple HTTP requests, for each
 * request a transaction is begun at the start and commited (or rolled back) at the end.
 * This implements the 'OpenSessionInView' (hibernate) or 'OpenPersistenceManagerInView'
 * pattern.
 * A future possible enhancement is to add an optional "free persistence provider' mechanism
 * at the end of each HTTP request.
 * 
 * @author Chris Colman
 */
public abstract 
class PersistenceProvider
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Depth of the deepest transaction. 0 = no active transaction.
 * beginTx increments and commitTx and rollbackTx decrement depth.
 * See rollbackTx for more information.
 */
protected int depth = 0;
    
    public ExpojoContext expojoContext;

// -[Methods]-




/**
 * Returns true if the given object is of a class that has been configured to be 'able
 * to be persisted' by whatever the current ORM implementation requires.
 */
public abstract boolean isPersistenceCapable(Object object);





/**
 * Detects if the given object related directly or indirectly to any attached objects.
 * Typically used when re-detaching an supposedly already detached object graph to ensure
 * that there are no references to attached objects.
 */
public abstract void detectAttachedRelationships(Object o);





/**
 * Returns the object of the given class and oid.
 */
public abstract Object getObject(Class cls, long oid);





/**
 * Returns the numeric portion of the OID for the given object.
 */
public abstract long getObjectId(Object o);


/**
 * Forces a real rollback, regardless of the current transaction depth. Afterwards the
 * transaction is reopened and the txDepth is restored to its previous value less 1.
 */
public void forceRollbackTx()
{
	int txDepth = getTxDepth();

	// Commit enough times to get a real commit through to the database
	while (getTxDepth() != 0)
		rollbackTx();
	
	// Restore transaction depth back to where it was
	while (getTxDepth() != txDepth)
		beginTx();
}




/**
 * Deletes the persistent objects contained in the collection.
 * Maybe be overridden by some derived classes to perform a native ORM Collection delete.
 */
public void deleteAll(Collection toDelete)
{
	// Need to make a copy because the provided collection might be the collection implementing a 
	// relationship. As items are deleted they will/might be deleted (depending on how JDO works!)
	// and so we can miss every second item!
	Object[] deathRow = toDelete.toArray();

	for (Object o: deathRow)
	{
		delete(o);
	}
}


/**
 * Returns a summary of the statistics regarding the PersistenceProvider. Typically this
 * will be a count of the number of objects it is currently managing in it's L1 cache.
 */
public String getStatistics()
{
	return "PersistenceProvider: No statistics available";
}




/**
 * Returns a detached copy of the given persistent object.
 */
public abstract Object detachCopy(Object po);




/**
 * Forces a real commit, regardless of the current transaction depth. Afterwards the
 * transaction is reopened and the txDepth is restored to its previous value less 1.
 */
public void forceCommitTx()

{
	int txDepth = getTxDepth();

	// Commit enough times to get a real commit through to the database
	while (getTxDepth() != 0)
		commitTx();
	
	// Restore transaction depth back to where it was
	while (getTxDepth() != txDepth)
		beginTx();
}




/**
 * Reattaches the given unattached object to the PersistenceProvider
 */
public abstract Object attach(Object object);


/**
 * Static method that returns the PersistenceManager of the ModelExposer attached to
 * the current thread.
 */
public static PersistenceProvider get()
{
	return ModelExposer.get().getPersistenceProvider();
}




/**
 * Flushes all object modifications to SQL (as yet uncommitted) that makes newly created
 * objects and changes available to DB queries within the current transaction.
 */
public abstract void flush();





/**
 * Rollsback any active transaction and sets the tx depth back to 0.
 */
public void clearTx()
{
	if ( depth != 0 )
	{
		depth = 1;
		
		rollbackTx();
	}
}

/**
 * Not normally required. Causes all of the uncommitted changes to be committed to the
 * database. Occassionally it is desirable or sometimes even necessary to get all changes
 * to your model up to a certain point commited to the database prior to proceeding further
 *  - regardless of the current transaction depth. This method will do a forced commit
 * and return with the transaction depth at the same level it was at when called.
 */
public void flushChanges()
{
	flush();
}

/**
 * Refresh all transactional instances - only has effect if within the context of an
 * open transaction.
 */
public abstract void refreshAll();





/**
 * Refreshes a single object instance.
 */
public abstract void refresh(Object o);


/**
 * Returns the transaction depth. This does not infer support for nested transactions
 * (they would have to be supported by the underlying ORM) but it does allow for more
 * than one call to beginTx without a corresponding commitTx or rollbackTx so long as
 * the beginTx calls eventually matches the sum of the commitTx and rollbackTx calls.
 */
public int getTxDepth()
{
	return depth;
}




/**
 * Evicts all objects from the PersistenceManager's cache.
 */
public abstract void evictAll();





/**
 * Evicts a persistent object from the PersistenceProvider's cache.
 */
public abstract void evict(Object po);





/**
 * Returns true if the PersistenceProvider has an active transaction.
 */
public abstract boolean hasActiveTx();




/**
 * Sets the owning ExpojoContext.
 */
public void setExpojoContext(ExpojoContext iExpojoContext)
{
	expojoContext = iExpojoContext;
}




/**
 * Opens a persistence provider.
 */
public abstract void open();




/**
 * Processes an exception thrown while in a transaction. The exception
 * could be relevent to the persistence technology or a generic RuntimeException.
 * If it's an optimistic verification exception (optimistic locking check
 * failed) then no exception is thrown and the persistence technology
 * may or may not need clean. In the case of JDO a rollback as already
 * been performed.
 */
public abstract RuntimeException processException(RuntimeException e);





/**
 * Closes the persistence provider. It can no longer be used after being
 * closed.
 */
public abstract void close();





/**
 * Deletes the persistent object from the datastore.
 */
public abstract void delete(Object object); {
}




/**
 * Makes the given object persistent.
 */
public abstract Object persist(Object object); {
}




/**
 * Rolls back a transaction.
 */
public abstract void rollbackTx();




/**
 * Commits a transaction if the transaction depth is 1, otherwise merely decreases the
 * transaction depth. To force a real commit regardless of the current transaction depth
 * use forceCommitTx.
 */
public abstract void commitTx();




/**
 * Begins a transaction.
 */
public abstract void beginTx();

}


