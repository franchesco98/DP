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


	<%-- para las fechas --%>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
<script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>

<script>
	$( function() {
	  $( "#datepicker1" ).datepicker();
	} );
	</script>





<form:form action="fixUpTask/edit.do" modelAttribute="fixUpTask">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	

<jstl:if test="${idZero}">
<form:hidden path="ticker" />
<form:hidden path="moment" />

</jstl:if>




<jstl:if test="${!idZero}">

	<form:label path="ticker">
	<spring:message code="fixUpTask.ticker" />:
	</form:label>
	<form:input  path="ticker"  readonly="true"  />
	<form:errors cssClass="error" path="ticker" />
	<br />

    <form:label path="moment">
	<spring:message code="fixUpTask.moment" />:
	</form:label>
	<form:input  path="moment" readonly="true"  />
	<form:errors cssClass="error" path="moment" />
	<br />
	
</jstl:if>	
	
	<form:label path="description">
	<spring:message code="fixUpTask.description" />:
	</form:label>
	<form:input  path="description" readonly="${toShow}" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="address">
	<spring:message code="fixUpTask.address" />:
	</form:label>
	<form:input  path="address" readonly="${toShow}" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="maxPrice">
	<spring:message code="fixUpTask.maxPrice" />:
	</form:label>
	<form:input  path="maxPrice" readonly="${toShow}"  />
	<form:errors cssClass="error" path="maxPrice" />
	<br />
	
	<form:label path="startTime">
	<spring:message code="fixUpTask.startTime"/>:
	</form:label>
	<form:input  path="startTime"  id="datepicker" readonly="${toShow}" placeholder="yyyy-mm-dd" />
	<form:errors cssClass="error" path="startTime" />
	<br />
	
	<form:label path="endTime">
	<spring:message code="fixUpTask.endTime" />:
	</form:label>
	<form:input  path="endTime"  id="datepicker1" readonly="${toShow}" placeholder="yyyy-mm-dd" />
	<form:errors cssClass="error" path="endTime" />
	<br />

 
	<p>
		<spring:message code="fixUpTask.commentAdvice" />
		<spring:message code="fixUpTask.commentAdvice1" /> 
	</p>	
<sprin

<jstl:if test="${idZero}">

   <input type="submit" name="create" value="<spring:message code="fixUpTask.create" />" /> 
   

</jstl:if>

<jstl:if test="${!idZero}">

   <input type="submit" name="save" value="<spring:message code="fixUpTask.save" />" /> 
   <input type="submit" name="delete" value="<spring:message code="fixUpTask.delete" />" />

</jstl:if>


<input type="button" name="cancel"

		value="<spring:message code="fixUpTask.cancel" />"

		onclick="javascript: relativeRedir('customer/list.do');" />

</form:form>

<p>
<spring:message code="fixUpTask.phases" />: 
<a href="controller/phase/list.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.phases.link" /></a>
</p>





