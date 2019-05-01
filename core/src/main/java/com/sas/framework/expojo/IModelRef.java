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
import com.sas.framework.expojo.IBound;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IModelRef
 * Diagram    : Expojo Core
 * Project    : ExPOJO Core
 * Type       : interface
 * A reference bound to a domain model object. Extends IBound implying that the binding
 * can be detached. It is assumed that enough information (eg., primary key) is stored
 * for the model reference so that it can be restored again at a later time when getObject
 * is called.
 * 
 * @author Chris Colman
 */
public abstract 
interface IModelRef<T>
 extends IBound
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Sets object
 */
public abstract void setObject(T object);



/**
 * Returns object
 */
public abstract T getObject();

}


