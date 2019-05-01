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
    
import com.sas.framework.system.IModule;
    
import com.sas.framework.expojo.ModelExposer;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ProxyModule
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : concrete
 * Describe here
 * 
 * @author Chris Colman
 */
public 
class ProxyModule implements IModule
{
// -[KeepWithinClass]-


// -[Fields]-
    
    protected IModule impl;

// [Added by Code Injection Wizard: Customizable proxy]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 

public void init()
{


            impl.init();


            return;

}

public void addComponents(ModelExposer modelExposer)
{


            impl.addComponents(modelExposer);


            return;

}

public void establishGenesisObjects()
{


            impl.establishGenesisObjects();


            return;

}

public void start()
{


            impl.start();


            return;

}

public void stop()
{


            impl.stop();


            return;

}

public void destroy()
{


            impl.destroy();


            return;

}

public String[] getMetaDataPackages()
{

            String[] retVal;

            retVal = impl.getMetaDataPackages();


            return retVal;

}


// -[Methods]-




/**
 * Constructs the object
 */
public ProxyModule(IModule iModule)
{
	impl = iModule;
}

}


