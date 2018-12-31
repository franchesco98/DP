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



<form:form action="tutorialController/edit.do"
	modelAttribute="tutorialtest">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<jstl:if test="${toShow}">

			<form:hidden path="sections"/>
			<form:hidden path="pictures"/>
	
		</jstl:if>
	

	
	<jstl:if test="${!toShow}">

		<form:hidden path="handyWorker"/>

	</jstl:if>


	

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
	<form:textarea path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br/>

	<jstl:if test="${!toShow}">
	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<form:textarea path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br/>

	<form:label path="sections">
		<spring:message code="tutorial.section" />
	</form:label>
	<form:select path="sections">
    	 <form:options items="${tutorialSectionsTitles}"/>
   	</form:select>
	
	
	<br/>



	</jstl:if>

	<jstl:if test="${toShow}">
	

		<spring:message code="tutorial.section" />:
		<a href="Controller/section/list.do"><spring:message code="tutorial.section" /> </a>
		<br/>

		<spring:message code="tutorial.pictures" />
		<a href="Controller/tutorial/pictures.do"><spring:message code="tutorial.pictures" /> </a>
		<br/>
		
	<spring:message code="tutorial.handyWorker" />:
		<a href="Controller/handyWorker/edit.do"><jstl:out value="${tutorial.handyWorker.name}"/> </a>
	
		</jstl:if>

<jstl:if test="${!toShow}">

<spring:message code="tutorial.picturesAdvice" />
<spring:message code="tutorial.Advice" />

</jstl:if>



	<jstl:if test="${tutorial.id==0}">

		<input type="submit" name="createTutorial"
			value="<spring:message code="tutorial.create" />" />

	</jstl:if>

	<jstl:if test="${tutorial.id!=0}">
		<input type="submit" name="editTutorial"
			value="<spring:message code="tutorial.edit" />" />
		<input type="submit" name="deleteTutorial"
			value="<spring:message code="tutorial.delete" />" />

	</jstl:if>

	
	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="window.location.href = 'controller/tutorialController/list.do'" />


</form:form>