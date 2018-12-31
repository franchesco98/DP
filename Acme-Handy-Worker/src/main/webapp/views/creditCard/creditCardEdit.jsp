

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">





<form:form action="creditCard/customer,sponsor/edit.do" modelAttribute="creditCard">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />


	<form:label path="name">
		<spring:message code="creditCard.name" />:
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name" />
	<br />
	
	
	<form:label path="brandName">
		<spring:message code="creditCard.brandName" />:
	</form:label>
	<br />
	<form:select path="brandName" items="${brandNames}" />
   	<br />
   	
	<form:label path="number">
		<spring:message code="creditCard.number" />:
	</form:label>
	<form:input path="number" />
	<form:errors cssClass="error" path="number" />
	<br />

	<form:label path="expirationMonth">
		<spring:message code="creditCard.expirationMonth" />:
	</form:label>
	<form:input path="expirationMonth" placeholder="mm" />
	<form:errors cssClass="error" path="expirationMonth" />
	<br />

	<form:label path="expirationYear">
		<spring:message code="creditCard.expirationYear" />:
	</form:label>
	<form:input path="expirationYear" placeholder="yyyy" />
	<form:errors cssClass="error" path="expirationYear" />
	<br />
	
		<form:label path="CVVCode">
		<spring:message code="creditCard.CVVCode" />:
	</form:label>
	<form:input path="CVVCode" />
	<form:errors cssClass="error" path="CVVCode" />
	<br />




	<input type="submit" name="save"
		value="<spring:message code="creditCard.save.edit" />" />



	<input type="button" name="cancel"
		value="<spring:message code="creditCard.cancel" />"
		onclick="javascript: relativeRedir('creditCard/customer,sponsor/list.do');" />
		<br/>
		
<jstl:if test="${message!=null}">
		<spring:message code="${message}"/>
</jstl:if>

</form:form>
