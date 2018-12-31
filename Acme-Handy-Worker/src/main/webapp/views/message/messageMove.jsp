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




<form:form action="message/move.do" modelAttribute="String">


	<form:label path="">
		<spring:message code="message.priority" />:
	</form:label>
	<form:select path="" items="${destinationBoxes}" itemLabel="name"  itemValue="id" />
	<br />
	<br />
	




<input type="submit" name="save" value="<spring:message code="message.send" />" />

<input type="button" name="cancel"

		value="<spring:message code="message.cancel" />"

		onclick="javascript: relativeRedir('message/list.do?boxId=${originBox.id}');"  />

</form:form>