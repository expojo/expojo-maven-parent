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
package com.expojo.app.wexpojo.ui.aspect.logout;

import java.lang.*;
import com.expojo.app.wexpojo.ui.aspect.WebsitePage;
    
import com.sas.app.wexpojo.biz.website.IWebsite;
    
import com.expojo.app.wexpojo.WexpojoApplication;
    
import com.sas.framework.iam.user.IUser;
    
import com.expojo.app.wexpojo.ui.session.IWexpojoSession;


// -[KeepBeforeClass]-
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

// -[Class]-

/**
 * Class Name : LogoutPage
 * Diagram    : Core website page and panel
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Page that invalidates the session and returns to the logout destination page.
 * 
 * @author Chris Colman
 */
public 
class LogoutPage extends WebsitePage
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Constructs the object
 */
public LogoutPage(PageParameters parameters)
{
	super(parameters);
	
	// logout by inavalidating the session
	getSession().invalidate();


	String destUrl = null;

	if (parameters != null)
	{
		StringValue destUrlValue = parameters.get("redirectTo");

		if (destUrlValue != null)
			destUrl = destUrlValue.toString();
	}

	if (destUrl != null)
		throw new RedirectToUrlException(destUrl);
	else
		throw new RestartResponseException(WexpojoApplication.get().getHomePage());
}

/**
 * Constructs the object
 */
public LogoutPage()
{
	this(null);
}

}


