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


<display:table
	pagesize="8" name="boxes" id="row" 
	requestURI="BoxController/list" >
	
	<display:column property="name" titleKey="box.name" />

<%-- Columna para mostrar link para editar la box, solo si no es una box del sistema--%>
	<display:column   titleKey="spaces" >
				<jstl:if test="${row.systemBox== 'false'}">
  					<a href="controller/box/edit.do?boxId=${row.id}">
  					<spring:message code="box.edit" />
  					</a>
				</jstl:if>
	</display:column>
	
	
<%-- Columna para mostrar link para ver los messages de una box--%>
	<display:column   titleKey="spaces" >
				<jstl:if test="${fn:length(row.messages) gt 0}">
  					<a href="controller/message/list.do?boxId=${row.id}">
  					<spring:message code="box.showMessages" />
  					</a>
				</jstl:if>
	</display:column>
		
	

</display:table>

<br />

<a href="#">
  	<spring:message code="box.back" />
</a>

<br/>
<br />		
  			
<input type="button" name = "createBox" value ="<spring:message code = "box.create"/>" onclick="window.location.href = 'controller/boxController/create.do'">

		
		
		
