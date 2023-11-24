/*
 * generated by Xtext 2.32.0
 */
package org.contextmapper.servicecutter.dsl.ide

import com.google.inject.Guice
import org.contextmapper.servicecutter.dsl.ServiceCutterConfigurationDSLRuntimeModule
import org.contextmapper.servicecutter.dsl.ServiceCutterConfigurationDSLStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class ServiceCutterConfigurationDSLIdeSetup extends ServiceCutterConfigurationDSLStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new ServiceCutterConfigurationDSLRuntimeModule, new ServiceCutterConfigurationDSLIdeModule))
	}
	
}
