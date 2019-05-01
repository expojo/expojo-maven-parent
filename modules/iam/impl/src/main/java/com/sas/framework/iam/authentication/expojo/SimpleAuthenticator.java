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
import com.sas.framework.iam.authentication.AuthenticationException;
import com.sas.framework.iam.user.IUserRepository;


// -[Class]-

/**
 * Class Name : SimpleAuthenticator
 * Diagram    : Authentication Impl
 * Project    : Entity Model Framework
 * Type       : concrete
 * A very simple authenticator - not for production use.
 * 
 * @author Chris Colman
 */
public 
class SimpleAuthenticator implements IAuthenticator
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Set to true if password should be ignored. This might be the case if operating in
 * some kind of demonstration mode where we don't want to force users to enter a password.
 */
private boolean noPasswordCheck = false;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(SimpleAuthenticator.class);


// -[Methods]-

/**
 * Sets noPasswordCheck
 */
public void setNoPasswordCheck(boolean noPasswordCheck)
{
    this.noPasswordCheck = noPasswordCheck;
}

/**
 * Returns noPasswordCheck
 */
public boolean getNoPasswordCheck()
{
    return noPasswordCheck;
}

/**
 * Finds a user with the given credentials (username and password and optionally realm)
 * and returns it. This is called to authenticate a user in the system.
 * If authentication failed null will be retuned otherwise a User object will be returned.
 * If realm is not null then the first matching attempt will occur on all OrgRoleUser
 * that reference an OrgRoleInst for the give OrgUnit (IRealm).
 */
public IUser authenticateCredentials(String username, String password, boolean digested, IRealm realm)
{
	if (digested)
		throw new AuthenticationException("This authenticator does not support digested passwords - not for use in a production system");

	IUser foundUser = null;
	IUserRepository userRep = UserRepository.get();
	
	IUser user = userRep.findUserByUsername(username);

	if ( user != null )
	{
		boolean passwordMatch = false;

		if (noPasswordCheck)
			passwordMatch = true; // should never occur for production systems!!!
		else
			passwordMatch = password.equals(user.getPassword());
			
		if ( passwordMatch )
		{
			logger.info("authenticateCredentials: password OK");

			foundUser = user;
		}
		else
			logger.warn("authenticateCredentials: incorrect password supplied for username: " + username);
	}

	return foundUser;
}

}


