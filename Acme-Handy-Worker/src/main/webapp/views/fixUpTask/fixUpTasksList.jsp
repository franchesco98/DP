
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table
	pagesize="5" name="fixUpTasks" id="row"
	requestURI="${requestURI }" >

	<display:column property="ticker" titleKey="fixUpTask.ticker" />

	<display:column property="moment"  titleKey="fixUpTask.moment" sortable="true"  format="{0,date,dd/MM/yyyy HH:mm}" />
		

	<display:column   titleKey="fixUpTask.description" >
		<jstl:out value="${row.description}" />
	</display:column>

	<display:column   titleKey="fixUpTask.address" >
		<jstl:out value="${row.address}" />
	</display:column>

	<display:column   titleKey="fixUpTask.maxPrice" >
		<jstl:out value="${row.maxPrice}" />(${(row.maxPrice*vat)/100})
	</display:column>

	<display:column>
		<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id }&edit=0"><spring:message code="fixUpTask.show" /></a>
	</display:column>


<security:authorize access="hasRole('CUSTOMER')">

    <display:column>
		<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id }&edit=1"><spring:message code="fixUpTask.edit" /></a>
	</display:column>

	<display:column>
		<a href="application/customer/list.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.application" /></a>
	</display:column>


 </security:authorize>

     <security:authorize access="hasRole('HANDYWORKER')">

		<display:column>
				<a href="application/handyworker/create.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.application.create" /></a>
			</display:column>

	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">

		<display:column>
				<a href="complaint/customer/create.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.complaint.create" /></a>
			</display:column>


			<display:column>
				<a href="complaint/customer/list.do?fixUpTaskId=${row.id}"><spring:message code="fixUpTask.complaints" /></a>
			</display:column>

	</security:authorize>

</display:table>


<security:authorize access="hasRole('CUSTOMER')">

<input type="button" name="Create"

		value="<spring:message code="fixUpTask.create" />"

		onclick="javascript: relativeRedir('fixUpTask/customer/create.do');" />


</security:authorize>
