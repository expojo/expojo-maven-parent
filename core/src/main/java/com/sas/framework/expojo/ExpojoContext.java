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
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.ExpojoComponent;
    
import com.sas.framework.expojo.ThreadBindListener;
    
import com.sas.framework.expojo.IWrappedOperation;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;

// -[KeepBeforeClass]-
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.Iterator;
import java.util.Arrays;

import java.lang.AssertionError;


// -[Class]-

/**
 * Class Name : ExpojoContext
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : concrete
 * Central class for implementing the "Exposed Model" pattern.
 * Supports the 'availability' of the ExpojoContext to all objects in a thread via attachment
 * to the thread ie., TDP (Thread Dependency Provisioning) instead of the less performant,
 * reflection heavy and/or annotation/scanning or XML based form of dependency provisioning
 * known as Dependency Injection (DI).
 * 
 * In most scenarios threads are pooled resources (eg., servlet environment) and so should
 * not be permanently attached to a ModelExposer. attachThread and detachThread are provided
 * and work with the ThreadLocal attribute to provide almost 'singleton' like (without
 * the bad bits) access to the ExpojoContext from within a thread that has been attached.
 * 
 * No automatic attachment/detachment takes place in the constructor, init or destroy
 * methods because no assumptions are made that these methods are running in the thread
 * that is the one that should be attached to the ExpojoContext.
 * 
 * Typically in a web app environment the developer will configure the ExpojoServletFilter
 * in web.xml and this will perform the dependency injection automatically. This is designed
 * to work perfectly with the "OpenPersistenceManagerInView" and "OpenSessionInView"
 * strategies.
 * 
 * See web/web.xml for an example web.xml file that configures the ExpojoFoundation and
 * ExpojoServletFilter for a typical web application.
 * @see com.sas.framework.expojo.servlet.ExpojoFoundation
 * @see com.sas.framework.expojo.servlet.ExpojoServletFilter
 * 
 * ExpojoContext effeciently supports the provisioning of a set of "components" which
 * are indexed via a Class based key. The developer is free to choose whatever class
 * they like to key their components on so long as they use the same key for registration
 * of the component as they do for retrieval of the component.
 * Components are either RepositoryComponentS or ServiceComponentS and the key used will
 * typically be the base class or interface that defines that repository or service's
 * API. It is not mandatory that the key must be an interface but many will choose to
 * do this as it is fairly common practice.
 * 
 * For example a user repository that provides access to IUser objects will typically
 * have an interface IUserRepository that defines the method signatures of the generic
 * query methods of the user repository. It will then have an implementation class for
 * a particular ORM. Queriy method implementations are notoriously ORM specific so the
 * implementation will be specific to that developer's favourite ORM API e.g. JDO, JPA
 * or ORM engine.
 * 
 * The developer would typically use IUserRepository.class for the key of the user repostory.
 * Regardless of the particular repository component implementation registered with the
 * ExpojoContext the client code that requires that repository can expect to be provisioned
 * with "an" implemenation of IUserRepository when they request a component using that
 * key. Client code can therefore be written in a completely ORM agnostic manner. Any
 * ORM specifics are hidden from most of the applications code so that the bulk of the
 * code can be written as simple POJOs - assuming that the persistence and retrieval
 * of model objects happens automatically and transparently - a truly "zen" way to write
 * Java applications.
 * 
 * IBound detachment feature option
 * Some UI frameworks (eg., Wicket) provide built in detachment of model objects and
 * so registering them via Expojo is not necessary as the UI framework will already be
 * detaching them.
 * Some UI frameworks do not provide a built in detachment mechanism. Expojo's IBound
 * registration feature can provide that in this scenario.
 * ExpojoContext can store references to individual IBoundS. When detachBounds is called
 * (by ExpojoServletFilter after servicing HTTP request but before committing transaction)
 * all IBoundS that have been registered with the ExpojoContext will have their detach
 * method's called. When a UI element is used again later the getObject method of any
 * IModelReference (extends IBound) implementation knows how to re-attach to a valid
 * reference to the model object (implementation varies depending on persistence technology
 * used (eg., Hibernate, JDO, JPA).
 * IBoundS are registered using weak refrences and so will automatically be released
 * soon after there are no strong references to a particular IBound object any more.
 * 
 * @author Chris Colman
 */
public 
class ExpojoContext extends ModelExposer
{
// -[KeepWithinClass]-

// -[Fields]-



/**
 * A thread local used to store the persistence provider per thread. This has an initialization
 * construct so we can always assume that the threadLocalExpojoContext is not null.
 */
protected static transient ThreadLocal threadLocalExpojoContext = new ThreadLocal();



/**
 * If true dumps information about the transaction depth each time begin, commit or rollback
 * are called.
 */
protected boolean traceTxDepth = false;



/**
 * Number of active threads currently associated with this ModelExposer. There can be
 * no more than one.
 */
private int activeThreads = 0;



/**
 * Stores references to registered IBoundS.
 */
private WeakHashMap<IBound, Integer> boundRegistrations;
    
