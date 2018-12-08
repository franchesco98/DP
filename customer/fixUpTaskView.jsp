<%--
 * action-2.jsp
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


	<security:authorize access="hasRole('CUSTOMER')">
	

<form:form action="fixUpTask/edit.do" modelAttribute="fixUpTask">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	


	<form:label path="ticker">
	<spring:message code="fixUpTask.ticker" />:
	</form:label>
	<form:input  path="ticker"  readonly="readonly"  />
	<form:errors cssClass="error" path="ticker" />
	<br />

    <form:label path="moment">
	<spring:message code="fixUpTask.moment" />:
	</form:label>
	<form:input  path="moment"  />
	<form:errors cssClass="error" path="moment" />
	<br />
	
	<form:label path="description">
	<spring:message code="fixUpTask.description" />:
	</form:label>
	<form:input  path="description"  />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="address">
	<spring:message code="fixUpTask.address" />:
	</form:label>
	<form:input  path="address"  />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="maxPrice">
	<spring:message code="fixUpTask.maxPrice" />:
	</form:label>
	<form:input  path="maxPrice"  />
	<form:errors cssClass="error" path="maxPrice" />
	<br />
	
	<form:label path="startTime">
	<spring:message code="fixUpTask.startTime" />:
	</form:label>
	<form:input  path="startTime"  />
	<form:errors cssClass="error" path="startTime" />
	<br />
	
	<form:label path="endTime">
	<spring:message code="fixUpTask.endTime" />:
	</form:label>
	<form:input  path="endTime"  />
	<form:errors cssClass="error" path="endTime" />
	<br />

 

<jstl:if test="${idZero}">

   <input type="submit" name="create" value="<spring:message code="fixUpTask.create" />" /> 
   

</jstl:if>

<jstl:if test="${idNoZero}">

   <input type="submit" name="save" value="<spring:message code="fixUpTask.save" />" /> 
   <input type="submit" name="delete" value="<spring:message code="fixUpTask.delete" />" />

</jstl:if>


<input type="button" name="cancel"

		value="<spring:message code="fixUpTask.cancel" />"

		onclick="javascript: relativeRedir('customer/list.do');" />

</form:form>

<p><spring:message code="fixUpTask.phases" /></p>

<display:table
	pagesize="5" name="phases" id="row" 
	requestURI="fixUpTaskController/edit" >
	
	<display:column property="title" titleKey="phase.title" />


	<display:column   titleKey="phase.description" >
		<jstl:out value="${row.description}" />
	</display:column>

	<display:column>
		<a href="controller/phase/edit.do"><spring:message code="fixUpTask.show" /></a>
	</display:column>

</display:table>



</security:authorize>
