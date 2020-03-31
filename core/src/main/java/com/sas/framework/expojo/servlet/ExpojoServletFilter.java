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
import javax.servlet.Filter;
    
import com.sas.framework.expojo.ModelExposer;
    
import javax.servlet.ServletContext;
    
import javax.servlet.ServletContextEvent;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;
    
import javax.servlet.ServletException;
    
import com.sas.framework.expojo.ExpojoContext;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-
import java.lang.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


// -[Class]-

/**
 * Class Name : ExpojoServletFilter
 * Diagram    : Expojo Servlet
 * Project    : ExPOJO Core
 * Type       : concrete
 * A servlet filter that automatically implements dependency injection by calls to attachThread
 * at the start of doFilter and detachThread after the filter chain/servlet has generated
 * the response. Any servlet for which this filter is assigned can be guaranteed to have
 * get:ModelExposer return a valid, session specific ModelExposer object.
 * This filter accepts an ignorePaths parameter in the web.xml which can contain a comma
 * separated list of paths (no spaces, no leading slash) which should NOT be wrapped
 * by exPOJO.
 * Typically ignorePaths is used for paths such as images/, resources/ etc., Such http
 * requests are usually satisfied by the servlet container without any datastore access
 * and so there is no need to wrap the request within an exPOJO dependency injection.
 * 
 * @author Chris Colman
 */
public 
class ExpojoServletFilter implements Filter
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Name of the attribute used to store the ModelExposer in the session.
 */
public static final String ATTR_MODEL_EXPOSER = "expojo.modex";



/**
 * RequestId counter.
 */
private int requestId = 0;



/**
 * Stores the paths to be ignored as specified in the ignorePaths parameter.
 */
private HashSet<String> ignorePaths = new HashSet<String>();



/**
 * true if a new exposer is created for every http request
 * false if exposer/session affinity should be used so that the exposer is attached to
 * the session and reused for all requests for the same session (legacy mode - new code
 * should always use exposerPerRequest=true)
 */
private static boolean exposerPerRequest = false;
    
    protected ServletContext servletContext;
    
    protected ExpojoFoundation expojoFoundation;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(ExpojoServletFilter.class);


// -[Methods]-

/**
 * Returns the name of the application specific class that extends ExpojoFoundation.
 */
public String getExpojoFoundationClassName(FilterConfig filterConfig)
  throws ServletException
{
	String expojoFoundationClassName = filterConfig.getInitParameter("expojoFoundationClassName");
	
	// Transitional only!! Remove after all application web.xml files have been updated to the new parameter name
	if ( expojoFoundationClassName == null || expojoFoundationClassName.length() == 0 )
		expojoFoundationClassName = filterConfig.getInitParameter("expojoContextClassName");

	return expojoFoundationClassName;
}




/**
 * Returns true if exposer per request mode is used.
 */
public static boolean isExposerPerRequestMode()
{
	return exposerPerRequest;
}




/**
 * Logs a trace message via the ExpojoFoundation
 */
public void logTrace(String msg)
{
	ExpojoFoundation.sLog(ExpojoFoundation.LT_TRACE, msg);
}




/**
 * Logs a warning message via the ExpojoFoundation
 */
public void logWarn(String msg)
{
	ExpojoFoundation.sLog(ExpojoFoundation.LT_WARN, msg);
}




/**
 * Logs an info message via the ExpojoFoundation
 */
public void logInfo(String msg)
{
	ExpojoFoundation.sLog(ExpojoFoundation.LT_INFO, msg);
}




/**
 * Logs an error message via the ExpojoFoundation
 */
public void logError(String msg)
{
	ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, msg);
}

