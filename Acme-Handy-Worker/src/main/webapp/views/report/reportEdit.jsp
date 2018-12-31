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


<form:form action="report/referee/edit.do" modelAttribute="report">

    <form:hidden path="id" />
	<form:hidden path="version" />
	

        <form:label path="complaint">
            <spring:message code="report.complaint" />:
        </form:label>
        <form:input path="complaint" readonly="true" />
        <form:errors cssClass="error" path="complaint" />
        <br>


 <jstl:if test="${report.id!=0}">

         <form:label path="writtenMoment">
            <spring:message code="report.writtenMoment" />:
        </form:label>
        <form:input path="writtenMoment" readonly="true"/>
        <form:errors cssClass="error" path="writtenMoment" />
        <br>
        
 </jstl:if>
  

  
 <jstl:if test="${report.id==0}">
<form:hidden path="writtenMoment" />
</jstl:if>


		
			 <form:label path="description">
					<spring:message code="report.description" />:
			</form:label>
			<form:textarea path="description" readonly="${toShow}"/>
			<form:errors cssClass="error" path="description" />
		    <br>
     
            <form:label path="attachment">
                <spring:message code="report.attachment" />:
            </form:label>
            <form:textarea path="attachment" readonly="${toShow}" />
            <form:errors cssClass="error" path="attachment" />
            <br>
            
            
            <security:authorize access="hasRole('REFEREE')">	
      		<form:label path="isFinal">
                <spring:message code="report.isFinal" />:
            </form:label>
             <form:select path="isFinal">
                <form:option value="${true}"><spring:message code="report.yes" /></form:option>
                <form:option value="${false}"><spring:message code="report.no" /></form:option>
            </form:select>
            <br>
            
         	</security:authorize>

 			<security:authorize access="hasAnyRole('CUSTOMER','HANDYWORKER')">
   			 <form:hidden path="isFinal" />
   			</security:authorize>
	
 
<security:authorize access="hasRole('REFEREE')">
  
 <jstl:if test="${report.id==0}">

<input type="submit" name="save" value="<spring:message code="report.create" />" /> 
</jstl:if>

<jstl:if test="${report.id!=0 &&  toShow && !report.isFinal}">
<input type="button" name="edit"
		value="<spring:message code="report.edit" />"
		onclick="javascript: relativeRedir('report/referee/edit.do?reportId=${report.id}');" />
</jstl:if>			

<jstl:if test="${report.id!=0 &&  !toShow}">
   <input type="submit" name="save" value="<spring:message code="report.save" />" /> 

	
	
<input type="submit" name="delete"
			value="<spring:message code="report.delete" />"
			onclick="return confirm('<spring:message code="report.confirm.delete" />')" />
</jstl:if>

<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: relativeRedir('complaint/referee/list.do?my=1');" />
			
</security:authorize>

<jstl:if test="${toShow && report.id!=0}">
<input type="button" name="notes"
		value="<spring:message code="report.note" />"
		onclick="javascript: relativeRedir('note/referee,customer,handyworker/list.do?reportId=${report.id}');" />

<input type="button" name="createNote"
		value="<spring:message code="report.note.create" />"
		onclick="javascript: relativeRedir('note/referee,customer,handyworker/create.do?reportId=${report.id}');" />
</jstl:if>

<security:authorize access="hasRole('CUSTOMER')">		 
<input type="button" name="cancel"
		value="<spring:message code="report.cancel" />"
		onclick="javascript: relativeRedir('complaint/customer,referee/show.do?&complaintId=${report.complaint.id}');" />		 
</security:authorize>  
    
</form:form>
