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




<display:table
	pagesize="5" name="fixUpTasks" id="row" 
	requestURI="ApplicationController/list" >
	
	<display:column property="moment" titleKey="application.moment" />


	<display:column   titleKey="application.status" >
		<jstl:out value="${row.status}" />
	</display:column>
	
	<display:column   titleKey="application.offeredPrice" >
		<jstl:out value="${row.offeredPrice}" />
	</display:column>
	
	<display:column   titleKey="application.handyWorkerComments" >
	
			<a href="controller/application/show.do?customer=0&fixUpTaskId=${row.id}">
  			<spring:message code="application.show" />
  			</a>
	
			<security:authorize access="hasRole('HANDYWORKER')">
				<jstl:if test="${row.status}== 'PENDING'">
					<jstl:out value="|" />
  					<a href="controller/application/edit.do?customer=0&fixUpTaskId=${row.id}">
  					<spring:message code="application.edit" />
  					</a>
				</jstl:if>
			</security:authorize>	
	
	</display:column>
	
	<display:column   titleKey="application.customerComments" >
			<a href="controller/application/show.do?customer=1&fixUpTaskId=${row.id}">
  			<spring:message code="application.show" />
  			</a>
	
			<security:authorize access="hasRole('CUSTOMER')">
				<jstl:if test="${row.status}== 'PENDING'">
					<jstl:out value="|" />
  					<a href="controller/application/edit.do?customer=1&fixUpTaskId=${row.id}">
  					<spring:message code="application.edit" />
  					</a>
				</jstl:if>
			</security:authorize>	
	</display:column>
	
	<%--Columna para el boton de aceptar application, que solo se les mostrará a los costumers--%>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column   titleKey="" >
			<jstl:if test="${row.status}== 'PENDING'">

  			<a href="controller/application/edit.do?accept=1fixUpTaskId=${row.id}">
  			<spring:message code="application.accept" />
  			</a>

			</jstl:if>
		</display:column>
	</security:authorize>	
	
<%--	
	<display:column>
		<a href="controller/fiUpTask/edit.do?fixUpTaskId=${row.id }&edit=0"><spring:message code="fixUpTask.show" /></a>
	</display:column>
--%>
	
	<%--Columna para el boton de aceptar application, que solo se les mostrará a los costumers--%>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column   titleKey="" >
			<jstl:if test="${row.status}== 'PENDING'">

  			<a href="controller/application/edit.do?accept=0fixUpTaskId=${row.id}">
  			<spring:message code="application.reject" />
  			</a>

			</jstl:if>
		</display:column>
	</security:authorize>	

</display:table>

<br />
<br />

<security:authorize access="hasRole('CUSTOMER')">
	
  				<a href="fixUpTask/list.do">
  				<spring:message code="application.back" />
  				</a>
		
</security:authorize>	
		
		
		
