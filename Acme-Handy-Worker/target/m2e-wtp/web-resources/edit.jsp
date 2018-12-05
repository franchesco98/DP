<%--

 * edit.jsp

 *

 * Copyright (C) 2018 Universidad de Sevilla

 * 

 * The use of this project is hereby constrained to the conditions of the 

 * TDG Licence, a copy of which you may download from 

 * http://www.tdg-seville.info/License.html

 --%>





<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<security:authorize access="isAuthenticated()">

	<security:authentication property="principal.username" />

</security:authorize>



<form:form action="actor/edit.do" modelAttribute="actor">

	<form:hidden path="id" />

	<form:hidden path="version" />

	<form:hidden path="isSuspicious" />
	
    <form:hidden path="isBanned" />

	<form:hidden path="userAccount" />
	
	
<%-- --%>


	<form:label path="name">

		<spring:message code="actor.name" />

	</form:label>

	<form:input path="name" />

	<form:errors class="error" path="name" />

	

	<form:label path="middleName">

		<spring:message code="actor.middleName" />

	</form:label>

	<form:input path="middleName" />

	<form:errors class="error" path="middleName" />

	



	<form:label path="surname">

		<spring:message code="actor.surname" />

	</form:label>

	<form:input path="surname" />

	<form:errors class="error" path="surname" />

	



	<form:label path="photo">

		<spring:message code="actor.photo" />

	</form:label>

	<form:input path="photo" />

	<form:errors class="error" path="photo" />

	



	<form:label path="email">

		<spring:message code="actor.email" />

	</form:label>

	<form:input path="email" />

	<form:errors class="error" path="email" />

	



	<form:label path="phone">

		<spring:message code="actor.phone" />

	</form:label>

	<form:input path="phone" />

	<form:errors class="error" path="phone" />

	



	<form:label path="address">

		<spring:message code="actor.address" />

	</form:label>

	<form:input path="address" />

	<form:errors class="error" path="address" />

	



	<security:authorize access="hasRole('ADMIN')">

		<form:label path="isBanned">

			<spring:message code="actor.isBanned" />

		</form:label>

	Is Banned <input type="checkbox" value="True" />

		<form:errors class="error" path="isBanned" />

		

	</security:authorize>



	<input type="submit" name="save"

		value="<spring:message code="actor.save" />" />; 



	<input type="button" name="cancel"

		value="<spring:message code="message.cancel" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />



	





</form:form>