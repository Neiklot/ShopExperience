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
	var clientTable = false;
	var tableCards = false;
	//TABLE CLIENTS
	function setupGrid() {
		if (clientTable) {
			$("#dataTable").GridUnload();
			clientTable = false;
		} else {
			clientTable = true;
			jQuery(function() {
				jQuery("#dataTable")
						.jqGrid(
								{
									url : "/shopExperience/getClients",
									datatype : 'json',
									mtype : 'GET',
									colNames : [ 'Cliente', 'Password',
											'Tarjetas', 'Total puntos' ],
									colModel : [
											{
												name : 'clientName',
												index : 'ClientName',
												width : 250,
												editable : true,
												editrules : {
													required : true
												},
												editoptions : {
													size : 10
												}
											},
											{
												name : 'password',
												index : 'password',
												width : 100,
												editable : true,
												editrules : {
													required : true
												},
												editoptions : {
													size : 10
												}
											},
											{
												name : 'card',
												index : 'card',
												width : 100,
												editrules : {
													required : true
												},
												edittype : 'select',
												editable : true,
												ajaxSelectOptions : {
													contentType : "application/json",
													dataType : 'json',
													type : "GET"
												},
												editoptions : {
													dataUrl : '/shopExperience/getSelectableShops',
													buildSelect : function(data) {
														var obj = JSON
																.parse(data);
														var html = '<select>', length = obj.length, i = 0, item;
														for (; i < length; i++) {
															item = obj[i];
															html += '<option value=' + item + '>'
																	+ item
																	+ '</option>';
														}
														html += '</select>';
														return html;
													}
												}
											}, {
												name : 'card_points',
												index : 'card_points',
												width : 100,
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
									autowidth : true,
									rownumbers : true,
									pager : '#pager',
									sortname : 'id',
									viewrecords : true,
									sortorder : "asc",
									caption : "Clients",
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

				jQuery("#dataTable").jqGrid('navGrid', '#pager', {
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

				jQuery("#dataTable").navButtonAdd('#pager', {
					caption : "Add",
					buttonicon : "ui-icon-plus",
					onClickButton : addRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTable").navButtonAdd('#pager', {
					caption : "Edit",
					buttonicon : "ui-icon-pencil",
					onClickButton : editRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTable").navButtonAdd('#pager', {
					caption : "Delete",
					buttonicon : "ui-icon-trash",
					onClickButton : deleteRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#btnFilter").click(function() {
					jQuery("#dataTable").jqGrid('searchGrid', {
						multipleSearch : false,
						sopt : [ 'eq' ]
					});
				});

				// Toolbar Search
				//jQuery("#dataTable").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
			});

		}

	}

	//TABLE CARDS	

	function setupGridCards() {
		if (tableCards) {
			$("#dataTableCards").GridUnload();
			tableCards = false;
		} else {
			tableCards = true;
			jQuery(function() {
				jQuery("#dataTableCards").jqGrid({
					url : "/shopExperience/getCards",
					datatype : 'json',
					mtype : 'GET',
					colNames : [ 'Barcode', 'Points' ],
					colModel : [ {
						name : 'barcode',
						index : 'barcode',
						width : 250,
						editable : true,
						editrules : {
							required : true
						},
						editoptions : {
							size : 10
						}
					}, {
						name : 'points',
						index : 'points',
						width : 100,
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
					autowidth : true,
					rownumbers : true,
					pager : '#pagerCards',
					sortname : 'id',
					viewrecords : true,
					sortorder : "asc",
					caption : "Cards",
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

				jQuery("#dataTableCards").jqGrid('navGrid', '#pagerCards', {
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

				jQuery("#dataTableCards").navButtonAdd('#pagerCards', {
					caption : "Add",
					buttonicon : "ui-icon-plus",
					onClickButton : addCard,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTableCards").navButtonAdd('#pagerCards', {
					caption : "Edit",
					buttonicon : "ui-icon-pencil",
					onClickButton : editRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTableCards").navButtonAdd('#pagerCards', {
					caption : "Delete",
					buttonicon : "ui-icon-trash",
					onClickButton : deleteRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTableCards").click(function() {
					jQuery("#dataTableCards").jqGrid('searchGrid', {
						multipleSearch : false,
						sopt : [ 'eq' ]
					});
				});

				// Toolbar Search
				//jQuery("#dataTable").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
			});

		}

	}
</script>
<script type="text/javascript">
	function addRow() {

		// Get the currently selected row
		jQuery("#dataTable").jqGrid(
				'editGridRow',
				'new',
				{
					url : "/shopExperience/addClient",
					editData : {},
					recreateForm : true,
					beforeShowForm : function(form) {
					},
					closeAfterAdd : true,
					reloadAfterSubmit : false,
					afterSubmit : function(response, postdata) {

						var result = "Registation OK";
						var errors = "";

						if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors += result.message[i] + "";
							}
						} else {
							jQuery("#dialog").text(
									'Entry has been added successfully');
							jQuery("#dialog").dialog({
								title : 'Success',
								modal : true,
								buttons : {
									"Ok" : function() {
										jQuery(this).dialog("close");
									}
								}
							});
						}
						// only used for adding new records
						var new_id = null;

						return [ result.success, errors, new_id ];
					}
				});

	}

	function addCard() {

		// Get the currently selected row
		jQuery("#dataTableCards").jqGrid(
				'editGridRow',
				'new',
				{
					url : "/shopExperience/addCard",
					editData : {},
					recreateForm : true,
					beforeShowForm : function(form) {
					},
					closeAfterAdd : true,
					reloadAfterSubmit : false,
					afterSubmit : function(response, postdata) {

						var result = "Registation OK";
						var errors = "";

						if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors += result.message[i] + "";
							}
						} else {
							jQuery("#dialog").text(
									'Entry has been added successfully');
							jQuery("#dialog").dialog({
								title : 'Success',
								modal : true,
								buttons : {
									"Ok" : function() {
										jQuery(this).dialog("close");
									}
								}
							});
						}
						// only used for adding new records
						var new_id = null;

						return [ result.success, errors, new_id ];
					}
				});

	}

	function editRow() {
		// Get the currently selected row
		var row = jQuery("#dataTable").jqGrid('getGridParam', 'selrow');

		if (row != null)
			jQuery("#dataTable")
					.jqGrid(
							'editGridRow',
							row,
							{
								url : "/spring-jqgrid-integration/krams/crud/edit",
								editData : {},
								recreateForm : true,
								beforeShowForm : function(form) {
								},
								closeAfterEdit : true,
								reloadAfterSubmit : false,
								afterSubmit : function(response, postdata) {
									var result = eval('('
											+ response.responseText + ')');
									var errors = "";

									if (result.success == false) {
										for (var i = 0; i < result.message.length; i++) {
											errors += result.message[i] + "";
										}
									} else {
										jQuery("#dialog")
												.text(
														'Entry has been edited successfully');
										jQuery("#dialog")
												.dialog(
														{
															title : 'Success',
															modal : true,
															buttons : {
																"Ok" : function() {
																	jQuery(this)
																			.dialog(
																					"close");
																}
															}
														});
									}

									return [ result.success, errors, null ];
								}
							});
		else
			jQuery("#dialogSelectRow").dialog();
	}

	function deleteRow() {
		// Get the currently selected row
		var row = jQuery("#dataTable").jqGrid('getGridParam', 'selrow');

		// A pop-up dialog will appear to confirm the selected action
		if (row != null)
			jQuery("#dataTable")
					.jqGrid(
							'delGridRow',
							row,
							{
								url : '/spring-jqgrid-integration/krams/crud/delete',
								recreateForm : true,
								beforeShowForm : function(form) {
									//change title
									jQuery(".delmsg").replaceWith(
											'<span style="white-space: pre;">'
													+ 'Delete selected record?'
													+ '</span>');

									//hide arrows
									jQuery('#pData').hide();
									jQuery('#nData').hide();
								},
								reloadAfterSubmit : false,
								closeAfterDelete : true,
								afterSubmit : function(response, postdata) {
									var result = eval('('
											+ response.responseText + ')');
									var errors = "";

									if (result.success == false) {
										for (var i = 0; i < result.message.length; i++) {
											errors += result.message[i] + "";
										}
									} else {
										jQuery("#dialog")
												.text(
														'Entry has been deleted successfully');
										jQuery("#dialog")
												.dialog(
														{
															title : 'Success',
															modal : true,
															buttons : {
																"Ok" : function() {
																	jQuery(this)
																			.dialog(
																					"close");
																}
															}
														});
									}
									// only used for adding new records
									var new_id = null;

									return [ result.success, errors, new_id ];
								}
							});
		else
			jQuery("#dialogSelectRow").dialog();
	}
</script>
<script>
var cliente_id="";

function searchCustomer(){
	var text=document.getElementById("barcodes").value;
	 //var clientName=$.get("/shopExperience/searchClientByBarcode",{barcode:text});
$.ajax(
    {
        type: "GET",
        url:"/shopExperience/searchClientByBarcode"+"?barcode="+text,
        dataType:"json",
        success: function(result) {
        	var json_x = result;
        	document.getElementById("findClient").value=" "+json_x.clientName+" "+json_x.apellido1+" DNI: "+json_x.nif;
        	cliente_id=json_x.id;
        },
        error: function(x, e) {
        	document.getElementById("findClient").value="Error de búsqueda";
        }
        });
	       
}
function compra(){
	var cliente=document.getElementById("barcodes").value;
	var importe=document.getElementById("Importe").value;
	
	$.ajax(
		    {
		        type: "GET",
		        url:"/shopExperience/addCompra"+"?client_id="+cliente_id+"&importe="+importe,
		        dataType:"XMLHttpRequest",
		        success: function(result) {
		        	alert("Compra anotada al cliente:"+cliente_id+" de un importe de: "+importe);
		        },
		        error: function(x, e) {
		        	alert("Error al añadir compra");
		        }
		        });
	
	reset();
	resetImporte();
}

function reset(){
	document.getElementById("barcodes").value="";
	document.getElementById("findClient").value="";
}
function resetImporte(){
	document.getElementById("Importe").value="";
}
</script>
</head>
<body>
	<div class="bodyPage">
		<div class="buttons">
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGrid();' title='New Shop' class='boton'
				style='background: url("/shopExperience/resources/css/images/clients.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridCards();' title='New Shop' class='boton'
				style='background: url("/shopExperience/resources/css/images/barcodes.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td>

		</div>
		<div class="centreDiv" style="margin-left: 3%; overflow: auto;">
			<div>
				<table
					style="align: center; padding: 40px 40px 40px 40px; font-family: Georgia, serif; color: #4E443C; font-variant: small-caps; size: 50px; text-transform: none; font-weight: 100; margin-bottom: 0;">
					<tr>
						<td>Introduzca Compra: </td></tr><tr><td><input oninput="searchCustomer()" onclick="reset()"
							type="text" class="inputs" placeholder="Introduzca Código de barras o Nombre y Apellidos" id="barcodes"/>
							</td><td><output style="font-family: Georgia, serif; color: #4E443C; font-variant: small-caps; size: 50px; text-transform: none; font-weight: 100; margin-bottom: 0;"" id="findClient"></output>
							 </tr><tr><td> <input
							type="number" class="inputs" placeholder="Importe"  id="Importe" onclick="resetImporte()"/>
						</td>
					
				<td style=''><a
				onClick='compra();' title='compra' class='boton'
				style='background: url("/shopExperience/resources/css/images/barcodes.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a></td></tr>
</table>

			</div>
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
						<table id="dataTableCards"></table>
						<div id="pagerCards"></div>
					</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>

         
<style> 
 .inputs {
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    -ms-border-radius: 3px;
    -o-border-radius: 3px;
    border-radius: 3px;
    -webkit-box-shadow: 0 1px 0 #FFF, 0 -2px 5px rgba(0, 0, 0, 0.08) inset;
    -moz-box-shadow: 0 1px 0 #fff, 0 -2px 5px rgba(0, 0, 0, 0.08) inset;
    -ms-box-shadow: 0 1px 0 #fff, 0 -2px 5px rgba(0, 0, 0, 0.08) inset;
    -o-box-shadow: 0 1px 0 #fff, 0 -2px 5px rgba(0, 0, 0, 0.08) inset;
    box-shadow: 0 1px 0 #FFF, 0 -2px 5px rgba(0, 0, 0, 0.08) inset;
    -webkit-transition: all 0.5s ease;
    -moz-transition: all 0.5s ease;
    -ms-transition: all 0.5s ease;
    -o-transition: all 0.5s ease;
    transition: all 0.5s ease;
    background: #EAE7E7;
    border: 1px solid #BED6F8;
    color: #777;
    font: 13px Helvetica, Arial, sans-serif;
    margin: 0 0 10px;
    padding: 15px 10px 15px 40px;
    width: 400px;
}

.inputs:focus {
    -webkit-box-shadow: 0 0 2px #BED6F8 inset;
    -moz-box-shadow: 0 0 2px #ed1c24 inset;
    -ms-box-shadow: 0 0 2px #ed1c24 inset;
    -o-box-shadow: 0 0 2px #ed1c24 inset;
    box-shadow: 0 0 2px #BED6F8 inset;
    background-color: #FFF;
    border: 1px solid #BED6F8;
    outline: none;
}
</style> 