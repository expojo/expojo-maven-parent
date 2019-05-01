// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2007, 2015. Step Ahead Software Pty Ltd.
 * This software is released under the Apache 2 License, a copy of which can be found
 * at:
 * http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.system;

import java.lang.*;
import com.sas.framework.system.IModule;
    
import com.sas.framework.expojo.ModelExposer;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : DefaultModule
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : concrete
 * Implementation of IModule that has "no op" implementations of each method. Used for
 * convenience as the base class for many modules who do not need to provide custom implementations
 * of every method.
 * 
 * @author Chris Colman
 */
public 
class DefaultModule implements IModule
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Initializes the module
 */
public void init()
{
}

/**
 * Adds the repository and services components for a module to the ModelExposer.
 */
public void addComponents(ModelExposer modelExposer)
{
}


/**
 * Establishes genesis objects for this module if the system as has determined that they
 * are not yet established in the datastore.
 */
public void establishGenesisObjects()
{
}


/**
 * Starts the module after initialization of the main system components has completed.
 */
public void start()
{
}


/**
 * Stops the module.
 */
public void stop()
{
}


/**
 * Releases any references or resources used by the module.
 */
public void destroy()
{
}


/**
 * Returns an array of packages that contain metadata files for this module. This method
 * can return null or empty array if this module does not contain persistent classes.
 * This method is typically used by an application to ensure that all meta data is included
 * and available to the persistence framework during startup.
 */
public String[] getMetaDataPackages()
{
	return new String[0];
}


}


