// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2006, 2014 Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.iam.authentication;

import java.lang.*;
    
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.framework.iam.user.IUser;


// -[KeepBeforeClass]-
import java.util.Map;


// -[Class]-

/**
 * Class Name : IAuxiliaryChallenge
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : interface
 * An auxiliary authentication factor that may be required after the initial identification+password
 * authentication has taken place.
 * 
 * @author Chris Colman
 */
public abstract 
interface IAuxiliaryChallenge
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Allows the auxliiary challenge to process authentication tokens etc., sent as page
 * parameters to the success URL.
 * Returns true if the challenge was successful, false if the challenge failed.
 */
public abstract boolean processGenericPageParameters(IUser user, IRealm realm, Map<String, String> pageParameters);




/**
 * Returns the URL that the user's browser will be redirected to so that they can perform
 * any registration required for the auxiliary authentication challenge mechanism.
 * e.g. For a SMS auxiliary authentication challenge the registration URL will display
 * a form asking the user to enter the mobile phone number that they would like security
 * codes sent to.
 * Note: For auxiliary challlenges where no registration is required this method should
 * return null.
 */
public abstract String getRegistrationUrl(String protocolAndDomain, IUser user, String siteVisitorId, IRealm realm);




/**
 * Returns the URL that the user's browser will be redirected to so that they can perform
 * the auxiliary authentication challenge.
 */
public abstract String getChallengeUrl(String protocolAndDomain, IUser user, String siteVisitorId, IRealm realm);

}