    public PersistenceProvider persistenceProvider;
    
    protected HashMap<Object, ExpojoComponent> components;
    
    protected ThreadBindListener threadBindListener;

// -[Methods]-




/**
 * Returns the service associated with the given key.
 */
public ServiceComponent getService(Class key)

{
	return (ServiceComponent)components.get(key);
}




/**
 * Typesafe method to return a component of the type specified by the argument.
 * 
 * e.g.
 * 
 * IMyRepo repo = ExpojoContext.component(IMyRepo.class);
 * 
 * Should make a shorthand form available:
 * 
 * IMyRepo repo = Ex.get(IMyRepo.class);
 */
public static <C> C component(Class<C> cls)
{
	ExpojoContext me = get();
	
	if ( me != null )
	{
		HashMap<Object, ExpojoComponent> components = me.components;
		if ( components != null )
			return (C)components.get(cls);
		else
		{
			ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExpojoContext.components was null");
		}
	}
	else
	{
		ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExpojoContext was null");
	}
	
	return null;
}



/**
 * Dumps a list of the components to the info log.
 */
public void logComponents()
{
	Object[] componentKeys = components.keySet().toArray();
	Arrays.sort(componentKeys);
	
	ExpojoFoundation.sLogInfo("List of components");
	for (Object key: componentKeys)
	{
		ExpojoComponent component = components.get(key);
		ExpojoFoundation.sLogInfo("Component: " + component.getName() + " [Class: " + component.getClass().getSimpleName() + "]");
	}
}

/**
 * Wrapping operations within the context of an open transaction and managing the commit
 * or rollback is a very common requirement in enterprise applications and the executeWrappedCreate
 * method enables this pattern to be implemented without having to explicitly rewrite
 * the entire transaction management construct each time.
 * If no ModelExposer is associated with the current thread then it creates one and calls
 * executeWrapped on it. If a ModelExposer was created then before returning it destroys
 * it.
 * This method does not take parameters that are to be passed to the IWrappedOperation
 * because typical usage involves an anonymous class that can access attributes of the
 * class it is instantiated in or final locals.
 * 
 * ModelExposer.executeWrappedCreate assumes that no transaction context is present already
 * and so creates a new ModelExposer, attaches it to the current thread and then executes
 * the provided implementation of IWrappedOperation. This can be provided as an anonymous
 * class or an explicit class. The call to the 'execute' method is wrapped within a 'begin
 * transaction' and 'commit transaction' and all are within a try block whose catch will
 * perform a rollback.  The finally block detaches the ModelExposer from the thread and
 * then destroys it.
 */
public static void executeWrappedCreate(IWrappedOperation operation, Object param1, Object param2)
{
	boolean created = false;

	ExpojoContext expojoContext = ModelExposer.get();

	if ( expojoContext == null )
	{
		expojoContext = ExpojoContextFactory.get().createExpojoContext();
		created = true;
	}

	try
	{
		expojoContext.executeWrapped(operation, param1, param2);
	}
	catch(Exception e)
	{
		// rethrow original cause if there is one
		Throwable cause = e.getCause();

		if ( cause != null && cause instanceof RuntimeException )
			throw (RuntimeException)cause;
		else if ( e instanceof RuntimeException )
			throw (RuntimeException)e;
		else
			ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "Exception thrown while executing transaction wrapped operation: " + e.getMessage());
	}
	finally
	{
		if ( created )
			expojoContext.destroy();
	}
}

/**
 * Wrapping operations within the context of an open transaction and managing the commit
 * or rollback is a very common requirement in enterprise applications and the executeWrappedCreate
 * method enables this pattern to be implemented without having to explicitly rewrite
 * the entire transaction management construct each time.
 * If no ModelExposer is associated with the current thread then it creates one and calls
 * executeWrapped on it. If a ModelExposer was created then before returning it destroys
 * it.
 * This method does not take parameters that are to be passed to the IWrappedOperation
 * because typical usage involves an anonymous class that can access attributes of the
 * class it is instantiated in or final locals.
 * 
 * ModelExposer.executeWrappedCreate assumes that no transaction context is present already
 * and so creates a new ModelExposer, attaches it to the current thread and then executes
 * the provided implementation of IWrappedOperation. This can be provided as an anonymous
 * class or an explicit class. The call to the 'execute' method is wrapped within a 'begin
 * transaction' and 'commit transaction' and all are within a try block whose catch will
 * perform a rollback.  The finally block detaches the ModelExposer from the thread and
 * then destroys it.
 */
