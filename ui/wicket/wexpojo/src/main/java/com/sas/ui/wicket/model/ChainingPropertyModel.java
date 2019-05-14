// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2006, 2012 Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.ui.wicket.model;

import java.lang.*;
import org.apache.wicket.model.ChainingModel;


// -[KeepBeforeClass]-

import org.apache.wicket.model.IModel;

// -[Class]-

/**
 * Class Name : ChainingPropertyModel
 * Diagram    : Wicket models for Icons and Attribute modifiers
 * Project    : Pagebloom
 * Type       : concrete
 * Holds the getInnermostModelObject method that should have been in Wicket's ChainingModel,
 * not AbstractPropertyModel.
 * 
 * @author Chris Colman
 */
public 
class ChainingPropertyModel<D>
 extends ChainingModel<String>
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Returns the innermost object.
 */
public D getInnermostModelObject()
{
	Object object = getTarget();
	while (object instanceof IModel)
	{
		Object tmp = ((IModel<?>)object).getObject();
		if (tmp == object)
		{
			break;
		}
		object = tmp;
	}
	return (D)object;
}



/**
 * Constructs the object
 */
public ChainingPropertyModel(Object modelObject)
{
	super(modelObject);
}

}


