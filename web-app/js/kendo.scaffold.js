(function($) {
	var kendo = window.kendo,       
		culture = kendo.culture || {};
		
	if (culture.scaffold === undefined) {
		culture.scaffold = {
			noRecordsSelectedMsg: "No records selected",
			onlyOneRecordSelectedMsg: "Only one record should be selected",
			removeConfirmationMsg: "Are you sure you want to remove the selected(s) record(s)",
			alertTitle: "Alert",
			confirmTitle: "Confirm"						
		}				
	}
	 			
	var Scaffold = kendo.Class.extend({
		init: function(options){					
			var that = this; 
				defaults = {window: null, route: "", grid: null	},
			 	options = $.extend({}, defaults, options);
									
			that.win = options.window.data("kendoWindow");	
			that.grid = options.grid.data("kendoGrid");	
			
			that.frm = $(options.window).find("form:first");					
			that.validator = that.frm.kendoValidator().data("kendoValidator");														
			that.route = options.route.replace('/index','');					
									
			that.frm.attr("action", that.route+"/save");
			that.frm.attr("method", "POST");
								
			that.noRecordsSelectedMsg = culture.scaffold.noRecordsSelectedMsg;
			that.onlyOneRecordSelectedMsg = culture.scaffold.onlyOneRecordSelectedMsg;
			that.removeConfirmationMsg = culture.scaffold.removeConfirmationMsg;
			that.alertTitle = culture.scaffold.alertTitle;
			that.confirmTitle = culture.scaffold.confirmTitle;	
		},

		clearErrors: function() {		
			var div = $('.row-errors:first');
			if (div.length) div.empty();
		},
		
		showErrors: function(errors) {				
			var div = $('.row-errors:first');
			if (div.length) {						
				var li = "";																		
				for (i in errors)
					li += '<li>'+errors[i].message+'</li>';							
				div.append('<ul>'+li+'</ul>');
			}
		},							
		
		validate: function(multiple) {	
			var that = this,
				rows = that.grid.select(),
				valid = false;
									
			if (rows.length == 0)
				kendo.messager.alert(that.noRecordsSelectedMsg);						
			else if (!multiple && rows.length > 1)			
				kendo.messager.alert(that.onlyOneRecordSelectedMsg);
			else
				valid = true;
					
			return valid;
		},
		
		clear: function () {
			this.frm.find('input,select,textarea').each(function(){			
				var t = this.type, tag = this.tagName.toLowerCase();
				if (t == 'text' || t == 'hidden' || t == 'password' || tag == 'textarea'){
					this.value = '';
				} else if (t == 'file'){
					var file = $(this);
					file.after(file.clone().val(''));
					file.remove();
				} else if (t == 'checkbox' || t == 'radio'){
					this.checked = false;
				} else if (tag == 'select'){
					this.selectedIndex = -1;
				}			
			});														
		},
		
		add: function() {
			var that = this;
			that.clearErrors();
			that.clear();			
			that.win.center();					            	
			that.win.open();   		
		},

		edit: function() {									
			var that = this;
			that.clearErrors();									
			if (that.validate(false)) {			
				var data = that.grid.dataItem(that.grid.select());			
				var url = that.route+"/show/"+data.id;
										
				$.ajax({
					url: url,				
					dataType: 'json',
					success: function(data){	
						if (data.success === false)
							kendo.messager.alert(data.error);
						else {
							var viewModel = kendo.observable(data);
							kendo.bind(that.frm, viewModel);						
							that.win.center();
							that.win.open();
						}
					}				
				});
			}
		},
		
		remove: function () {	
			var that = this;
			if (that.validate(true)) {			
				kendo.messager.confirm(that.removeConfirmationMsg, function(r){
					if (r == true) {
						var rows = that.grid.select();
						for(var i in rows) {
							data = that.grid.dataItem(rows[i]);				
							$.post(that.route +'/delete/'+data.id, function(data) {
								that.refresh();			
							});						
						}					
					}				
				});				
			}
		},
		
		refresh: function() {									
			this.grid.dataSource.read();		
			this.grid.clearSelection();
		},
		
		save: function() {						
			var that = this;
			if (that.validator.validate()) {										
				$.post(that.frm.attr('action'), that.frm.serialize(), function(data) {							
					if (data.success){					
						that.win.close();	
						that.grid.dataSource.read();
					}
					else {				
						that.clearErrors();
						that.showErrors(data.messages.errors);													
					}
				});														
			}		
		},
		
		search: function(name, value) {				
			this.grid.dataSource.read({field: name, value: value, page: 1});							
		}
	});		
	
	kendo.Scaffold = Scaffold;
})(jQuery);