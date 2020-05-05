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
import com.sas.framework.expojo.IModelRef;
    
import com.sas.framework.expojo.ExpojoContext;
    
import com.sas.framework.expojo.jdo.JdoModelRef;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : PerThreadModelRefCache
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * Keeps a per thread cache of JdoModelRef objects that reference a single model object.
 * The OID is stored in an instance of this class but the per thread JdoModelRefs are
 * stored thread locally.
 * 
 * The main purpose of this class is to support scenarios where a non thread specific
 * object (eg., Session specific or global) needs to reference a model object but can
 * not use a single JdoModelRef instance due to the multithreaded nature of the application
 * and the assumption that each thread will have its own persistence provider/manager.
 * JdoModelRefs are single PM and therefore single thread bound. This class maintains
 * a cache of JdoModelRef objects on a per thread basis.
 * 
 * It is important that when a thread has completed working on a particular unit of work
 * (eg., a http request) that it called detach on this class. detach will detach and
 * dereference the JdoModelRef associated with the current thread.
 * 
 * This class uses ThreadLocal to associate JdoModelRefS with the thread they were created
 * for.
 * 
 * @author Chris Colman
 */
public 
class PerThreadModelRefCache<T>
 implements IModelRef<T>
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * The OID of the object being referenced. The value of -1 indicates that no object is
 * referenced.
 */
private long oid = -1;



/**
 * Stores the class type for later use in getObject.
 * We must store the class because primary key on it's own is not enough as it is usually
 * unique to an individual table only (in an ORM scenario).
 */
protected Class cls;



/**
 * The ThreadLocal object used to store per thread ModelRefs
 */
private transient ThreadLocal<JdoModelRef<T>> threadLocalModelRef = new ThreadLocal<JdoModelRef<T>>();


// -[Methods]-

/**
 * Returns the thread local associated with this thread or creates one if there is none.
 */
public ThreadLocal<JdoModelRef<T>> getThreadLocalModelRef()
{
	if (threadLocalModelRef == null)
		threadLocalModelRef = new ThreadLocal<JdoModelRef<T>>();
		
	return threadLocalModelRef;
}

/**
 * Typically detach on a ModelRef detaches the ModelRef and leaves it open to be reattachd
 * some time later. In this case the ModelRef instance associated with the current Thread
 * is detached and then dissassociated with the thread altogether as it is pointless
 * (and potentially insecure due to the nature of the shared thread pool in most web
 * app containers) to keep a reference to it. The only data it holds is the OID and that
 * is held as an attribute of this PerThreadModelRefCache anyway.
 */
public void detach()
{
	// Must detach MR from the actual model object and then
	// unbind them from this current thread because threads are reused for many different http requeusts
	// so for security reasons these security sensitive objects should never be left attached to the thread
	JdoModelRef<T> modelRef = threadLocalModelRef.get();
	if (modelRef != null)
	{
		modelRef.detach();
		threadLocalModelRef.set(null);
	}
}

/**
 * Sets object
 */
public void setObject(T object)
{
	ThreadLocal<JdoModelRef<T>> threadLocalModelRef = getThreadLocalModelRef();
		
	if (object != null)
	{
		cls = object.getClass();
		oid = ExpojoContext.pp().getObjectId(object);
		
		JdoModelRef<T> modelRef = threadLocalModelRef.get();

		if ( modelRef == null )
			threadLocalModelRef.set(new JdoModelRef<>(object));
		else
			modelRef.setObject(object);
	}
	else
	{
		detach();
		oid = -1;
		threadLocalModelRef.set(null);
	}
}

/**
 * Returns object
 */
public T getObject()
{
	JdoModelRef<T> modelRef = getThreadLocalModelRef().get();
	if ( modelRef == null )
	{
		if ( oid != -1 )
		{
			// cls is assigned in setobject when oid is assigned so we can assume 
			// that cls has been assigned here because we know oid != -1
			T object = (T)ExpojoContext.pp().getObject(cls, oid);
			setObject(object);
			return object;
		}
		return null;
	}
	return modelRef.getObject();
}

}


