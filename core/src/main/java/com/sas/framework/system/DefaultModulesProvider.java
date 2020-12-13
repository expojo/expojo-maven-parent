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
import com.sas.framework.system.IModulesProvider;
    
import com.sas.framework.system.IModule;
    
import com.sas.framework.system.IModule;


// -[KeepBeforeClass]-
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

// -[Class]-

/**
 * Class Name : DefaultModulesProvider
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : concrete
 * Default implementation of IModuleProvider that stores a List of IModuleS.
 * 
 * @author Chris Colman
 */
public 
class DefaultModulesProvider implements IModulesProvider
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Map of properties to be made available to modules via getProperty().
 */
private Map<String,Object> properties = new HashMap<>();
    
    protected Collection<IModule> modules;


// -[Methods]-

/**
 * Describe here
 */
public void setProperty(String name, Object property)
{
	properties.put(name, property);
}

/**
 * Returns the property with the given name.
 */
public Object getProperty(String name)
{
	return properties.get(name);
}



/**
 * Returns the colleciton of IModuleS.
 */
public Collection<IModule> getModules()
{
	return modules;
}

/**
 * Adds an IModule.
 */
public void addModule(IModule module)
{
	modules.add(module);
}




/**
 * Constructs the object
 */
public DefaultModulesProvider()
{
	modules = new ArrayList<>();
}

}


