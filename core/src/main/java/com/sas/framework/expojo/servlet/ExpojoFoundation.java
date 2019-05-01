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
package com.sas.framework.expojo.servlet;

import java.lang.*;
    
import com.sas.framework.expojo.ExpojoContextFactory;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.IWrappedOperation;
    
import com.sas.framework.expojo.ExpojoContext;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;




// -[Class]-

/**
 * Class Name : ExpojoFoundation
 * Diagram    : Expojo Servlet
 * Project    : ExPOJO Core
 * Type       : abstract
 * This is the foundation of any Expojo based application and is responsible for the
 * establishment of the business layer components and their initialization and, in particular,
 * the underlying persistence engine (typically an ORM) that is providing the transparent
 * persistence services for the model objects in the business layer.
 * It contains methods that are called for application initialization and shutdown.
 * It is designed to be extended by an application specific class which needs to override
 * the abstract methods..
 * You may also like to optionally override establishGenesisObjects to establish the
 * initial objects in the datastore when hasGenesisObjects returns true.
 * @see com.sas.framework.expojo.ExpojoContextFactory#createPersistenceProvider
 * @see com.sas.framework.expojo.ExpojoContextFactory#addComponents
 * 
 * This class currently depends on classes in the Java Servlet API but this could be
 * changed if the class was ever required to run in a non Servlet API environment (e.g.
 * Java desktop application) but that need has not yet arisen.
 * 
 * @author Chris Colman
 */
public abstract 
class ExpojoFoundation
{
// -[KeepWithinClass]-
static public final int LT_TRACE = 1;
static public final int LT_DEBUG = 2;
static public final int LT_INFO = 3;
static public final int LT_WARN = 4;
static public final int LT_ERROR = 5;
static public final int LT_FATAL = 6;



// -[Fields]-



/**
 * Returns this singleton.
 */
public static transient ExpojoFoundation me = null;



/**
 * Stores the applicaiton context.
 */
private String contextPath;



/**
 * Initially false but set to true at the completion of contextInitialized. The ExpojoServletFilter
 * will halt processing of the servlet filter chain until initialized becomes true. 
 */
protected static boolean initialized = false;
    
