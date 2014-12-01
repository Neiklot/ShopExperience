function setupGridProducts() {
	if (tableProducts) {
		$("#dataTableProducts").GridUnload();
		tableProducts = false;
	} else {
		tableProducts = true;
		jQuery(function() {
			jQuery("#dataTableProducts").jqGrid(
					{
						url : "/shopExperience/getProducts",
						datatype : 'json',
						mtype : 'GET',
						colNames : [ 'Nombre', 'Descripción', 'Imagen',
								'Tienda', 'Precio/Valor' ],
						colModel : [ {
							name : 'name',
							index : 'name',
							width : 250,
							editable : true
						}, {
							name : 'description',
							index : 'decription',
							width : 250,
							editable : true,
							editrules : {
								required : true
							},
							editoptions : {
								size : 10
							}
						}, {
							name: 'image_url',
						    index: 'image_url',
						    formatter: formatImage,
						    editable: true,
						    edittype: 'file',
						    editoptions: {
						        enctype: "multipart/form-data"
						    },
						    width: 210,
						    align: 'center',
						    search: false
						}, {
							name : 'value',
							index : 'value',
							width : 250,
							editable : true,
							editrules : {
								required : true
							},
							editoptions : {
								size : 10
							}
						}, {
							name : 'shop_name',
							index : 'shop_name',
							width : 250,
							editable : true,
							editrules : {
								required : true
							},
							editoptions : {
								size : 10
							}
						} ],
						postData : {},
						rowNum : 10,
						rowList : [ 10, 20, 40, 60 ],
						height : 200,
						width : 980,
						shrinkToFit : false,
						rownumbers : true,
						pager : '#pagerProducts',
						sortname : 'id',
						viewrecords : true,
						sortorder : "asc",
						caption : "Products",
						emptyrecords : "Empty records",
						loadonce : false,
						loadComplete : function() {
						},
						jsonReader : {
							root : "rows",
							page : "page",
							total : "total",
							records : "records",
							repeatitems : false,
							cell : "cell",
							id : "id"
						}
					});

			jQuery("#dataTableProducts").jqGrid('navGrid', '#pagerProducts', {
				edit : false,
				add : false,
				del : false,
				search : true
			}, {}, {}, {}, {
				sopt : [ 'eq', 'ne', 'cn', 'bw', 'ew' ],
				closeOnEscape : true,
				multipleSearch : true,
				closeAfterSearch : true
			});

			jQuery("#dataTableProducts").navButtonAdd('#pagerProducts', {
				caption : "Añadir",
				buttonicon : "ui-icon-plus",
				onClickButton : addProduct,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableProducts").navButtonAdd('#pagerProducts', {
				caption : "Editar",
				buttonicon : "ui-icon-pencil",
				onClickButton : editRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableProducts").navButtonAdd('#pagerProducts', {
				caption : "Eliminar",
				buttonicon : "ui-icon-trash",
				onClickButton : deleteRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableProducts").click(function() {
				jQuery("#dataTableProducts").jqGrid('searchGrid', {
					multipleSearch : false,
					sopt : [ 'eq' ]
				});
			});

			// Toolbar Search
			// jQuery("#dataTable").jqGrid('filterToolbar',{stringResult:
			// true,searchOnEnter : true, defaultSearch:"cn"});
		});

	}

}
function addProduct() {

	// Get the currently selected row
	jQuery("#dataTableProducts").jqGrid('editGridRow', 'new', {
		url : "/shopExperience/addProduct",
		editData : {},
		recreateForm : true,
		beforeShowForm : function(form) {
		},
		closeAfterAdd : true,
		reloadAfterSubmit : false,
		dataType : "json",
		afterSubmit : function(response){
			var data=response.responseText;
			
			    if (data!=null) {
			        if ($("#image_url").val() != "") {
			            ajaxFileUpload(data);
			        }
			    }  

			    return [data.success, data.message, data.id];
		}
	});
}

function ajaxFileUpload(id) 
{
    $("#loading")
    .ajaxStart(function () {
        $(this).show();
    })
    .ajaxComplete(function () {
        $(this).hide();
    });

    $.ajaxFileUpload
    (
        {
            url: "/shopExperience/uploadImage",
            secureuri: false,
            fileElementId: 'image_url',
            dataType: 'json',
            data:{id:id},
            success: function (data, status) {

                if (typeof (data.success) != 'undefined') {
                    if (data.success == true) {
                        return;
                    } else {
                        alert(data.message);
                    }
                }
                else {
                    return alert('Failed to upload logo!');
                }
            },
            error: function (data, status, e) {
                return alert('Failed to upload logo!');
            }
        }
    )          }  


function formatImage(cellValue, options, rowObject) {
    var imageHtml = "";
    if(cellValue){
        if(cellValue.indexOf("_thumb") == -1){
//        	http://localhost:8080/shopExperience/resources/css/images/Shop&Experience.png
        	cellValue="/shopExperience/resources"+cellValue.substr(cellValue.lastIndexOf("/"), cellValue.length);
            imageHtml = '<div><img src="' + cellValue + '" title="" target="_blank" width="150" height="150" /></div>';    
        }else{
            imageHtml = '<div class="gallery"><img src="' + cellValue + '" width="50" height="50" alt="" border="0"/></div>';
        }
    }
    return imageHtml;
}
function unformatImage(cellValue, options, cellObject) {
    return $(cellObject.html()).attr("originalValue");
}

