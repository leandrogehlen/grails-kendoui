package org.grails.plugin.kendoui

import grails.converters.JSON

import org.grails.plugin.resource.ResourceTagLib

class KendouiTagLib {

	private static final String KENDO_WIDGET = "kendo.widget"
	private static final String KENDO_MASTER = "kendo.master"
	private static final String KENDO_COLUMNS = "kendo.columns"
	private static final String KENDO_JQUERY_FUNCTION = "\n\$(function() {//kendo-widgets \n"

	private static int counter = 1

	static namespace = "k"

	def grailsResourceProcessor

	private storePageFragment(fragment, String disposition = "head") {
		request.setAttribute('resources.need.layout', true)

		def trkName = ResourceTagLib.makePageFragmentKey("script", disposition)
		grailsResourceProcessor.addDispositionToRequest(request, disposition, "Kendoui JS")

		def trk = request[trkName]
		if (!trk) {
			trk = []
			request[trkName] = trk
		}

		if (!trk.contains(KENDO_JQUERY_FUNCTION)) {
			trk << KENDO_JQUERY_FUNCTION
			trk << "});\n"
		}

		trk.set(trk.size() -1, fragment + "\n")
		trk << "});\n"
	}

	private void createWidget(attrs, body, String name) {
		def child = attrs.findAll {k, v -> !HtmlUtil.isHtmlAttribute(k) || k == "data" }
		def widget = request.getAttribute(KENDO_WIDGET)
		def master = (widget == null)

		if (master){
			widget = ["$name" : child]
		}
		else {
			if (name == "column") {
				def columns = request.getAttribute(KENDO_COLUMNS)
				columns << child
			}
			else if (name == "columns") {
				widget["$name"] = request.getAttribute(KENDO_COLUMNS)
			}
			else {
				widget["$name"] = child
			}
		}

		request.setAttribute(KENDO_MASTER, false)
		request.setAttribute(KENDO_WIDGET, child)
		try {
			body.call()
		}finally {
			request.setAttribute(KENDO_MASTER, master)
			request.setAttribute(KENDO_WIDGET, widget)
		}
	}

	private void storeWidget(attrs, body, String name, String tag="div") {
		def disposition = attrs.remove("disposition") ?: "head"
		createWidget(attrs, body, name)

		if ( request.getAttribute(KENDO_MASTER) ) {
			def widget = request.getAttribute(KENDO_WIDGET)
			if (widget) {
				def content = widget.get("$name")
				def id = attrs?.id?.encodeAsJavaScript() ?: "kw${counter++}"
				def json = (content != [:]) ? (widget.get("$name") as JSON) as String : ""

				def htmlAttrs = attrs.findAll{k,v -> (HtmlUtil.isHtmlAttribute(k) && k != 'id')}
				htmlAttrs = HtmlUtil.tagEncode(htmlAttrs)

				out << "<$tag id=\"$id\" $htmlAttrs>"
				out << body.call()
				out << "</$tag>"

				storePageFragment("\$('#$id').$name($json);", disposition)
			}

			request.removeAttribute(KENDO_MASTER)
			request.removeAttribute(KENDO_WIDGET)
		}
	}


	def script = {attrs, body ->
		storePageFragment(body(), "defer")
	}

	/**
	 * @attr id
	 */
	def button = {attrs, body ->
		def icon = attrs?.iconCls ? "<span class=\"k-icon ${attrs.iconCls}\"></span>" : ""
		out << "<a class=\"k-button k-button-icontext\" ${HtmlUtil.tagEncode(attrs)}> ${icon}"
		out << body.call()
		out << "</a>"
	}

	/**
	 * @attr id
	 * @attr label
	 */
	def searchBox = {attrs, body ->
		storeWidget(attrs,  body,  "kendoSearchBox", "input")
	}

	/**
	 * @attr id
	 */
	def textBox = {attrs, body ->
		out << "<input class=\"k-textbox\" ${HtmlUtil.tagEncode(attrs)} />"
	}

	/**
	 * @attr id
	 * @attr animation
	 * @attr dataTextField
	 * @attr delay
	 * @attr enable
	 * @attr filter
	 * @attr height
	 * @attr highlightFirst
	 * @attr ignoreCase
	 * @attr minLength
	 * @attr placeholder
	 * @attr separator
	 * @attr suggest
	 * @attr template
	 */
	def autoComplete = {attrs, body ->
		storeWidget(attrs,  body,  "kendoAutoComplete", "input")
	}

