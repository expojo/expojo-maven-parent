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
package com.sas.framework.expojo.servlet;

import java.lang.*;


// -[KeepBeforeClass]-

import javax.servlet.ServletContext;


// -[Class]-

/**
 * Class Name : TcpHostAttributes
 * Diagram    : Expojo Foundation, ServletFilter
 * Project    : ExPOJO Core
 * Type       : concrete
 * The specifications of a host that supports TCP connections.
 * Includes: 
 * - hostname
 * - subdomain name
 * - port number
 * - whether the connection supports TLS/SSL or not
 * 
 * Also contains methods to configure the attributes from a given servlet context, the
 * values of which are configurable in a context.xml file.
 * 
 * @author Chris Colman
 */
public 
class TcpHostAttributes
{
// -[KeepWithinClass]-


// -[Fields]-



/**
 * The hostname
 */
private String host;



/**
 * The prefix that appears at the start of every parameter. This is necessary to support
 * multple TcpHostAttribute instances in the same application and configured in the same
 * file.
 * 
 * NOTE: The parameterPrefixes must be unique within any given application.
 */
private String parameterPrefix = "service";



/**
 * The subdomain prefix that goes in front of the host name.
 */
private String subdomain;



/**
 * Port number.
 */
private int port;



/**
 * true if the host supports TLS/SSL encryption.
 */
private boolean encrypted = false;


// -[Methods]-

/**
 * Forms a URL for the host using:
 * 
 * [http|https]://subdomain.host:port
 */
public String getUrl()
{
	String protocol = getEncrypted() ? "https://" : "http://";
	String url = protocol + getSubdomain() + "." + getHost() + ":" + getPort();
	
	return url;
}

/**
 * Constructs the object
 */
public TcpHostAttributes(String iParameterPrefix)
{
	parameterPrefix = iParameterPrefix;
}

/**
 * Sets parameterPrefix
 */
public void setParameterPrefix(String parameterPrefix)
{
    this.parameterPrefix = parameterPrefix;
}

/**
 * Returns parameterPrefix
 */
public String getParameterPrefix()
{
    return parameterPrefix;
}

/**
 * Sets encrypted
 */
public void setEncrypted(boolean encrypted)
{
    this.encrypted = encrypted;
}

/**
 * Returns encrypted
 */
public boolean getEncrypted()
{
    return encrypted;
}

/**
 * Sets port
 */
public void setPort(int port)
{
    this.port = port;
}

/**
 * Returns port
 */
public int getPort()
{
    return port;
}

/**
 * Sets host
 */
public void setHost(String host)
{
    this.host = host;
}

/**
 * Returns host
 */
public String getHost()
{
    return host;
}

/**
 * Sets subdomain
 */
public void setSubdomain(String subdomain)
{
    this.subdomain = subdomain;
}

/**
 * Returns subdomain
 */
public String getSubdomain()
{
    return subdomain;
}

/**
 * Reads the specs from parameters within the given ServletContext and assigns to the
 * appropriate attribute.
 */
public void readConfigParameters(ServletContext servletContext)
{
		// retrieve app server parameters
		host = servletContext.getInitParameter(parameterPrefix + ".host");

		subdomain = servletContext.getInitParameter(parameterPrefix + ".subdomain");
		if ( subdomain == null )
			subdomain = "dev";

		// if empty then port 80 is assumed
		String portStr = servletContext.getInitParameter(parameterPrefix + ".port");
		if ( portStr != null )
			port = Integer.parseInt(portStr);
		else
			port = 80;

		encrypted = false;

		String entityServiceSecuredBySslStr = servletContext.getInitParameter(parameterPrefix + ".ssl");
		if (entityServiceSecuredBySslStr != null && entityServiceSecuredBySslStr.length() > 0)
		{
			if ( entityServiceSecuredBySslStr.equals("true") )
				encrypted = true;
		}
}

}


