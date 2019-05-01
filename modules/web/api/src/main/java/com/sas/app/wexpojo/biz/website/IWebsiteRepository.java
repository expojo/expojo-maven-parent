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
import java.io.Serializable;


// -[KeepBeforeClass]-

import java.util.Collection;

// -[Class]-

/**
 * Class Name : IWebsiteRepository
 * Diagram    : Website API
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * Basic repository of hosted websites.
 * 
 * @author Chris Colman
 */
public abstract 
interface IWebsiteRepository extends Serializable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Returns all IWebsites in the repository.
 */
public abstract Collection<IWebsite> findAllWebsites();




/**
 * Finds a website given its host name and returns it.
 * If not found this will return null (no OptionalS in this code friends. People who
 * need trainig wheels to handle nulls are in the wrong job)
 */
public abstract IWebsite findWebsiteByHostName(String hostName);




/**
 * Adds a website to the repo.
 */
public abstract void addWebsite(IWebsite website);

}


