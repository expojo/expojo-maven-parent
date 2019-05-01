// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2008,2019. Step Ahead Software Pty Ltd. All rights reserved.
 * Usage is governed by the terms of the Apache 2 License.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.app.wexpojo.biz.website;

import java.lang.*;
import com.sas.framework.system.DefaultModule;
    
import com.sas.framework.expojo.ModelExposer;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : WebsiteModule
 * Diagram    : Website module
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Module for providing website persistence.
 * 
 * @author Chris Colman
 */
public 
class WebsiteModule extends DefaultModule
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Adds service and repository components to the ModelExposer.
 */
public void addComponents(ModelExposer modelExposer)
{
	// Add components
	modelExposer.addRepository(IWebsiteRepository.class, new WebsiteRepository());
}

}


