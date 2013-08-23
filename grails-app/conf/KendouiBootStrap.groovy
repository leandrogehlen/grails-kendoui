import grails.converters.JSON

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.plugin.kendoui.KendouiConfig;
import org.grails.plugin.kendoui.converter.DomainClassMarshaller
import org.grails.plugin.kendoui.converter.JavaScriptMapMarshaller


class KendouiBootStrap {

	GrailsApplication grailsApplication
	
    def init = { servletContext ->
		JSON.registerObjectMarshaller( new JavaScriptMapMarshaller() )
		if (KendouiConfig.registerMarshaller) {
			JSON.registerObjectMarshaller( new DomainClassMarshaller(true, grailsApplication) )				
			JSON.createNamedConfig(DomainClassMarshaller.DOMAIN_LOAD, {
				it.registerObjectMarshaller(new DomainClassMarshaller(true, true, grailsApplication))
			})
		}
    }
	
    def destroy = {
    }	
}
