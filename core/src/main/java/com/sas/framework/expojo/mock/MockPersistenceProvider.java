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
package com.sas.framework.expojo.mock;

import java.lang.*;
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.ExpojoContext;
    
import com.sas.framework.expojo.ExpojoContextFactory;


// -[KeepBeforeClass]-
import java.util.*;


// -[Class]-

/**
 * Class Name : MockPersistenceProvider
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : concrete
 * Provides a 'drop in' mock persistence provider that does not persist objects to a
 * database. It's purpose is to allow expojo applications to be run/tested in a memory
 * only environment to exercise unit tests, business rules etc., without the overhead
 * of database persistence. It is only intended to be used as a testing mechanism. Applications
 * running in a production environment would typically use a true persistence provider
 * like JDO or Hibernate.
 * 
 * @author Chris Colman
 */
public 
class MockPersistenceProvider extends PersistenceProvider
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Stores a reference to the first persisted object - which should be the system object.
 * This can then be used by the Mock repository in getSystemEntity to return the top
 * level system entity object.
 * This is a static because it's shared by all threads - heh it's only the mock system
 * ok - we're not going to have a private cache for each PersistenceProvider.
 */
protected static Object firstPersistedObject;



/**
 * Implements a simple storage system for objects. persist just adds the object to the
 * Vector and delete removes it.
 */
protected static Vector objects;


// -[Methods]-




/**
 * Returns true if the given object is of a class that has been configured to be 'able
 * to be persisted' by whatever the current ORM implementation requires.
 */
public boolean isPersistenceCapable(Object object)
{
	return true;
}

/**
 * Detects if the given object related directly or indirectly to any attached objects.
 * Typically used when re-detaching an supposedly already detached object graph to ensure
 * that there are no references to attached objects.
 */
public void detectAttachedRelationships(Object o)
{
	// no op
}

/**
 * Returns the object of the given class and oid.
 */
public Object getObject(Class cls, long oid)
{
	return null;
}

/**
 * Returns the numeric portion of the OID for the given object.
 */
public long getObjectId(Object o)
{
	return o.hashCode();
}

/**
 * Returns a detached copy of the given persistent object.
 */
public Object detachCopy(Object po)
{
	return po;
}

/**
 * Reattaches the given unattached object to the PersistenceProvider
 */
public Object attach(Object object)
{
	return object;
}

/**
 * Forces a real commit, regardless of the current transaction depth. Afterwards the
 * transaction is reopened and the txDepth is restored to its previous value less 1.
 */
public void forceCommitTx()
{
}

/**
 * Flushes all object modifications to SQL (as yet uncommitted) that makes newly created
 * objects and changes available to DB queries within the current transaction.
 */
public void flush()
{
	// no such operation required for mock persistence provider
}



/**
 * Refresh all transactional instances - only has effect if within the context of an
 * open transaction.
 */
public void refreshAll() {
}



/**
 * Refreshes a single object instance.
 */
public void refresh(Object o) {
}



/**
 * Evicts all objects from the PersistenceManager's cache.
 */
public void evictAll()
 {
}



/**
 * Evicts a persistent object from the PersistenceProvider's cache.
 */
public void evict(Object po)
 {
}

/**
 * Gets all objects of the given type.
 */
public Collection getExtent(Class cls)
{
	Vector extent = new Vector();
	
	Iterator i = objects.iterator();
	
	while ( i.hasNext() )
	{
		Object o = i.next();
		
		if ( o.getClass().equals(cls) )
			extent.add(o);
	}
	
	return extent;
}




/**
 * Returns true if the PersistenceProvider has an active transaction.
 */
public boolean hasActiveTx()
{
	return depth > 0;
}

/**
 * Sets firstPersistedObject
 */
public void setFirstPersistedObject(Object firstPersistedObject)
{
    this.firstPersistedObject = firstPersistedObject;
}

/**
 * Returns firstPersistedObject
 */
public Object getFirstPersistedObject()
{
    return firstPersistedObject;
}



/**
 * Closes the persistence provider. It can no longer be used after being
 * closed.
 */
public void close()

 {
}



/**
 * Processes an exception thrown while in a transaction. The exception
 * could be relevent to the persistence technology or a generic RuntimeException.
 * If it's an optimistic verification exception (optimistic locking check
 * failed) then no exception is thrown and the persistence technology
 * may or may not need clean. In the case of JDO a rollback as already
 * been performed.
 */
public RuntimeException processException(RuntimeException e)

 {
	return new RuntimeException(e);
 }

/**
 * Deletes the persistent object from the datastore.
 */
public void delete(Object object)
{
	// remove the object
	objects.remove(object);
}

/**
 * Makes the given object persistent.
 */
public Object persist(Object object)
{
	// save
	if ( firstPersistedObject == null )
		firstPersistedObject = object;
		
	// store the object
	objects.add(object);

	return object;
}



/**
 * Rolls back a transaction.
 */
public void rollbackTx()

{

}



/**
 * Commits a transaction.
 */
public void commitTx()

{

}



/**
 * Begins a transaction.
 */
public void beginTx()

{

}



/**
 * Opens a persistence provider.
 */
public void open()
{
}

/**
 * Constructs the object giving it a PersistenceManager.
 */
public MockPersistenceProvider()
{
	objects = new Vector();
}

}


