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


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ExpojoComponent
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : abstract
 * Superclass for all service and repository components in ExPojo. These will be easily
 * accessible via any ExPojoContext.
 * Pooling of ExPojoContextS may offer a performance benefit in the future as this will
 * void the need to instantiate the individual components each time a ExPojoContext is
 * required.
 * 
 * @author Chris Colman
 */
public abstract 
class ExpojoComponent
{
// -[KeepWithinClass]-


// -[Fields]-
    
    protected transient ExpojoContext expojoContext;



// -[Methods]-

/**
 * Returns the ExpojoContext
 */
public ExpojoContext getExpojoContext()
{
	return expojoContext;
}




/**
 * Returns an object used to identify the ModelExposerComponent. In the base class this
 * returns getName() but it can be overridden in derived classes to return unique integers
 * or Class objects or whatever is required.
 * Returning getName() in this base class is mainly for backwards compatibility.
 */
public final Object getId()
{
	return getName();
}

/**
 * Overridden in derived classes to allow initialization.
 */
public void init(ExpojoContext expojoContext)
{
	this.expojoContext = expojoContext;
}




/**
 * Overridden in derived classes to return the name of the model exposer component -
 * only used for backwards compatibility.
 */
public final String getName()
{
	// used only for legacy components. All new components should
	// override getId
	return "";
}

/**
 * Returns a ThreadLocal version of the appropriate PersistenceProvider. Each thread
 * that calls this will then end up with a unique connection to the persistence service
 * with which to access the data store.
 */
public PersistenceProvider getPersistenceProvider()
{
	return expojoContext.getPersistenceProvider();
}




/**
 * Sets the ExpojoContext of which this component forms a part. There should be no need
 * to ever call this explicitly. This is called automatically by the addService and addRepository
 * methods when an ExpojoComponent is added to the ExpojoContext.
 */
public void setExpojoContext(ExpojoContext expojoContext)
{
	this.expojoContext = expojoContext;
}

}


