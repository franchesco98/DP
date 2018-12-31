
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table
	pagesize="5" name="creditCard" id="row"
	requestURI="${requestURI }" >

	<display:column property="name" titleKey="creditCard.name" />

	<display:column   titleKey="creditCard.number">
		<jstl:out value="${row.number}" />
	</display:column>
	
	<display:column   titleKey="creditCard.expirationYear">
		<jstl:out value="${row.expirationYear}" />
	</display:column>

<display:column>
		<a href="creditCard/customer,sponsor/edit.do?creditCardId=${row.id}"><spring:message code="creditCard.edit" /></a>
</display:column>

</display:table>
<br />

<input type="button" name="Create"

		value="<spring:message code="creditCard.create" />"

		onclick="javascript: relativeRedir('creditCard/customer,sponsor/create.do');" />




