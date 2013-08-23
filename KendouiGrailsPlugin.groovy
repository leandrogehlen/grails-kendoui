class KendouiGrailsPlugin {

    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Grails Kendoui Plugin"
    def author = "Leandro Guindani Gehlen"
    def authorEmail = "leandrogehlen@gmail.com"
    def description = "Supplies jQuery KendoUI resources and taglibs. Depends on jQuery KendoUI. See http://www.kendoui.com/"

    def documentation = "http://grails.org/plugin/kendoui"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
	def license = "APACHE"	

    // Any additional developers beyond the author specified above.	
	//def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
	//def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
	//def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
