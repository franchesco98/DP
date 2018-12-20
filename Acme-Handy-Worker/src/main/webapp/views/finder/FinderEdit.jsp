

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>

<script>
	$(function() {
		$("#datepicker1").datepicker();
	});
</script>

 <security:authorize access="hasRole('HANDYWORKER')">

<form:form action="finder/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTasks" />
	<form:hidden path="lastUpdate" />

	


	
	
	
	<form:label path="keyWord">
		<spring:message code="finder.keyWord" />:
	</form:label>
	<form:input path="keyWord"/>
	<form:errors cssClass="error" path="keyWord" />
	<br />
	
	<form:label path="category">
		<spring:message code="finder.category" />:
	</form:label>

	<form:input path="category"/>
	<form:errors cssClass="error" path="category" />
	<br />


	<form:label path="warranty">
		<spring:message code="finder.warranty" />:
	</form:label>
	<form:input path="warranty" />
	<form:errors cssClass="error" path="warranty" />
	<br />

	<form:label path="priceMin">
		<spring:message code="finder.priceMin" />:
	</form:label>
	<form:input path="priceMin" placeholder="0.00" />
	<form:errors cssClass="error" path="priceMin" />
	<br />

	<form:label path="priceMax">
		<spring:message code="finder.priceMax" />:
	</form:label>
	<form:input path="priceMax" placeholder="0.00" />
	<form:errors cssClass="error" path="priceMax" />
	<br />

	<form:label path="dateMin">
		<spring:message code="finder.dateMin" />:
	</form:label>
	<form:input id="datepicker" path="dateMin" placeholder="yyyy-mm-dd" />
	<br />

	<form:label path="dateMax">
		<spring:message code="finder.dateMax" />:
	</form:label>
	<form:input id="datepicker1" path="dateMax" placeholder="yyyy-mm-dd" />
	<br />


<jstl:if test="${isInCache}">

		<a href="controller/fiUpTask/list.do?finderId=${finder.id }"><spring:message code="finder.showCache" /></a>

</jstl:if>

	<input type="submit" name="save"
		value="<spring:message code="finder.save" />" />



	<input type="button" name="cancel"
		value="<spring:message code="finder.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
		
</form:form>
</security:authorize>