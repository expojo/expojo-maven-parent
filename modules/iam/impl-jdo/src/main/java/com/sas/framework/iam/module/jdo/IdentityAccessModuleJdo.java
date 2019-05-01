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
package com.sas.framework.iam.module.jdo;

import java.lang.*;
import com.sas.framework.iam.module.IdentityAccessModule;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.iam.user.expojo.UserRepository;
    
import com.sas.framework.iam.user.IUserRepository;
import com.sas.framework.iam.user.expojo.UserRepositoryJdo;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IdentityAccessModuleJdo
 * Diagram    : Expojo Module
 * Project    : Entity Model Framework
 * Type       : concrete
 * The IModule implementation for this identity and access management module.
 * 
 * @author Chris Colman
 */
public 
class IdentityAccessModuleJdo extends IdentityAccessModule
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Adds the repository and services components for a module to the ModelExposer.
 */
public void addComponents(ModelExposer modelExposer)
{
	super.addComponents(modelExposer);

	modelExposer.addRepository(IUserRepository.class, new UserRepositoryJdo());
}

}


