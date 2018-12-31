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


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<h3><jstl:out value="${box.name}" /></h3>

<display:table
	pagesize="4" name="messagesToList" id="row" 
	requestURI="${requestURI}" >
	
	<display:column property="moment" titleKey="message.moment" sortable="true"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	
	<display:column   titleKey="message.sender">
  				<jstl:out value="${row.sender.name}" /> <jstl:out value="${row.sender.surname}" /> <jstl:out value="(${row.sender.userAccount.username})" /> 
	</display:column>
	
		<display:column   titleKey="message.priority">
  				<jstl:out value="${row.priority}" /> 
	</display:column>
	
	<display:column   titleKey="message.subjet">
  				<jstl:out value="${row.subject}" /> 
	</display:column>
	

	<display:column   titleKey="spaces" >
  					<a href="message/edit.do?boxId=${box.id}&messageId=${row.id}">
  					<spring:message code="message.show" />
  					</a>
	</display:column>
	
	<display:column   titleKey="spaces" >
  					<a href="message/move.do?boxId=${box.id}&messageId=${row.id}">
  					<spring:message code="message.move" />
  					</a>
	</display:column>
		
	

</display:table>

<br />

<a href="box/list.do">
  	<spring:message code="message.back" />
</a>

		
		
		
