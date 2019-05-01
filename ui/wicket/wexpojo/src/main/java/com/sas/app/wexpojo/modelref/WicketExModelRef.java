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
package com.sas.app.wexpojo.modelref;

import java.lang.*;
import com.sas.framework.expojo.ExModelRef;
import org.apache.wicket.model.IModel;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : WicketExModelRef
 * Diagram    : Wicket Model References
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Provides a convenient Wicket IModel implementation which references any persistent
 * domain model object.
 * 
 * @author 
 */
public 
class WicketExModelRef<T>
 extends ExModelRef<T> implements IModel<T>
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * WARNING: This constructor should only be used for the default model for a Wicket component.
 * For all other cases, use another constructor.
 */
public WicketExModelRef(T o)
{
	super(o);
}

}


