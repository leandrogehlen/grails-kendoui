<% import grails.persistence.Event %>
<k:window id="win" title="\${g.message(code: '${domainClass.propertyName}.label')}" width="400px" visible="\${false}" modal="\${true}">
	<form id="frm" method='POST' autocomplete="off" ><%
		excludedProps = Event.allEvents.toList() << 'version'
		allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
		hiddenNames = ['id', 'version']

		props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
		Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))

		hiddenNames.each{name-> %>
		<g:hiddenField name="${name}" data-bind="value: ${name}"/><%}

		props.each {p -> %>
		<div class="row-input">
			<label for="${p.name}"><g:message code="${domainClass.propertyName}.${p.name}"/></label>
			${renderEditor(p)}
		</div><%}%>

		<div class="row-errors">
		</div>

		<div class="row-buttons">
			<k:button id="btnSave" iconCls="k-si-tick"><g:message code="default.button.save.label"/></k:button>
		</div>
	</form>
</k:window>
