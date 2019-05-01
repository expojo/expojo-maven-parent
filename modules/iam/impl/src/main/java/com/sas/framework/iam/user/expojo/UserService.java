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
package com.sas.framework.iam.user.expojo;

import java.lang.*;

import com.sas.framework.iam.user.IUserService;
import com.sas.framework.expojo.ServiceComponent;
    
import com.sas.framework.expojo.Ex;
    
import com.sas.framework.iam.user.impl.User;
    
import com.sas.framework.iam.user.AbstractUserSession;
    
import com.sas.framework.iam.user.IUser;
    
import com.sas.framework.iam.authentication.AuthenticationException;
    
import com.sas.framework.iam.realm.IRealm;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-

import com.sas.framework.expojo.ExpojoContext;
import com.sas.framework.expojo.PersistenceProvider;


// -[Class]-

/**
 * Class Name : UserService
 * Diagram    : Expojo Components
 * Project    : Entity Model Framework
 * Type       : concrete
 * Describe here
 * 
 * @author Chris Colman
 */
public 
class UserService extends ServiceComponent implements IUserService
{
// -[KeepWithinClass]-


// -[Fields]-

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(UserService.class);


// -[Methods]-




/**
 * This will establish a new persistent user in the system. The user contains all of
 * the details of the new user.
 */
private void persistNewUser(User user)
{
	// Persist new user
	PersistenceProvider pp = ExpojoContext.pp();
		
	pp.persist(user);
		
	// Flush changes here because we need the User object to be committed to the database so that
	// it acquires an Object ID (on which memberId is based)
	pp.flush();
}




/**
 * Registers a new User directly, typically not within the context of a sign up process
 * initiated via the UI. It sends no activation or welcome emails. It will activate the
 * User if initialState indicates that the User should be active.
 * 
 * This method is typically used when registering a User in the process of establishing
 * standard objects within a system at initialization time.
 * 
 * Creates a new User with the specified initialState. Associated processing will occur
 * for some states.
 * 
 * Will check for uniqueness of username if checkUsernameIsUnique is true but won't if
 * false. This allows use to avoid double checking in the case that this method is called
 * within a context where the check has already been performed.
 * 
 * Sets up an initial SiteMember role on the website the user signed up on.
 */
public User registerUser(String username, String password, int initialState, boolean checkUsernameIsUnique)
{
	if (checkUsernameIsUnique)
	{
		IUser user = UserRepository.get().findUserByUsername(username);
		if ( user != null )
			throw new AuthenticationException("Username " + username + " is already taken by another user");
	}
	
	logger.warn("No existing user found for successful credentials. Will create new User: " + username);

	// Create a new user
	User user = User.create(username, password);
	if (user != null)
	{
		user.setState(initialState);

		// Persists it in a way that causes the memberId to be assigned properly
		persistNewUser(user);
	}
	
	return user;
}

/**
 * Registers a new User in the context of a sign up process, typically initiated from
 * the UI.
 * The OrgUnit provides the context for the application in which the user is operating
 * when registering even though the User created can log in to different applications
 * in the system.
 * If the system does not require auto registration then the AbstractUserSession object
 * will be set to this current User and it's Entity effectively making the User logged
 * on in the system.
 * Returns true if the new user was logged on i.e. OrgUnit does not require activation
 * via the link in an activation email.
 */
public boolean registerUser(IRealm realm, String siteVisitorId, AbstractUserSession session, String username, String password, String firstName, String lastName)
{
	// return value - will be set to true if auto logon occurred
	boolean loggedOn = false;
	

	// Check if new username is unique
	UserRepository userRepository = UserRepository.get();
	
	IUser user = userRepository.findUserByUsername(username);

	if (user != null)
	{
		throw new AuthenticationException("This email address is already in use which most likely means that you have previously sign up for an account. Close this form and click 'Forgot password' if you can't remember your current password.");
	}

	try
	{
		// User does not yet exist so create it, passing in username and password
		User newUser = registerUser(username, password, User.sActive, false);

		// Populate person name fields
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);

		session.setUser(newUser);
		session.setEntity(newUser);

		loggedOn = true;

		logger.trace("Autologin of newly registered user: " + username);
	}
	catch(Exception e)
	{
		String err = "Sign up Failed: for user: " + username + " Site Visitor ID: " + siteVisitorId;
		logger.error(err, e);
		throw new AuthenticationException(err + " - " + e.getMessage());
	}

	return loggedOn;
}

/**
 * Static convenience method to return the service.
 */
public static UserService get()
{
  return (UserService) Ex.c(IUserService.class);
}

}


