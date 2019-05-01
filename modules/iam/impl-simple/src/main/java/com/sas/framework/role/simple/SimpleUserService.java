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
import com.sas.framework.iam.user.IUserService;
    
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.framework.iam.user.IUser;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : SimpleUserService
 * Diagram    : Simple User Repository Implementation
 * Project    : IAM Simple Implementation
 * Type       : concrete
 * Describe here
 * 
 * @author Chris Colman
 */
public 
class SimpleUserService implements IUserService
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Finds a user with the given user credentials (username and password). This is called
 * to authenticate a user in the system.
 */
public IUser authenticateCredentials(String username, String password, boolean digested, IRealm realm)

{
	IUser user = SimpleUserRepository.get().findUserByUsername(username);
	
	if ( !digested && user.getPassword().equals(password) )
		return user;
	else
		return null;
}

}


