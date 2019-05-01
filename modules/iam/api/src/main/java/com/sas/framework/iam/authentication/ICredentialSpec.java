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
import java.io.Serializable;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : ICredentialSpec
 * Diagram    : Authentication
 * Project    : Entity Model Framework
 * Type       : interface
 * Specifies the name and behaviour of credentials. This can be used by a flexibly implemented
 * login form to use field names and behaviour for a wide range of credential types.
 * 
 * Hint: not all authentications are simply: email + password.
 * 
 * @author Chris Colman
 */
public abstract 
interface ICredentialSpec extends Serializable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Returns true if the id field needs to be a correctly formatted email address.
 */
public abstract boolean isIdFieldEmailAddress();




/**
 * For some scenarios a password is not required if the value of the ID field acts as
 * both ID and security eg., it may be a hard to guess identifying and authenticating
 * combination 'security code'.
 */
public abstract boolean isPasswordRequired();




/**
 * Returns the name of the field used to authenticate the user, typically a password
 * but in some scenarios it may be a code given to the user via an email marketing campaign
 * or letter box drop.
 */
public abstract String getPasswordFieldName();




/**
 * Returns the name of the identifying field.
 * eg., Username, Email, Member number etc.,
 */
public abstract String getIdFieldName();

}


