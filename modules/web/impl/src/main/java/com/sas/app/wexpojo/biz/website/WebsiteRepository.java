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
import com.sas.app.wexpojo.biz.website.IWebsiteRepository;
import com.sas.framework.expojo.RepositoryComponent;
    
import com.sas.app.wexpojo.biz.website.WebsiteInMemoryStore;


// -[KeepBeforeClass]-
import java.util.*;

import com.sas.framework.expojo.Ex;


// -[Class]-

/**
 * Class Name : WebsiteRepository
 * Diagram    : Website Repository
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Provides access to the website store.
 * 
 * @author Chris Colman
 */
public 
class WebsiteRepository extends RepositoryComponent implements IWebsiteRepository
{
// -[KeepWithinClass]-


// -[Fields]-
    
    protected WebsiteInMemoryStore websiteStore;


// -[Methods]-

/**
 * Returns the Website store.
 */
protected WebsiteInMemoryStore getWebsiteStore()
{
	return WebsiteInMemoryStore.get();
}

/**
 * Constructs the object, initializing the reference to the website store.
 */
public WebsiteRepository()
{
}

/**
 * Returns all IWebsites in the repository.
 */
public Collection<IWebsite> findAllWebsites()
{
	return getWebsiteStore().findAllWebsites();
}

/**
 * Finds a website given its host name and returns it.
 * If not found this will return null (no OptionalS in this code friends. People who
 * need trainig wheels to handle nulls are in the wrong job)
 */
public IWebsite findWebsiteByHostName(String hostName)
{
	return getWebsiteStore().findWebsiteByHostName(hostName);
}

/**
 * Adds a website to the repo.
 */
public void addWebsite(IWebsite website)
{
	getWebsiteStore().addWebsite(website);
}

/**
 * Static convenience method to return the repository
 */
public static IWebsiteRepository get()
{
	return Ex.c(IWebsiteRepository.class);
}

}


