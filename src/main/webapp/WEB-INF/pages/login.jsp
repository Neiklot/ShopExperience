<%@ include file="header.jsp"%>
<head>
<title>Login Page</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body onload='document.f.j_username.focus();'>
	<div class="bodyPage">
		<div class="bodyPage">

			<c:if test="${not empty error}">
				<div class="errorblock">
					Your login attempt was not successful, try again.<br /> Caused :
					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
				</div>
			</c:if>

			<form name='f' action="<c:url value='j_spring_security_check' />"
				method='POST'>

				<table style='margin-left: 25%'>
					<tr>
						<td>User:</td>
						<td><input type='text' name='j_username' value=''></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='j_password' /></td>
					</tr>
					<tr>
						<td ><button style='width:120px;height:50px;' class='btn btn-primary' name="submit" type="submit"
							value="submit" >Aceptar</button></td>
						<td><button style='width:120px;height:50px;' class='btn btn-primary' name="reset" type="reset" >Borrar</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>
