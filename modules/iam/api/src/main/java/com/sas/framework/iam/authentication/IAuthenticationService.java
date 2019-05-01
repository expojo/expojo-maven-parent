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
    
import com.sas.framework.iam.user.IUser;
    
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.framework.iam.authentication.IAuxiliaryChallenge;
    
import com.sas.framework.iam.authentication.IAuthenticator;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IAuthenticationService
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : interface
 * Interface for any authentication service.
 * 
 * @author Chris Colman
 */
public abstract 
interface IAuthenticationService
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Returns an IAuxiliaryChallenge for the given realm if available for the realm.
 * This should be used to determine if a newly registered account should proceed to registration
 * for the auxiliary challenge.
 * This should not be used to determine if, after a user passes first factor authentication
 * (eg., password), that they be challenged with the auxiliary authentication challenge.
 */
public abstract IAuxiliaryChallenge getAuxiliaryChallengeForRealm(IRealm realm);




/**
 * While an IAuxiliaryChallenge may be associated with this IAuthenticationService it
 * may not be required in every circumstance (e.g. business rules may be configured to
 * only require an auxiliary authentication challenge in certain cases: e.g. user has
 * logged in a device that they have never logged in from before).
 * This method will only return non null if the user is required to perform a challenge
 * for an auxiliary factor of authentication (e.g. enter a security code sent via email
 * or an SMS).
 */
public abstract IAuxiliaryChallenge userRequiresAuxiliaryChallenge(IUser user, String deviceId, IRealm realm);



/**
 * Calls authenticateCredentials on each of the  IAuthenticators in the authenticators
 * list until one passes authentication or the list is exhausted. 
 */
public abstract IUser authenticateCredentials(String username, String password, boolean digested, IRealm realm);

}


