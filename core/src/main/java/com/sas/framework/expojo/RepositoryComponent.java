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
package com.sas.framework.expojo;

import java.lang.*;
import com.sas.framework.expojo.ExpojoComponent;
    
import com.sas.framework.expojo.ExpojoContext;










// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : RepositoryComponent
 * Diagram    : Expojo Context and Components
 * Project    : ExPOJO Core
 * Type       : abstract
 * Superclass for repository components - ie., those that provide facilities for finding
 * and locating domain objects in the datastore.
 * Extend this class to make your application specific repositories. Typically these
 * will include query methods written to an persistence engine specific query API and
 * query language. Ideally you should provide an abstract superclass for each type of
 * repository you require and then provide a concrete implementation for your specific
 * persistence engine and name them appropriately (eg., HibernateRoleManagerRepository
 * or JdoRoleManagerRepository, IBatisRoleManagerRepository).
 * Structuring your repositories in this way makes your application code (GUI and model)
 * completely independent of any particular persistence engine.
 * 
 * @author Chris Colman
 */
public abstract 
class RepositoryComponent extends ExpojoComponent
{
// -[KeepWithinClass]-


// -[Fields]-












// -[Methods]-

}


