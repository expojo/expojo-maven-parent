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


// -[KeepBeforeClass]-
import com.sas.framework.expojo.ModelExposer;
import javax.jdo.FetchPlan;


// -[Class]-

/**
 * Class Name : JdoUtil
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * Provides static utility methods related to JDO.
 * 
 * @author Chris Colman
 */
public 
class JdoUtil
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Returns the fetch plan for the current persistence manager.
 */
public static FetchPlan getFetchPlan()
{
	return ((JdoPersistenceProvider)ModelExposer.pp()).getPm().getFetchPlan();
}

}


