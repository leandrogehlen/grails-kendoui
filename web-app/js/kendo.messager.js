(function($) {			
	var kendo = window.kendo,	
		culture = kendo.culture || {};
	
	if (culture.message === undefined ) {
		culture.message = {
			info: "Information",
			alert: "Alert",
			confirm: "Confirm",
			error: "Error",
			yes: "Yes",
			no: "No",
			ok: "Ok"		
		}
	}
	
	var Messager = kendo.Class.extend({
		message: function(msg, title, icon, fn) {      				      				    	
			var win = $('#kendo_msg_dlg');      	

			if (!win.length)
				win = $("<div id=\"kendo_msg_dlg\"></div>").appendTo("body");
			else    		
				win.empty();    	 

			win.append("<div class=\"message-image\"><div class=\"message-icon icon-"+icon+"-32\"></div><div>");
			win.append("<p class=\"message-content\">"+msg+"</p>");    	

			var buttons = $("<div class=\"message-buttons\"><div>").appendTo(win);

			if (icon == 'question') {    		
				var yes = $("<span class=\"k-button\" style=\"width: 80px\">"+culture.message.yes+"</span>").appendTo(buttons);    		    	
				var no = $("<span class=\"k-button\" style=\"width: 80px\">"+culture.message.no+"</span>").appendTo(buttons);

				yes.css("margin-right",5);
				yes.click(function(){   
					win.data("kendoWindow").close();
					if (fn) fn(true);       			
				});

				no.click(function(){  
					win.data("kendoWindow").close();
					if (fn) fn(false);    			
				});    		
			}
			else {   						
				var ok = $("<span class=\"k-button\" style=\"width: 80px\">"+culture.message.ok+"</span>").appendTo(buttons);    		
				ok.click(function(){    		
					win.data("kendoWindow").close();
				});    		
			}
			
			win.kendoWindow({title: title, modal: true, visible: false, resizable: false});    	    	
			win.data("kendoWindow").center().open();    	    	    	    			
		},

		info: function(msg){    					
			this.message(msg, culture.message.info, 'info');
		},

		alert: function(msg){		
			this.message(msg, culture.message.alert, 'warning');
		},

		confirm: function(msg, fn){		
			this.message(msg, culture.message.confirm, 'question', fn);
		},

		error: function(msg){
			this.message(msg, culture.message.error, 'error');
		}
	});

	kendo.messager = new Messager();	
})(jQuery);