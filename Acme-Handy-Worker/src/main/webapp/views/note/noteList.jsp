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




<display:table pagesize="5" name="notes" id="row"
	requestURI="${requestURI}">

	<display:column property="writtenMoment" titleKey="note.writtenMoment"  sortable="true"  format="{0,date,dd/MM/yyyy HH:mm}" />



<display:column>
<a href="note/referee,customer,handyworker/edit.do?noteId=${row.id}&edit=0">
			<spring:message code="note.show" />
		</a>
</display:column>

	<display:column>
<security:authorize access="hasRole('CUSTOMER')">
		<a href="note/referee,customer,handyworker/edit.do?noteId=${row.id}&edit=1">
			<spring:message code="note.comment" />
		</a>
</security:authorize>	
<security:authorize access="hasRole('REFEREE')">	
		<a href="note/referee,customer,handyworker/edit.do?noteId=${row.id}&edit=1">
			<spring:message code="note.comment" />
		</a>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">	
		<a href="note/referee,customer,handyworker/edit.do?noteId=${row.id}&edit=1">
			<spring:message code="note.comment" />
		</a>
</security:authorize>
	</display:column>


	
</display:table>
