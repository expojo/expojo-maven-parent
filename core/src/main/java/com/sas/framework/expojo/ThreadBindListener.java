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


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ThreadBindListener
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : interface
 * A listener interface for optional notification of thread attach/detach calls.
 * Any application that needs to perform work during attachment or detachment of the
 * ModelExposer can provide an implementation of the ThreadBindListener and perform that
 * work in there.
 * Attach is called after attachement occurs and detach is called before detachment occurs
 * although changes to persistent objects should be avoided if possible.
 * 
 * 
 * @author Chris Colman
 */
public abstract 
interface ThreadBindListener
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Called just before the ExpojoContext is detached from the current thread.
 */
public abstract void preDetach(ExpojoContext expojoContext);




/**
 * Called just after the ExpojoContext has been attached to the current thread.
 */
public abstract void postAttach(ExpojoContext expojoContext);

}