	/**
	 * @attr id
	 * @attr culture
	 * @attr depth
	 * @attr footer
	 * @attr format
	 * @attr max
	 * @attr min
	 * @attr start
	 * @attr value
	 */
	def calendar = {attrs, body ->
		storeWidget(attrs,  body,  "kendoCalendar")
	}

	/**
	 * @attr id
	 * @attr palette
	 * @attr columns
	 * @attr value
	 * @attr format
	 * @attr max
	 * @attr min
	 * @attr start
	 * @attr value
	 */
	def colorPalette = {attrs, body ->
		storeWidget(attrs,  body,  "kendoColorPalette")
	}

	/**
	 * @attr id
	 * @attr buttons
	 * @attr columns
	 * @attr messages
	 * @attr palette
	 * @attr preview
	 * @attr toolIcon
	 * @attr value
	 */
	def colorPicker = {attrs, body ->
		storeWidget(attrs,  body,  "kendoColorPicker")
	}

	/**
	 * @attr id
	 * @attr autoBind
	 * @attr cascadeFrom
	 * @attr dataSource
	 * @attr dataTextField
	 * @attr dataValueField
	 * @attr delay
	 * @attr enable
	 * @attr filter
	 * @attr height
	 * @attr highlightFirst
	 * @attr ignoreCase
	 * @attr index
	 * @attr minLength
	 * @attr suggest
	 * @attr template
	 * @attr text
	 * @attr value
	 */
	def comboBox = {attrs, body ->
		storeWidget(attrs,  body,  "kendoComboBox")
	}

	/**
	 * @attr id
	 * @attr culture
	 * @attr dates
	 * @attr depth
	 * @attr footer
	 * @attr format
	 * @attr max
	 * @attr min
	 * @attr parseFormats
	 * @attr start
	 * @attr value
	 */
	def datePicker = {attrs, body ->
		storeWidget(attrs,  body,  "kendoDatePicker")
	}

	/**
	 * @attr id
	 * @attr culture
	 * @attr dates
	 * @attr depth
	 * @attr footer
	 * @attr format
	 * @attr interval
	 * @attr max
	 * @attr min
	 * @attr parseFormats
	 * @attr start
	 * @attr timeFormat
	 * @attr value
	 */
	def dateTimePicker = {attrs, body ->
		storeWidget(attrs,  body,  "kendoDateTimePicker")
	}


	/**
	 * @attr id
	 * @attr autoBind
	 * @attr cascadeFrom
	 * @attr dataSource
	 * @attr dataTextField
	 * @attr dataValueField
	 * @attr delay
	 * @attr enable
	 * @attr height
	 * @attr ignoreCase
	 * @attr index
	 * @attr optionLabel
	 * @attr template
	 * @attr text
	 * @attr value
	 */
	def dropDownList = {attrs, body ->
		attrs.style = "text-align: left"
		storeWidget(attrs,  body,  "kendoDropDownList", "select")
	}

	/**
	 * @attr id
	 * @attr encoded
	 * @attr messages
	 * @attr stylesheets
	 * @attr tools
	 * @attr imageBrowser
	 * @attr delay
	 * @attr enable
	 * @attr height
	 * @attr ignoreCase
	 * @attr index
	 * @attr optionLabel
	 * @attr template
	 * @attr text
	 * @attr value
	 */
	def editor = {attrs, body ->
		storeWidget(attrs,  body,  "kendoEditor")
	}

	/**
	 * @attr id
	 * @attr opacity
	 * @attr buttons
	 * @attr value
	 * @attr preview
	 */
	def flatColorPicker = {attrs, body ->
		storeWidget(attrs,  body,  "kendoFlatColorPicker")
	}

	/**
	 * @attr id
	 * @attr autoBind
	 * @attr columnMenu
	 * @attr dataSource
	 * @attr detailTemplate
	 * @attr editable
	 * @attr filterable
	 * @attr detailTemplate
	 * @attr groupable
	 * @attr height
	 * @attr navigatable
	 * @attr pageable
	 * @attr reorderable
	 * @attr resizable
	 * @attr rowTemplate
	 * @attr scrollable
	 * @attr selectable
	 * @attr sortable
	 * @attr toolbar
	 */
	def grid = {attrs, body ->
		storeWidget(attrs,  body,  "kendoGrid")
	}

	def columns = {attrs, body ->
		request.setAttribute(KENDO_COLUMNS, [])
		storeWidget(attrs,  body,  "columns")
	}

	/**
	 * @attr aggregates
	 * @attr attributes
	 * @attr encoded
	 * @attr field
	 * @attr filterable
	 * @attr footerTemplate
	 * @attr format
	 * @attr groupHeaderTemplate
	 * @attr groupFooterTemplate
	 * @attr headerAttributes
	 * @attr headerTemplate
	 * @attr hidden
	 * @attr sortable
	 * @attr template
	 * @attr title
	 * @attr width
	 * @attr values
	 * @attr menu
	 */
	def column = {attrs, body ->
		storeWidget(attrs,  body, "column")
	}

