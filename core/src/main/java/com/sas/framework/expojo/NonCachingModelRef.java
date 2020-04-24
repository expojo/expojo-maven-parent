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
import com.sas.framework.expojo.ClsIdModelRef;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : NonCachingModelRef
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : concrete
 * An IModelRef implementation that stores an OID and the class of the object being referenced
 * but never stores/caches a reference to the actual model object - it will retrieve
 * from the data store each time getObject() is called.
 * 
 * This should be used for low frequency access, like storing a reference to a model
 * object in the session object where caching references to real objects is undesirable
 * because it is not each to detach in a safe way if multiple threads can execute for
 * the same session object.
 * 
 * @author Chris Colman
 */
public 
class NonCachingModelRef<T>
 extends ClsIdModelRef<T>
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Override to avoid throwing Exception if object can no longer be retrieved - just catches
 * any exception and returns null.
 */
public T getObject()
{
	try
	{
		return super.getObject();
	}
	catch(Exception e)
	{
		return null;
	}
}

/**
 * Constructs the object
 */
public NonCachingModelRef(T o)
{
	super(o);
}

/**
 * Detaches the object.
 */
public void detach()
{
	// non caching model ref so nothing to detach
}

}


