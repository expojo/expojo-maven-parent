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
package com.sas.framework.iam.user.impl;

import java.lang.*;
import com.sas.framework.iam.user.IUser;
import com.sas.framework.entity.IEntity;
    
import com.sas.framework.entity.IEntity;


// -[KeepBeforeClass]-

import java.util.Date;

// -[Class]-

/**
 * Class Name : User
 * Diagram    : Domain Model
 * Project    : Entity Model Framework
 * Type       : concrete
 * Simple implementation of User
 * 
 * @author Chris Colman
 */
public 
class User implements IUser, IEntity
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * NoDesc
 */
protected String username;



/**
 * NoDesc
 */
protected String password;



/**
 * First name of user
 */
private String firstName;



/**
 * Last name of user.
 */
private String lastName;



/**
 * Time of the current log on. At the next logon this will be assigned to previousLogOn.
 */
private Date currentLogon;



/**
 * Time of the previous log on.
 */
private Date previousLogon;



/**
 * String used to activate a user account - could be sent via an email link.
 */
private String activationCode;



/**
 * State of the user account:
 * awaitingApproval - perhaps for some verification process.
 * active - account is active
 * temporarilyDisabled
 * disabled
 */
protected int state = sAwaitingActivation;



/**
 * User has not yet activated the account by clicking on the link in the activation email.
 */
public static final transient int sAwaitingActivation = 0;



/**
 * Account is active.
 */
public static final transient int sActive = 1;



/**
 * User account has been disabled. Activation will not re-enable an account in this state.
 */
protected static final transient int sDisabled = 2;



/**
 * NoDesc
 */
public static final transient int sRegistering = 3;



/**
 * Some web apps auto sign in a user after their account has been activated via the link
 * in the activation email. Others require an explicit logon post activation. This state
 * represents the post activation state for web apps that require explicit logon.
 * (There is no code that assigns the stat to this value yet - but that could change
 * in the future)
 */
public static final transient int sActiveAwaitingFirstLogon = 4;


// -[Methods]-

/**
 * Sets lastName
 */
public void setLastName(String lastName)
{
    this.lastName = lastName;
}

/**
 * Returns lastName
 */
public String getLastName()
{
    return lastName;
}

/**
 * Sets firstName
 */
public void setFirstName(String firstName)
{
    this.firstName = firstName;
}

/**
 * Returns firstName
 */
public String getFirstName()
{
    return firstName;
}




/**
 * Factory method for creating a new User role for an existing Person. Each person should
 * only ever have one user role.
 * This method will not persist the User so it can be safely used within a form that
 * does not commit the User to the database until such time as the User hits submit with
 * valid form data.
 * This method does not activate the User.
 */
public static User create(String iUsername, String iPassword)
{
	User user = new User(iUsername, iPassword);
	
	return user;
}

/**
 * Sets state
 */
public void setState(int state)
{
    this.state = state;
}

/**
 * Returns state
 */
public int getState()
{
    return state;
}

/**
 * Sets activationCode
 */
public void setActivationCode(String activationCode)
{
    this.activationCode = activationCode;
}

/**
 * Returns activationCode
 */
public String getActivationCode()
{
    return activationCode;
}



/**
 * Sets previousLogon
 */
private void setPreviousLogon(Date previousLogon)

{
	if ( previousLogon != null )
	{
		this.previousLogon = new Date(previousLogon.getTime());
	}
	else
	{
		this.previousLogon = null;	
	}
}

/**
 * Sets currentLogon
 */
private void setCurrentLogon(Date currentLogon)
{
	if ( currentLogon != null )
	{
		// if the current logon is no more than 2 seconds after the previous logon
		// then we probably have a Chrome 'double pump request' sequence so don't
		// bother updating for the second logon
		if
		(
			previousLogon == null
			||
			currentLogon.getTime() - previousLogon.getTime() > 2000
		)
		{	
			setPreviousLogon(this.currentLogon);
			this.currentLogon = new Date(currentLogon.getTime());
		}
	}
}

/**
 * The name of this entity - not necessarily unique across the system.
 */
public String getName()
{
	return username;
}

/**
 * Returns the name to display for this entity.
 * The display name is unique across the system so that it is possibly to uniquely identify
 * an entity by it's display name. The display name does not have to have any resemblance
 * to the name returned by getName but it may.
 * e.g. getName may return Bill Smith for many entities but for each entity the getDisplayName
 * for each must be different so you could end up with display names being billsmith1,
 * billsmith57, bill smith A, Bill Smith 27 etc.,
 */
public String getDisplayName()
{
	return username;
}

/**
 * Returns the IEntity associated with this SimpleUser - may actually be the SimpleUser
 * itself.
 */
public IEntity getEntity()
{
	return this;
}



/**
 * Describe here
 */
public long getMemberId()

{
	return 1;
}



/**
 * Describe here
 */
public String toString()

{
	return username;
}

/**
 * Called when a successful authentication has taken place.
 */
public void registerAuthentication()
{
	setCurrentLogon(new Date());
}

/**
 * Returns currentLogon
 */
public Date getCurrentLogon()
{
	return currentLogon;
}

/**
 * Returns previousLogon
 */
public Date getPreviousLogon()
{
	return previousLogon;
}



/**
 * Sets password
 */
public void setPassword(String password)
{
	this.password = password;
}



/**
 * Returns password
 */
public String getPassword()
{
	return password;
}



/**
 * Sets username
 */
public void setUsername(String username)
{
	this.username = username; 
}



/**
 * Returns username
 */
public String getUsername()
{
	return username;
}

/**
 * Constructs the object
 */
private User(String iUsername, String iPassword)
{
	username = iUsername;
	password = iPassword;

	state = sAwaitingActivation;
}

}


