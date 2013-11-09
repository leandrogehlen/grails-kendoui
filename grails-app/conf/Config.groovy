grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"

log4j = {
    error 'org.codehaus.groovy.grails',
          'org.springframework'
}

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside null
                scriptlet = 'none' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
remove this line */
// grails-kendoui configuration
// available "en", "pt_BR"
grails.plugin.kendoui.culture = 'en'

//folder name found in "kendoui/styles"
grails.plugin.kendoui.theme = 'Black'

//defines whether use source or compressed library (available: 'src', 'mim')
grails.plugin.kendoui.library.type = 'src'

//Auto register marshaller on bootstrap
grails.plugin.kendoui.register.marshaller = true

//Defines location where was extracted JQuery Kendoui JavaScript library
grails.plugin.kendoui.location = 'kendoui'
