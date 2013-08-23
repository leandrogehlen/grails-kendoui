<% import grails.persistence.Event %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title><g:message code="${domainClass.propertyName}.title"/></title>
		<r:require module="kendoui_scaffold"/>

		<r:script>
			\$(function() {
				var scaffold = new kendo.Scaffold({
					window: \$('#win'),
				 	grid: \$('#grid'),
				 	route: '\${g.createLink()}'
				});

				\$('#btnAdd').click(function(){
					scaffold.add();
				});

				\$('#btnEdit').click(function(){
					scaffold.edit();
				});

				\$('#btnDelete').click(function(){
					scaffold.remove();
				});

				\$('#btnRefresh').click(function(){
					scaffold.refresh();
				});

				\$('#btnSave').click(function(){
					scaffold.save();
				});

				\$('#txtSearch').change( function () {
				    scaffold.search(\$("#cbxSearch").val(), \$("#txtSearch").val() );
				});
			});
		</r:script>
	</head>
	<body>

		<script type="text/x-kendo-template" id="toolbar">
		<div id="tb" class="scaffoldbar">
			<div class="scaffoldbar-left">
				<k:button id="btnAdd" iconCls="k-i-plus"><g:message code="default.button.create.label"/></k:button>
				<k:button id="btnEdit" iconCls="k-i-pencil"><g:message code="default.button.edit.label"/></k:button>
				<k:button id="btnDelete" iconCls="k-si-minus"><g:message code="default.button.delete.label"/></k:button>
				<k:button id="btnRefresh" iconCls="k-i-refresh"><g:message code="default.button.refresh.label"/></k:button>
			</div>
			<div class="scaffoldbar-right">
				<k:dropDownList id="cbxSearch" disposition="defer"><%
					excludedProps = Event.allEvents.toList() << 'version'
					allowedNames = domainClass.persistentProperties*.name << 'id' << 'dateCreated' << 'lastUpdated'
					props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
					Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))

					props.eachWithIndex { p, i ->
					if (p.name != 'id' && i < 6) { %>
					<option value="${p.name}"><g:message code="${domainClass.propertyName}.${p.name}"/></option><%}}%>
				</k:dropDownList>
				<k:searchBox id="txtSearch" disposition="defer" />
			</div>
		</div>
		</script>

		<k:grid id="grid" height="100%" sortable="${true}" selectable="multiple" pageable="${true}"
			toolbar="js:kendo.template(\\\$('#toolbar').html())">
			<k:dataSource pageSize="${20}" serverPaging="${true}" serverSorting="${true}">
				<k:schema data="rows" total="total" />
				<k:transport read="\${g.createLink(action: 'list')}" />
			</k:dataSource>
			<k:columns><%
				width = (props.size > 0) ? (100 / props.size) as Integer : ""
				width = (width) ? "width=\"$width\"" : ""

				props.eachWithIndex { p, i ->
				if (i < 6) {
					attrs = (p.name == 'id') ? 'hidden="true"' : 'sortable="true"'%>
				<k:column field="${p.name}" $attrs $width><g:message code="${domainClass.propertyName}.${p.name}"/></k:column><%} }%>
			</k:columns>
		</k:grid>

		<g:render template="form"/>
	</body>
</html>
