//Association Table
function setupGridAssociations() {
		if (associationTable) {
			associationTable = false;
			$("#dataTableAssociation").GridUnload();
		} else {
			associationTable = true;
			jQuery("#dataTableAssociation").jqGrid(
					{
						url : "/shopExperience/getAssociations",
						datatype : "json",
						mtype : 'GET',
						colNames : [ 'Id Associacion',
								'Nombre de la Asociación','Descripción','Número de tiendas','Número de Clientes' ],
						colModel : [ {
							name : 'associationId',
							index : 'associationId',
							width : 180,
							hidden:true
						}, {
							name : 'associationName',
							index : 'associationName',
							width : 200,
							editable : true,
							editrules : {
								required : true
							}
						},  {
							name : 'description',
							index : 'description',
							width : 200,
							editable : true,
							editrules : {
								required : false
							}
						},{
							name : 'nShops',
							index : 'nShops',
							width : 160
						},{
							name : 'nClients',
							index : 'nClients',
							width : 160
						}],
						postData : {},
						rowNum : 10,
						rowList : [ 10, 20, 40, 60 ],
						height : 200,
						width : 980,
						shrinkToFit : false,
						rownumbers : true,
						pager : '#pagerAssociation',
						sortname : 'id',
						viewrecords : true,
						sortorder : "asc",
						caption : "Asociaciones",
						emptyrecords : "Ningún resultado",
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
			
			jQuery("#dataTableAssociation").jqGrid('navGrid', '#pagerAssociation', {
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

			jQuery("#dataTableAssociation").navButtonAdd('#pagerAssociation', {
				caption : "Añadir",
				buttonicon : "ui-icon-plus",
				onClickButton : addAssociation,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableAssociation").navButtonAdd('#pagerAssociation', {
				caption : "Editar",
				buttonicon : "ui-icon-pencil",
				onClickButton : editRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableAssociation").navButtonAdd('#pagerAssociation', {
				caption : "Eliminar",
				buttonicon : "ui-icon-trash",
				onClickButton : deleteRow,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableAssociation").navButtonAdd('#pagerAssociation', {
				caption : "Ver Detalles",
				buttonicon : "ui-icon-plusthick",
				onClickButton : verDetalles,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#dataTableAssociation").navButtonAdd('#pagerAssociation', {
				caption : "Exportar",
				buttonicon : "ui-icon-document",
				onClickButton : exportGrid,
				position : "last",
				title : "",
				cursor : "pointer"
			});

			jQuery("#btnFilter").click(function() {
				jQuery("#dataTableAssociation").jqGrid('searchGrid', {
					multipleSearch : false,
					sopt : [ 'eq' ]
				});
			});
			
		}
	}

function addAssociation() {

	// Get the currently selected row
	jQuery("#dataTableAssociation").jqGrid(
			'editGridRow',
			'new',
			{
				url : "/shopExperience/addAssociation",
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