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
    
import com.sas.framework.expojo.ModelExposer;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IModule
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : interface
 * Base class for all Modules that make up the system - like a very cut down version
 * of OSGi that should be eventually migrated to OSGi with more time.
 * 
 * The lifecycle of a module during application execution is:
 * 
 * init - called before persistence management has commenced
 * establishGenesisObjects - called after persistence management has commenced and only
 * if hasGenesisObjects returned false in Expojo listener.
 * start - called after persistence managerment has commenced and after establishGenesisObjects
 * was called (only called if genesis objects did not already exist)
 * 
 * ... application executes
 * 
 * stop - called when application is shutting down
 * destroy - called after application has stopped running
 * 
 * 
 * @author Chris Colman
 */
public abstract 
interface IModule
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Adds the repository and services components for a module to the ModelExposer.
 */
public abstract void addComponents(ModelExposer modelExposer);



/**
 * Establishes genesis objects for this module if the system as has determined that they
 * are not yet established in the datastore.
 */
public abstract void establishGenesisObjects();



/**
 * Releases any references or resources used by the module.
 */
public abstract void destroy();




/**
 * Returns an array of packages that contain metadata files for this module. This method
 * can return null or empty array if this module does not contain persistent classes.
 * This method is typically used by an application to ensure that all meta data is included
 * and available to the persistence framework during startup.
 */
public abstract String[] getMetaDataPackages();




/**
 * init is called before any vital systems and/or start up operations (eg., workflow,
 * data migrations, persistence framework) have been initialized.
 * If you need some code to be executed at start up of a module that requires these vital
 * systems and operations then place that code in the start method.
 * If a module requires new objects to be created during the 'genesis' phase of a new
 * system then override establishGenesisObjects and create such new objects in that method.
 */
public abstract void init(IModulesProvider modulesProvider);




/**
 * Stops the module.
 */
public abstract void stop();





/**
 * Starts the module
 */
public abstract void start();


}


