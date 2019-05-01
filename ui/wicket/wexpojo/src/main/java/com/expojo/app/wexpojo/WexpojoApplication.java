// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2008,2019. Step Ahead Software Pty Ltd. All rights reserved.
 * Usage is governed by the terms of the Apache 2 License.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.expojo.app.wexpojo;

import java.lang.*;
import org.apache.wicket.protocol.http.WebApplication;
    
import com.expojo.app.wexpojo.ui.session.IWexpojoSession;
    
import com.sas.app.wexpojo.biz.website.IWebsiteRepository;
    
import com.sas.app.wexpojo.biz.website.IWebsite;


// -[KeepBeforeClass]-
import javax.servlet.ServletContext;

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import org.apache.wicket.markup.html.WebPage;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.expojo.app.wexpojo.ui.aspect.logout.LogoutPage;

import static org.apache.wicket.settings.IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE;


// -[Class]-

/**
 * Class Name : WexpojoApplication
 * Diagram    : Wexpojo - App Core
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : abstract
 * Describe here
 * 
 * @author Chris Colman
 */
public abstract 
class WexpojoApplication extends WebApplication
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Returns the base URL of the given website. e.g.
 * 
 * protocol://webistehostname:port
 * 
 * eg.,
 * https://www.mysite.com:8080
 * 
 * The protocol and port will typically be provided some some environmental parameters
 * (e.g. Tomcat's context.xml file)
 */
public abstract String getBaseUrlOfWebsite(IWebsite website);





/**
 * Returns the WebsitePage extending class to use to display errors.
 * We insist on a page that extends WebsitePage so that the error page is styled appropriately
 * for the website in which the error occurred.
 */
public abstract Class<? extends WebPage> getErrorPage();




/**
 * Creates and returns an instance of the application's authenticate (log in) page for
 * this application.
 * Any application is free to use whatever class for the authenticate page so long as
 * it extends WebPage.
 */
public abstract WebPage createAuthenticatePage(PageParameters pageParameters);

/**
 * Creates a new session when required by calling newWexPojoSession.
 */
public final Session newSession(Request request, Response response)
{
	return (Session)newWexpojoSession(request, response);
}




/**
 * Creates a new WebSession (or extended) object that implements IWexpojoSession.
 * This is called by newSession and is made abstract to enforce derived classes to create
 * a WebSession that is an implementation of IWexpojoSession.
 */
protected abstract IWexpojoSession newWexpojoSession(Request request, Response response);




/**
 * Gets the website repository.
 */
public abstract IWebsiteRepository getWebsiteRepository();




/**
 * Constructs the object
 */
public WexpojoApplication()
{

}

/**
 * Getter of the singletone.
 */
public static WexpojoApplication get()
{
	return (WexpojoApplication)WebApplication.get();
}

/**
 * Initializes the app post construction of the application object.
 */
public void init()
{
	super.init();
	
	// Mount the logout page - every webapp needs a log out page and there's not that much variability
	mountPage("/logout", LogoutPage.class);

	// Configure application settings

	// Do not raise an error if a Java component is not used by the markup - this lets
	// us provide a panel with optional components without raising an error for those
	// that is does not use
	getDebugSettings().setComponentUseCheck(false);

	boolean debugMode = true;
	
	if ( debugMode )
	{
		// This shouldn't be on, it causes wicket elements to be written to the output stream, which breaks CSS's ability
		// to reference immediate parent and immediate child elements
//		getMarkupSettings().setStripWicketTags(false);

		getMarkupSettings().setStripComments(false);

		getDebugSettings().setOutputComponentPath(true); // Wicket 7.x: .setComponentPathAttributeName("pb-component-path");
		getDebugSettings().setAjaxDebugModeEnabled(false);
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		getDebugSettings().setOutputMarkupContainerClassName(true);
	}
	else
	{
		// Use our error page instead of the bland "Unknown error" page
		getApplicationSettings().setInternalErrorPage(getErrorPage());
		getExceptionSettings().setUnexpectedExceptionDisplay(SHOW_INTERNAL_ERROR_PAGE);
	}


	// Don't throw exception if resource is not found
	getResourceSettings().setThrowExceptionOnMissingResource(false);
}


}


