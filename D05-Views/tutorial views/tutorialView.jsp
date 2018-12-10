<%--
 * tutorialView.jsp
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


<security:authorize access="hasRole('HANDYWORKER')">
<form:form action="tutorialController/edit.do"
	modelAttribute="tutorialtest">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />

	<form:label path="title">
		<spring:message code="tutorial.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="moment">
		<spring:message code="tutorial.moment" />
	</form:label>
	<form:input path="moment" />
	<form:errors cssClass="error" path="moment" />
	<br />

	<form:label path="summary">
		<spring:message code="tutorial.summary" />
	</form:label>
	<form:input path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />

	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br />

	<jstl:if test="${id==0}">

		<input type="submit" name="createTutorial"
			value="<spring:message code="tutorial.create" />" />

	</jstl:if>

	<jstl:if test="${id!=0}">
		<input type="submit" name="editTutorial"
			value="<spring:message code="tutorial.edit" />" />
		<input type="submit" name="deleteTutorial"
			value="<spring:message code="tutorial.delete" />" />

	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="window.location.href = 'controller/tutorialController/list.do'" />

</form:form>
<br>
<spring:message code="tutorial.sections" />

<display:table pagesize="5" name="tutorialtest.sections" id="row"
	requestURI="tutorialController/edit">

	<display:column property="number" titleKey="tutorial.section.number" />

	<display:column property="title" titleKey="tutorial.section.title" />

	<display:column property="pieceOfText"
		titleKey="tutorial.section.pieceOfText" />

	<display:column property="pictures"
		titleKey="tutorial.section.pictures" />

	<display:column>
		<a href="sectionController/edit.do"><spring:message code="tutorial.section.edit"/></a>
	</display:column>
</display:table>


</security:authorize>