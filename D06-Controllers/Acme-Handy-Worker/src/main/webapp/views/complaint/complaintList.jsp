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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>



<display:table
	pagesize="5" name="complaints" id="row" 
	requestURI="ApplicationController/list" >
	
	<display:column property="ticker" titleKey="complaint.ticker" />
	
	<display:column property="moment" titleKey="complaint.moment"/>
	
	<display:column>
		<a href="complaint/edit.do?complaintId=*">
			<spring:message code="complaint.show" />
		</a>
    </display:column>
    
    <security:authorize access="REFEREE">

    <jstl:if test="complaint.referee==null">

        <display:column>
            <a href="complaint/edit.do?referee=1&complaintId=*">
                <spring:message code="complaint.referee.assign" />
            </a>
        </display:column>

    </jstl:if>

</security:authorize>
  
</display:table>