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
import com.sas.framework.expojo.ModelRef;


// -[KeepBeforeClass]-
import javax.jdo.JDOHelper;

import com.sas.framework.expojo.servlet.*;


// -[Class]-

/**
 * Class Name : JdoModelRef
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * Implements some JDO specific methods required by JDO model references.
 * This could have been implemented generically in the core and use methods in the JdoPersistenceProvider
 * to get object ID and version info but this *could* affect performance.
 * 
 * @author Chris Colman
 */
public 
class JdoModelRef<T>
 extends ModelRef<T>
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Describe here
 */
public void detach()
{
	// If the object is wrapped by another JdoModelRef it may be detached by the other ref, setting its state to
	// detached-clean. When this JdoModelRef then goes to detach, isPersistent() will return false and the object will
	// not be nulled by the super.detach() call. To solve this, I've added the extra condition isDetached(). This way
	// if the object is not null and it's either persistent or already detached, we null the object reference and avoid
	// any stale object problems.
	
	T object = getObjectNoRetrieve();

	boolean exPojoDetachable = JDOHelper.isPersistent(object) || JDOHelper.isDetached(object);
	
	if (object != null )
	{
		if ( ExpojoServletFilter.isExposerPerRequestMode() && exPojoDetachable)
		{
			super.detach();
			// This is copied from base class to preserve behaviour, since the conditional above
			// should prevent the base method being called if this condition is true.
			// Won't this be an expected/normal (i.e. common) situation now that WMRs can wrap a persistent new object
			if (getId() == -1)
				System.out.println("An attempt was made to detach an object which has not yet been made persistent: " + (object != null ? object.getClass().toString() : "null"));
		}
	}

}

/**
 * Returns details of the state of the ModelRef without changing its state in any way.
 */
public String getStateDetails()
{
	StringBuilder sb = new StringBuilder();

	if (cls != null)
		sb.append(" Cls=" + cls.getSimpleName());
		
	sb.append(" OID=" + getId());

	sb.append(" Ref JID=" + System.identityHashCode(this));
	sb.append(" Obj->");
	
	T object = getObjectNoRetrieve();

	if (object == null)
		sb.append(" null");
	else
	{
		sb.append(" JID=" + System.identityHashCode(object));
		sb.append(", JDOState=" + JDOHelper.getObjectState(object));
	}

	return sb.toString();
}




/**
 * Constructs the object
 */
public JdoModelRef()
{
	super(null);
}




/**
 * Constructs the object
 */
public JdoModelRef(T o)
{
	super(o);
}

/**
 * Returns the numeric portion of the OID for the given object.
 */
public Long getObjectId(Object o)
{
	return JdoPersistenceProvider._getObjectId(o);
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
			
		if (o instanceof JdoModelRef )
		{
			// hashCode is the OID so two identical hashCodeS means same object
			// also compare versions because inmethod will only redisplay things it thinks have changed
			// (Note: the above comparison of IDs ignores versions - is this an issue?)

			JdoModelRef<T> other = ((JdoModelRef<T>)o);
			T object = getObjectNoRetrieve();

			if ( other.getObject() == null || object == null )
				return false;

			if ( other.getObject().hashCode() != object.hashCode() )
				return false;
			
			Object otherVersion = JDOHelper.getVersion(other.getObject());
			Object thisVersion = JDOHelper.getVersion(getObject());
			
			if ( otherVersion != null && thisVersion != null )
				return otherVersion.equals(thisVersion);
			else if ( otherVersion == thisVersion )
				return true;
			else
				return false;
		}
	}

	return false;
}

}


