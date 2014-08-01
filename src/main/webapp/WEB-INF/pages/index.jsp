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
	$(document).ready(function() {
		setupGrid();
		getSelected();
	});

	function setupGrid() {
		jQuery("#dataTable").jqGrid(
				{
					url : "/shopExperience/getClients",
					datatype : "json",
					jsonReader : {
						repeatitems : false,
						id : "ref"
					},
					colNames : [ 'Id Cliente', 'Cliente', 'Total Productos',
							'Ultimo Producto', 'Tarjeta Asignada' ],
					colModel : [ {
						name : 'clientId',
						index : 'clientId',
						width : 100
					}, {
						name : 'clientName',
						index : 'clientName',
						width : 250
					}, {
						name : 'totalProducts',
						index : 'totalProducts',
						width : 100
					}, {
						name : 'lastProduct',
						index : 'lastProduct',
						width : 250
					}, {
						name : 'card',
						index : 'card',
						width : 250
					} ],
					rowNum : 5,
					rowList : [ 5, 10, 20 ],
					height : 165,
					pager : "#pagingDiv",
					viewrecords : true,
					caption : "Clientes con productos",
					onSelectRow : function() {
						var selr = jQuery('#dataTable').jqGrid('getGridParam',
								'selrow');
						if (selr)
							popupwindow(selr);
						else
							alert("No selected row");
						return false;
					}
				});
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
			<td style='margin-top: 10px;padding:45px;'><a title='New client' class='boton'
				href="<c:url value="/register" />"> <span></span></a>
			</td>
			<td style='margin-top: 10px;padding:45px;'><a title='New Shop' class='boton'
				href="<c:url value="/register" />"> <span></span></a>
			</td>
			<td style='margin-top: 10px;padding:45px;'><a title='New User' class='boton'
				href="<c:url value="/register" />"> <span></span></a>
			</td></div>
			<div class="centreDiv" style="margin-left: 3%">
				<div>
					<tbody>
						<h2>Usuarios</h2>
						<div>
							<table id="dataTable"></table>
							<div id="pagingDiv"></div>
						</div>
				</div>
			</div>
	</div>
</body>
<%@ include file="footer.jsp"%>