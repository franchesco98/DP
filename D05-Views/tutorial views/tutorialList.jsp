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
	<display:column property="moment" titleKey="tutorial.moment" />
	<display:column property="summary" titleKey="tutorial.summary" />
	<display:column titleKey="tutorial.sections">
		<jstl:forEach items="${row.sections}" var="item">
			<jstl:out value="${item.title}"></jstl:out>
		</jstl:forEach>
	</display:column>
	<display:column>
		<a href = "tutorialController/edit.do"><spring:message code ="tutorial.show"/></a>
	</display:column>
</display:table>

<input type="button" name = "createTutorial" value ="<spring:message code = "tutorial.create"/>" onclick="window.location.href = 'controller/tutorialController/create.do'">
