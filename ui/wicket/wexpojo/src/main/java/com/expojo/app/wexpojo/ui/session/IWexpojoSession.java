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
package com.expojo.app.wexpojo.ui.session;

import java.lang.*;
    
import com.sas.framework.iam.user.IUser;
    
import org.apache.wicket.markup.html.WebPage;
    
import com.sas.app.wexpojo.ui.session.IWorkflowOrchestrator;


// -[KeepBeforeClass]-
import java.util.Map;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.sas.framework.iam.realm.IRealm;


// -[Class]-

/**
 * Class Name : IWexpojoSession
 * Diagram    : Session
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * Interface implemented by all WexPojoSession classes.
 * 
 * This has methods for extracting the authenticated IUser from the session. Implementation
 * of this is left up to the application.
 * 
 * Authenticated user implies a fully authenticated user. In multi factor authentication
 * with 1, 2, 3, n factors of authentication the getUser method should return null unless
 * all required factors of authentication have been sucessfully processed.
 * 
 * @author Chris Colman
 */
public abstract 
interface IWexpojoSession
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Detaches the session. This needs to be overridden to detach any associated IWorkflowOrchestrator
 */
public abstract void detach();




/**
 * Returns the workflow orchestrator if there is one.
 */
public abstract IWorkflowOrchestrator getWorkflowOrchestrator();




/**
 * Sets the workflow orchestrator.
 */
public abstract void setWorkflowOrchestrator(IWorkflowOrchestrator iWorkflowOrchestrator);




/**
 * Allows a general app wide processing of certain generic page parameters if required.
 * Any page specific parameters will likely not be handled by this method.
 */
public abstract void processGenericPageParameters(WebPage webPage, PageParameters pageParameters);



/**
 * Marks the session as having an unsatisfied auxiliary authentication challenge.
 */
public abstract void setUnsatisfiedAuxiliaryChallenge(boolean iUnsatisfiedAuxiliaryChallenge);




/**
 * Returns true if an auxiliary authentication challenge was required AND has not been
 * satisfied.
 */
public abstract boolean hasUnsatisfiedAuxiliaryChallenge();




/**
 * Returns a unique identifier for the visitor visiting this website.
 * This value is typically stored as a cookie in the user's browser.
 * A single user may access the website from multiple browsers across a range of different
 * PCs, Laptops and handheld devices. There will be a separate, unique site visitor Id
 * for each of these browsers.
 */
public abstract String getSiteVisitorId();




/**
 * Attempts to authenticate with the given username and password.
 * Returns an implementation of IUser if successful or null if not.
 * realm is an optional parameter that may be null. If not null then the authentication
 * process is widened to consider a match for username and password in any org specific
 * roles within the realm.
 * Note: the realm parameter is not used for pagebloom standard. It is currently a pagebloom
 * enterprise feature only.
 */
public abstract IUser authenticate(String username, String password, boolean enableAutoSignIn, IRealm realm);




/**
 * Returns the authenticated user associated with this session.
 * 
 * This will return null unless all required authentication factors have been processed
 * and succeeded.
 * 
 * NOTE: it is possible that an implementation may have a reference to the IUser but
 * should NOT return it due to not all authentication factor processes being completed
 * successfully.
 */
public abstract IUser getUser();

}


