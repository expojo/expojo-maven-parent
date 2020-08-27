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
package com.sas.ui.wicket.model;

import java.lang.*;
import com.sas.ui.wicket.model.ChainingPropertyModel;


// -[KeepBeforeClass]-
import org.apache.wicket.model.IModel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;

// -[Class]-

/**
 * Class Name : IconPropertyModel
 * Diagram    : Wicket models for Icons and Attribute modifiers
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : abstract
 * Displays an icon based on a the domain model object D.
 * 
 * Currently implemented to use "icofont" but could be easily enhanced to be switchable
 * between a range of different font icon libraries eg., Font-Awesome.
 * 
 * The generics here are slightly confusing.
 * 
 * The base class ChainingModel is instantiated with 'String' type for its T parameter
 * type because we need to ensure that Component.getDefaultModelObjectAsString sees a
 * String objet returned (not the domain model object which determines the value of that
 * String via its current state or whatever).
 * 
 * This class then has its own type parameter to specify the domain model object and
 * that type is given the generic parameter 'D'.
 * 
 * 
 * @author Chris Colman
 */
public abstract 
class IconPropertyModel<D>
 extends ChainingPropertyModel<D>
{
// -[KeepWithinClass]-
public enum IconLib
{
	icofont,
	fontawsome
}


// -[Fields]-



/**
 * Provides an optional size suffix to use to size icons differently to the default for
 * this class.
 */
private String size;



/**
 * The icon library used by this model.
 */
private IconLib iconLib;


// -[Methods]-

/**
 * Sets iconLib
 */
public void setIconLib(IconLib iconLib)
{
    this.iconLib = iconLib;
}

/**
 * Returns iconLib
 */
public IconLib getIconLib()
{
    return iconLib;
}

/**
 * Constructs the object
 */
public IconPropertyModel(Object modelObject, String iSize)
{
	super(modelObject);
	size = iSize;
	iconLib = IconLib.icofont;
}

/**
 * Sets size
 */
public void setIconSize(String size)
{
    this.size = size;
}

/**
 * Returns the icon size suffix - this may be a suffix like 2x, 4x or sm, lg, xl etc.,
 * so not necessarily numeric for all icon font libraries.
 * 
 * eg., for icofont the classes are 
 * icofont-1x, icofont-2x, icofont-4x etc.,
 * 
 * This method returns the 1x, 2x, 4x etc., suffix as appropriate.
 */
public String getIconSize(D d)
{
	if (size != null)
		return size;

	if (iconLib == IconLib.icofont)
	{
		return "2x"; // default value
	}
	else
	{
		return null;
	}
}

/**
 * Returns the string that contains all the CSS classes required to display the icon.
 */
public String getIconCssClasses(D d)
{
	String iconName = getIconName(d);
	StringBuilder sb;
	
	if (iconLib == IconLib.icofont)
	{
		sb = new StringBuilder("icofont icofont-");
		sb.append(getIconSize(d));
		sb.append(" icofont-");
		sb.append(iconName == null ? "question" : iconName);
	}
	else
	{
		sb = new StringBuilder("fa");
		String sz = getIconSize(d);
		if (sz != null)
		{
			sb.append(" fa-");
			sb.append(sz);
		}
		
		sb.append(" fa-");
		sb.append(iconName == null ? "question" : iconName);
	}

	return sb.toString();
}

/**
 * Returns a String object representing the icon.
 */
public String getObject()
{
	D object = getInnermostModelObject();
	
	StringBuilder sb = new StringBuilder("<i class=\"");
	String iconCssClasses = getIconCssClasses(object);
	sb.append(iconCssClasses);
	sb.append("\" aria-hidden=\"true\"></i>");

	return sb.toString();
}




/**
 * Provides the name of the icon to use based on the given object.
 * If null is returned then a '?' icon will be displayed.
 */
public abstract String getIconName(D object);

/**
 * Constructs the object
 */
public IconPropertyModel(Object modelObject)
{
	this(modelObject, null);
}

}