public static void executeWrappedCreate(IWrappedOperation operation)
{
	executeWrappedCreate(operation, null, null);
}

/**
 * Calls detach on all registerd IBoundS
 */
public void detachBounds()
{
	// Detach all registered IModelReference objects
	if ( boundRegistrations != null )
		for (IBound bound: boundRegistrations.keySet())
			bound.detach();
}

/**
 * Registers an IBound. All registered IBoundS will have their detach method called when
 * detachThread is called on this ModelExposer.
 */
public void registerBound(IBound bound)
{
	if ( boundRegistrations == null )
		boundRegistrations = new WeakHashMap<IBound, Integer>();

	// We aren't really interested in retrieving individual values as the IBound forms the key
	// A WeakHashSet (if it existed in the JDK) would have sufficed. I noticed that a number
	// of third parties have implemented their own WeakHashSet class.
	boundRegistrations.put(bound, new Integer(0));
}

/**
 * Returns a summary of the ModelExposer including statistics from the PersistenceProvider.
 */
public String toString()
{
	return "Model Exposer: " + hashCode() + 
		" Repository/service count: " + (components != null ? components.size() : 0) + " " + (persistenceProvider != null ? persistenceProvider.getStatistics() : "");
}

/**
 * Wraps an operation inside an open transaction with this ModelExposer attached to the
 * current thread.
 * After execution the transaction is committed (or rolled back on exception) and this
 * ModelExposer is detached from the thread.
 */
public void executeWrapped(IWrappedOperation operation, Object param1, Object param2)
{
	ExpojoContext threadMex = ExpojoContext.get();
	if ( threadMex != null )
	{
		// This transaction is already wrapped so just
		// execute the operation
		// We assume it also has an active transaction
		if ( threadMex.getPersistenceProvider().hasActiveTx() )
			operation.execute(param1, param2);
		else
			throw new RuntimeException("executeWrapped: thread is already wrapped in a ModelExposer but it has no active transaction");
	}
	else
	{
		attachThread();
		
		PersistenceProvider pp = pp();
		
		try
		{
			// Open the transaction
			pp.beginTx();
			
			// execute the operation
			operation.execute(param1, param2);
			
			// Commit the changes
			pp.commitTx();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			
			// pass it up the chain
			throw new RuntimeException("ModelExposer: Exception thrown while performing a wrapped operation", e);
		}
		finally
		{
			// Some other Throwable - could be an Assertion thrown during unit testing - must always rollback
			// any active transaction
			if ( pp.hasActiveTx() )
				pp.rollbackTx();
		
			detachThread();
		}
	}
}

/**
 * Static convenience method to return a persistence provider.
 */
public static PersistenceProvider pp()
{
	ExpojoContext ec = get();
	if (ec != null)
		return ec.getPersistenceProvider();
	return null;
}

/**
 * Sets a listener on the binding of this ModelExposer to a thread via attachThread/detachThread.
 */
public void setThreadBindListener(ThreadBindListener iThreadBindListener)
{
	threadBindListener = iThreadBindListener;
}




/**
 * Returns the ExpojoComponent with the given key.
 */
public ExpojoComponent getComponent(Class key)
{
	return components.get(key);
}




/**
 * Adds a component to the components map, keyed via the given key.
 * 
 * The developer needs to decide what class/interface will be used as the key for the
 * component's storage in the map
 * 
 * It doesn't matter so long as the key used for storage is the same as is used for retrieval.
 * 
 * 
 * Some may have an interface that contains all required methods with a base abstract
 * implementation which is ORM agnostic and then an ORM specific derived class which
 * implements the methods.
 * 
 * Some may not have such an interface and simply use the base abstract class as the
 * key for storage and retrieval
 * .
 * 
 * Whatever the case the user now needs to specify the class that will be used as the
 * key in the components map
 */
protected void addComponent(Class key, ExpojoComponent component)
{
	if ( components.containsKey(key) )
		throw new RuntimeException("ExpojoContext already contains a component with key: " + key);

	component.setExpojoContext(this);

	components.put(key, component);
}




/**
 * Returns the ExpojoComponent with the given Id.
 * This method should not be called prior to a ModelExposer being completely initialized.
 * This means that when ExpojoComponent's are being initialized themselves they must
 * use getComponent on the ModelInstance provided to their init method instead of using
 * the static get method. 
 */
public static ExpojoComponent get_legacy(Object id)
{
	ExpojoContext me = get();
	
	if ( me != null )
	{
		HashMap<Object, ExpojoComponent> components = me.components;
		if ( components != null )
			return components.get(id);
		else
		{
			ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExPojoContext.components was null");
		}
	}
	else
	{
		ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExPojoContext was null");
	}
	
	return null;
}

/**
 * Sets activeThreads
 */
public void setActiveThreads(int activeThreads)
{
    this.activeThreads = activeThreads;
}

