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
    
import com.sas.app.wexpojo.biz.website.IWebsite;


// -[KeepBeforeClass]-
import java.util.*;


// -[Class]-

/**
 * Class Name : WebsiteInMemoryStore
 * Diagram    : Website Domain Model Impl
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * This is a simple non persistent "in memory" store.
 * 
 * It must be populated at start up time by the application.
 * 
 * @author Chris Colman
 */
public 
class WebsiteInMemoryStore
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Static reference to the singleton WebsiteInMemoryStore.
 */
private static final WebsiteInMemoryStore self = new WebsiteInMemoryStore();
    
    protected HashMap<Object, IWebsite> websites;


// -[Methods]-




/**
 * Static convenience method to return the website store.
 */
public static WebsiteInMemoryStore get()
{
	return self;
}

/**
 * Returns all IWebsites in the repository.
 */
public Collection<IWebsite> findAllWebsites()
{
	return websites.values();
}




/**
 * Constructs the object
 */
private WebsiteInMemoryStore()
{
	websites = new HashMap<>();
}




/**
 * Finds a website given its host name and returns it.
 * If not found this will return null (no OptionalS in this code friends. People who
 * need trainig wheels to handle nulls are in the wrong job)
 */
public IWebsite findWebsiteByHostName(String hostName)
{
	return websites.get(hostName);
}




/**
 * Adds a website to the repo.
 */
public void addWebsite(IWebsite website)
{
	websites.put(website.getHostName(), website);
}

}


