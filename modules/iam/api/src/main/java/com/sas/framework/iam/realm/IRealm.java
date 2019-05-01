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
package com.sas.framework.iam.realm;

import java.lang.*;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IRealm
 * Diagram    : Entity
 * Project    : Entity Model Framework
 * Type       : interface
 * Interface representing a realm. IUserS can be associated with an IRealm. This can
 * be used to support multi tenanted systems.
 * A Realm is typically some type of an organization which is really just a group of
 * people gathered together in some type of group to "do stuff".
 * 
 * @author Chris Colman
 */
public abstract 
interface IRealm
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Returns true if this realm supports auxiliary authentication challenges.
 * Supporting an auxiliary authentication challenge does not mean that an authentication
 * challenge will occur every time a user logs on because the decision of when to challenge
 * may be based on other factors eg., user has not logged in for more than 3 months etc.,
 */
public abstract boolean supportsAuxiliaryChallenge();




/**
 * Returns the name of this realm.
 */
public abstract String getName();


}


