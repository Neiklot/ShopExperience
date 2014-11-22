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
$( document ).ready(function() {
		$.ajax({
			type : "GET",
			url : "/shopExperience/getUserLogged",
			dataType : "json",
			success : function(result) {
				var json_x = result;
				document.getElementById("findClient").value="Hola "+json_x.client.clientName;
			},
			error : function(x, e) {
				alert("Error cliente");
			}
		});
	});
</script>
</head>

<body>
	<div class="bodyPage">
		<div class="centreDiv" style="margin-left: 3%; overflow: auto;">
			<output style="font-family: Georgia, serif; color: #4E443C; font-variant: small-caps; size: 50px; text-transform: none; font-weight: 100; margin-bottom: 0;"" id="findClient"></output>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>



