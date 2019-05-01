// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2006, 2019 Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.role.simple;

import java.lang.*;
import com.sas.framework.iam.user.IUser;
    
import com.sas.framework.entity.IPerson;
    
import com.sas.framework.entity.IEntity;


// -[KeepBeforeClass]-
import java.util.*;

// -[Class]-

/**
 * Class Name : SimpleUser
 * Diagram    : Simple User Repository Implementation
 * Project    : IAM Simple Implementation
 * Type       : concrete
 * Simple user
 * 
 * @author Chris Colman
 */
public 
class SimpleUser implements IUser
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
 * Code to activate this user is the system requires user activation.
 */
private String activationCode;


// -[Methods]-




/**
 * Returns the IEntity associated with this SimpleUser - may actually be the SimpleUser
 * itself.
 */
public IEntity getEntity()
{
	return null;
}

/**
 * Describe here
 */
public long getMemberId()
{
	return 1;
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
 * Constructs the object
 */
public SimpleUser(String iUsername, String iPassword)
{
	username = iUsername;
	password = iPassword;
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
}




/**
 * Returns currentLogon
 */
public Date getCurrentLogon(){
	return new Date();
}




/**
 * Returns previousLogon
 */
public Date getPreviousLogon(){
	return new Date();
}




/**
 * Returns null if the testPassword is regarded as strong enough to be used as a password
 * otherwise returns a string explaining why the password is not regarded as strong enough.
 */
public String isStrongPassword(String testPassword){
	return null;
}




/**
 * Sets password
 */
public void setPassword(String password){
	this.password = password;
}




/**
 * Returns password
 */
public String getPassword(){
	return password;
}




/**
 * Sets username
 */
public void setUsername(String username){
	this.username = username; 
}




/**
 * Returns username
 */
public String getUsername(){
	return username;
}

}


