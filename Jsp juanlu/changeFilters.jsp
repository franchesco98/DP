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





<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>

<form:form action="application/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTasks" />
	<form:hidden path="lastUpdate" />

	


	<!--    Precio, si es un Customer no podrá editarlo -->
	<form:label path="keyWord">
		<spring:message code="application.status" />:
	</form:label>

	<form:select path="keyWord">
		<form:options items="${possibleStatus}" />
	</form:select>
	<br />
	<!--  Comentario del HW, si es un Customer no podrá editarlo -->
	<form:label path="category">
		<spring:message code="application.status" />:
	</form:label>

	<form:select path="category">
		<form:options items="${possibleStatus}" />
	</form:select>
	<br />


	<form:label path="warranty">
		<spring:message code="application.comment" />:
	</form:label>
	<form:input path="warranty" />
	<form:errors cssClass="error" path="warranty" />
	<br />

	<form:label path="priceMin">
		<spring:message code="application.offeredPrice" />:
	</form:label>
	<form:input path="priceMin" placeholder="0.00" />
	<form:errors cssClass="error" path="priceMin" />
	<br />

	<form:label path="priceMax">
		<spring:message code="application.offeredPrice" />:
	</form:label>
	<form:input path="priceMax" placeholder="0.00" />
	<form:errors cssClass="error" path="priceMax" />
	<br />

	<form:label path="dateMin">
		<spring:message code="application.offeredPrice" />:
	</form:label>
	<form:input id="datepicker" path="dateMin" placeholder="yyyy-mm-dd" />
	<br />

	<form:label path="dateMax">
		<spring:message code="application.offeredPrice" />:
	</form:label>
	<form:input id="datepicker" path="dateMax" placeholder="yyyy-mm-dd" />
	<br />




	<input type="submit" name="save"
		value="<spring:message code="application.save" />" />



	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
		
</form:form>