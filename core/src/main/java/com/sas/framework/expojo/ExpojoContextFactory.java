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
    
import com.sas.framework.system.IModule;
    
import com.sas.framework.system.IModulesProvider;
    
import com.sas.framework.system.DefaultModulesProvider;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.expojo.ExpojoContext;

// -[KeepBeforeClass]-
import java.util.Collection;


// -[Class]-

/**
 * Class Name : ExpojoContextFactory
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : abstract
 * Superclass for all persistence ExpojoContext factories.
 * createExpojoContext will create an ExpojoContext with a new, appropriate instance
 * of a PersistenceProvider.
 * The PersistenceProvider will be returned by the abstract method createPersistenceProvider.
 * 
 * @author Chris Colman
 */
public abstract 
class ExpojoContextFactory
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * True if dealing with a test database. By convention make sure any test database has
 * the word 'test' in its URL. When initializing the persistence technology in the derived
 * class be sure to call setTestDb according to whether the URL contains 'test' in it.
 * This is not used by the model exposer itself but can be used by other classes to know
 * whether they are dealing with a test database or not. The  PersistenceTestCase uses
 * this valid to assert that it is only operating on test databases. Adding and removing
 * objects from a live production system could have detrimental affects on the data store
 * especially when the destroyTestObjects method removes all objects from the database!
 */
protected transient boolean testDb = true;



/**
 * Reference to this singleton.
 */
private static ExpojoContextFactory self;
    
    protected IModulesProvider modulesProvider;












// -[Methods]-




/**
 * returns true if it is safe to proceed with a detach.
 */
public abstract boolean isSafeToDetach(ModelRef modelRef);





/**
 * Returns details of the state of the ModelRef without changing its state in any way.
 */
public abstract String getStateDetails(ModelRef modelRef);





/**
 * Overriden method that calls a static method to return the numeric portion of the OID
 * for the given object.
 * The difference between this getObjectId method and the method of the same name in
 * PersistenceProvider is that this one does not require an ExpojoContext bound to the
 * current thread in order to locate the PersistenceProvider so that it can be called.
 * This form of the method is typically only required by ModelRef style objects which
 * can sometimes be called up to execute in the absense of an ExpojoContext bound to
 * the current thread.
 */
public abstract long getObjectId(Object o);





/**
 * Compares the versions of two different objects. This can be very important for some
 * UI elements that perform upates on an object and expect the updated object to NOT
 * "equal" an instance of the same object in memory that was not yet updated.
 */
public abstract boolean compareObjectVersions(Object thisObject, Object otherObject);


/**
 * Returns the IModulesProvider that is providing the modules for the application.
 */
public IModulesProvider getModulesProvider()
{
	return modulesProvider;
}

/**
 * Constructs the object taking an IModuleProvider.
 */
public ExpojoContextFactory(IModulesProvider iModulesProvider)
{
	self = this;
	
	if (iModulesProvider == null)
		modulesProvider = new DefaultModulesProvider();
	else
		modulesProvider = iModulesProvider;
}

/**
 * Returns a collection of modules from the IModuleProvider.
 */
public final Collection<IModule> getModules()
{
	return modulesProvider.getModules();
}




/**
 * Initializes the model exposer
 */
public abstract void init();




/**
 * Returns this singleton or null if no factory is available.
 */
public static ExpojoContextFactory get()
{
	return self;
}

/**
 * Constructs the object
 */
public ExpojoContextFactory()
{
	this(null);
}




/**
 * Adds service and repository components to the ModelExposer. This method is called
 * whenever the ExpojoSerletFilter is establishing a new session, and thus a new ModelExposer.
 * This method adds the services and repositories to the ModelExposer. Within the scope
 * of an individual HTTP request the ModelExposer, and hence its services and repositories,
 * are made available to the application via dependency injection.
 * 
 * This implementation of the method will add all components belonging to the IModules
 * returned by the overwritten getModules method. For apps that use the IModule infrastructure
 * this should be sufficient. For apps that don't use IModuleS or have specific requirements
 * this method can be overwritten to perform specialized behaviour.
 */
public void addComponents(ModelExposer modelExposer)
{
	// Add the components for each module
	for(IModule module: getModules())
	{
		module.addComponents(modelExposer);
	}
}

/**
 * Creates an ExpojoContext.
 * Application developers should rarely need to call this. This is called implicitly
 * by the ExpojoServletFilter to establish an ExpojoContext object for each new session
 * or request that is started (depending on whether expojoContext per request is true
 * or not)..
 */
public ExpojoContext createExpojoContext()
{
	// Create the ExpojoContext
	ExpojoContext expojoContext = new ExpojoContext(createPersistenceProvider());

	// Add service and respository components
	addComponents(expojoContext);
	
	// initialize the ExpojoContext
	expojoContext.init();
	
	return expojoContext;
}



/**
 * Describe here
 */
public void close() {
}




/**
 * Returns testDb
 */
public boolean isTestDb()
{
    return testDb;
}




/**
 * Overridden in derived classes to provide PersistenceProvider appropriate
 * to the persistence technology used.
 */
public abstract PersistenceProvider createPersistenceProvider();

}


