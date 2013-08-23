package org.grails.plugin.kendoui

import org.apache.commons.lang.StringUtils;

import grails.util.Holders

class KendouiConfig {

	static ConfigObject getConfig() {
		Holders.grailsApplication.config.grails.plugin.kendoui
	}

	static String getCulture() {
		getConfig().culture ?: 'en'
	}

	static String getTheme() {
		getConfig().theme ?: 'Default'
	}

	static String getLocation() {
		getConfig().location ?: 'kendoui'
	}
	
	static String getLibraryType() {
		getConfig().library.type == 'src' ? '' : '.min'
	}
	
	static Boolean getRegisterMarshaller() {
		getConfig().register.marshaller ? getConfig().register.marshaller as Boolean : true
	}

}
