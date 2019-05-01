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
import com.sas.framework.iam.user.IUserRepository;
import com.sas.framework.expojo.RepositoryComponent;
    
import com.sas.framework.iam.user.impl.User;
    
import com.sas.framework.expojo.Ex;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : UserRepository
 * Diagram    : Expojo Components
 * Project    : Entity Model Framework
 * Type       : abstract
 * Describe here
 * 
 * @author Chris Colman
 */
public abstract 
class UserRepository extends RepositoryComponent implements IUserRepository
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Static convenience method to return the repository.
 */
public static UserRepository get()

{
  return (UserRepository) Ex.c(IUserRepository.class);
}

}


