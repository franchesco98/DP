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