    protected ExpojoContextFactory expojoContextFactory;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(ExpojoFoundation.class);


// -[Methods]-

/**
 * Logs a message of the given type to System.out. This is not intended to be an exhaustive
 * logging mechanism nor can it be called directly. The implementation of this method
 * is to avoid exPOJO having any dependency on log4j or any other specific logging framework.
 */
private static void logToStdOut(int logType, String msg)
{
	String level;

	switch(logType)
	{
		case LT_FATAL:
			logger.error("FATAL: " + msg);
			break;
		case LT_ERROR:
			logger.error(msg);
			break;
		case LT_WARN:
			logger.warn(msg);
			break;
		case LT_INFO:
			logger.info(msg);
			break;
		case LT_DEBUG:
			logger.debug(msg);
			break;
		case LT_TRACE:
			logger.trace(msg);
			break;
		default:
			logger.error("Unspecified log level: " + msg);
	}
}

/**
 * Static logger method. Logs a message of the given type to System.out. This is not
 * intended to be an exhaustive logging mechanism but one that is intended to be overridden
 * in derived context classes to allow logging powerful logging frameworks like log4j.
 * The implementation of this method is to avoid exPOJO having any dependency on log4j
 * or any other specific logging framework.
 */
public static void sLogWarn(String msg)
{
	sLog(LT_WARN, msg);
}

/**
 * Static logger method. Logs a message of the given type to System.out. This is not
 * intended to be an exhaustive logging mechanism but one that is intended to be overridden
 * in derived context classes to allow logging powerful logging frameworks like log4j.
 * The implementation of this method is to avoid exPOJO having any dependency on log4j
 * or any other specific logging framework.
 */
public static void sLogInfo(String msg)
{
	sLog(LT_INFO, msg);
}

/**
 * Static logger method. Logs a message of the given type to System.out. This is not
 * intended to be an exhaustive logging mechanism but one that is intended to be overridden
 * in derived context classes to allow logging powerful logging frameworks like log4j.
 * The implementation of this method is to avoid exPOJO having any dependency on log4j
 * or any other specific logging framework.
 */
public static void sLogError(String msg)
{
	sLog(LT_ERROR, msg);
}




/**
 * Performs the parts of the app/context initialization that must occur before the persistence
 * framework has been initialized.
 * For example this method will typically be overridden to perform the reading of parameters
 * configured in context.xml or some other config file.
 * It can be assumed that the persistence framework has not yet been initialized by the
 * time this method is called and therefore initialization of objects that can determine
 * the persistence framework initialization behaviour can occur in this method.
 */
protected abstract void initContextPrePersistenceInit(final ServletContextEvent event);


/**
 * Initializatoin of the model.
 * Prerequisite: data model already exists.
 * Use: this method can be used to perform data migration etc., if required.
 */
public void initDataModel()
{
}


/**
 * Returns true if the datastore already has the genesis objects (i.e. if it is not a
 * new, blank system).
 * If this method returns false then establishGenesisObjects will be called to establish
 * genesis objects for a new blank system.
 * NOTE: This method is called each time the servlet context starts up so it is important
 * to check if a well known 'key' genesis object already exists (via a query method in
 * an appropriate repository) and only return false if this key object can not be found.
 */
public abstract boolean hasGenesisObjects();


/**
 * Returns details of the application such as Name version etc
 */
public String getCopyrightInfo()
{
	return "My App, Copyright 20XX My Software Company. All rights reserved.";
}


/**
 * Creates an ExpojoContext. Application developers should rarely need to call this.
 * This is called implicitly by the ExpojoServletFilter to establish an ExpojoContext
 * object for each new session or request (depending on whether expojoContext per request
 * is used).
 */
public ExpojoContext createExpojoContext()
{
	return expojoContextFactory.createExpojoContext();
}


/**
 * Performs the initialization of the app/context including calls to initialize the data
 * model and calls overrideable initApplication method once everything else is initialized.
 * It can be assumed that the persistence framework has been initialized by the time
 * this method is called.
 */
private final void initContextPostPersistenceInit(final ServletContextEvent event)
{
	// Perform each stage within its own ModelExposer/session to avoid holding
	// a single connection open for what can be a long start up - especially if
	// migrates are involved
	
	ModelExposer.executeWrappedCreate
	(
		new IWrappedOperation()
		{
			public void execute(Object arg0, Object arg1)
			{
				if ( !hasGenesisObjects() )
				{
					// Create from scratch the essential entities in of the data model
					log(LT_INFO, "Establishing new data model...");
					establishGenesisObjects(event);
				}
			}
		}
	);	
	
	// At this point the data model is established so now initialize it
	// This is where any data migration takes place if necessary
	log(LT_INFO, "Initializing data model...");
	ModelExposer.executeWrappedCreate
	(
		new IWrappedOperation()
		{
			public void execute(Object arg0, Object arg1)
			{
				initDataModel();
			}
		}
	);	
	
	// Allows derived class to perform any special initialization for the application
	ModelExposer.executeWrappedCreate
	(
		new IWrappedOperation()
		{
			public void execute(Object arg0, Object arg1)
			{
				initApplication();
			}
		}
	);	
}


/**
 * Logs a message of the given type to System.out. This is not intended to be an exhaustive
 * logging mechanism but one that is intended to be overridden in derived context classes
 * to allow logging powerful logging frameworks like log4j. The implementation of this
 * method is to avoid exPOJO having any dependency on log4j or any other specific logging
 * framework.
 */
public void log(int logType, String msg)
{
	logToStdOut(logType, msg);
}




/**
 * This method is called by the ExpojoServletFilter.doFilter method when there is no
 * ExpojoContext bound to the session yet i.e. it must be a new session.
 *  This method returns an ExpojoContext which will then be bound to the new session
 * (or new request if expojoContextPerRequest is true) by the ExpojoServletFilter. 
 * The algorithm for establishing the ExpojoContext can be completely under the control
 * if the application chooses to override this method in its extended ExpojoFoundation
 * class or it can simply inherit the behaviour provided by this class which it to create
 * a new ExpojoContext at each call - which can very innefficient when the HTTP clients
 * are robots that do not accept session identification mechanisms like cookies and URL
 * rewriting with session IDs.
 * An overridden establishExpojoContext method should consider the following scenarios:
 * Possible Secanrios:
 * 1. A normal user using a browser - the app should create a new a ModelExposer
 * 2. A crawler - crawlers don't support cookies and will ignore URL rewriting
 * 	attempts so it is not possible to have one session span more than one HTTP
 *  request - each request will return false for getSession(false);
 * In order to avoid creating a new ModelExposer and thus a PersistenceProvider and Level
 * 1 cache for each new request from the crawler the application should 
 * associate a ExpojoContext with the crawler and return that when required rather than
 * return a new ModelExposer each time a request is made. The synchronized section below
 * will ensure that only one thread can service a specific crawler's thread at a time
 * ensuring that the crawler's PersistenceProvider is not accessed concurrently.
 */
public ExpojoContext establishExpojoContext(ServletRequest servletRequest)
{
	// The default behaviour is to treat robots like any other client (even though
	// they support cookies and ignore URL rewriting) and just create a new
	// ModelExposer for each of their requests - which is very innefficient
	// in terms of CPU and memory usage
	return createExpojoContext();
}


/**
 * Returns details of the application such as Name, Copyright info etc.,
 */
public String getApplicationInfo()
{
	return "My App Version 0.00. Database version 0";
}



/**
 * Static logger method. Logs a message of the given type to System.out. This is not
 * intended to be an exhaustive logging mechanism but one that is intended to be overridden
 * in derived context classes to allow logging powerful logging frameworks like log4j.
 * The implementation of this method is to avoid exPOJO having any dependency on log4j
 * or any other specific logging framework.
 */
public static void sLog(int logType, String msg)
{
	if (me != null)
		me.log(logType, msg);
	else
		logToStdOut(logType, msg); // use default system out for logging until expojoContext has been initialized
}


/**
 * Sets contextPath
 */
public void setContextPath(String contextPath)
{
    this.contextPath = contextPath;
}


/**
 * Returns contextPath
 */
public String getContextPath()
{
    return contextPath;
}


/**
 * Called when the application context is being destroyed. Typically an application will
 * not override this method but override destroyApplication if application specific destruction
 * is required.
 */
public void contextDestroyed(ServletContextEvent event)
{
	log(LT_INFO, "Stopping application...");
	
	destroyApplication();
	
	expojoContextFactory.close();
	expojoContextFactory = null;

	log(LT_INFO, "Application stopped.");
}


/**
 * Called by the framework to allow application specific destruction behaviour. Override
 * if  required.
 */
public void destroyApplication()
{
}


/**
 * Overridden in derived classes to perform any applicaiton specific initialization,
 * if required, after the framework initialization has taken place.
 */
public void initApplication()
{
}




/**
 * Genesis objects are the essential, persistent, system objects that must be created
 * right at the beginning of the life of a new system and from which all other objects
 * will go forth and multiply. 
 * If hasGenesisObjects() returns false then this method is called to create the genesis
 * objcets.
 * If you're a creationist then this method represents the time in which a supernatural
 * entity called god, built the earth and everything around it. If you're an evolutionist
 * then this method represents the big bang. Take your pick.
 * This method is wrapped by code that has created a ModelExposer object and attached
 * it to this thread.
 * 
 * This method should ideally be located in a core ServiceComponent of the application.
 */
public void establishGenesisObjects(ServletContextEvent event)
{
}


/**
 * Performs application initialisation and establishment of the PersistenceProviderFactory.
 * It is not necessary to override this method but if it is then be sure to call this
 * method first in the overridden method. Typically any application specific initialization
 * is done by overriding initApplication which this method calls.
 */
public synchronized void contextInitialized(ServletContextEvent event)
{
	log(LT_INFO, "Initializing context...");
	log(LT_INFO, getApplicationInfo());
	log(LT_INFO, getCopyrightInfo());
	
	// Setup the contextPath variable
	ServletContext servletContext = event.getServletContext();

	String realPath = servletContext.getRealPath("");

	if ( realPath != null && realPath.length() > 0 )
	{	
		// Must escape if separator is '\' (i.e. the escape char!)
		String fileSeparator = File.separator;
		if ( fileSeparator.equals("\\") )
			fileSeparator = "\\\\";
		
		String pathComponents[] = realPath.split(fileSeparator);

		// The last element is the contextPath
		contextPath = pathComponents[pathComponents.length-1];
		if ( contextPath.equals("ROOT") )
			contextPath = "";
		else
			contextPath = "/" + contextPath;
	}
	else
		contextPath = "";

	// Perform init required before initialization of persistence framework
	initContextPrePersistenceInit(event);

	// If no modelExposerFactory has been configured then configure one
	expojoContextFactory = ExpojoContextFactory.get();
	if ( expojoContextFactory == null )
	{
		// Get the derived class to create a specific persistence provider factory
		expojoContextFactory = createExpojoContextFactory(event);
	}

	// Initialize the ModelExposerFactory
	log(LT_INFO, "Initializing persistence engine...");

	expojoContextFactory.init();

	log(LT_INFO, "Persistence engine initialized");

	log(LT_INFO, "Starting application...");

	// Perform init that requires an activated persistence framework
	initContextPostPersistenceInit(event);
	
	// Set initialized now that initialization if has completed
	initialized = true;

	log(LT_INFO, "Application started.");
	log(LT_INFO, "Context initialization complete.");
}


/**
 * Returns this singleton object.
 */
public static ExpojoFoundation get()
{
	return me;
}


/**
 * Sets 'me' to this.
 */
public ExpojoFoundation()
{
	me = this;
}




/**
 * Returns expojoContextFactory. Called by ExpojoServletFilter when creating a ExpojoContext.
 */
public ExpojoContextFactory getExpojoContextFactory()
{
	return expojoContextFactory;
}




/**
 * Overridden in a derived class to create the ExpojoContextFactory.
 */
public abstract ExpojoContextFactory createExpojoContextFactory(ServletContextEvent event);


}


