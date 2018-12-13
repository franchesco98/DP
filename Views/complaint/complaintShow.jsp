<%--

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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<form:form action="complaint/edit.do" modelAttribute="complaint">

    

<jstl:if test="complaint.id!=0">

        <form:label path="ticker">
            <spring:message code="complaint.ticker" />:
        </form:label>
        <form:input path="ticker" readonly="true"/>
        <form:errors cssClass="error" path="ticker" />
        <br>

        <form:label path="moment">
            <spring:message code="complaint.moment" />:
        </form:label>
        <form:input path="moment" readonly="true"/>
        <form:errors cssClass="error" path="moment" />
        <br>


        <form:label path="customer">
            <spring:message code="complaint.customer.name" />:
        </form:label>
        <form:input path="customer" readonly="true"/>
        <form:errors cssClass="error" path="customer" />
        <br>
        
 </jstl:if>
    
 <jstl:if test="${complaint.id==0}">
<form:hidden path="ticker" />
<form:hidden path="moment" />
<form:hidden path="customer" />

</jstl:if>

		

    <form:label path="fixUpTask.ticker">
            <spring:message code="complaint.fixUpTask" />:
        </form:label>
        <form:input path="fixUpTask.ticker" readonly="true"/>
        <form:errors cssClass="error" path="ticker" />
        <br>
    
	<jstl:if test="${complaint.referee!=null}">

	<spring:message code="complaint.referee"/>:
	<jstl:out value="${complaint.referee.name}" />
    <br>
    
    </jstl:if>

            <jstl:if test="${complaint.id!=0}">
            <jstl:set var="attachments" value="${fn:split(c1.attachments, ';')}" />
        
            <spring:message code="complaint.attachments" />: 
            <jstl:forEach items="${attachments}" var="attachment">
                <jstl:out value="${attachment}" /> <br>
            </jstl:forEach>
            </jstl:if>
            
            <jstl:if test="${complaint.id==0}">
            <form:label path="attachments">
                <spring:message code="complaint.attachments" />:
            </form:label>
            <form:textarea path="attachments" readonly="${toShow}" />
            <form:errors cssClass="error" path="attachments" />
            <br>
            
            </jstl:if>

    <form:label path="description">
		<spring:message code="complaint.description" />:*
	</form:label>
	<form:textarea path="description" readonly="${toShow}"/>
	<form:errors cssClass="error" path="description" />
    <br>
    

  
	<jstl:if test=${!toShow}"">

    <spring:message code="complaint.picturesAdvice" />
    <spring:message code="complaint.advice" />

     </jstl:if>
	
	 
     
    
        <jstl:if test="${complaint.id!=0}">

                <input type="submit" name="createComplaint"
                value="<spring:message code="complaint.create" />" />

        </jstl:if>
     

	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="window.location.href = 'controller/complaintController/list.do'" />

    
</form:form>