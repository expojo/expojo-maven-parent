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
import com.sas.framework.iam.user.IUserRepository;
import com.sas.framework.entity.IPersonRepository;
    
import com.sas.framework.role.simple.SimpleUser;
    
import com.sas.framework.iam.user.IUser;
    
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.framework.entity.IPerson;


// -[KeepBeforeClass]-
import java.util.*;


// -[Class]-

/**
 * Class Name : SimpleUserRepository
 * Diagram    : Simple User Repository Implementation
 * Project    : IAM Simple Implementation
 * Type       : concrete
 * Simple implementation
 * 
 * @author Chris Colman
 */
public 
class SimpleUserRepository implements IUserRepository, IPersonRepository
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * NoDesc
 */
private static SimpleUserRepository self;
    
    protected Vector users;


// -[Methods]-




/**
 * Temporary in place of a TDI provider.
 */
public static IUserRepository get()
{
	if (self == null)
		self = new SimpleUserRepository();
		
	return self;
}




/**
 * Finds an IPerson associated with the given display name.
 */
public IPerson findPersonByDisplayName(String displayName)
{
	return null;
}

/**
 * Constructs the object
 */
public SimpleUserRepository()
{
	self = this;

	users = new Vector();
	
	// super user
	users.add(new SimpleUser("super@admin.org", "123"));
	
	// anonymous user
	users.add(new SimpleUser("anon@ymous.org", "123"));

	users.add(new SimpleUser("jo@blow.com", "123"));
	users.add(new SimpleUser("john@smith.com", "123"));
	users.add(new SimpleUser("chrisc@stepahead.com.au", "123"));
}




/**
 * Returns all users in the system.
 */
public Collection findAllUsers(){
	return users;
}




/**
 * Finds the user with the given username.
 */
public IUser findUserByUsername(String username){
	Iterator i = users.iterator();
	
	while ( i.hasNext() )
	{
		IUser user = (IUser)i.next();
		
		if ( user.getUsername().equals(username) )
			return user;
	}
	
	return null;
}

}


