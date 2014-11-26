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