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
package com.sas.framework.iam.authentication.expojo;

import java.lang.*;
import com.sas.framework.iam.authentication.IAuthenticator;
    
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.framework.iam.user.IUser;
    
import com.sas.framework.expojo.Ex;
    
import com.sas.framework.iam.user.expojo.UserRepository;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-
import com.sas.framework.iam.user.IUserRepository;


// -[Class]-

/**
 * Class Name : AnonymousAuthenticator
 * Diagram    : Authentication Impl
 * Project    : Entity Model Framework
 * Type       : concrete
 * A non production authenticator that always passes every authentication - it does not
 * check username or password.
 * 
 * Regardless of the username or password entered it always returns the "anonymous" user.
 * 
 * This is for TESTING purposes only and should NEVER be used in a production environment.
 * 
 * @author Chris Colman
 */
public 
class AnonymousAuthenticator implements IAuthenticator
{
// -[KeepWithinClass]-


// -[Fields]-

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(AnonymousAuthenticator.class);


// -[Methods]-

/**
 * Finds a user with the given credentials (username and password and optionally realm)
 * and returns it. This is called to authenticate a user in the system.
 * If authentication failed null will be retuned otherwise a User object will be returned.
 * If realm is not null then the first matching attempt will occur on all OrgRoleUser
 * that reference an OrgRoleInst for the give OrgUnit (IRealm).
 */
public IUser authenticateCredentials(String username, String password, boolean digested, IRealm realm)
{
	IUserRepository userRep = UserRepository.get();
	
	return userRep.findUserByUsername("anon@ymous.org");
}

}


