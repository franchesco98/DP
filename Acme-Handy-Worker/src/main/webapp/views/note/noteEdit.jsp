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




<form:form action="note/referee,customer,handyworker/edit.do" modelAttribute="note">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="report" />

<jstl:if test="${!toShow}">
<form:hidden path="writtenMoment" />
</jstl:if>
<jstl:if test="${toShow}">
<form:label path="writtenMoment">
	<spring:message code="note.writtenMoment" />:
	</form:label>
	<form:input  path="writtenMoment"  readonly="true"/>
	<br />
</jstl:if>

<jstl:if test="${!toShow}">
<security:authorize access="hasRole('CUSTOMER')">

	<form:label path="customerComment">
	<spring:message code="note.comment" />:
	</form:label>
	<form:textarea  path="customerComment"  readonly="${toShow}"/>
	<br />
	<form:hidden path="refereeComment" />
	<form:hidden path="handyWorkerComment" />
	
</security:authorize>	

<security:authorize access="hasRole('REFEREE')">

	<form:label path="refereeComment">
	<spring:message code="note.comment" />:
	</form:label>
	<form:textarea  path="refereeComment"  readonly="${toShow}"/>
	<br />
	<form:hidden path="customerComment" />
	<form:hidden path="handyWorkerComment" />
	
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">

	<form:label path="handyWorkerComment">
	<spring:message code="note.comment" />:
	</form:label>
	<form:textarea  path="handyWorkerComment"  readonly="${toShow}"/>
	<br />
	<form:hidden path="refereeComment" />
	<form:hidden path="customerComment" />
	
</security:authorize>
</jstl:if>

<jstl:if test="${toShow}">

<form:label path="refereeComment">
	<spring:message code="note.comment.referee" />:
	</form:label>
	<form:textarea  path="refereeComment"  readonly="${toShow}"/>
	<br />
	

<form:label path="handyWorkerComment">
	<spring:message code="note.comment.handy" />:
	</form:label>
	<form:textarea  path="handyWorkerComment"  readonly="${toShow}"/>
	<br />

<form:label path="customerComment">
	<spring:message code="note.comment.customer" />:
	</form:label>
	<form:textarea  path="customerComment"  readonly="${toShow}"/>
	<br />	
	
</jstl:if>

<jstl:if test="${!toShow}">
<input type="submit" name="save" value="<spring:message code="note.save" />" /> 
</jstl:if>
<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('note/referee,customer,handyworker/list.do?reportId=${note.report.id}');" />


</form:form>