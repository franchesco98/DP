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


<form:form action="${rol}/edit.do" modelAttribute="${rol}">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="boxes" />
	
	
	



<%--meter esta variable en el controlador --%>
	<jstl:choose>
    <jstl:when test="${rol} == 'admin'">
       
	<form:label path="isBanned">
	<spring:message code="actor.isBanned" />:
	</form:label>
	<form:checkbox  path="isBanned"  />
	<br />
	
	<form:label path="isSuspicious">
	<spring:message code="actor.isSuspicious" />:
	</form:label>
	<form:checkbox  path="isSuspicious"  />
	<br />

    </jstl:when>    
    <jstl:otherwise>
       
       <form:hidden path="isBanned" />
       <form:hidden path="isSuspicious" />
        
    </jstl:otherwise>
</jstl:choose>

    
    
    
    
	
	<form:label path="name">
	<spring:message code="actor.name" />:
	</form:label>
	<form:input  path="name" readonly="${toShow}" />
	<form:errors cssClass="error" path="name" />
	<br />

    <form:label path="middleName">
	<spring:message code="actor.middleName" />:
	</form:label>
	<form:input  path="middleName"  readonly="${toShow}" />
	<form:errors cssClass="error" path="middleName" />
	<br />
	
	<form:label path="surname">
	<spring:message code="actor.surname" />:
	</form:label>
	<form:input  path="surname" readonly="${toShow}"  />
	<form:errors cssClass="error" path="surname" />
	<br />
	
	<form:label path="photo">
	<spring:message code="actor.photo" />:
	</form:label>
	<form:input  path="photo" readonly="${toShow}"  />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="email">
	<spring:message code="actor.email" />:
	</form:label>
	<form:input  path="email" readonly="${toShow}" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phoneNumber">
	<spring:message code="actor.phoneNumber" />:
	</form:label>
	<form:input  path="phoneNumber" readonly="${toShow}" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br />
	
	<form:label path="address">
	<spring:message code="actor.address" />:
	</form:label>
	<form:input  path="address" readonly="${toShow}" />
	<form:errors cssClass="error" path="address" />
	<br />

    <form:label path="userAccount.username">
	<spring:message code="actor.userName" />:
	</form:label>
	<form:input  path="userAccount.username" readonly="${toShow}"  />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	
	<form:label path="userAccount.password">
	<spring:message code="actor.password" />:
	</form:label>
	<form:password showPassword="true"  path="userAccount.password" readonly="${toShow}"  />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />

<jstl:if test="${rol}=='handyWorker'">

    <form:label path="make">
	<spring:message code="actor.make" />:
	</form:label>
	<form:input  path="make" readonly="${toShow}"  />
	<form:errors cssClass="error" path="make" />
	<br />

</jstl:if>


<security:authorize access="isAnonymous()">

	
<jstl:if test="${!toShow}}">
<input type="submit" name="create" value="<spring:message code="actor.create" />" /> 
</jstl:if>

</security:authorize>



<security:authorize access="isAuthenticated()">

<jstl:if test="${rol=='customer' || rol=='handyWorker'}">

	<form:label path="score">
	<spring:message code="actor.score" />:
	</form:label>
	<form:input  path="score" readonly="true" />
	<form:errors cssClass="error" path="score" />
	<br />

</jstl:if>
</security:authorize>


<jstl:if test="${!toShow}">


<input type="submit" name="save" value="<spring:message code="actor.save" />" />


</jstl:if>



<input type="button" name="cancel"

		value="<spring:message code="actor.cancel" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />



</form:form>