/**
 * Call filter to performing filter operations and hand on request to the next filter
 * in the chain.
 */
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws java.io.IOException, ServletException
{
	HttpServletRequest httpServletRequest = (HttpServletRequest)request;

	String contextPath = httpServletRequest.getContextPath();
	String servletPath = httpServletRequest.getServletPath();
	
	// [START] This block borrowed from Wicket source code, WicketFilter class
	// licensed under Apache license which allows for this type of reuse
	final String relativePath;
	if (contextPath.length() > 0)
		relativePath = contextPath + "/" + servletPath;
	else
		relativePath = servletPath;

	if (ignorePaths.size() > 0 && relativePath.length() > 0)
	{
		for (String path : ignorePaths)
		{
			if (relativePath.startsWith(path))
			{
				//logWarn("doFilter: bypassing ExpojoServletFilter for ignored path: '" + relativePath + "'");
				chain.doFilter(request, response);
				return;
			}
		}
	}
	// [END]

	//logWarn("doFilter: requested URL: '" + relativePath + "'");
	
	boolean alreadyWrapped = true;
	ExpojoContext expojoContext = ExpojoContext.get();

	// exposer per request should never reuse a modelexposer attached to the thread recently allocated to service a http request
	if ( exposerPerRequest && expojoContext != null )
	{
		logWarn("doFilter: thread had attached ME in exposerPerRequest mode");
		expojoContext.detachThread();
		expojoContext = null;
	}

	if (expojoContext == null)
	{
		alreadyWrapped = false;
		HttpSession session = null;

		if (!exposerPerRequest)
		{
			//logWarn("doFilter: exposerPerRequest == false");
			// We should always get a session here (maybe??) - creating one if none already exists - this is innefficient
			// when it comes to serving static resources but a filter parameter should be set up to avoid 
			// using this filter for static resources
			session = httpServletRequest.getSession(true);
			
			if ( session != null )
			{
				//logWarn("doFilter: session exists");
			
				// Get the ExpojoContext if one already exists or create it
				expojoContext = (ExpojoContext)session.getAttribute(ATTR_MODEL_EXPOSER);
			}
			else
			{
				//logWarn("doFilter: session does NOT exist");
			}
		}
		
		if ( expojoContext == null )
		{
			// No expojoContext in the session yet so it must be a new session.
			// The application class that extends ExpojoSerlvetContextListener 
			// is responsible for providing a ExpojoContext that will be
			// bound to this new session.
			// Possible Secanrios:
			// 1. A normal user using a browser - the app will create a new a ExpojoContext
			// 2. A crawler - crawlers don't support cookies and will ignore URL rewriting
			// attempts so it is not possible to have one session span more than one HTTP
			// request - each request will return false for getSession(false);
			// In order to avoid creating a new ExpojoContext and thus a PersistenceProvider and
			// Level 1 cache for each new request from the crawler the application should 
			// associate a ExpojoContext with the crawler and return that when required rather
			// than return a new ExpojoContext each time a request is made. The synchronized section
			// below will ensure that only one thread can service a specific crawler's thread
			// at a time ensuring that the crawler's PersistenceProvider is not accessed concurrently.
		
			// ExpojoContext not yet created so create it and assign it to the session
			expojoContext = ExpojoFoundation.get().establishExpojoContext(request);
			
			//logInfo("doFilter: ME is " + expojoContext);
			
			if (!exposerPerRequest && session != null )
				session.setAttribute(ATTR_MODEL_EXPOSER, expojoContext);
		}
	}
	
	if ( expojoContext == null )
		logError("doFilter: ME is null before synchronized block... how can this happen?");
	
	// exposerPerRequest == true
	// no point in synchronizing on expojoContext as each thread gets its own expojoContext anyway.
	// Does this mean we need to synchronize at all? The main reason for synchronization before
	// was because PersistenceManagers are not thread safe - if each request has it's own thread therefore
	// it's own ExpojoContext/PersistenceManager we don't really need to be concerned at the non thread
	// safe nature of the PersistenceManager.
	// 
	// exposerPerRequest == false
	// We must prevent multiple requests for the same session from being handled concurrently
	// because the PersistenceProvider is most likely not thread safe
	synchronized (expojoContext)
	{
		try
		{
			// Dependency injection takes place here by attaching this session's ExpojoContext 
			// to this current thread that is about to be used to service this request
			if (!alreadyWrapped)
			{
				expojoContext.attachThread();
		
				if (expojoContext.persistenceProvider.getTxDepth() != 0)
				{
					logError("exPOJO: ERROR: TXN Depth not zero on entry");
					
					// Clears any active transaction and sets tx depth to 0
					expojoContext.persistenceProvider.clearTx();
				}
				
				try
				{
					// If request comes in with a parameter refreshCache with value '1' then the
					// cache will be refreshed
					// Update any objects in the PP cache that have been updated by other PPs
					String refreshCacheValue = request.getParameter("refreshCache");
					if (refreshCacheValue != null && refreshCacheValue.equals("1"))
						expojoContext.persistenceProvider.refreshAll();
				}
				catch (Exception e)
				{
					logError("exPOJO: Exception while refreshing objects in cache: " + e);
				}
				
				// Begin a transaction for this request
				expojoContext.persistenceProvider.beginTx();
			}
			
			// Call next filter in the chain or the servlet if no more filters
			chain.doFilter(request, response);

			if (!alreadyWrapped)
			{
				// Don't detachBounds yet incase non detached objects still required in the servicing
				// of the original request
				expojoContext.detachBounds();

				// Commit any changes made during this init call
				expojoContext.persistenceProvider.commitTx();
			}
		}
		catch (ServletException se)
		{
			// need to catch ServletException first as it requires a different method to 
			// access the cause - strange but true.
			logError("ServletException thrown while servicing HTTP request: " + se);
			logError("The following might help:");
			se.printStackTrace();
		
			for (Throwable cause = se.getRootCause(); cause != null; cause = cause.getCause())
			{
				logError("Caused by: " + cause);
				cause.printStackTrace();
			}
			
			expojoContext.persistenceProvider.rollbackTx();
		}
		catch (Exception e)
		{
			logError("Exception thrown while servicing HTTP request: " + e);
			logError("The following might help:");
			e.printStackTrace();
		
			for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause())
			{
				logError("Caused by: " + cause);
				cause.printStackTrace();
			}
			
			expojoContext.persistenceProvider.rollbackTx();
		}
		finally
		{
			if (!alreadyWrapped)
			{
				if (expojoContext.persistenceProvider.getTxDepth() != 0)
					logError("exPOJO: ERROR: TXN Depth not zero on exit");

				// Request has been processed using this current thread so detach the ExpojoContext 
				// from this thread
				expojoContext.detachThread();
			}
		}
	}
	
	if (exposerPerRequest && !alreadyWrapped)
	{
		//logInfo("doFilter: destroying ExpojoContext");
		expojoContext.destroy();
	}
}

