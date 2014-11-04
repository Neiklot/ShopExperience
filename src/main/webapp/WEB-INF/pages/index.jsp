<%@ include file="header.jsp"%>

<head>
<meta charset="utf-8">
<title>ShopExperience Clientes y Productos</title>


<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"
	type="text/javascript"></script>
<script
	src="http://www.trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js"
	type="text/javascript"></script>
<script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.js"
	type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>

<script>
	// 	$(document).ready(function() {
	// 	});
	var clientTable = false;
	var shopTable = false;
	function setupGrid() {
		if (clientTable) {
			$("#dataTable").GridUnload();
			clientTable = false;
		} else {
			clientTable = true;
			 jQuery(function() {
			  jQuery("#dataTable").jqGrid({
				  url : "/shopExperience/getClients",
			   datatype: 'json',
			   mtype: 'GET',
			   colNames : [ 'Cliente','Password', 'Tarjetas', 'Total puntos' ],
				colModel : [ {
					name : 'clientName',
					index : 'ClientName',
					width : 250
					,editable:true, editrules:{required:true}, editoptions:{size:10}
				}, 
				 {
					name : 'password',
					index : 'password',
					width : 100,editable:true, editrules:{required:true}, editoptions:{size:10}
				},
				{
					name : 'card',
					index : 'card',
					width : 100, editrules:{required:true},
						edittype: 'select', editable: true,
						ajaxSelectOptions: {
						    contentType: "application/json",
						    dataType: 'json',
						    type: "GET"
						},
						editoptions: {
						    dataUrl: '/shopExperience/getSelectableShops',
						    buildSelect: function (data) {
						    	var obj = JSON.parse(data);
						        var html = '<select>', length = obj.length, i = 0, item;
						        for (; i < length; i++) {
						            item = obj[i];
						            html += '<option value=' + item + '>' + item + '</option>';
						        }
						        html += '</select>';
						        return html;
						    }
						}			
				},
				{
					name : 'card_points',
					index : 'card_points',
					width : 100,editable:true, editrules:{required:true}, editoptions:{size:10}
				} ],
			      postData: {
			   },
			   rowNum:10,
			      rowList:[10,20,40,60],
			      height: 200,
			      autowidth: true,
			   rownumbers: true,
			      pager: '#pager',
			      sortname: 'id',
			      viewrecords: true,
			      sortorder: "asc",
			      caption:"Clients",
			      emptyrecords: "Empty records",
			      loadonce: false,
			      loadComplete: function() {
			   },
			      jsonReader : {
			          root: "rows",
			          page: "page",
			          total: "total",
			          records: "records",
			          repeatitems: false,
			          cell: "cell",
			          id: "id"
			      }
			  });


			jQuery("#dataTable").jqGrid('navGrid','#pager',
				    {edit:false,add:false,del:false,search:true},
				    { },
				          { },
				          { },
				    {
				        sopt:['eq', 'ne', 'cn', 'bw', 'ew'],
				           closeOnEscape: true,
				            multipleSearch: true,
				             closeAfterSearch: true }
				  );
				 
				 
				   
			jQuery("#dataTable").navButtonAdd('#pager',
				    {  caption:"Add",
				     buttonicon:"ui-icon-plus",
				     onClickButton: addRow,
				     position: "last",
				     title:"",
				     cursor: "pointer"
				    }
				  );
				   
				  jQuery("#dataTable").navButtonAdd('#pager',
				    {  caption:"Edit",
				     buttonicon:"ui-icon-pencil",
				     onClickButton: editRow,
				     position: "last",
				     title:"",
				     cursor: "pointer"
				    }
				  );
				   
				  jQuery("#dataTable").navButtonAdd('#pager',
				   {  caption:"Delete",
				    buttonicon:"ui-icon-trash",
				    onClickButton: deleteRow,
				    position: "last",
				    title:"",
				    cursor: "pointer"
				   }
				  );
				 
				  jQuery("#btnFilter").click(function(){
					  jQuery("#dataTable").jqGrid('searchGrid',
				     {multipleSearch: false,
				      sopt:['eq']}
				   );
				  });
				 
				  // Toolbar Search
				  //jQuery("#dataTable").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
			 });

		}
	
	}

	function setupGridShops() {
		if (shopTable) {
			shopTable = false;
			$("#dataTableShop").GridUnload();
		} else {
			shopTable = true;
			jQuery("#dataTableShop").jqGrid(
					{
						url : "/shopExperience/getShops",
						datatype : "json",
						jsonReader : {
							repeatitems : false,
							id : "ref"
						},
						colNames : [ 'Id Tienda', 'Nombre de la Tienda' ],
						colModel : [ {
							name : 'shopId',
							index : 'shopId',
							width : 100
						}, {
							name : 'shopName',
							index : 'shopName',
							width : 100
						}, ],
						rowNum : 5,
						rowList : [ 5, 10, 20 ],
						height : 165,
						pager : "#pagingDivShops",
						viewrecords : true,
						hiddengrid : true,
						caption : "Tiendas",
						multipleGroup : true,
						onSelectRow : function() {
							var selr = jQuery('#dataTableShop').jqGrid(
									'getGridParam', 'selrow');
							if (selr)
								popupwindow(selr);
							else
								alert("No selected row");
							return false;
						}
					});
		}
	}
	function setupGridAssociations() {
		if (shopTable) {
			shopTable = false;
			$("#dataTableAssociation").GridUnload();
		} else {
			shopTable = true;
			jQuery("#dataTableAssociation").jqGrid(
					{
						url : "/shopExperience/getAssociations",
						datatype : "json",
						jsonReader : {
							repeatitems : false,
							id : "ref"
						},
						colNames : [ 'Id Associacion',
								'Nombre de la Associacion' ],
						colModel : [ {
							name : 'associationId',
							index : 'associationId',
							width : 100
						}, {
							name : 'associationName',
							index : 'associationName',
							width : 100
						}, ],
						rowNum : 5,
						rowList : [ 5, 10, 20 ],
						height : 165,
						pager : "#pagingDivShops",
						viewrecords : true,
						hiddengrid : true,
						caption : "Asociaciones",
						onSelectRow : function() {
							var selr = jQuery('#dataTableShop').jqGrid(
									'getGridParam', 'selrow');
							if (selr)
								popupwindow(selr);
							else
								alert("No selected row");
							return false;
						}
					});
		}
	}

	function popupwindow(selr) {
		var url = "http://localhost:8080/shopExperience/index";
		var title = selr;
		var w = 250;
		var h = 250;
		var left = (screen.width / 2) - (w / 2);
		var top = (screen.height / 2) - (h / 2);
		return window
				.open(
						url,
						selr,
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='
								+ w
								+ ', height='
								+ h
								+ ', top='
								+ top
								+ ', left=' + left);
	}
