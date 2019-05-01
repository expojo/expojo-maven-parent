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
package com.sas.framework.expojo.jdo.datanucleus;

import java.lang.*;
import com.sas.framework.expojo.jdo.JdoExpojoContextFactory;
    
import com.sas.framework.system.IModule;
    
import com.sas.framework.util.Util;
    
import com.sas.framework.expojo.jdo.JdoPersistenceProvider;
    
import com.sas.framework.expojo.PersistenceProvider;
    
import com.sas.framework.expojo.servlet.ExpojoFoundation;
    
import com.sas.framework.system.IModulesProvider;
    
import com.sas.framework.expojo.ModelRef;
    
import com.sas.framework.expojo.servlet.ExpojoServletFilter;

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


// -[KeepBeforeClass]-
import java.util.*;


// -[Class]-

/**
 * Class Name : NucleusJdoExpojoContextFactory
 * Diagram    : Expojo JDO specific classes
 * Project    : Expojo JDO implementation
 * Type       : concrete
 * ModelExposer factory for DataNucleus/JDO.
 * 
 * @author Chris Colman
 */
public 
class NucleusJdoExpojoContextFactory extends JdoExpojoContextFactory
{
// -[KeepWithinClass]-


// -[Fields]-

// [Added by Code Injection Wizard: Log4J Logging Support]
// Do not edit code injected by the wizard directly in the source file as
// as it will be overwritten during subsequent updates. 
private static final Logger logger = LogManager.getLogger(NucleusJdoExpojoContextFactory.class);


// -[Methods]-



/**
 * Constructs the object
 */
public NucleusJdoExpojoContextFactory() {
}




/**
 * Constructs the object
 */
public NucleusJdoExpojoContextFactory(IModulesProvider iModulesProvider)
{
	super(iModulesProvider);
}

/**
 * Customizes properties
 */
public void customizeProperties(Properties properties)
{
	super.customizeProperties(properties);

	List<String> packages = new ArrayList<>();
	Map<String, IModule> packagesAsMap = new HashMap<>();

	for (IModule module : getModules())
	{
		String[] metaDataPackages = module.getMetaDataPackages();

		// Check for duplicates because the way Java class loaders work only the first instance
		// of a package.jdo file to be discovered will be used!!!
		for (String mdp: metaDataPackages)
		{
			IModule precedentModule = packagesAsMap.get(mdp);
			if (precedentModule != null)
			{
				logger.error("MetaDataPackage '" + mdp + "' already exists in the list of metadata packages.");
				logger.error("    Module attempting to add it: " + module.getClass().getSimpleName());
				logger.error("    Module that has already added it: " + precedentModule.getClass().getSimpleName());
			}

			packages.add(mdp);
			packagesAsMap.put(mdp, module);
		}
	}
	String value = Util.collectionToDelimitedString(packages, ",");
	properties.setProperty("datanucleus.autoStartMetaDataFiles", value);
}



/**
 * Returns the name of the properties file used for configuring the JDO datastore.
 */
public String getPropertiesFilename()
{
	return "datanucleus.properties";
}

}


