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
package com.expojo.app.wexpojo.ui.aspect;

import java.lang.*;
import org.apache.wicket.markup.html.WebPage;
    
import com.sas.app.wexpojo.biz.website.IWebsite;
    
import com.expojo.app.wexpojo.WexpojoApplication;
    
import com.sas.framework.iam.user.IUser;
    
import com.expojo.app.wexpojo.ui.session.IWexpojoSession;


// -[KeepBeforeClass]-
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.request.mapper.parameter.*;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sas.framework.iam.user.IUser;

// -[Class]-

/**
 * Class Name : WebsitePage
 * Diagram    : Core website page and panel
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : abstract
 * WexPOJO supports multiple website within the one app.
 * 
 * Website is a page that is aware of the website that it is rendering for.
 * 
 * 
 * @author Chris Colman
 */
public abstract 
class WebsitePage extends WebPage
{
// -[KeepWithinClass]-

String wexpojoBaseUrl = "/pagebloom/";

String styleBaseUrl = wexpojoBaseUrl + "css/";


// -[Fields]-



/**
 * MarkupContainer for the entire body. This is used to enable us to do attribute modification
 * on the body tag if required.
 */
protected WebMarkupContainer body;



/**
 * ID of the body component in markup.
 */
public static final String bodyId = "body";
    
    protected IWebsite website;
    // Generated access methods for relationship: website
    public IWebsite getWebsite() { return website; }
    public void setWebsite(IWebsite website) { this.website = website; }


// -[Methods]-

/**
 * Returns protocol and domain name eg., https://mywebsite.com
 */
public String getProtocolAndDomain()
{
	return WexpojoApplication.get().getBaseUrlOfWebsite(getWebsite());
}

/**
 * Returns the current session.
 */
public IWexpojoSession getWexpojoSession()
{
	return (IWexpojoSession)getSession();
}




/**
 * Returns true if the page should only be accessed by authenticated users or false if
 * the page is not protected i.e. can be accessed by unauthenticated users.
 */
public boolean isProtected()
{
	if (website != null && website.getBehaviour().allPagesRequireAuth())
		return true;
	else
		return false;
}




/**
 * Pages returning true will not be redirected to log in page.
 * 
 * This should only ever be overridden by the page used for authenticating the user eg.,
 * a page with a log on form.
 */
public boolean isAuthenticatePage()
{
	return false;
}

/**
 * Returns body
 */
public WebMarkupContainer getBody()
{
	return body;
}

/**
 * Attempts to derive the user from the session.
 */
public IUser getUser()
{
	IWexpojoSession wexpojoSession = getWexpojoSession();
	
	if (wexpojoSession != null)
	{
		// Will only return not null if the user has been authenticated via all required factors of authentication
		return wexpojoSession.getUser();
	}
	
	return null;
}

/**
 * Describe here
 */
public void injectHeaderData()
{
	WebMarkupContainer globalStyle = new WebMarkupContainer("globalStyle");
	add(globalStyle);

	WebMarkupContainer templateThemeStyle = new WebMarkupContainer("templateThemeStyle");
	add(templateThemeStyle);

	WebMarkupContainer templateCustomStyle = new WebMarkupContainer("templateCustomStyle");
	add(templateCustomStyle);

	WebMarkupContainer siteStyle = new WebMarkupContainer("siteStyle");
	add(siteStyle);
	

/*
	A section of markup inside the <head> element should look like this:
	
	<link href="" pb:id="globalStyle" rel="stylesheet" type="text/css">
    <link href="" pb:id="templateThemeStyle" rel="stylesheet" type="text/css">
    <link href="" pb:id="templateCustomStyle" rel="stylesheet" type="text/css">
    <link href="" pb:id="siteStyle" rel="stylesheet" type="text/css">

	Files stored under the webapp/ directory should look like this:
	(assumes files are served from within the app's (probably exploded) war file)_
	
	/pagebloom/css/global.css
	/pagebloom/css/t/${templateId}/${themeId}.css
	/pagebloom/css/t/${templateId}/custom.css
	/pagebloom/o/${websiteId}/css/site.css
*/


	//#OPTIM - change the String concatenations to StringBuilder appends

	// Global CSS
	globalStyle.add(new AttributeModifier("href", styleBaseUrl.toString() + "global.css"));

	// Adds t/${templateId}/
	String templateStyleBaseUrl = styleBaseUrl + "t/" + website.getTemplateId() + "/";

	// Template theme specific CSS
	templateThemeStyle.add(new AttributeModifier("href", templateStyleBaseUrl.toString() + website.getThemeId() + ".css"));
	
	// Template custom CSS - CSS customization applied to all themes in a given template
	templateCustomStyle.add(new AttributeModifier("href", templateStyleBaseUrl.toString() + "custom.css"));

	// Site Specific CSS
	String siteStyleUrl = wexpojoBaseUrl + "o/" + website.getSiteId() + "/site.css";
	siteStyle.add(new AttributeModifier("href", siteStyleUrl));
}

/**
 * Describe here
 */
protected void onInitialize()
{
	super.onInitialize();

	IWexpojoSession wexpojoSession = getWexpojoSession();
	
	if (wexpojoSession != null)
	{
		wexpojoSession.processGenericPageParameters(this, getPageParameters());
	}
	
	if ( isProtected() && !isAuthenticatePage() )
	{
		boolean authenticated = false;
		
		IUser user = getUser();
		if (user != null)
		{
			if (wexpojoSession != null)
			{
				if (!wexpojoSession.hasUnsatisfiedAuxiliaryChallenge())
					authenticated = true;
			}
		}
		
		if (!authenticated)
			throw new RestartResponseAtInterceptPageException(WexpojoApplication.get().createAuthenticatePage(getPageParameters()));
	}

	injectHeaderData();
	
	body = new WebMarkupContainer(bodyId);
	add(body);
}

/**
 * Returns the variation (if any) for the associated website.
 */
public String getVariation()
{
	if (website != null)
		return String.valueOf(website.getTemplateId());
		
	return "";
}

/**
 * Constructs the object
 */
public WebsitePage(PageParameters parameters)
{
	super(parameters);

	website = getWebsiteFromDomainName();

}

/**
 * Derives the Website object from the domain name.
 */
public IWebsite getWebsiteFromDomainName()
{
	HttpServletRequest httpServletRequest =(HttpServletRequest)getRequest().getContainerRequest();

	String serverName = httpServletRequest.getServerName();
	
	IWebsite website = WexpojoApplication.get().getWebsiteRepository().findWebsiteByHostName(serverName);

	if (website == null)
		throw new RuntimeException("Could not find website associated with domain the URL: " + httpServletRequest.getRequestURL());

	return website;
}

/**
 * Constructs the object
 */
public WebsitePage()
{
	this(null);
}

}


