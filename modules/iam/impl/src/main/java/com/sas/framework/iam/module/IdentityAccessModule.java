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
package com.sas.framework.iam.module;

import java.lang.*;
import com.sas.framework.system.DefaultModule;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.iam.authentication.IAuthenticationService;
    
import com.sas.framework.iam.user.expojo.UserService;
    
import com.sas.framework.iam.user.IUserService;


// -[KeepBeforeClass]-

import com.sas.framework.iam.authentication.IAuthenticationService;

// -[Class]-

/**
 * Class Name : IdentityAccessModule
 * Diagram    : Expojo Module
 * Project    : Entity Model Framework
 * Type       : concrete
 * The IModule implementation for this identity and access management module.
 * 
 * @author Chris Colman
 */
public 
class IdentityAccessModule extends DefaultModule
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Returns an array of packages that contain metadata files for this module. This method
 * can return null or empty array if this module does not contain persistent classes.
 * This method is typically used by an application to ensure that all meta data is included
 * and available to the persistence framework during startup.
 */
public String[] getMetaDataPackages()
{
	final String[] packages = 
	{
		"com/sas/framework/iam/user/impl/package.jdo"
	};
	
	return packages;
}

/**
 * Adds the repository and services components for a module to the ModelExposer.
 */
public void addComponents(ModelExposer modelExposer)
{
	modelExposer.addService(IUserService.class, new UserService());
}

}


