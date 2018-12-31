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

<link rel="stylesheet" href="styles/statusApplication.css" type="text/css"></link>



<display:table
	pagesize="5" name="applications" id="row"
	requestURI="${requestURI }" >

	<display:column property="moment" titleKey="application.moment" sortable="true"  format="{0,date,dd/MM/yyyy HH:mm}"/>

<jstl:set var="statusColor" value="${row.status}"></jstl:set>
<jstl:if test="${greyColor && row.status== 'PENDING'}">
<jstl:set var="statusColor" value="expired"></jstl:set>
</jstl:if>

	<display:column   titleKey="application.status" class="${statusColor}">
		<jstl:out value="${row.status}" />
	</display:column>




	<display:column   titleKey="application.offeredPrice" >
		<jstl:out value="${row.offeredPrice}" />(${(row.offeredPrice*vat)/100})
	</display:column>

	<display:column   titleKey="application.handyWorkerComments" >

			<a href="controller/application/show.do?customer=0&fixUpTaskId=${row.id}">
  			<spring:message code="application.show" />
  			</a>

			<security:authorize access="hasRole('HANDYWORKER')">
				<jstl:if test="${row.status == 'PENDING'}">
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
				<jstl:if test="${row.status== 'PENDING'}">
					<jstl:out value="|" />
  					<a href="application/edit.do?customer=1&fixUpTaskId=${row.id}">
  					<spring:message code="application.edit" />
  					</a>
				</jstl:if>
			</security:authorize>
	</display:column>

	<%--Columna para el boton de aceptar application, que solo se les mostrar� a los costumers--%>
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column >
			<jstl:if test="${!greyColor && row.status == 'PENDING'}">

  			<a href="application/customer/edit.do?applicationId=${row.id}&accept=1">
  			<spring:message code="application.accept" />
  			</a>

			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column   titleKey="application.actions" >

			<security:authorize access="hasRole('HANDYWORKER')">
				<jstl:if test="${row.status == 'ACCEPTED'}">
  					<a href="phase/handyworker/create.do?fixUpTaskId=${row.fixUpTask.id}">
  					<spring:message code="application.phase" />
  					</a>
				</jstl:if>
			</security:authorize>

	</display:column>

<%--
	<display:column>
		<a href="controller/fiUpTask/edit.do?fixUpTaskId=${row.id }&edit=0"><spring:message code="fixUpTask.show" /></a>
	</display:column>
--%>

	<%--Columna para el boton de aceptar application, que solo se les mostrar� a los costumers--%>
	<security:authorize access="hasRole('CUSTOMER')">

		<display:column >
			<jstl:if test="${!greyColor && row.status== 'PENDING' }">

  			<a href="application/customer/edit.do?applicationId=${row.id}&accept=0">
  			<spring:message code="application.reject" />
  			</a>

			</jstl:if>
		</display:column>

	</security:authorize>

</display:table>

<br />
<br />

<security:authorize access="hasRole('CUSTOMER')">

  				<a href="fixUpTask/customer/list.do">
  				<spring:message code="application.back" />
  				</a>

</security:authorize>
