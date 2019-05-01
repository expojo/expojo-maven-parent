// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2006, 2014 Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.framework.system;

import java.lang.*;
import java.lang.RuntimeException;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : OperationalException
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : concrete
 * (Not reallly appropriate to this project - should be in some higher level project)
 * Used when an operation was attempted but failed for some reason other than a bug.
 * eg., permissions/authorization exception, something failed because it had already
 * been completed (maybe by someone else)
 * OperationalExceptions should not result in an error log entry with stack trace as
 * they are not "errors" as such. They should be logged as a warning with no stack trace.
 * More specialized (derived) forms of OperationalExceptions are encouraged for example
 * UserException could represent operational expcetions involving manipulation of users
 * or their roles.
 * UI code should catch OperationalException first and display warning and then catch
 * Exception to log as an error with a stack trace.
 * 
 * @author Chris Colman
 */
public 
class OperationalException extends RuntimeException
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Constructs the object
 */
public OperationalException(String reason, Throwable cause)

{
	super(reason, cause);
}



/**
 * Constructs the object
 */
public OperationalException(String reason)


{
	super(reason);
}

}


