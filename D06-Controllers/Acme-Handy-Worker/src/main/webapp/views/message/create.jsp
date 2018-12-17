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




<form:form action="message/save.do" modelAttribute="message">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="sender" />
	
		
<jstl:if test="${!toShow}">
		<form:label path="recipients">
	<spring:message code="message.recipients" />:
	</form:label>
	<form:input  path="recipients" />
	<spring:message>message.recipients.tex</spring:message>
	<form:errors cssClass="error" path="recipients" />
	<br />
</jstl:if>


	
	<form:label path="subjet">
	<spring:message code="message.subjet" />:
	</form:label>
	<form:input  path="subjet"  />
	<form:errors cssClass="error" path="subjet" />
	<br />

    <form:label path="body">
	<spring:message code="message.body" />:
	</form:label>
	<form:input  path="body"  readonly="${toShow}" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	
	
	<form:label path="tag">
	<spring:message code="message.tag" />:
	</form:label>
	<form:input  path="tag" readonly="${toShow}"  />
	<spring:message>message.tag.tex</spring:message>
	<form:errors cssClass="error" path="tag" />
	<br />
	
	
	
	  <form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<form:select path="priority">
    	 <form:options items="${priorities}"  />
   	</form:select>
	<br />
	









<input type="submit" name="send" value="<spring:message code="message.send" />" />; 




<input type="button" name="cancel"

		value="<spring:message code="message.cancel" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />



</form:form>