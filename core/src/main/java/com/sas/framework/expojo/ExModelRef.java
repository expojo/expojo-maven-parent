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
import com.sas.framework.expojo.ModelRef;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ExModelRef
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : concrete
 * A ModelRef that will use generic methods of the ExpojoContextFactory to perform any
 * ORM specific operations required for ModelRef behaviour.
 * 
 * @author Chris Colman
 */
public 
class ExModelRef<T>
 extends ModelRef<T>
{
// -[KeepWithinClass]-


// -[Fields]-

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(ExModelRef.class);


// -[Methods]-

/**
 * Returns the result of the wrapped object's toString method.
 */
public String toString()
{
	// Use no retrieve version because we can't risk reloading an object after it has been detached
	T o;


	if (Ex.pp().hasActiveTx())
		o = getObject();
	else
		o = getObjectNoRetrieve();

	if ( getId() == -1 )
		logger.error("Sup wid dis no OID?: " + cls.getSimpleName());


	return (o != null) ? o.toString() : "Null object: " + getId();
}

/**
 * Describe here
 */
public void detach()
{
	if (ExpojoContextFactory.get().isSafeToDetach(this))
		super.detach();
}

/**
 * Returns details of the state of the ModelRef without changing its state in any way.
 */
public String getStateDetails()
{
	return ExpojoContextFactory.get().getStateDetails(this);
}

/**
 * Returns the numeric portion of the OID for the given object.
 */
public Long getObjectId(Object o)
{
	return ExpojoContextFactory.get().getObjectId(o);
}

/**
 * Needs to be overridden because default equals plays all kinds of havoc.
 */
public boolean equals(Object o)
{
	if (o == this)
	{
		return true;
	}
	else if (o == null)
	{
		return false;
	}
	else if (o instanceof ModelRef )
	{
		ModelRef<T> modelRef = ((ModelRef<T>)o);
		
		if ( getId() != -1 && getId() == modelRef.getId() )
			return true;
			
		if (o instanceof ExModelRef )
		{
			// hashCode is the OID so two identical hashCodeS means same object
			// also compare versions because inmethod will only redisplay things it thinks have changed
			// (Note: the above comparison of IDs ignores versions - is this an issue?)

			ExModelRef<T> other = ((ExModelRef<T>)o);
			T myObject = getObjectNoRetrieve();

			if ( other.getObject() == null || myObject == null )
				return false;

			if ( other.getObject().hashCode() != myObject.hashCode() )
				return false;
			
			return ExpojoContextFactory.get().compareObjectVersions(getObject(), other.getObject());
		}
	}

	return false;
}




/**
 * Constructs the object
 */
public ExModelRef()
{
	super(null);
}


/**
 * Constructs the object
 */
public ExModelRef(T o)
{
	super(o);
}

}


