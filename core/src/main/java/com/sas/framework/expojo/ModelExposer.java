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
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ModelExposer
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : abstract
 * An ExpojoContext provides all the dependencies that any code may require including:
 * repository components, service components and a persistence provider which supports
 * the main operations of the underlying ORM e.g. persist, attach, delete, evict, getObject,
 * getObjectId etc.,
 * In Expojo an ExpojoContext is bound to the current thread via a number of mechanisms.
 * This is known as TDP (Thread Dependency Provisioning).
 * In TDP, unlikle DI, individual objects do not need to each be injected with the dependency
 * when required, instead the dependencies are provisioned via the ExpojoContext object
 * and are thus available to any code executing a bound thread without having to "pre-litter"
 * that code with dependency injection importing annotated attribute declarations with
 * all the dependencies one *thinks* they might need.
 * In a web app the ExpojoFilter can be configured so that every thread servicing a http
 * request has an ExpojoContext automatically bound to it at the start of the request
 * processing and automatically unbound after the http request has been completed.
 * Any code executing in the thread that is servicing the HTTP request thus has quick
 * and simple, high performance access to the ExpojoContext via ExpojoContext.get().
 * Expojo is both highly productive during development time and highly performant at
 * runtime compared to less efficient traditional dependency injection mechanisms which
 * involve a more invasive, physical injection of the dependency. 
 * 
 * @author Chris Colman
 */
public abstract 
class ModelExposer
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Returns a ThreadLocal version of the appropriate PersistenceProvider. Each thread
 * that calls this will then end up with a unique connection to the persistence service
 * with which to access the data store.
 */
public abstract PersistenceProvider getPersistenceProvider();





/**
 * Destroys this model exposer instance, closing it's PersistenceProvider.
 */
public abstract void destroy();




/**
 * Returns a repository associated with the given key.
 */
public abstract RepositoryComponent getRepository(Class key);




/**
 * Returns the service associated with the given key.
 */
public abstract ServiceComponent getService(Class key);




/**
 * Returns the ExpojoComponent with the given Id.
 * This method should not be called prior to a ModelExposer being completely initialized.
 * This means that when ExpojoComponent's are being initialized themselves they must
 * use getComponent on the ModelInstance provided to their init method instead of using
 * the static get method. 
 */
public static ExpojoComponent get_legacy(Object id)
{
	return ExpojoContext.get_legacy(id);
}




/**
 * Adds a repository to the model exposer.
 */
public abstract void addRepository(Class key, RepositoryComponent repositoryComponent);




/**
 * Adds a service component to the model exposer.
 */
public abstract void addService(Class key, ServiceComponent serviceComponent);




/**
 * Static convenience method to return a persistence provider.
 */
public static PersistenceProvider pp()

{
	return get().getPersistenceProvider();
}




/**
 * Detaches a ModelExposer from the current thread - enabling it to be garbage collected
 * if necessary (assuming ThreadLocal had the last remaining reference to the ModelExposer).
 * This is called automatically by the ExpojoServletFilter after the current HTTP request
 * has been serviced.
 */
public abstract void detachThread();





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
public abstract void attachThread();




/**
 * Returns the ExpojoContext for the current thread. Each thread will be transiently
 * bound to its an ExpojoContext instance - thus avoiding issues with the Singleton pattern
 * and more traditional, obsfuscated and lower performance, reflection/AOP based dependency
 * injection frameworks (eg., Spring, Guava).
 */
public static ExpojoContext get()

{
	return ExpojoContext.get();
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

}


