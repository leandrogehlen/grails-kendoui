import grails.converters.JSON

import org.grails.plugin.kendoui.KendouiConfig
import org.grails.plugin.kendoui.converter.DomainClassMarshaller
import org.grails.plugin.kendoui.converter.JavaScriptMapMarshaller

class KendouiGrailsPlugin {

    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def title = "Grails Kendoui Plugin"
    def author = "Leandro Guindani Gehlen"
    def authorEmail = "leandrogehlen@gmail.com"
    def description = "Supplies jQuery KendoUI resources and taglibs. Depends on jQuery KendoUI. See http://www.kendoui.com/"

    def documentation = "http://grails.org/plugin/kendoui"

    def license = "APACHE"
    def issueManagement = [system: "GITHUB", url: "https://github.com/leandrogehlen/grails-kendoui/issues"]
    def scm = [url: "https://github.com/leandrogehlen/grails-kendoui"]

    def doWithApplicationContext = { ctx ->

        JSON.registerObjectMarshaller( new JavaScriptMapMarshaller() )

        if (KendouiConfig.registerMarshaller) {
            JSON.registerObjectMarshaller( new DomainClassMarshaller(true, application) )
            JSON.createNamedConfig DomainClassMarshaller.DOMAIN_LOAD, {
                it.registerObjectMarshaller(new DomainClassMarshaller(true, true, application))
            }
        }
    }
}