jQuery.extend({
	

    createUploadIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jUploadFrame' + id;
            var iframeHtml = '<iframe id="' + frameId + '" name="' + frameId + '" style="position:absolute; top:-9999px; left:-9999px"';
			if(window.ActiveXObject)
			{
                if(typeof uri== 'boolean'){
					iframeHtml += ' src="' + 'javascript:false' + '"';

                }
                else if(typeof uri== 'string'){
					iframeHtml += ' src="' + uri + '"';

                }	
			}
			iframeHtml += ' />';
			jQuery(iframeHtml).appendTo(document.body);

            return jQuery('#' + frameId).get(0);			
    },
    createUploadForm: function(id, fileElementId, data)
	{
		//create form	
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = jQuery('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');	
		if(data)
		{
			for(var i in data)
			{
				jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}			
		}		
		var oldElement = jQuery('#' + fileElementId);
		var newElement = jQuery(oldElement).clone();
		jQuery(oldElement).attr('id', fileId);
		jQuery(oldElement).before(newElement);
		jQuery(oldElement).appendTo(form);


		
		//set attributes
		jQuery(form).css('position', 'absolute');
		jQuery(form).css('top', '-1200px');
		jQuery(form).css('left', '-1200px');
		jQuery(form).appendTo('body');		
		return form;
    },
ajaxFileUpload: function(s) {
    // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
    s = jQuery.extend({}, jQuery.ajaxSettings, s);
    var id = new Date().getTime()        
	var form = jQuery.createUploadForm(id, s.fileElementId, (typeof(s.data)=='undefined'?false:s.data));
	var io = jQuery.createUploadIframe(id, s.secureuri);
	var frameId = 'jUploadFrame' + id;
	var formId = 'jUploadForm' + id;		
    // Watch for a new set of requests
    if ( s.global && ! jQuery.active++ )
	{
		jQuery.event.trigger( "ajaxStart" );
	}            
    var requestDone = false;
    // Create the request object
    var xml = {}   
    if ( s.global )
        jQuery.event.trigger("ajaxSend", [xml, s]);
    // Wait for a response to come back
    var uploadCallback = function(isTimeout)
	{			
		var io = document.getElementById(frameId);
        try 
		{				
			if(io.contentWindow)
			{
				 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
            	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;
				 
			}else if(io.contentDocument)
			{
				 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
            	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
			}						
        }catch(e)
		{
			jQuery.handleError(s, xml, null, e);
		}
        if ( xml || isTimeout == "timeout") 
		{				
            requestDone = true;
            var status;
            try {
                status = isTimeout != "timeout" ? "success" : "error";
                // Make sure that the request was successful or notmodified
                if ( status != "error" )
				{
                    // process the data (runs the xml through httpData regardless of callback)
                    var data = jQuery.uploadHttpData( xml, s.dataType );    
                    // If a local callback was specified, fire it and pass it the data
                    if ( s.success )
                        s.success( data, status );

                    // Fire the global callback
                    if( s.global )
                        jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                } else
                    jQuery.handleError(s, xml, status);
            } catch(e) 
			{
                status = "error";
                jQuery.handleError(s, xml, status, e);
            }

            // The request was completed
            if( s.global )
                jQuery.event.trigger( "ajaxComplete", [xml, s] );

            // Handle the global AJAX counter
            if ( s.global && ! --jQuery.active )
                jQuery.event.trigger( "ajaxStop" );

            // Process result
            if ( s.complete )
                s.complete(xml, status);

            jQuery(io).unbind()

            setTimeout(function()
								{	try 
									{
										jQuery(io).remove();
										jQuery(form).remove();	
										
									} catch(e) 
									{
										jQuery.handleError(s, xml, null, e);
									}									

								}, 100)

            xml = null

        }
    }
    // Timeout checker
    if ( s.timeout > 0 ) 
	{
        setTimeout(function(){
            // Check to see if the request is still happening
            if( !requestDone ) uploadCallback( "timeout" );
        }, s.timeout);
    }
    try 
	{

		var form = jQuery('#' + formId);
		jQuery(form).attr('action', s.url);
		jQuery(form).attr('method', 'POST');
		jQuery(form).attr('target', frameId);
        if(form.encoding)
		{
			jQuery(form).attr('encoding', 'multipart/form-data');      			
        }
        else
		{	
			jQuery(form).attr('enctype', 'multipart/form-data');			
        }			
        jQuery(form).submit();

    } catch(e) 
	{			
        jQuery.handleError(s, xml, null, e);
    }
	
	jQuery('#' + frameId).load(uploadCallback	);
    return {abort: function () {}};	

},

uploadHttpData: function( r, type ) {
    var data = !type;
    data = type == "xml" || data ? r.responseXML : r.responseText;
    // If the type is "script", eval it in global context
    if ( type == "script" )
        jQuery.globalEval( data );
    // Get the JavaScript object, if JSON is used.
    if ( type == "json" )
        eval( "data = " + data );
    // evaluate scripts within html
    if ( type == "html" )
        jQuery("<div>").html(data).evalScripts();

    return data;
}
})

