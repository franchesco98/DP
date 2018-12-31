<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/handyworker/edit.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="handyWorker" />
	<form:hidden path="fixUpTask" />


	<form:label path="offeredPrice">
		<spring:message code="application.offeredPrice" />:
	</form:label>
	<form:input path="offeredPrice" readonly="${toShow}"/>
	<form:errors cssClass="error" path="offeredPrice" />

	<br />

	<form:label path="handyWorkerComments">
		<spring:message code="application.handyWorkerComments" />:
	</form:label>
	</br>
	<form:textarea path="handyWorkerComments" rows="5" cols="30"  />
	<form:errors cssClass="error" path="handyWorkerComments" />

	<br />






	<input type="submit" name="save"
		value="<spring:message code="application.save" />" />&nbsp; 
	<jstl:if test="${application.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="application.delete" />"
			onclick="return confirm('<spring:message code="application.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/handyworker/list.do');" />
	<br />


</form:form>
