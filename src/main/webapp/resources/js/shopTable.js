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
						colNames : [ 'Nombre de la Tienda','Associación','Número de Clientes' ],
						colModel : [  {
							name : 'shopName',
							index : 'shopName',
							width : 200
						}, {
							name : 'association',
							index : 'assciation',
							width : 100
						},
						 {
							name : 'clients',
							index : 'clients',
							width : 200
						}],
						postData : {},
						rowNum : 10,
						rowList : [ 10, 20, 40, 60 ],
						height : 200,
						width : 980,
						shrinkToFit : false,
						rownumbers : true,
						pager : '#pagerCard',
						sortname : 'id',
						viewrecords : true,
						sortorder : "asc",
						caption : "Tarjetas",
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
		}
	}