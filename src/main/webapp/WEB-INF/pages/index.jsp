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
<script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js"
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
			jQuery("#dataTable").jqGrid(
					{
						url : "/shopExperience/getClients",
						datatype : "json",
						jsonReader : {
							repeatitems : false,
							id : "ref"
						},
						colNames : [ 'Cliente', 'Tarjetas',
								'Total puntos' ],
						colModel : [ {
							name : 'clientName',
							index : 'clientName',
							width : 250
						}, {
							name : 'card',
							index : 'card',
							width : 100
						}, {
							name : 'card_points',
							index : 'card_points',
							width : 100
						} ],
						rowNum : 5,
						rowList : [ 5, 10, 20 ],
						height : 165,
						pager : "#pagingDiv",
						viewrecords : true,
						hiddengrid : true,
						caption : "Clientes",
						onSelectRow : function() {
							var selr = jQuery('#dataTable').jqGrid(
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
		</div>
		<div class="centreDiv" style="margin-left: 3%; overflow: auto;">
			<div>
				<tbody>
					<h2></h2>
					<div>
						<table id="dataTable"></table>
						<div id="pagingDiv"></div>
					</div>
			</div>
			<tbody>
				<h2></h2>
				<div>
					<table id="dataTableShop"></table>
					<div id="pagingDivShops"></div>
				</div>
		</div>
	</div>
	</div>
</body>
<%@ include file="footer.jsp"%>