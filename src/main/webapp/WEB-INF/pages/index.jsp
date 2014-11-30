<%@ include file="header.jsp"%>
<head>
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
<script type="text/javascript"
	src="<c:url value="/resources/js/clientTable.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/cardTable.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/shopTable.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/associationTable.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/productTable.js" />"></script>

<script type="text/javascript">
	var clientTable = false;
	var shopTable = false;
	var tableCards = false;
	var associationTable = false;
	var tableProducts = false;
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

			<button onClick='setupGrid();' class='btn btn-primary'
				style='width: 120px; height: 50px; margin-top: 10px;'>Clientes</button>
			<button onClick='setupGridCards();' class='btn btn-primary'
				style='width: 120px; height: 50px; margin-top: 10px;'>Tarjetas</button>
			<button onClick='setupGridProducts();' class='btn btn-primary'
				style='width: 120px; height: 50px; margin-top: 10px;'>Productos</button>
			<button onClick='setupGridShops();' class='btn btn-primary'
				style='width: 120px; height: 50px; margin-top: 10px;'>Tiendas</button>
			<button onClick='setupGridAssociations();' class='btn btn-primary'
				style='width: 120px; height: 50px; margin-top: 10px;'>Asociaciones</button>
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
						<table id="dataTableProducts"></table>
						<div id="pagerProducts"></div>
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
						<div id="pagerAssociation"></div>
					</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>
