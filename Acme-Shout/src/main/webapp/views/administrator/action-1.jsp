<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<head> <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script> </head>



<body>

<div id="graficos" class="graficos" > 


<canvas id="grafica">
        </canvas>

</div>

<script type="text/javascript"> 
var migrafica = document.getElementById('grafica').getContext('2d');

var todosGritos = ${statistics.get('count.all.shouts')};
var gritosCortos = ${statistics.get('count.short.shouts')};
var gritosLargos = ${statistics.get('count.long.shouts')};
var graficaBarras = new Chart(migrafica, {type: 'bar', data: {labels: ['Todos los gritos','Gritos cortos','Gritos largos'], datasets:[{label:'indicadores',data:[todosGritos,gritosCortos,gritosLargos], backgroundColor:'blue'}]}
 
 });


</script>

</body>

<%-- CASO ESTUDIO 5
<table>
  <tr>
    <th><spring:message code="administrator.indicator" /></th>
    <th><spring:message code="administrator.value" /></th>
  </tr>
  
  <tr>
    <td><spring:message code="administrator.count.all.shouts" /></td>
    <td><jstl:out value="${statistics.get('count.all.shouts')}" /></td>
  </tr>
  <tr>
    <td><spring:message code="administrator.count.short.shouts" /></td>
    <td><jstl:out value="${statistics.get('count.short.shouts')}" /></td>
  </tr>
  <tr>
    <td><spring:message code="administrator.count.long.shouts" /></td>
    <td><jstl:out value="${statistics.get('count.long.shouts')}" /></td>
  </tr>
</table>
 --%>