	/**
	 * @attr columns
	 * @attr filterable
	 * @attr sortable
	 */
	def columnMenu = {attrs, body ->
		storeWidget(attrs,  body,  "columnMenu")
	}

	/**
	 * @attr id
	 * @attr autoBind
	 * @attr dataSource
	 * @attr editTemplate
	 * @attr navigatable
	 * @attr selectable
	 * @attr template
	 * @attr altTemplate
	 */
	def listView = {attrs, body ->
		storeWidget(attrs,  body,  "kendoListView")
	}

	/**
	 * @attr id
	 * @attr closeOnClick
	 * @attr direction
	 * @attr hoverDelay
	 * @attr openOnClick
	 * @attr popupCollision
	 */
	def menu = {attrs, body ->
		storeWidget(attrs,  body,  "kendoMenu")
	}

	/**
	 * @attr id
	 * @attr autoBind
	 * @attr dataSource
	 * @attr dataTextField
	 * @attr dataValueField
	 * @attr delay
	 * @attr enable
	 * @attr filter
	 * @attr height
	 * @attr highlightFirst
	 * @attr ignoreCase
	 * @attr minLength
	 * @attr maxSelectedItems
	 * @attr placeholder
	 * @attr itemTemplate
	 * @attr tagTemplate
	 * @attr value
	 */
	def multiSelect = {attrs, body ->
		storeWidget(attrs,  body,  "kendoMultiSelect")
	}

	/**
	 * @attr id
	 * @attr culture
	 * @attr decimals
	 * @attr downArrowText
	 * @attr format
	 * @attr max
	 * @attr min
	 * @attr placeholder
	 * @attr spinners
	 * @attr step
	 * @attr upArrowText
	 * @attr value
	 */
	def numericTextBox = {attrs, body ->
		storeWidget(attrs,  body,  "kendoNumericTextBox")
	}

	/**
	 * @attr id
	 * @attr autoBind
	 * @attr buttonCount
	 * @attr dataSource
	 * @attr selectTemplate
	 * @attr linkTemplate
	 * @attr info
	 * @attr input
	 * @attr numeric
	 * @attr pageSizes
	 * @attr previousNext
	 * @attr refresh
	 */
	def pager = {attrs, body ->
		storeWidget(attrs,  body,  "kendoPager")
	}


	/**
	 * @attr id
	 * @attr animation
	 * @attr expandMode
	 */
	def panelBar = {attrs, body ->
		storeWidget(attrs,  body,  "kendoPanelBar")
	}


	/**
	 * @attr id
	 * @attr largeStep
	 * @attr max
	 * @attr min
	 * @attr orientation
	 * @attr selectionEnd
	 * @attr selectionStart
	 * @attr smallStep
	 * @attr tickPlacement
	 */
	def rangeSlider = {attrs, body ->
		storeWidget(attrs,  body,  "kendoRangeSlider")
	}

	/**
	 * @attr id
	 * @attr decreaseButtonTitle
	 * @attr increaseButtonTitle
	 * @attr largeStep
	 * @attr max
	 * @attr min
	 * @attr orientation
	 * @attr showButtons
	 * @attr smallStep
	 * @attr tickPlacement
	 * @attr value
	 */
	def slider = {attrs, body ->
		storeWidget(attrs,  body,  "kendoSlider")
	}

	/**
	 * @attr id
	 * @attr orientation
	 */
	def splitter = {attrs, body ->
		storeWidget(attrs,  body,  "kendoSplitter")
	}

	/**
	 * @attr id
	 * @attr dataContentField
	 * @attr dataContentUrlField
	 * @attr dataImageUrlField
	 * @attr dataSpriteCssClass
	 * @attr dataTextField
	 * @attr dataUrlField
	 */
	def tabStrip = {attrs, body ->
		storeWidget(attrs,  body,  "kendoTabStrip")
	}

	/**
	 * @attr id
	 * @attr culture
	 * @attr dates
	 * @attr format
	 * @attr interval
	 * @attr max
	 * @attr min
	 * @attr parseFormats
	 * @attr value
	 */
	def timePicker = {attrs, body ->
		storeWidget(attrs,  body,  "kendoTimePicker")
	}

