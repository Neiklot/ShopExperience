<%@ include file="header.jsp" %>

<head>
    <meta charset="utf-8">
    <title>ShopExperience Usuarios y Productos</title>
    

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js" type="text/javascript"></script>
    <script src="http://www.trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js" type="text/javascript"></script>
    <script src="http://www.trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.0/jquery-ui.min.js"></script>


    <script>
        $(document).ready(function() {
            setupGrid();
            getSelected();
        });
            
        function setupGrid(){
            jQuery("#dataTable").jqGrid({
                url: "/shopExperience/getUsers",
                datatype: "json",
                jsonReader: {repeatitems: false, id: "ref"},
                colNames:['Id Usuario','Usuario','Total Productos','Ultimo Producto','Tarjeta Asignada'],
                colModel:[
                    {name:'userId',index:'userId', width:100},
                    {name:'userName',index:'userName', width:250},
                    {name:'totalProducts',index:'totalProducts', width:100},
                    {name:'lastProduct',index:'lastProduct', width:250},
                    {name:'card',index:'card', width:250}
                ],
                rowNum:5,
                rowList:[5,10,20],
                height:165,
                pager: "#pagingDiv",
                viewrecords: true,
                caption: "Usuarios con productos",
                onSelectRow: function(){ 
    	            var selr = jQuery('#dataTable').jqGrid('getGridParam','selrow'); 
    	            if(selr) popupwindow(selr); 
    	            else alert("No selected row"); 
    	            return false; 
    	        }
            });
        }
        
        
        function popupwindow(selr) {
        	var url="http://localhost:8080/shopExperience/index";
        	var title=selr;
        	var w=250;
        	var h=250;
        	  var left = (screen.width/2)-(w/2);
        	  var top = (screen.height/2)-(h/2);
        	  return window.open(url, selr, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
        	} 
    </script>

</head>
<body>
	<td ><h1><a href="<c:url value="/j_spring_security_logout" />"> Logout</a></h1></td>
<!-- 	<td><button id="getselected">Get Selected Rows</button></td> -->
	<td ><a href="<c:url value="/register" />"> New User </a></h1></td>
  <div class="centreDiv"  style="margin-left:25%" >

  
      <div>
      <tbody>
	          <h2>Usuarios</h2>
          <div>
              <table id="dataTable"></table>
              <div id="pagingDiv"></div>
          </div>
      </div>

  
  </div>
</body>
<%@ include file="footer.jsp" %>