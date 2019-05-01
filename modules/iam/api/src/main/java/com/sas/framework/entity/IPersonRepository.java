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
    
import com.sas.framework.entity.IPerson;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IPersonRepository
 * Diagram    : Entity
 * Project    : Entity Model Framework
 * Type       : interface
 * Respository to retrieve IPerson objects.
 * 
 * @author Chris Colman
 */
public abstract 
interface IPersonRepository
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Finds an IPerson by its display name.
 */
public abstract IPerson findPersonByDisplayName(String displayName);

}


