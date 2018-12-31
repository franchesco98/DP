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

<%--Meter en el controlador de donde venga la fixUpTask asociada y el HW asociado en el controlador si hay que crearla (si es para editarla ya estara introducido)--%>


<%--Vemos el rol del usuario logueado para poner un titulo u otro--%>


	<security:authorize access="hasRole('HANDYWORKER')">

		<spring:message code="create" />:
		<br />
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">

		<spring:message code="update" />:
		<br />
	</security:authorize>
	
<%--FixUpTask relacionada--%>

		<spring:message code="relatedFUT" />
		<jstl:out value="${application.fixUpTask.ticker}" /> 
		
		
		
<form:form action="application/handyWorker/edit.do" modelAttribute="application">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="creditCard" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="customerComments" />

	

    <%--Precio, si es un Customer no podrÃ¡ editarlo--%>
    <form:label path="offeredPrice">
		<spring:message code="application.offeredPrice" />:
	</form:label>
		<form:input  path="offeredPrice"  placeholder = "0.00"/>
		<form:errors cssClass="error" path="offeredPrice" />
	<br />
    
    <%--Comentario del HW, si es un Customer no podrÃ¡ editarlo--%>
    <form:label path="handyWorkerComments">
		<spring:message code="application.handyWorkerComments" />:*
	</form:label>
		<form:textarea  path="handyWorkerComments"  />
		<form:errors cssClass="error" path="handyWorkerComments" />
	<br />
    
    <spring:message code="application.commentAdvice" />
    
  


	<input type="submit" name="save" value="<spring:message code="application.save" />" />; 



	<input type="button" name="cancel"

		value="<spring:message code="application.cancel" />"

		onclick="javascript: relativeRedir('fixUpTask/list.do');" />



</form:form>

	