	/**
	 * @attr id
	 * @attr autoHide
	 * @attr content
	 * @attr callout
	 * @attr filter
	 * @attr iframe
	 * @attr height
	 * @attr width
	 * @attr position
	 * @attr showAfter
	 * @attr showOn
	 * @attr enabled
	 * @attr format
	 * @attr template
	 */
	def tooltip = {attrs, body ->
		def name = (request.getAttribute(KENDO_MASTER)) ? "tooltip" : "kendoTooltip"
		storeWidget(attrs,  body,  name)
	}

	/**
	 * @attr id
	 * @attr dataImageUrlField
	 * @attr dataSource
	 * @attr dataSpriteCssClassField
	 * @attr dataTextField
	 * @attr dataUrlField
	 * @attr dragAndDrop
	 * @attr loadOnDemand
	 * @attr template
	 */
	def treeView = {attrs, body ->
		storeWidget(attrs,  body,  "kendoTreeView")
	}

	/**
	 * @attr id
	 * @attr enabled
	 * @attr multiple
	 */
	def upload = {attrs, body ->
		storeWidget(attrs,  body,  "kendoUpload")
	}

	/**
	 * @attr id
	 * @attr actions
	 * @attr appendTo
	 * @attr draggable
	 * @attr iframe
	 * @attr maxHeight
	 * @attr maxWidth
	 * @attr minHeight
	 * @attr minWidth
	 * @attr modal
	 * @attr resizable
	 * @attr title
	 * @attr visible
	 * @attr width
	 * @attr width
	 */
	def window = {attrs, body ->
		storeWidget(attrs,  body,  "kendoWindow")
	}


	def animation = {attrs, body ->
		storeWidget(attrs,  body,  "animation")
	}

	/**
	 * @attr effects
	 * @attr duration
	 */
	def close = {attrs, body ->
		storeWidget(attrs,  body,  "close")
	}

	/**
	 * @attr effects
	 * @attr duration
	 * @attr show
	 */
	def open = {attrs, body ->
		storeWidget(attrs,  body,  "open")
	}

	/**
	 * @attr effects
	 * @attr duration
	 */
	def collapse = {attrs, body ->
		storeWidget(attrs,  body,  "collapse")
	}

	/**
	 * @attr effects
	 * @attr duration
	 * @attr show
	 */
	def expand = {attrs, body ->
		storeWidget(attrs,  body,  "expand")
	}

	/**
	 * @attr empty
	 * @attr content
	 */
	def month = {attrs, body ->
		storeWidget(attrs,  body,  "month")
	}

	/**
	 * @attr ui
	 * @attr extra
	 * @attr ui
	 * @attr ui
	 * @attr ui
	 *
	 * extra
	 */
	def filterable = {attrs, body ->
		storeWidget(attrs,  body,  "filterable")
	}

	/**
	 * @attr confirmation
	 * @attr createAt
	 * @attr destroy
	 * @attr mode
	 * @attr template
	 * @attr update
	 */
	def editable = {attrs, body ->
		storeWidget(attrs,  body,  "editable")
	}

	/**
	 * @attr pageSize
	 * @attr previousNext
	 * @attr numeric
	 * @attr buttonCount
	 * @attr input
	 * @attr pageSizes
	 * @attr refresh
	 * @attr info
	 */
	def pageable = {attrs, body ->
		storeWidget(attrs,  body,  "pageable")
	}

	/**
	 * @attr virtual
	 */
	def scrollable = {attrs, body ->
		storeWidget(attrs,  body,  "scrollable")
	}

	/**
	 * @attr allowUnsort
	 * @attr mode
	 */
	def sortable = {attrs, body ->
		storeWidget(attrs,  body,  "sortable")
	}

	def operators = {attrs, body ->
		storeWidget(attrs,  body,  "operators")
	}

	/**
	 * @attr eq
	 * @attr neq
	 * @attr startswith
	 * @attr contains
	 * @attr doesnotcontain
	 * @attr endswith
	 */
	def string = {attrs, body ->
		storeWidget(attrs,  body,  "string")
	}

	/**
	 * @attr eq
	 * @attr neq
	 * @attr gte
	 * @attr gt
	 * @attr lte
	 * @attr lt
	 */
	def number = {attrs, body ->
		storeWidget(attrs,  body,  "number")
	}

	/**
	 * @attr eq
	 * @attr neq
	 * @attr gte
	 * @attr gt
	 * @attr lte
	 * @attr lt
	 */
	def date = {attrs, body ->
		storeWidget(attrs,  body,  "string")
	}

	/**
	 * @attr eq
	 * @attr neq
	 */
	def enums = {attrs, body ->
		storeWidget(attrs,  body,  "enums")
	}

