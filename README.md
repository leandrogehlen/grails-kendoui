Grails KendoUI
==============

Provides integration for the JQuery KendoUI library with Grails

Grails KendoUI Plugin helps you to develop rich applications using the jQuery KendoUI library.

The plugin implements taglibs for writing the compontentes in gsp, plus the scripts to generate scaffold to produce CRUD quick and powerful.


Including the resources
------------------------

You must use the Grails resources framework to make use of this plugin.

Download http://www.kendoui.com/download.aspx, extracting content in webapp/js/kendoui

Configuration
-------------
The following settings are available
```groovy
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
```

TagLibs
------

```xml
<html>
    <head>
        <title>Hello World Demo</title>
        <r:require module="kendoui_core"/>
        <r:layoutResources />
    </head>
    <body>
        <k:widgetName property1="value1" property2="value2" ...>
        </k:widgetName>
    </body>
</html>
```

Scaffold
--------
We can generate scaffold with commands:

```
grails kendoui-generate-controller [domainClass]
grails kendoui-generate-view [domainClass]
grails kendoui-generate-all [domainClass]
grails kendoui-install-templates
```
