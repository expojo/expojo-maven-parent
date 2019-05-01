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
import java.io.Serializable;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IBound
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : interface
 * An interface that implies that the implementation can be bound to an another object
 * (eg., a model object) and that this binding can be detached.
 * IBound objects can be registered with the ModelExposer. Whenever a ModelExposer is
 * detached from a thread (via a call to detachThread) the detach method on all registered
 * IBound objects is called. This is very similar in concept to Wicket's IDetachable
 * interface.
 * 
 * 
 * @author Chris Colman
 */
public abstract 
interface IBound extends Serializable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Detaches the object.
 */
public abstract void detach();

}


