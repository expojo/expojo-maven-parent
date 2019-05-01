// -[KeepHeading]-


// -[Copyright]-

/**
 * 
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.expojo.jdo;

import java.lang.*;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;


// -[KeepBeforeClass]-
import java.util.*;
import java.lang.reflect.Field;

import javax.jdo.JDOHelper;


// -[Class]-

/**
 * Class Name : AttachedRelationshipDetector
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * Scans an object graph, using reflection to inspect all relationships that are non-null
 * and implement Persistable, looking for relationships that are still attached and throws
 * an exception if it finds any. Attached objects that are referenced from detached objects
 * are currently not detached properly during the Wicket detach cycle and can cause duplication
 * in the database.
 * 
 * @author Chris Colman
 */
public 
class AttachedRelationshipDetector
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Stores a record of objects that have already been checked by this detector.
 */
private Set<org.datanucleus.enhancement.Persistable> checkedObjects = new HashSet<>();



/**
 * Reference to the initial object, so a log message can be written at the end of the
 * check.
 */
private org.datanucleus.enhancement.Persistable initialObject;


// -[Methods]-

/**
 * Recursively checks the object for attached relationships and throws an exception if
 * any are detected.
 */
public void checkObject(org.datanucleus.enhancement.Persistable object)
{
	// If the object is null or already in the list of checked objects, return immediately
	if (object == null || checkedObjects.contains(object))
		return;

	// Set initial object if necessary
	if (initialObject == null)
		initialObject = object;

	// Add object to checked list
	checkedObjects.add(object);

	// Check the current object
	if (JDOHelper.getObjectId(object) != null && !JDOHelper.isDetached(object) && !JDOHelper.isDeleted(object))
		throw new IllegalStateException("Object " + object.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(object)) + " (OID=" + JDOHelper.getObjectId(object) + ") is still attached!");

	// Only check fields if the current object is detached
	if (JDOHelper.isDetached(object))
	{
		// Get a list of all fields of the current object
		List<Field> fields = new ArrayList<>();
		Class<?> currentClass = object.getClass();

		while (currentClass != null)
		{
			fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
			currentClass = currentClass.getSuperclass();
		}

		// Check if the field is non-null and implements Persistable, and recurse
		for (Field field : fields)
		{
			// Store the original accessibility flag
			boolean originalAccessible = field.isAccessible();
			field.setAccessible(true);

			try
			{
				Object fieldValue = field.get(object);

				if 
				(
					fieldValue != null && fieldValue instanceof 
					org.datanucleus.enhancement.Persistable
//					javax.jdo.spi.PersistenceCapable
				)
					checkObject
					(
						(
							org.datanucleus.enhancement.Persistable
//							javax.jdo.spi.PersistenceCapable
						)
						fieldValue
					);
			}
			catch (IllegalAccessException ex)
			{
				ExpojoFoundation.sLogError("Illegal access of field. This should never happen.");
			}

			// Set the field to its original accessibility
			field.setAccessible(originalAccessible);
		}
	}

	// Completion message
	if (object == initialObject)
		ExpojoFoundation.sLogInfo("Completed check on " + checkedObjects.size() + " objects");
}

}


