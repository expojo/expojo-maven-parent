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
import com.sas.framework.iam.realm.IRealm;
    
import com.sas.app.wexpojo.biz.website.IWebsiteBehaviour;
    
import com.sas.app.wexpojo.biz.website.IWebsiteBehaviour;
    
import com.sas.framework.iam.authentication.ICredentialSpec;


// -[KeepBeforeClass]-
import com.sas.framework.iam.authentication.standard.StandardCredentialSpec;


// -[Class]-

/**
 * Class Name : Website
 * Diagram    : Website Domain Model Impl
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Stores details about a website.
 * 
 * @author Chris Colman
 */
public 
class Website implements IWebsite, IRealm
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * Returns unique ID for this website.
 * Can be used a path component in the filename of various site specific files. This
 * allows each website to have its own separate subdirectory for site specific files.
 * .
 */
private int siteId;



/**
 * Name of the website.
 */
private String name;



/**
 * The host name of the webserver(s) hosting this website.
 */
private String hostName;



/**
 * The unique ID (integer) of a particular template.
 * 
 * By mandatory convention these are ID numbers (integer) not names. This allows abstraction
 * of the internal identity of a template away from its name so that name changes to
 * the template will not affect the operation or require renaming of markup or CSS files
 * or directories.
 * 
 * 
 */
private int templateId;



/**
 * The ID of the theme within the template that this website is using.
 * The head section of the HTML will be generated to include the following CSS file:
 * 
 * //pagebloom/css/t/${templateId}/${themeId}.css
 */
private String themeId;
    
    protected IWebsiteBehaviour behaviour;


// -[Methods]-

/**
 * Return the realm associated with this website.
 */
public IRealm getRealm()
{
	return this;
}

/**
 * Returns true if this realm supports auxiliary authentication challenges.
 * Supporting an auxiliary authentication challenge does not mean that an authentication
 * challenge will occur every time a user logs on because the decision of when to challenge
 * may be based on other factors eg., user has not logged in for more than 3 months etc.,
 */
public boolean supportsAuxiliaryChallenge()
{
	return behaviour.supportsAuxiliaryChallenge();
}

/**
 * Sets siteId
 */
public void setSiteId(int siteId)
{
    this.siteId = siteId;
}

/**
 * Returns siteId
 */
public int getSiteId()
{
    return siteId;
}

/**
 * Sets hostName
 */
public void setHostName(String hostName)
{
    this.hostName = hostName;
}

/**
 * Returns hostName
 */
public String getHostName()
{
    return hostName;
}

/**
 * Returns an ICredentialSpec appropriate for this website.
 */
public ICredentialSpec getCredentialSpec()
{
	return new StandardCredentialSpec();
}

/**
 * Constructs the object
 */
public Website(int iId, String iName, String iHostName, IWebsiteBehaviour iBehaviour)
{
	siteId = iId;
	name = iName;
	hostName = iHostName;
	behaviour = iBehaviour;
}




/**
 * Return IWebsiteBehaviour.
 */
public IWebsiteBehaviour getBehaviour()
{
	return behaviour;
}

/**
 * Sets themeId
 */
public void setThemeId(String themeId)
{
    this.themeId = themeId;
}




/**
 * Returns themeId or, if null, will return "default".
 */
public String getThemeId()
{
    return themeId != null ? themeId : "default";
}

/**
 * Sets templateId
 */
public void setTemplateId(int templateId)
{
    this.templateId = templateId;
}

/**
 * Returns templateId
 */
public int getTemplateId()
{
    return templateId;
}

/**
 * Sets name
 */
public void setName(String name)
{
    this.name = name;
}

/**
 * Returns name
 */
public String getName()
{
    return name;
}

/**
 * Constructs the object
 */
public Website(int iId, String iName, String iHostName)
{
	this(iId, iName, iHostName, new UnrestrictedWebsiteBehaviour());
}

}


