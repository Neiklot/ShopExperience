<%@ include file="header.jsp" %>
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
	<c:if test="${not empty error}">
		<div class="errorblock">
			Your login attempt was not successful, try again.<br /> Caused :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
 
	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST' >
 	Login with Clientname and Password (Custom Page)
		<table   style='margin-left:25%;margin-top:18px;'>
			<tr>
				<td>Client:</td>
				<td colspan="2"><input type='text' name='j_username' value=''>
				</td>
			</tr>
			<tr >
				<td>Password:</td>
				<td colspan="2"><input type='password' name='j_password' />
				</td>
			</tr>
			<tr><td></td>
				<td><input name="submit" type="submit"
					value="Aceptar" />
				</td>
				<td><input name="reset" type="reset" />
				</td>
			</tr>
		</table>
 
	</form>
	</div>
</body>
<%@ include file="footer.jsp" %>
