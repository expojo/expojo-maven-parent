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
import com.sas.ui.wicket.model.ChainingPropertyModel;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : StringPropertyModel
 * Diagram    : Wicket models for Icons and Attribute modifiers
 * Project    : Pagebloom
 * Type       : abstract
 * A non reflection based property model class that can be extended to provide a String
 * value required for a particular purpose.
 * For example - providing the CSS style in an attribute modifier in model based way
 * - i.e. will update the view during partial updates.
 * 
 * @author Chris Colman
 */
public abstract 
class StringPropertyModel<D>
 extends ChainingPropertyModel<D>
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Provides the value of this StringPropertyModel instance.
 */
public abstract String getValue(D object);

/**
 * Returns a String object representing the icon.
 */
public String getObject()
{
	D object = getInnermostModelObject();
	
	return getValue(object);
}



/**
 * Constructs the object
 */
public StringPropertyModel(Object modelObject)

{
	super(modelObject);
}

}


