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
import java.io.Serializable;


// -[KeepBeforeClass]-

import com.sas.framework.entity.IEntity;
import com.sas.framework.iam.user.IUser;


// -[Class]-

/**
 * Class Name : AbstractUserSession
 * Diagram    : Association of user with a session
 * Project    : Entity Model Framework
 * Type       : interface
 * Methods for associated an implementation of this class with a given user.
 * 
 * @author 
 */
public abstract 
interface AbstractUserSession extends Serializable
{
// -[KeepWithinClass]-
public static String sessionId = "USER";


// -[Fields]-


// -[Methods]-




/**
 * Sets the iEntity.
 */
public abstract void setEntity(IEntity iEntity);




/**
 * Returns entity.
 */
public abstract IEntity getEntity();



/**
 * Sets the IUser
 */
public abstract void setUser(IUser iUser);



/**
 * Returns the user
 */
public abstract IUser getUser();

}


