import org.grails.plugin.kendoui.KendouiConfig

def theme = KendouiConfig.theme
def culture = KendouiConfig.culture
def location = KendouiConfig.location
def suffix = KendouiConfig.libraryType

modules = {
				
	kendoui_core {
		resource url: "js/$location/styles/kendo.rtl${suffix}.css", disposition: 'head'
		resource url: "js/$location/styles/kendo.common${suffix}.css", disposition: 'head'
		resource url: "js/$location/styles/kendo.${theme}${suffix}.css", disposition: 'head'
		resource url: [plugin: "kendoui", dir: "css", file: "kendo.messager.css"], disposition: 'head'		
				
		resource url: "js/$location/js/jquery${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.core${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.web${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/cultures/kendo.culture.${culture}${suffix}.js", disposition: 'head'
		resource url: [plugin: "kendoui", dir: "js", file: "kendo.messager.js"], disposition: 'head'
		resource url: [plugin: "kendoui", dir: "js/cultures", file: "kendo.culture.${culture}.js"], disposition: 'head'
	}
	
	kendoui_data {
		resource url: "js/$location/js/kendo.data${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.data.odata${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.data.xml${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.binder${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.validator${suffix}.js", disposition: 'head'		
	}
	
	kendoui_framework {
		resource url: "js/$location/js/kendo.editable${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.groupable${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.reorderable${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.resizable${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.selectable${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.sortable${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.router${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.userevents${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.view${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.fx${suffix}.js", disposition: 'head'
	}
	
	kendoui_all {
		dependsOn "kendoui_core, kendoui_data, kendoui_framework"
		resource url: "js/$location/js/kendo.autocomplete${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.calendar${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.colorpicker${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.columnmenu${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.combobox${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.datepicker${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.datetimepicker${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.draganddrop${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.dropdownlist${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.editor${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.filtermenu${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.grid${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.imagebrowser${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.list${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.listview${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.menu${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.mobile.scroller${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.multiselect${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.numerictextbox${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.pager${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.panelbar${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.popup${suffix}.js", disposition: 'head'				
		resource url: "js/$location/js/kendo.slider${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.splitter${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.tabstrip${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.timepicker${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.tooltip${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.treeview${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.upload${suffix}.js", disposition: 'head'				
		resource url: "js/$location/js/kendo.window${suffix}.js", disposition: 'head'				
	}	
		
	kendoui_scaffold {
		dependsOn "kendoui_core"
				
		resource url: "js/$location/js/kendo.grid${suffix}.js", disposition: 'head'		
		resource url: "js/$location/js/kendo.window${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.validator${suffix}.js", disposition: 'head'
		resource url: "js/$location/js/kendo.binder${suffix}.js", disposition: 'head'
		
		resource url: [plugin: "kendoui", dir: "css", file: "kendo.scaffold.css"], disposition: 'head'
		resource url: [plugin: "kendoui", dir: "js", file: "kendo.scaffold.js"], disposition: 'head'				
	}
}