</script>
<script type="text/javascript">
 
function addRow() {
 
 // Get the currently selected row
    jQuery("#dataTable").jqGrid('editGridRow','new',
      {  url: "/shopExperience/addClient",
     editData: {
       },
       recreateForm: true,
       beforeShowForm: function(form) {
       },
    closeAfterAdd: true,
    reloadAfterSubmit:false,
    afterSubmit : function(response, postdata)
    {

           var result = "Registation OK";
     var errors = "";
      
           if (result.success == false) {
      for (var i = 0; i < result.message.length; i++) {
       errors +=  result.message[i] + "";
      }
           }  else {
            jQuery("#dialog").text('Entry has been added successfully');
      jQuery("#dialog").dialog(
        { title: 'Success',
         modal: true,
         buttons: {"Ok": function()  {
          jQuery(this).dialog("close");}
         }
        });
                 }
        // only used for adding new records
        var new_id = null;
         
     return [result.success, errors, new_id];
    }
      });
 
}
 
function editRow() {
 // Get the currently selected row
 var row = jQuery("#dataTable").jqGrid('getGridParam','selrow');
  
 if( row != null )
  jQuery("#dataTable").jqGrid('editGridRow',row,
   { url: "/spring-jqgrid-integration/krams/crud/edit",
    editData: {
          },
          recreateForm: true,
          beforeShowForm: function(form) {
          },
    closeAfterEdit: true,
    reloadAfterSubmit:false,
    afterSubmit : function(response, postdata)
    {
              var result = eval('(' + response.responseText + ')');
     var errors = "";
      
              if (result.success == false) {
      for (var i = 0; i < result.message.length; i++) {
       errors +=  result.message[i] + "";
      }
              }  else {
               jQuery("#dialog").text('Entry has been edited successfully');
      jQuery("#dialog").dialog(
        { title: 'Success',
         modal: true,
         buttons: {"Ok": function()  {
          jQuery(this).dialog("close");}
         }
        });
                 }
            
     return [result.success, errors, null];
    }
   });
 else jQuery( "#dialogSelectRow" ).dialog();
}
 
