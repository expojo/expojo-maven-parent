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
package com.sas.framework.iam.authentication.standard;

import java.lang.*;
import com.sas.framework.iam.authentication.ICredentialSpec;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : StandardCredentialSpec
 * Diagram    : Authentication Credentials
 * Project    : Entity Model Framework
 * Type       : concrete
 * Specifies the field names and behaviour for a standard log on form.
 * 
 * Standard credential spec - mandatory username (as email) and password.
 * 
 * @author Chris Colman
 */
public 
class StandardCredentialSpec implements ICredentialSpec
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Returns true if the id field needs to be a correctly formatted email address.
 */
public boolean isIdFieldEmailAddress()
{
	return true;
}

/**
 * For some scenarios a password is not required if the value of the ID field acts as
 * both ID and security eg., it may be a hard to guess identifying and authenticating
 * combination 'security code'.
 */
public boolean isPasswordRequired()
{
	return true;
}

/**
 * Returns the name of the field used to authenticate the user, typically a password
 * but in some scenarios it may be a code given to the user via an email marketing campaign
 * or letter box drop.
 */
public String getPasswordFieldName()
{
	return "Password";
}

/**
 * Returns the name of the identifying field.
 * eg., Username, Email, Member number etc.,
 */
public String getIdFieldName()
{
	return "Email address";
}

}


