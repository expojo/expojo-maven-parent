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
import org.apache.wicket.model.IModel;
import com.sas.av.ui.wicket.IModelList;


// -[KeepBeforeClass]-
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.model.IDetachable;




// -[Class]-

/**
 * Class Name : DetachableListModel
 * Diagram    : Wicket Model References
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * A Wicket model that represents a list of IModel (implies detachable) objects. When
 * the model is detached, all members of the list will also be detached.
 * 
 * 
 * @author 
 */
public 
class DetachableListModel<T extends IDetachable>
 implements IModel<List<T>>, IModelList<T>
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * NoDesc
 */
protected List<T> list;


// -[Methods]-



/**
 * Reverses the order of the items in the list.
 */
public void reverseOrder()

{
	Collections.reverse(list);
}



/**
 * Returns an iterator over the internal list.
 */
public Iterator<T> iterator()

{
	return list.iterator();
}



/**
 * Detaches the given WickeModelRef and then removes it from the list.
 */
public void detachAndRemove(T item)

{
	list.remove(item);

	item.detach();
}



/**
 * Adds an item to the internal List
 */
public void add(int index, T t)

{
	list.add(index, t);
}



/**
 * Adds an item to the internal List
 */
public void add(T t)

{
	list.add(t);
}



/**
 * Describe here
 */
public void detach()

{
	for (IDetachable item : list)
		item.detach();
}



/**
 * Describe here
 */
public void setObject(List<T> list)

{
	this.list = list;
}



/**
 * Describe here
 */
public List<T> getObject()

{
	return list;
}



/**
 * Clears items from the list.
 */
public void clear()
 {
	list.clear();
}



/**
 * Constructs the object with an empty list.
 */
public DetachableListModel()

{
	list = new ArrayList<T>();
}



/**
 * Constructs the object
 */
public DetachableListModel(List<T> list)

{
	this.list = list;
}

}