/**
 * Does nothing.
 */
public void init(FilterConfig filterConfig)
  throws ServletException
{
	logInfo("Initializing ExpojoServletFilter...");

	servletContext = filterConfig.getServletContext();
	
	String expojoFoundationClassName = getExpojoFoundationClassName(filterConfig);
	
	if ( expojoFoundationClassName == null || expojoFoundationClassName.length() == 0 )
		throw new ServletException("Error initializing ExpojoServletFilter: expojoFoundationClassName parameter <init-param> element not specified or empty - it needs to be the fully qualified class name of the expojo context class that extends ExpojoFoundation");

	try
	{
		Class<?> clazz = Class.forName(expojoFoundationClassName);
		expojoFoundation = (ExpojoFoundation)clazz.newInstance();
	}
	catch(Exception e)
	{
		throw new ServletException("Failed to instantiate expojo servlet class: " + expojoFoundationClassName, e);
	}

	// expojoFoundation must be configured if reached this point

	// This must be done first before the ExpojoFoundation is initialized
	// because the ExojoServletContextListener may need to know which mode is used to determine which
	// properties file to use for the ORM etc.,
	String exposerPerRequestStr = filterConfig.getInitParameter("exposerPerRequest");
	if (exposerPerRequestStr != null )
	{
		if (exposerPerRequestStr.equals("true") )
			exposerPerRequest = true;
	}

	logInfo("Using a separate Exposer per request: " + exposerPerRequest);


/*
	// Wait until Context is initilized before commencement
	while ( !ExpojoFoundation.initialized )
	{
		try 
		{
			Thread.currentThread().sleep(200);
		}
		catch (InterruptedException ie)
		{
		}
	}
*/
	// [START] This block borrowed from Wicket source code, WicketFilter class
	// licensed under Apache 2 license which allows for this type of reuse
	String ignorePathsValue = filterConfig.getInitParameter("ignorePaths");

	logInfo("The following paths will not be wrapped by expojo: ");

	if ( ignorePathsValue != null && ignorePathsValue.length() != 0 )
	{
		String[] ignorePathsArray = ignorePathsValue.split(",");
		for (String ignorePath : ignorePathsArray)
		{
			// Always store with leading / because the servlet methods return paths with leading /
			// so it makes comparison easier/quicker
			if (!ignorePath.startsWith("/"))
			{
				ignorePath = "/" + ignorePath;
			}
			
			ignorePaths.add(ignorePath);
			logInfo(ignorePath);
		}
	}
	// [END]

	// Initialize the expojo context
	expojoFoundation.contextInitialized(new ServletContextEvent(servletContext));
}

/**
 * Does nothing.
 */
public void destroy()
{
	// Invoke destruction of the expojo foundation
	expojoFoundation.contextDestroyed(new ServletContextEvent(servletContext));
}

}


