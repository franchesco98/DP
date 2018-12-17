<%--
 * tutorialList.jsp
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

<display:table pagesize="3" name="tutorials" id="row"
	requestURI="tutorialController/list">


	<display:column property="title" titleKey="tutorial.title" />

	<display:column   titleKey="tutorial.moment" >
		<jstl:out value="${row.moment}" />
	</display:column>

	<display:column   titleKey="tutorial.handyWorker.table" >
		<jstl:out value="${row.handyWorker.name}" />
	</display:column>

	


	<display:column>
		<a href = "tutorialController/edit.do"><spring:message code ="tutorial.show"/></a>
	</display:column>

	<security:authorize access="HANDYWORKER">

	<display:column>
		<a href = "tutorialController/edit.do"><spring:message code ="tutorial.edit"/></a>
	</display:column>

</security:authorize>

</display:table>

<security:authorize access="HANDYWORKER">

<input type="button" name = "createTutorial" value ="<spring:message code = "tutorial.create"/>" onclick="window.location.href = 'controller/tutorialController/create.do'">

</security:authorize>