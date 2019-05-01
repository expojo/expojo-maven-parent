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
package com.sas.framework.expojo;

import java.lang.*;
import com.sas.framework.expojo.ExpojoContext;


// -[KeepBeforeClass]-

import java.util.HashMap;

import com.sas.framework.expojo.servlet.ExpojoFoundation;

// -[Class]-

/**
 * Class Name : Ex
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : concrete
 * A convenience class to provide shorthand access to key ExpojoContext aspects such
 * its components and the persistence provider.
 * 
 * No one should ever create an Ex instance so the constructor is private.
 * 
 * It is only ever used for its convenience static methods.
 * 
 * @author Chris Colman
 */
public 
class Ex extends ExpojoContext
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Static convenience method to return a persistence provider.
 */
public static PersistenceProvider pp()

{
	return get().getPersistenceProvider();
}




/**
 * No one should ever create an Ex instance so the constructor is private.
 * 
 * It is only ever used for its convenience static methods.
 */
private Ex(PersistenceProvider iPersistenceProvider)
{
	super(iPersistenceProvider);
}

/**
 * Shorthand, convenience method to return a component keyed by the cls argument and
 * also "typed" by it.
 * 
 * e.g.
 * 
 * IMyRepo repo = Ex.c(IMyRepo.class);
 */
public static <C> C c(Class<C> cls)
{
	// Get the ExpojoContext currently bound to this thread
	ExpojoContext me = ExpojoContext.get();
	
	if ( me != null )
	{
		HashMap<Object, ExpojoComponent> components = me.components;
		if ( components != null )
			return (C)components.get(cls);
		else
		{
			ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExpojoContext.components was null");
		}
	}
	else
	{
		ExpojoFoundation.sLog(ExpojoFoundation.LT_ERROR, "ExpojoContext was null");
	}
	
	return null;
}

}


