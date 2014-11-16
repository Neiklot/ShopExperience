<%@ include file="header.jsp"%>

<head>
<meta charset="utf-8">
<title>ShopExperience Clientes y Productos</title>

</script>

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
	var shopTable = false;
	var tableCards = false;
	var verDetalles = false;
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
											'SubNombre', 'Direcci�n',
											'C�digo Postal', 'Poblaci�n',
											'Tel�fono', 'M�vil', 'Email',
											'Cuenta', 'Cuenta IBAN',
											'Observaciones', 'Dado de Baja',
											'Tarjetas', 'Total puntos', ],
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
												editoptions : {
													size : 10
												}
											},
											{
												name : 'password',
												index : 'password',
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
												name : 'NIF',
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
												name : 'subNombre',
												index : 'subNombre',
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
					caption : "A�adir",
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
					width : 980,
					shrinkToFit : false,
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
					caption : "A�adir",
					buttonicon : "ui-icon-plus",
					onClickButton : addCard,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTableCards").navButtonAdd('#pagerCards', {
					caption : "Editar",
					buttonicon : "ui-icon-pencil",
					onClickButton : editRow,
					position : "last",
					title : "",
					cursor : "pointer"
				});

				jQuery("#dataTableCards").navButtonAdd('#pagerCards', {
					caption : "Eliminar",
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

	// TABLE SHOPS
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
	function verDetalles() {
		if (!verDetalles) {
			jQuery("#dataTable").hideCol('tipo');
			jQuery("#dataTable").hideCol('NIF');
			jQuery("#dataTable").hideCol('apellido2');
			jQuery("#dataTable").hideCol('password');
			jQuery("#dataTable").hideCol('subNombre');
			jQuery("#dataTable").hideCol('movil');
			jQuery("#dataTable").hideCol('email');
			jQuery("#dataTable").hideCol('cuenta');
			jQuery("#dataTable").hideCol('cuentaIban');
			jQuery("#dataTable").hideCol('observaciones');
			jQuery("#dataTable").hideCol('baja');
			verDetalles = true;
		} else {
			jQuery("#dataTable").showCol('tipo');
			jQuery("#dataTable").showCol('NIF');
			jQuery("#dataTable").showCol('apellido2');
			jQuery("#dataTable").showCol('password');
			jQuery("#dataTable").showCol('subNombre');
			jQuery("#dataTable").showCol('movil');
			jQuery("#dataTable").showCol('email');
			jQuery("#dataTable").showCol('cuenta');
			jQuery("#dataTable").showCol('cuentaIban');
			jQuery("#dataTable").showCol('observaciones');
			jQuery("#dataTable").showCol('baja');
			verDetalles = false;
		}
	}

	function addRow() {

		if (verDetalles) {
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

	function exportGrid() {
		jQuery("#dataTable").jqGrid('exportdata', 'xls', 'jqxGrid');
	}
</script>

</head>
<body>
	<div class="bodyPage">
		<div class="buttons">
			<!--<td style='margin-top: 10px; padding: 45px;'><a
				title='New client' class='boton'
				style='background: url("/shopExperience/resources/css/images/barcodes.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="/registerClient" />"> <span></span>
			</a></td>-->
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGrid();' title='Clientes' class='boton'
				style='background: url("/shopExperience/resources/css/images/clients.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a>Clientes</td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridCards();' title='Tarjetas' class='boton'
				style='background: url("/shopExperience/resources/css/images/barcodes.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a>Tarjetas</td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridShops();' title='Tiendas' class='boton'
				style='background: url("/shopExperience/resources/css/images/shops.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a>Tiendas</td>
			<td style='margin-top: 10px; padding: 45px;'><a
				onClick='setupGridAssociations();' title='Asociaciones'
				class='boton'
				style='background: url("/shopExperience/resources/css/images/shops.png") no-repeat center center, cornflowerblue;'
				href="<c:url value="#" />"> <span></span></a>Asociaciones</td>
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
						<table id="dataTableCards"></table>
						<div id="pagerCards"></div>
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



