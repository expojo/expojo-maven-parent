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
package com.sas.framework.util;

import java.lang.*;


// -[KeepBeforeClass]-
import java.util.*;


// -[Class]-

/**
 * Class Name : Util
 * Diagram    : Exopjo Module System
 * Project    : ExPOJO Core
 * Type       : concrete
 * Helps avoid dependencies on third party libraries to keep it extremely lightweight
 * 
 * @author Chris Colman
 */
public 
class Util
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Converts a collection of Strings to a single delimited String.
 */
public static String collectionToDelimitedString(Collection<String> strings, String delimiter)
{
	StringBuilder sb = new StringBuilder();
	
	boolean first = true;
	
	for (String item : strings)
	{
		if (first)
			first = false;
		else
			sb.append(delimiter);

		sb.append(item);
	}
	
	return sb.toString();
}

}


