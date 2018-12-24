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





	<spring:message code="update" />:
		<br />

	
<%--FixUpTask relacionada--%>

		<spring:message code="relatedFUT" />
		<jstl:out value="${application.fixUpTask.ticker}" /> 
		
		
		
<form:form action="application/edit.do" modelAttribute="application">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="creditCard" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="moment" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="status" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="handyWorkerComments" />
		
   		<form:label path="customerComments">
			<spring:message code="application.customerComments" />:*
		</form:label>
			<form:textarea  path="customerComments"  />
			<form:errors cssClass="error" path="customerComments" />
		<br />		
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:hidden path="customerComments" />
		
   		<form:label path="handyWorkerComments">
			<spring:message code="application.handyWorkerComments" />:*
		</form:label>
			<form:textarea  path="handyWorkerComments"  />
			<form:errors cssClass="error" path="handyWorkerComments" />
		<br />			
	</security:authorize>
			
	<spring:message code="application.commentAdvice" />


	<input type="submit" name="save" value="<spring:message code="application.save" />" />; 



	<input type="button" name="cancel"

		value="<spring:message code="application.cancel" />"

		onclick="javascript: relativeRedir('application/list.do');" />



</form:form>