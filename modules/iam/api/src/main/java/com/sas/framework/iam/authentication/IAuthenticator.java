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


// -[Class]-

/**
 * Class Name : IAuthenticator
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : interface
 * Describe here
 * 
 * @author Chris Colman
 */
public abstract 
interface IAuthenticator
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Finds a user with the given credentials (username and password and optionally realm)
 * and returns it. This is called to authenticate a user in the system.
 * If authentication failed null will be retuned otherwise a User object will be returned.
 */
public abstract IUser authenticateCredentials(String username, String password, boolean digested, IRealm realm);

}


