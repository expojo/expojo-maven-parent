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
import com.sas.framework.expojo.ExpojoContextFactory;
    
import com.sas.framework.expojo.jdo.JdoPersistenceProvider;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;
    
import com.sas.framework.system.IModulesProvider;
    
import com.sas.framework.expojo.ModelRef;
    
import com.sas.framework.expojo.servlet.ExpojoServletFilter;

// -[KeepBeforeClass]-
import java.lang.*;
import java.util.Properties;
import java.io.*;
import javax.jdo.*;


// -[Class]-

/**
 * Class Name : JdoExpojoContextFactory
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : abstract
 * A factory that produces ExpojoContextS with JdoPersistenceProviders. Typically this
 * can be shared by many threads in the same application.
 * 
 * @author Chris Colman
 */
public abstract 
class JdoExpojoContextFactory extends ExpojoContextFactory
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * A JDO PersistenceManagerFactory.
 */
protected PersistenceManagerFactory pmf = null;

// -[Methods]-

/**
 * returns true if it is safe to proceed with a detach.
 */
public boolean isSafeToDetach(ModelRef modelRef)
{
	boolean safeToDetach = false;
	
	// If the object is wrapped by another JdoModelRef it may be detached by the other ref, setting its state to
	// detached-clean. When this JdoModelRef then goes to detach, isPersistent() will return false and the object will
	// not be nulled by the super.detach() call. To solve this, I've added the extra condition isDetached(). This way
	// if the object is not null and it's either persistent or already detached, we null the object reference and avoid
	// any stale object problems.

	Object object = modelRef.getObject();
	
	boolean exPojoDetachable = JDOHelper.isPersistent(object) || JDOHelper.isDetached(object);
	
	if (object != null )
	{
		if ( ExpojoServletFilter.isExposerPerRequestMode() && exPojoDetachable)
		{
			safeToDetach = true;
			
			// This is copied from base class to preserve behaviour, since the conditional above
			// should prevent the base method being called if this condition is true.
			// Won't this be an expected/normal (i.e. common) situation now that WMRs can wrap a persistent new object
			if (modelRef.getId() == -1)
				System.out.println("An attempt was made to detach an object which has not yet been made persistent: " + (object != null ? object.getClass().toString() : "null"));
		}
	}

	return safeToDetach;
}

/**
 * Returns details of the state of the ModelRef without changing its state in any way.
 */
public String getStateDetails(ModelRef modelRef)
{
	StringBuilder sb = new StringBuilder();

	Class cls = modelRef.getClass();
	
	if (cls != null)
		sb.append(" Cls=" + cls.getSimpleName());
		
	sb.append(" OID=" + modelRef.getId());

	sb.append(" Ref JID=" + System.identityHashCode(modelRef));
	sb.append(" Obj->");
	
	Object object = modelRef.getObject();
	
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
 * Overriden method that calls a static method to return the numeric portion of the OID
 * for the given object.
 */
public long getObjectId(Object o)
{
	return JdoPersistenceProvider._getObjectId(o);
}

/**
 * Compares the versions of two different objects. This can be very important for some
 * UI elements that perform upates on an object and expect the updated object to NOT
 * "equal" an instance of the same object in memory that was not yet updated.
 */
public boolean compareObjectVersions(Object thisObject, Object otherObject)
{
	Object otherVersion = JDOHelper.getVersion(otherObject);
	Object thisVersion = JDOHelper.getVersion(thisObject);
	
	if ( otherVersion != null && thisVersion != null )
		return otherVersion.equals(thisVersion);
	else if ( otherVersion == thisVersion )
		return true;
	else
		return false;
}




/**
 * Constructs the object
 */
public JdoExpojoContextFactory() {
}




/**
 * Constructs the object
 */
public JdoExpojoContextFactory(IModulesProvider iModulesProvider)
{
	super(iModulesProvider);
}

/**
 * Overridden in any derived class that needs to customize the properties before the
 * PMF is created.
 */
public void customizeProperties(Properties properties)
{
	// by default, no customization takes place
}

/**
 * Initializes the model exposer.
 */
public void init()
{
	if ( pmf == null )
	{
		try
		{
			Properties properties = new Properties();

			String propertiesFilename = getPropertiesFilename();
			
			ExpojoFoundation.sLog(ExpojoFoundation.LT_INFO, "JdoModelExposerFactory.init: Using properties file: " + propertiesFilename);
			
			InputStream is = JdoPersistenceProvider.class.getClassLoader().getResourceAsStream(propertiesFilename);
		
			if (is == null)
			{
				throw new FileNotFoundException("Could not find '" + getPropertiesFilename() + "' file that defines the JDO persistence setup.");
			}

			properties.load(is);
			
			customizeProperties(properties);

			pmf = JDOHelper.getPersistenceManagerFactory(properties);		
		}
		catch (IOException ioe)
		{
			throw new RuntimeException(ioe);
		}
	}
}

/**
 * Describe here
 */
public void close()
{
	if ( pmf != null )
	{
		pmf.close();
		pmf = null;
	}
}


/**
 * Returns the name of the properties file used for configuring the JDO
 * datastore.
 */
public abstract String getPropertiesFilename();

/**
 * Overridden to provide PersistenceProvider appropriate to the persistence technology
 * used - in this case a JDO PersistenceManager.
 */
public PersistenceProvider createPersistenceProvider()
{
	if ( pmf == null )
	{
		init();
	}
	
	return new JdoPersistenceProvider(pmf);
}







}


