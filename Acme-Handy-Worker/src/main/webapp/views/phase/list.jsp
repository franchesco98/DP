<%--
 * index.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<display:table pagesize="5" name="phases" id="row"
	requestURI="${requestURI}">

	<display:column property="description" titleKey="phase.description" />


	<display:column property="startMoment" titleKey="phase.startmoment" />

	<display:column property="endMoment" titleKey="phase.endmoment" />

	<display:column property="title" titleKey="phase.title" />

	<display:column>

		<a href="phase/handyworker/edit.do?phaseId=${row.id}">
			<spring:message code="phase.edit" />
		</a>
	</display:column>


</display:table>
