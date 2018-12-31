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

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />

 
<%-- para las fechas
<script src="https://code.jquery.com/jquery-1.12.4.js" /></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" /></script>
 

 
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
	
  --%>	



<form:form action="fixUpTask/customer/edit.do"  modelAttribute="fixUpTask">

<jstl:if test="${fixUpTask.id==0}">

<spring:message code="fixUpTask.create.title" />:
<br/>
<br/>

</jstl:if>

<jstl:if test="${fixUpTask.id!=0}">

<spring:message code="fixUpTask.change.title" />:
<br/>
<br/>

</jstl:if>

	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	

<jstl:if test="${fixUpTask.id==0}">
<form:hidden path="ticker" />
<form:hidden path="moment" />

</jstl:if>




<jstl:if test="${fixUpTask.id!=0}">

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
	<jstl:if test="${fixUpTask.id!=0}">(${(fixUpTask.maxPrice*vat)/100})</jstl:if>
	<br />
	
	<form:label path="startTime">
	<spring:message code="fixUpTask.startTime"/>:
	</form:label>
	<form:input  path="startTime"  id="datepicker" readonly="${toShow}" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="startTime" />
	<br />
	
	<form:label path="endTime">
	<spring:message code="fixUpTask.endTime" />:
	</form:label>
	<form:input  path="endTime"  id="datepicker1" readonly="${toShow}" placeholder="dd/MM/yyyy HH:mm" />
	<form:errors cssClass="error" path="endTime" />
	<br />

<jstl:if test="${!toShow}">

	<form:label path="warranty">
		<spring:message code="fixUpTask.warranties" />:
	</form:label>
	<form:select path="warranty">
    	 <form:options items="${warranties}"  />
   	</form:select>
	<br />
	
	<form:label path="category">
		<spring:message code="fixUpTask.categories" />:
	</form:label>
	<form:select path="category">
    	 <form:options items="${categories}"  />
   	</form:select>
	<br />

</jstl:if>	

<jstl:if test="${toShow}">

 <form:label path="warranty">
	<spring:message code="fixUpTask.warranty" />:
	</form:label>
	<form:input  path="warranty"   readonly="${toShow}" />
	<br />
	
    <form:label path="category">
	<spring:message code="fixUpTask.category" />:
	</form:label>
	<form:input  path="category"   readonly="${toShow}" />
	<br />

</jstl:if>	

 


<jstl:if test="${fixUpTask.id==0}">

   <input type="submit" name="save" value="<spring:message code="fixUpTask.create" />" /> 
   

</jstl:if>

<jstl:if test="${fixUpTask.id!=0}">
<jstl:if test="${!toShow}">


   <input type="submit" name="save" value="<spring:message code="fixUpTask.save" />" /> 
  
<input type="submit" name="delete"
			value="<spring:message code="fixUpTask.delete" />"
			onclick="return confirm('<spring:message code="fixUpTask.confirm.delete" />')" />

</jstl:if>
</jstl:if>

<security:authorize access="hasRole('CUSTOMER')">
<input type="button" name="cancel"
		value="<spring:message code="fixUpTask.cancel" />"
        onclick="javascript: relativeRedir('fixUpTask/customer/list.do');" />
</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
<input type="button" name="cancel"
		value="<spring:message code="fixUpTask.cancel" />"
		onclick="javascript: relativeRedir('fixUpTask/handyworker/listAll.do');" />
		
		</security:authorize>

</form:form>

<p>
<spring:message code="fixUpTask.phases" />: 
<a href="controller/phase/list.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.phases.link" /></a>
</p>





