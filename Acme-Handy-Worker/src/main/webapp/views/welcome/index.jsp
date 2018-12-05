<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 


<%-- Como crear formularios --%>



<%-- para las fechas --%>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
<script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>




<form:form action="configuration/index" modelAttribute="confi">

<form:label path="creditCardMakes">

		<spring:message code="actor.name" />

	</form:label>
	
	<form:input path="creditCardMakes" />
	
	<br/>
	
	<form:label path="nameSystem">

		<spring:message code="actor.name" />

	</form:label>
	
	
	<form:input path="nameSystem" />
	
	<br/>
	
	<%-- para las fechas --%>
	<form:input  id="datepicker" path="spamWords" placeholder="yyyy-mm-dd"/>
	
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="actor.name" />" />

	

</form:form>



<%-- Como crear listas --%>


<display:table
	pagesize="5" name="fixUpTasks" id="row" 
	requestURI="WelcomeController/index" >
	
	<display:column property="ticker" titleKey="welcome.greeting.prefix" />


	<display:column   titleKey="actor.name" >
		<jstl:out value="${row.description}" />
		<jstl:out value="${row.ticker}" />
	</display:column>

</display:table>









