
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="payingApplication.title" />

<form:form action="application/customer/edit.do" modelAttribute="application">


	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="handyWorker" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="customerComments" />
	<form:hidden path="handyWorkerComments" />

   	<form:label path="creditCard">
		<spring:message code="creditCard.creditCard" />:
	</form:label>
	<form:select path="creditCard">
    	 <form:options items="${creditCards}"  />
   	</form:select>
	<br />
	
  <input type="submit" name="save" value="<spring:message code="creditCard.save" />" />


<input type="button" name="cancel"

		value="<spring:message code="creditCard.cancel" />"

		onclick="javascript: relativeRedir('application/customer/list.do?fixUpTaskId=${application.fixUpTask}');" />

<input type="button" name="create"

		value="<spring:message code="creditCard.create" />"

		onclick="javascript: relativeRedir('creditCard/customer,sponsor/create.do');" />

</form:form>
<br/>
<jstl:if test="${message!=null}">
		<spring:message code="${message}"/>
</jstl:if>


