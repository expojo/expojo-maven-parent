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
package com.sas.framework.iam.authentication;

import java.lang.*;
import com.sas.framework.system.OperationalException;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : AuthenticationException
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : concrete
 * Used to encapsulate information about certain authentication failures eg., account
 * lock out due to too many failed authentication attempts.
 * 
 * @author Chris Colman
 */
public 
class AuthenticationException extends OperationalException
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Constructs the object
 */
public AuthenticationException(String reason)

{
	super(reason);
}

}


