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




<form:form action="message/edit.do" modelAttribute="messageToCreate">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
<jstl:if test="${toShow}">
	<form:hidden path="flagSpam" />
	
	<form:label path="moment">
	<spring:message code="message.moment" />:
	</form:label>
	<form:input  path="moment"  readonly="true"/>
	<br />
	<br />
	<form:label path="sender">
	<spring:message code="message.sender" />:
	</form:label>
	<form:input  path="sender.userAccount.username"  readonly="true"/>
	<br />
	<br />
	
</jstl:if>

	<form:label path="recipients">
		<spring:message code="message.recipients" />:
	</form:label>
	<br />
	<form:select path="recipients" multiple="true" cssStyle="height:10em; width:10em;">
    	 <form:options items="${actors}" itemLabel="userAccount.username"  itemValue="id" readonly="${toShow}"/>
   	</form:select>
   	<br />

<jstl:if test="${!toShow}">
	<form:hidden path="sender" />
	<form:hidden path="moment" />	
	
   	<spring:message code="message.recipients.tex"/>
	<br />
	<br />
	<security:authorize access="hasRole('ADMIN')">
	   
	<form:label path="flagSpam">
	<spring:message code="message.broadcast" />:
	</form:label>
	<form:checkbox  path="flagSpam" value="true"/>
	<br />
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<form:hidden path="flagSpam" />	
	</security:authorize>
	<security:authorize access="hasRole('SPONSOR')">
	<form:hidden path="flagSpam" />	
	</security:authorize>
	<security:authorize access="hasRole('CUSTOMER')">
	<form:hidden path="flagSpam" />	
	</security:authorize>	
	<security:authorize access="hasRole('REFEREE')">
	<form:hidden path="flagSpam" />	
	</security:authorize>
	
</jstl:if>


	<br />	
	<form:label path="subject">
	<spring:message code="message.subjet" />:
	</form:label>
	<br />
	<form:textarea  path="subject" rows="3" cols="30" readonly="${toShow}"/>
	<form:errors cssClass="error" path="subject" />
	<br />
	<br />
    <form:label path="body">
	<spring:message code="message.body" />:
	</form:label>
	<br />
	<form:textarea  path="body" rows="5" cols="60" readonly="${toShow}" />
	<form:errors cssClass="error" path="body" />
	<br />
	<br />
	
	
	
	<form:label path="tag">
	<spring:message code="message.tag" />:
	</form:label>
	<br />
	<form:textarea  path="tag" rows="2" cols="20" readonly="${toShow}"/>
	<spring:message code="message.tag.tex"/>
	<br />
	<br />
	

	  <form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<form:select path="priority" items="${priorities}" readonly="${toShow}" />
	<br />
	<br />
	







<jstl:if test="${messageToCreate.id == 0}">

<input type="submit" name="save" value="<spring:message code="message.send" />" />

<input type="button" name="cancel"

		value="<spring:message code="message.cancel" />"

		onclick="javascript: relativeRedir('#');" />

</jstl:if>

<jstl:if test="${messageToCreate.id != 0}">

	<input type="button" name="delete"

		value="<spring:message code="message.delete" />"

		onclick="javascript: relativeRedir('message/delete.do?messageId=${messageToCreate.id}&boxId=${box.id}');" />
		
	
	<input type="button" name="cancel"

		value="<spring:message code="message.cancel" />"

		onclick="javascript: relativeRedir('message/list.do?boxId=${box.id}');" />

</jstl:if>





</form:form>