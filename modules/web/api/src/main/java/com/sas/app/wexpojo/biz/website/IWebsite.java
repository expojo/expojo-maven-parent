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
    
import com.sas.app.wexpojo.biz.website.IWebsiteBehaviour;
    
import com.sas.framework.iam.authentication.ICredentialSpec;
    
import com.sas.framework.iam.realm.IRealm;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IWebsite
 * Diagram    : Website API
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * WexPOJO supports multiple website within the one app.
 * 
 * IWebsite is an interface that represents a website.
 * 
 * 
 * @author Chris Colman
 */
public abstract 
interface IWebsite extends Serializable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-



/**
 * Return the realm associated with this website.
 */
public abstract IRealm getRealm();



/**
 * Returns an ICredentialSpec appropriate for this website.
 */
public abstract ICredentialSpec getCredentialSpec();



/**
 * Returns the website behaviour for this website. It is valid to return null for this
 * in which case default behaviour will be used.
 */
public abstract IWebsiteBehaviour getBehaviour();



/**
 * Returns the ID of the theme within the template that this website is using.
 * The head section of the HTML will be generated to include the following CSS file:
 * 
 * //pagebloom/css/t/${templateId}/${themeId}.css
 */
public abstract String getThemeId();




/**
 * Returns unique ID for this website.
 * Can be used a path component in the filename of various site specific files. This
 * allows each website to have its own separate subdirectory for site specific files.
 * 
 */
public abstract int getSiteId();




/**
 * Each website can specify which web template (collection of HTML files with appropriate
 * suffixes) should be used to display its pages.
 * 
 * Templates are identified by an integer ID and are a set of HTML files. Each template
 * can provide customized variations of the standard HTML file. 
 * 
 * For example:
 * 
 * HomePage.html may provide a vanilla home page.
 * 
 * HomePage_23.html may provide a home page styled according to the style of template
 * with ID - 23.
 * 
 * As well as providing HTML markup file variations for pages the web developer can also
 * provide markup files styled for a particular template for panels.
 * 
 * If a particular markup file does not have a variant appropriate for the template of
 * a particular website then Wicket will default to using the default markup - i.e. the
 * one with no variant suffix.
 */
public abstract int getTemplateId();




/**
 * Returns the host name of the webserver(s) hosting this website.
 */
public abstract String getHostName();




/**
 * Gets name of website.
 */
public abstract String getName();


}


