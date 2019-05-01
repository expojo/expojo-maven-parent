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
package com.sas.framework.expojo.mock;

import java.lang.*;
import com.sas.framework.expojo.ExpojoContextFactory;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.system.IModule;
    
import com.sas.framework.system.IModulesProvider;
    
import com.sas.framework.system.DefaultModulesProvider;
    
import com.sas.framework.expojo.ModelExposer;
    
import com.sas.framework.expojo.ExpojoContext;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : MockExpojoContextFactory
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : abstract
 * A mock ExpojoContextFactory that supports 'in memory' storage of objects only.
 * 
 * @author Chris Colman
 */
public abstract 
class MockExpojoContextFactory extends ExpojoContextFactory
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Overridden in derived classes to provide PersistenceProvider appropriate to the persistence
 * technology used.
 */
public PersistenceProvider createPersistenceProvider()

{
	return new MockPersistenceProvider();
}

}


