<%@ include file="header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

</head>
<body>
	<div class="bodyPage">
		<div class="centreDiv" style="margin-left: 25%">
			<form:form action="registerClient" method="post"
				commandName="clientForm">
				<table border="0">
					<tr>
						<td colspan="2" align="center"><h2>New Client
								- Registration</h2></td>
					</tr>
					<tr>
						<td>Client Name:</td>
						<td><form:input path="clientName" /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><form:password path="password" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							value="Register" /></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>