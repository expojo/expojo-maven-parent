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

import com.sas.framework.iam.user.expojo.UserRepository;
    
import com.sas.framework.iam.user.impl.User;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;




// -[KeepBeforeClass]-

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import java.util.Collection;

import com.sas.framework.expojo.jdo.JdoPersistenceProvider;
import com.sas.framework.expojo.jdo.JDOQueryResultCollection;

import com.sas.framework.iam.user.IUser;
import com.sas.framework.iam.user.impl.User;


// -[Class]-

/**
 * Class Name : UserRepositoryJdo
 * Diagram    : User Expojo Components
 * Project    : Entity Model Framework
 * Type       : concrete
 * User repository has query methods to retrieve users as their details.
 * 
 * @author Chris Colman
 */
public 
class UserRepositoryJdo extends UserRepository
{
// -[KeepWithinClass]-


// -[Fields]-

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(UserRepositoryJdo.class);


// -[Methods]-



/**
 * Convenience method used to obtain the JDO persistence manager for this object in this
 * thread.
 */
public PersistenceManager getPm()


{
	JdoPersistenceProvider jdoPersistenceProvider = (JdoPersistenceProvider)expojoContext.getPersistenceProvider();

	return jdoPersistenceProvider.getPm();
}



/**
 * Finds all users of the system.
 */
public Collection findAllUsers()

{
	PersistenceManager pm = getPm();

	Query q = pm.newQuery(User.class);
	q.declareImports("import com.sas.framework.entity.Person;");
	q.setFilter("entity instanceof Person && !username.endsWith(\"e1\") && !username.endsWith(\"e2\") && !username.startsWith(\"_deleted\")");	// #MOD remove the 'e1' clause when all moved to starting with '_deleted'
	q.setOrdering("((Person)entity).firstName ascending, ((Person)entity).lastName ascending");

	return new JDOQueryResultCollection(q, q.execute());
}



/**
 * Finds the user with the given username.
 */
public IUser findUserByUsername(String username)

{
	PersistenceManager pm = getPm();
	
	Query q = pm.newQuery(User.class);

	q.setFilter
	(
		"username.toLowerCase() == :username.toLowerCase()"
	);
	q.setUnique(true);

	User user = (User)q.execute(username);
	logger.info("findUserByUsername: " + username + " - " + ((user != null) ? "FOUND" : "NOT FOUND"));

	q.close(user);
	
	return user;
}

}


