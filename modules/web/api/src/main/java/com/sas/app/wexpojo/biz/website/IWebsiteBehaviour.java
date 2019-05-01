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
package com.sas.app.wexpojo.biz.website;

import java.lang.*;
import java.io.Serializable;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IWebsiteBehaviour
 * Diagram    : Website API
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * Specifies how a particular website should behave.
 * For example:
 * Should the home page allow unauthenticated access?
 * 
 * @author Chris Colman
 */
public abstract 
interface IWebsiteBehaviour extends Serializable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Returns true if the website has auxiliary challenge authentication available.
 * It does not mean that every log in will invoke an auxiliary authentication challenge
 * - that is left to the application to decide based on business rules.
 * e.g. auxiliary challenge may only be required if the user logs in via a device that
 * they have not previously logged in from.
 */
public abstract boolean supportsAuxiliaryChallenge();




/**
 * Returns true if all pages require an authenticated user - except for the Authenticate
 * pages.
 */
public abstract boolean allPagesRequireAuth();

}


