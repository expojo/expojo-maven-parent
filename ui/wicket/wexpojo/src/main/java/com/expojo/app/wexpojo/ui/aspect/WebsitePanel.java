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
import org.apache.wicket.markup.html.panel.Panel;
    
import com.sas.app.wexpojo.biz.website.IWebsite;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : WebsitePanel
 * Diagram    : Core website page and panel
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Base class for all panels that can have a specific website context.
 * 
 * @author Chris Colman
 */
public 
class WebsitePanel extends Panel
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Returns protocol and domain name eg., https://mywebsite.com
 */
public String getProtocolAndDomain()
{
	return getWebsitePage().getProtocolAndDomain();
}




/**
 * Constructs the object
 */
public WebsitePanel(String id)
{
	super(id);
}

/**
 * Returns the IWebsite for the WebsitePage that this WebsitePanel is part of.
 */
public IWebsite getWebsite()
{
	return getWebsitePage().getWebsite();
}

/**
 * Returns the WebsitePage that this page is a direct or indirect child of.
 */
public WebsitePage getWebsitePage()
{
	return findParent(WebsitePage.class);
}

}


