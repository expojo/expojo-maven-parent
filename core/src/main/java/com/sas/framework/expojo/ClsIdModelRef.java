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
import com.sas.framework.expojo.IModelRef;


// -[KeepBeforeClass]-



// -[Class]-

/**
 * Class Name : ClsIdModelRef
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : abstract
 * An IModelRef implementation that stores an OID and the class of the object being referenced.
 * 
 * @author Chris Colman
 */
public abstract 
class ClsIdModelRef<T>
 implements IModelRef<T>
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Stores the class type for later use in getObject.
 * We must store the class because primary key on it's own is not enough as it is usually
 * unique to an individual table only (in an ORM scenario).
 */
protected Class<? extends T> cls;



/**
 * Stores the ID (value of primary key) of the object to use when getting the object
 * again after it has been detached.
 * -1 indicates an invalid ID which implies a null object.
 */
private long id;


// -[Methods]-

/**
 * Returns object
 */
public T getObject()
{
	if ( id != -1 )
		return (T)ModelExposer.pp().getObject(cls, id);
	
	return null;
}




/**
 * Returns cls
 */
public Class getCls()
{
	return cls;
}

/**
 * Sets id
 */
public void setId(long id)
{
    this.id = id;
}

/**
 * Returns id
 */
public long getId()
{
    return id;
}

/**
 * Describe here
 */
public int hashCode()
{
	return (int)id;
}

/**
 * Sets object
 */
public void setObject(T object)
{
	if ( object != null )
	{
		cls = (Class<? extends T>)object.getClass();	
		id = ModelExposer.pp().getObjectId(object);
	}
	else
		id = -1;
}




/**
 * Constructs the object
 */
public ClsIdModelRef(T o)
{
	setObject(o);
}

}


