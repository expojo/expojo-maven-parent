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
import com.expojo.app.wexpojo.ui.aspect.WebsitePage;
    
import com.sas.app.wexpojo.biz.website.IWebsite;
    
import com.expojo.app.wexpojo.WexpojoApplication;
    
import com.sas.framework.iam.user.IUser;
    
import com.expojo.app.wexpojo.ui.session.IWexpojoSession;


// -[KeepBeforeClass]-

import org.apache.wicket.request.mapper.parameter.PageParameters;

// -[Class]-

/**
 * Class Name : ProtectedPage
 * Diagram    : Core website page and panel
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * A page whose access is protected from unauthenticated access.
 * If a browser attempts to view such a page and there is no currently authenticated
 * user associated with the session then the browser will be redirected to the authentication
 * page. After successful log in they will be redirected back to the page they were attempting
 * to view.
 * While it is possible to make any class protected by overriding isProtected() this
 * class acts as both a convenience and a marker class - any protected class can simply
 * derive from this class to make it obvious to developers viewing the code that is is
 * protected.
 * 
 * @author Chris Colman
 */
public 
class ProtectedPage extends WebsitePage
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Returns true if the page should only be accessed by authenticated users or false if
 * the page is not protected i.e. can be accessed by unauthenticated users.
 */
public boolean isProtected()
{
	return true;
}

/**
 * Constructs the object
 */
public ProtectedPage(PageParameters parameters)
{
	super(parameters);
}



/**
 * Constructs the object
 */
public ProtectedPage()

{
	this(null);
}

}


