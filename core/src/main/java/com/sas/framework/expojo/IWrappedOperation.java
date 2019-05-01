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
 * Class Name : IWrappedOperation
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : interface
 * An operation that is wrapped inside the context of a ModelExposer that is attached
 * to the thread and wrapped inside an open transaction.
 * 
 * @author Chris Colman
 */
public abstract 
interface IWrappedOperation
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Executes this operation wrapped with a thread attached ModelExposer and an open transaction.
 * param1 and param2 are the same values as thosed passed to ModelExposer.executeWrapped(param1,
 * param2). Obviously some casting will be required as appropriate if parameters will
 * be used by the operation.
 */
public abstract void execute(Object param1, Object param2);

}


