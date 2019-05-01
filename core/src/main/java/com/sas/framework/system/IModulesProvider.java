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


// -[KeepBeforeClass]-

import java.util.Collection;

// -[Class]-

/**
 * Class Name : IModulesProvider
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : interface
 * Provides a collection of IModuleS.
 * 
 * @author Chris Colman
 */
public abstract 
interface IModulesProvider
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Adds an IModule.
 */
public abstract void addModule(IModule module);



/**
 * Returns a collection of modules.
 */
public abstract Collection<IModule> getModules();

}