/**
 * Returns activeThreads
 */
public int getActiveThreads()
{
    return activeThreads;
}

/**
 * Sets traceTxDepth
 */
public void setTraceTxDepth(boolean traceTxDepth)
{
    this.traceTxDepth = traceTxDepth;
}

/**
 * Returns traceTxDepth
 */
public boolean getTraceTxDepth()
{
    return traceTxDepth;
}

/**
 * Implements Dependency Injection:
 * Attaches this ModelExposer to the current thread. This is called automatically by
 * the ExpojoServletFilter prior to servicing a HTTP request so that model objects can
 * call ModelExposer.get() and get this model exposer during the servicing of that HTTP
 * request.
 * This eliminates the need to manually inject a ModelExposer dependency into various
 * objects that might need one because the ModelExposer is available to all objects in
 * the current thread via ModelExposer.get().
 */
public void attachThread()
{
	if ( get() != null )
		throw new RuntimeException("ExpojoContext is already attached to this thread!");

	// Attach THIS ExpojoContext to THIS thread
	threadLocalExpojoContext.set(this);
	
	if ( threadBindListener != null )
		threadBindListener.postAttach(this);
}

/**
 * Detaches a ModelExposer from the current thread - enabling it to be garbage collected
 * if necessary (assuming ThreadLocal had the last remaining reference to the ModelExposer).
 * This is called automatically by the ExpojoServletFilter after the current HTTP request
 * has been serviced.
 */
public void detachThread()
{
	if ( threadBindListener != null )
		threadBindListener.preDetach(this);

	// Detach THIS ExpojoContext from THIS thread
	threadLocalExpojoContext.set(null);
}

/**
 * Called to destroy the ModelExposer.
 */
protected void finalize()
{
	ExpojoFoundation.sLog(ExpojoFoundation.LT_TRACE, "ModelExposer: " + hashCode() + " is being finalized");

	destroy();
}

/**
 * This should be called after construction to allow initialization of the exposer components
 * that have been added. Calls init on each ModelExposerComponent that has been added.
 * They may override ModelExposerComponent.init to set up references to other services
 * or repositories as required - in this case the super class init() must be called at
 * the end of the overwritten init() method.
 */
public void init()
{
	persistenceProvider.open();
	
	// Repositories are initialized before services so that services
	// can make use of them in their init methods if required
	Iterator<ExpojoComponent> i = components.values().iterator();
	
	while ( i.hasNext() )
	{
		ExpojoComponent component = i.next();
		
		if ( component instanceof RepositoryComponent )
		{
			RepositoryComponent repository = (RepositoryComponent)component;
		
			repository.init(this);
		}
	}	

	i = components.values().iterator();
	
	while ( i.hasNext() )
	{
		ExpojoComponent component = i.next();
		
		if ( component instanceof ServiceComponent )
		{
			ServiceComponent service = (ServiceComponent)component;
		
			service.init(this);
		}
	}	
}

/**
 * Returns a ThreadLocal version of the appropriate PersistenceProvider.
 * Each thread that calls this will then end up with a unique connection
 * to the persistence service with which to access the data store.
 */
public PersistenceProvider getPersistenceProvider()
{
	return persistenceProvider;
}




/**
 * Returns a repository associated with the given key.
 */
public RepositoryComponent getRepository(Class key)
{
	return (RepositoryComponent)components.get(key);
}

/**
 * Returns the ExpojoContext for the current thread. Each thread will be transiently
 * bound to its an ExpojoContext instance - thus avoiding issues with the Singleton pattern
 * and more traditional, obsfuscated and lower performance, reflection/AOP based dependency
 * injection frameworks (eg., Spring, Guava).
 */
public static ExpojoContext get()
{
	return (ExpojoContext)threadLocalExpojoContext.get();
}




/**
 * Destroys this model exposer instance, closing it's PersistenceProvider.
 */
public void destroy()
{
	if ( persistenceProvider != null )
	{
		// close the persistence provider for this ModelExposer
		persistenceProvider.close();
	
		persistenceProvider = null;
	}
}




/**
 * Adds a service component to the ExpojoContext and stores it in the components map
 * by the given key.
 */
public void addService(Class key, ServiceComponent serviceComponent)
{
	addComponent(key, serviceComponent);
}




/**
 * Adds a repository to the ExpojoContext and stores it in the component map via the
 * given key.
 */
public void addRepository(Class key, RepositoryComponent repositoryComponent)
{
	addComponent(key, repositoryComponent);
}




/**
 * Constructs the object
 */
public ExpojoContext(PersistenceProvider iPersistenceProvider)
{
	persistenceProvider = iPersistenceProvider;
	
	persistenceProvider.setExpojoContext(this);

	components = new HashMap<Object, ExpojoComponent>();
}

}


