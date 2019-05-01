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
package com.sas.framework.iam.user;

import java.lang.*;
    
import com.sas.framework.entity.IEntity;


// -[KeepBeforeClass]-
import java.util.Date;



// -[Class]-

/**
 * Class Name : IUser
 * Diagram    : User and User exposers
 * Project    : Entity Model Framework
 * Type       : interface
 * Interface for all users in a system.
 * 
 * @author Chris Colman
 */
public abstract 
interface IUser
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Describe here
 */
public abstract IEntity getEntity();



/**
 * Describe here
 */
public abstract long getMemberId();



/**
 * Sets the activationCode.
 */
public abstract void setActivationCode(String iActivationCode);



/**
 * Returns the activation code.
 */
public abstract String getActivationCode();



/**
 * Describe here
 */
public abstract String toString();




/**
 * Registers a successful authentication. Typically would update previous and current
 * logon times.
 */
public abstract void registerAuthentication();



/**
 * Returns currentLogon
 */
public abstract Date getCurrentLogon();



/**
 * Returns previousLogon
 */
public abstract Date getPreviousLogon();




/**
 * Returns (ideally a digested) password.
 */
public abstract String getPassword();



/**
 * Returns username
 */
public abstract String getUsername();

}


