var verDetallesValue = false;

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
								colNames : [ 'Cliente', 'Tipo', 'Password',
										'NIF', 'Apellido 1', 'Apellido 2',
										'SubNombre', 'Dirección',
										'Código Postal', 'Población',
										'Teléfono', 'Móvil', 'Email', 'Cuenta',
										'Cuenta IBAN', 'Observaciones',
										'Dado de Baja', 'Tarjetas',
										'Total puntos', ],
								colModel : [
										{
											name : 'clientName',
											index : 'clientName',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'tipo',
											index : 'tipo',
											width : 100,
											editable : true,
											hidden : true,
											editrules : {
												required : true
											},
											formatter : clientTypeFormatter,
											unformat: clientTypeUnFormatter,
											edittype : 'select',
											stype: 'select',
											searchoptions:{
												value : {
													1 : 'Asociación',
													2 : 'Tienda',
													3 : 'Cliente'
												}
											},
											editoptions : {
												value : {
													1 : 'Asociación',
													2 : 'Tienda',
													3 : 'Cliente'
												}
											}

										},
										{
											name : 'password',
											index : 'password',
											width : 125,
											formatter:passwordFormater,
											editable : true,
											hidden : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'nif',
											index : 'nif',
											width : 125,
											editable : true,
											hidden : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'apellido1',
											index : 'apellido1',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'apellido2',
											index : 'apellido2',
											width : 125,
											editable : true,
											hidden : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'subnombre',
											index : 'subnombre',
											hidden : true,
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'direccion',
											index : 'direccion',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'codigoPostal',
											index : 'codigoPostal',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'poblacion',
											index : 'poblacion',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'telefono',
											index : 'telefono',
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'movil',
											index : 'movil',
											hidden : true,
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'email',
											index : 'email',
											hidden : true,
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'cuenta',
											index : 'cuenta',
											hidden : true,
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'cuentaIban',
											index : 'cuentaIban',
											hidden : true,
											width : 125,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'observaciones',
											index : 'observaciones',
											hidden : true,
											width : 350,
											editable : true,
											editrules : {
												required : true
											},
											editoptions : {
												size : 10
											}
										},
										{
											name : 'baja',
											index : 'baja',
											hidden : true,
											width : 50,
											formatter: booleanFormatter,
											editable : true,
											editrules : {
												required : true
											},	edittype : 'select',
											stype: 'select',
											searchoptions:{
												value : {
													0 : 'No',
													1 : 'Si'
												}
											},
											editoptions : {
												value : {
													0 : 'No',
													1 : 'Si'
												}
											}
											},
										{
											name : 'card',
											index : 'card',
											width : 250,
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
													var obj = JSON.parse(data);
													var html = '<select>', length = obj.length, i = 0, item;
													for (; i < length; i++) {
														item = obj[i];
														html += '<option value='
																+ item
																+ '>'
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
								width : 980,
								shrinkToFit : false,
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
				caption : "Añadir",
				buttonicon : "ui-icon-plus",
				onClickButton : addRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTable").navButtonAdd('#pager', {
				caption : "Editar",
				buttonicon : "ui-icon-pencil",
				onClickButton : editRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTable").navButtonAdd('#pager', {
				caption : "Eliminar",
				buttonicon : "ui-icon-trash",
				onClickButton : deleteRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTable").navButtonAdd('#pager', {
				caption : "Ver Detalles",
				buttonicon : "ui-icon-plusthick",
				onClickButton : verDetalles,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTable").navButtonAdd('#pager', {
				caption : "Exportar",
				buttonicon : "ui-icon-document",
				onClickButton : exportGrid,
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
			// jQuery("#dataTable").jqGrid('filterToolbar',{stringResult:
			// true,searchOnEnter : true, defaultSearch:"cn"});
		});

	}
	
	function passwordFormater(cellvalue, options, rowObject){
		return "*****";
	}
	
	function clientTypeFormatter(cellvalue, options, rowObject) {
		switch (cellvalue) {
		case 1:
			return "Asociación";
			break;
		case 2:
			return "Tienda";
			break;
		case 3:
			return "Cliente";
			break;
		default:
			return "Error";
		}
	}
	
	function booleanFormatter(cellvalue, options, rowObject){
		if(cellvalue==true){
			return "Si";}
		return "No";
	}
	
	function clientTypeUnFormatter(cellvalue, options, rowObject) {
		switch (cellvalue) {
		case "Asociación":
			return 1;
			break;
		case "Tienda":
			return 2;
			break;
		case "Cliente":
			return 3;
			break;
		default:
			return "0";
		}
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
								url : "/shopExperience/deleteClient",
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
									var result = "Deletion OK";
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

	
	function exportGrid() {
		jQuery("#dataTable").jqGrid('exportdata', 'xls', 'jqxGrid');
	}
	
	function addRow() {

		if (!verDetallesValue) {
			verDetallesValue=true;
			verDetalles();
		}
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
									result);
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
	function verDetalles() {
		if (!verDetallesValue) {
			jQuery("#dataTable").hideCol('tipo');
			jQuery("#dataTable").hideCol('nif');
			jQuery("#dataTable").hideCol('apellido2');
			jQuery("#dataTable").hideCol('password');
			jQuery("#dataTable").hideCol('subnombre');
			jQuery("#dataTable").hideCol('movil');
			jQuery("#dataTable").hideCol('email');
			jQuery("#dataTable").hideCol('cuenta');
			jQuery("#dataTable").hideCol('cuentaIban');
			jQuery("#dataTable").hideCol('observaciones');
			jQuery("#dataTable").hideCol('baja');
			verDetallesValue = true;
		} else {
			jQuery("#dataTable").showCol('tipo');
			jQuery("#dataTable").showCol('nif');
			jQuery("#dataTable").showCol('apellido2');
			jQuery("#dataTable").showCol('password');
			jQuery("#dataTable").showCol('subnombre');
			jQuery("#dataTable").showCol('movil');
			jQuery("#dataTable").showCol('email');
			jQuery("#dataTable").showCol('cuenta');
			jQuery("#dataTable").showCol('cuentaIban');
			jQuery("#dataTable").showCol('observaciones');
			jQuery("#dataTable").showCol('baja');
			verDetallesValue = false;
		}
	}
	

/*
 * function popupwindow(selr) { var url =
 * "http://localhost:8080/shopExperience/index"; var title = selr; var w = 250;
 * var h = 250; var left = (screen.width / 2) - (w / 2); var top =
 * (screen.height / 2) - (h / 2); return window .open( url, selr, 'toolbar=no,
 * location=no, directories=no, status=no, menubar=no, scrollbars=no,
 * resizable=no, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ',
 * left=' + left); }
 */
}