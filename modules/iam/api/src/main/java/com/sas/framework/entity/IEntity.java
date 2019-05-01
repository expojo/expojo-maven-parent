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
package com.sas.framework.entity;

import java.lang.*;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IEntity
 * Diagram    : Entity
 * Project    : Entity Model Framework
 * Type       : interface
 * Interface representing an entity.
 * An entity could be any of these:
 * person
 * organization
 * software agent - i.e. a software agent that has access to use a particular system
 * vehicle
 * real estate asset
 * + many more
 * or a myriad of others
 * 
 * @author Chris Colman
 */
public abstract 
interface IEntity
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * The name of this entity - not necessarily unique across the system.
 */
public abstract String getName();




/**
 * Returns the name to display for this entity.
 * The display name is unique across the system so that it is possibly to uniquely identify
 * an entity by it's display name. The display name does not have to have any resemblance
 * to the name returned by getName but it may.
 * e.g. getName may return Bill Smith for many entities but for each entity the getDisplayName
 * for each must be different so you could end up with display names being billsmith1,
 * billsmith57, bill smith A, Bill Smith 27 etc.,
 */
public abstract String getDisplayName();


}


