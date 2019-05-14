// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2008,2019. Step Ahead Software Pty Ltd. All rights reserved.
 * Usage is governed by the terms of the Apache 2 License.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.av.ui.wicket;

import java.lang.*;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IModelList
 * Diagram    : Wicket Model References
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * Describe here
 * 
 * @author 
 */
public abstract 
interface IModelList<T>

{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Adds an item to the internal List
 */
public abstract void add(T t);

}