	/**
	 * @attr name
	 * @attr text
	 * @attr className
	 */
	def command = {attrs, body ->
		storeWidget(attrs,  body,  "command")
	}

	/**
	 * @attr autoUpload
	 * @attr batch
	 * @attr removeField
	 * @attr removeUrl
	 * @attr removeVerb
	 * @attr saveField
	 * @attr saveUrl
	 */
	def async = {attrs, body ->
		storeWidget(attrs,  body,  "async")
	}

	/**
	 * @attr template
	 * @attr url
	 */
	def content = {attrs, body ->
		storeWidget(attrs,  body,  "async")
	}


	/**
	 * @attr id
	 * @attr width
	 * @attr height
	 */
	def tileSize = {attrs, body ->
		storeWidget(attrs,  body,  "tileSize")
	}

	/**
	 * @attr name
	 * @attr tooltip
	 * @attr exec
	 */
	def tools = {attrs, body ->
		storeWidget(attrs,  body,  "tools")
	}

	/**
	 * @attr name
	 * @attr template
	 * @attr text
	 */
	def toolbar = {attrs, body ->
		storeWidget(attrs,  body,  "toolbar")
	}

	/**
	 * @attr collapsed
	 * @attr collapsible
	 * @attr max
	 * @attr min
	 * @attr resizable
	 * @attr scrollable
	 * @attr size
	 */
	def panes = {attrs, body ->
		storeWidget(attrs,  body,  "panes")
	}

	/**
	 * @attr name
	 * @attr checkChildren
	 * @attr template
	 */
	def checkboxes = {attrs, body ->
		storeWidget(attrs,  body,  "checkboxes")
	}

	/**
	 * @attr text
	 * @attr value
	 * @attr template
	 */
	def items = {attrs, body ->
		storeWidget(attrs,  body,  "items")
	}

	/**
	 * @attr path
	 * @attr value
	 * @attr template
	 */
	def imageBrowser = {attrs, body ->
		storeWidget(attrs,  body,  "imageBrowser")
	}

	/**
	 * @attr pageSize
	 * @attr serverPaging
	 * @attr serverSorting
	 */
	def dataSource = {attrs, body ->
		storeWidget(attrs,  body,  "dataSource")
	}

	/**
	 * @attr thumbnailUrl
	 * @attr uploadUrl
	 * @attr imageUrl
	 * @attr read
	 */
	def transport = {attrs, body ->
		storeWidget(attrs,  body,  "transport")
	}

	/**
	 * @attr contentType
	 * @attr data
	 * @attr dataType
	 * @attr type
	 * @attr url
	 * @attr read
	 */
	def read = {attrs, body ->
		storeWidget(attrs,  body,  "read")
	}

	/**
	 * @attr contentType
	 * @attr data
	 * @attr dataType
	 * @attr type
	 * @attr url
	 * @attr read
	 */
	def destroy = {attrs, body ->
		storeWidget(attrs,  body,  "destroy")
	}

	/**
	 * @attr contentType
	 * @attr data
	 * @attr dataType
	 * @attr type
	 * @attr url
	 * @attr read
	 */
	def create = {attrs, body ->
		storeWidget(attrs,  body,  "create")
	}

	/**
	 * @data
	 * @total
	 */
	def schema  = {attrs, body ->
		storeWidget(attrs,  body,  "schema")
	}

	/**
	 * @attr id
	 */
	def model = {attrs, body ->
		storeWidget(attrs,  body,  "model")
	}

	/**
	 * @attr uploadFile
	 * @attr orderBy
	 * @attr orderByName
	 * @attr orderBySize
	 * @attr directoryNotFound
	 * @attr invalidFileType
	 * @attr overwriteFile
	 * @attr search
	 * @attr columns
	 * @attr filter
	 * @attr sortAscending
	 * @attr sortDescending
	 * @attr and
	 * @attr clear
	 * @attr info
	 * @attr isFalse
	 * @attr isTrue
	 * @attr or
	 * @attr empty
	 * @attr display
	 * @attr page
	 * @attr of
	 * @attr itemsPerPage
	 * @attr first
	 * @attr last
	 * @attr next
	 * @attr previous
	 * @attr refresh
	 */
	def messages = {attrs, body ->
		storeWidget(attrs,  body,  "messages")
	}

	/**
	 * @attr cancel
	 * @attr dropFilesHere
	 * @attr remove
	 * @attr retry
	 * @attr select
	 * @attr statusFailed
	 * @attr statusUploaded
	 * @attr statusUploading
	 * @attr uploadSelectedFiles
	 */
	def localization = {attrs, body ->
		storeWidget(attrs,  body,  "localization")
	}
}
