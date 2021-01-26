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
import java.util.ArrayList;
import java.util.List;

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
 * Returns details of the host.
 */
public String toString()
{
	return "Host: [" + subdomain + ".]" + host + " Port: " + port + " Encrypted: " + (encrypted ? "Yes" : "No");
}



/**
 * Constructs the object
 */
public TcpHostAttributes() {
}




/**
 * Reads a set of parameters with a contiguously indexed parameter prefix within the
 * given ServletContext and returns a list of TcpHostAttributes read in.
 * 
 * Note: host is the only mandatory parameter for each set.
 * 
 * For example:
 * 
 * <Parameter name="entity.service.0.host" value="pagebloom.com"/>
 * <Parameter name="entity.service.0.subdomain" value= "dev"/>
 * <Parameter name="entity.service.0.port" value= "80"/>
 * <Parameter name="entity.service.0.ssl" value= "false"/>
 * 
 * <Parameter name="entity.service.1.host" value="employerstream.com.au"/>
 * <Parameter name="entity.service.1.subdomain" value= "dev"/>
 * <Parameter name="entity.service.1.port" value= "80"/>
 * <Parameter name="entity.service.1.ssl" value= "false"/>
 * 
 * The method repeatedly reads in the sets starting at index 0, then index 1 etc., stopping
 * as soon as the next indexed prefixes return no host value.
 * 
 * Implementation detail: It does not test for values other than 'host' as it is the
 * only mandatory parameter so testing for the others will not be a reliable test for
 * the presence of a parameter set.
 * 
 */
public static List<TcpHostAttributes> readSetOfConfigParameters(String parameterPrefix, ServletContext servletContext)
{
	List<TcpHostAttributes> tcpHostAttributesList = new ArrayList<>();

	for(int i = 0; ; i++)
	{
		// not worried about the performance of a small amount of string concats that will take place once at app start up
		TcpHostAttributes tcpHostAttributes = new TcpHostAttributes(parameterPrefix + "." + i);

		tcpHostAttributes.readConfigParameters(servletContext);

		String host = tcpHostAttributes.getHost();

		if (host == null)
			break;  // no parameters set with the current index so abort

		tcpHostAttributesList.add(tcpHostAttributes);
	}

	return tcpHostAttributesList;
}

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


