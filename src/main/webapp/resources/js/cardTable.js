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
					colNames : [ 'Cliente','Barcode', 'Points' ],
					colModel : [{
						name : 'clientIdentification',
						index : 'clientIdentification',
						width : 250,
						editable : false
					}, {
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
					caption : "Añadir",
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

