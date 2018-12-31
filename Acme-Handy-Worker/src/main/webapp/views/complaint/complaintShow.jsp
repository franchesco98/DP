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


<form:form action="complaint/customer/edit.do" modelAttribute="complaint">

    <form:hidden path="id" />
	<form:hidden path="version" />
	
	
<jstl:if test="${complaint.id!=0}">

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
            <spring:message code="complaint.customer" />:
        </form:label>
        <form:input path="customer.name" readonly="true"/>
        <form:errors cssClass="error" path="customer" />
        <br>
        
 </jstl:if>
    
 <jstl:if test="${complaint.id==0}">
<form:hidden path="ticker" />
<form:hidden path="moment" />
<form:hidden path="customer" />
<form:hidden path="fixUpTask" />
</jstl:if>

		

    <form:label path="fixUpTask.ticker">
            <spring:message code="complaint.fixUpTask" />:
        </form:label>
        <form:input path="fixUpTask.ticker" readonly="true"/>
        <form:errors cssClass="error" path="fixUpTask.ticker" />
        <br>
    
	<jstl:if test="${complaint.referee!=null}">


        <form:label path="referee.name">
            <spring:message code="complaint.referee" />:
        </form:label>
        <form:input path="referee.name" readonly="true"/>
        <form:errors cssClass="error" path="referee.name" />
        <br>
    </jstl:if>
    
    
    <jstl:if test="${complaint.referee==null}">

			<form:hidden path="referee" />
    
    </jstl:if>
 

            <jstl:if test="${toShow}">
          
            <spring:message code="complaint.attachments" />: 
           <jstl:forEach var="i"  items="listAttachments">
			<jstl:out value="${listAttachments}"></jstl:out>
			</jstl:forEach>
            </jstl:if>
            
            <jstl:if test="${!toShow}">
            <form:label path="attachments">
                <spring:message code="complaint.attachments" />:
            </form:label>
            <form:textarea path="attachments" readonly="false" />
            <form:errors cssClass="error" path="attachments" />
            <br>
            
            </jstl:if>
<br/>
    <form:label path="description">
		<spring:message code="complaint.description" />:*
	</form:label>
	<form:textarea path="description" readonly="${toShow}"/>
	<form:errors cssClass="error" path="description" />
    <br>
    

  
	<jstl:if test="${!toShow}">

    <spring:message code="complaint.picturesAdvice" />
      <br/>
    <spring:message code="complaint.advice" />
    <br/>

     </jstl:if>
	
	 
     
    
      
        
          <jstl:if test="${complaint.id==0}">

                <input type="submit" name="save"
                value="<spring:message code="complaint.create" />" />

        </jstl:if>
     
<security:authorize access="hasRole('CUSTOMER')">
	<input type="button" name="cancel"
		value="<spring:message code="complaint.cancel" />"
		onclick="window.location.href =  'fixUpTask/customer/list.do'" />
</security:authorize>


<security:authorize access="hasRole('REFEREE')">
<input type="button" name="cancel"
		value="<spring:message code="complaint.cancel" />"
		onclick="window.location.href =  'complaint/referee/list.do?my=1'" />
</security:authorize>
 <jstl:if test="${complaint.id!=0}">
 
 
 
 
<security:authorize access="hasRole('REFEREE')">

<jstl:if test="${hasReport }">

<input type="button" name="showReport"
		value="<spring:message code="complaint.referee.show.report" />"
		onclick="window.location.href =  'report/referee/show.do?complaintId=${complaint.id }'" />
 
</jstl:if>  

<jstl:if test="${!hasReport && complaint.referee!=null}">

<input type="button" name="createReport"
		value=" <spring:message code="complaint.referee.create.report" />"
		onclick="window.location.href =  'report/referee/create.do?complaintId=${complaint.id }'" />
 
            
</jstl:if>
</security:authorize>


<security:authorize access="hasRole('CUSTOMER')">

<jstl:if test="${hasReport && finalMode }">
 
 <input type="button" name="showReport"
		value="<spring:message code="complaint.referee.show.report" />"
		onclick="window.location.href =  'report/referee/show.do?&complaintId=${complaint.id }'" />

</jstl:if>  

</security:authorize>  


</jstl:if>
    
</form:form>