function deleteRow() {
 // Get the currently selected row
    var row = jQuery("#dataTable").jqGrid('getGridParam','selrow');
 
    // A pop-up dialog will appear to confirm the selected action
 if( row != null )
  jQuery("#dataTable").jqGrid( 'delGridRow', row,
           { url: '/spring-jqgrid-integration/krams/crud/delete',
      recreateForm: true,
               beforeShowForm: function(form) {
                 //change title
                 jQuery(".delmsg").replaceWith('<span style="white-space: pre;">' +
                   'Delete selected record?' + '</span>');
                  
        //hide arrows
                 jQuery('#pData').hide(); 
                 jQuery('#nData').hide(); 
               },
              reloadAfterSubmit:false,
              closeAfterDelete: true,
              afterSubmit : function(response, postdata)
      {
                   var result = eval('(' + response.responseText + ')');
       var errors = "";
        
                   if (result.success == false) {
        for (var i = 0; i < result.message.length; i++) {
         errors +=  result.message[i] + "";
        }
                   }  else {
                    jQuery("#dialog").text('Entry has been deleted successfully');
        jQuery("#dialog").dialog(
          { title: 'Success',
           modal: true,
           buttons: {"Ok": function()  {
            jQuery(this).dialog("close");}
           }
          });
                   }
                   // only used for adding new records
                   var new_id = null;
                    
       return [result.success, errors, new_id];
      }
           });
  else jQuery( "#dialogSelectRow" ).dialog();
}
 
</script>

</head>
<body>
	<div class="bodyPage">
		<div class="buttons">
			<td style='margin-top: 10px; padding: 45px;'><a
				title='New client' class='boton'
				style='background: url("/shopExperience/resources/css/images/barcodes.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="/registerClient" />"> <span></span>
			</a></td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGrid();' title='New Shop' class='boton'
				style='background: url("/shopExperience/resources/css/images/clients.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridShops();' title='New User' class='boton'
				style='background: url("/shopExperience/resources/css/images/shops.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridAssociations();' title='New Association'
				class='boton'
				style='background: url("/shopExperience/resources/css/images/shops.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td>
		</div>
		<div class="centreDiv" style="margin-left: 3%; overflow: auto;">
			<div>
				<tbody>
					<h2></h2>
					<div>
						<table id="dataTable"></table>
						<div id="pager"></div>
					</div>
			</div>
			<div>
				<tbody>
					<h2></h2>
					<div>
						<table id="dataTableShop"></table>
						<div id="pagingDivShops"></div>
					</div>
			</div>
			<div>
				<tbody>
					<h2></h2>
					<div>
						<table id="dataTableAssociation"></table>
						<div id="pagingDivAssociation"></div>
					</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>