<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="finder/handyworker/searchPost.do" modelAttribute="finder">

<form:label path="keyWord">

		<spring:message code="finder.keyword" />

	</form:label>
	
	<form:input path="keyWord" placeholder="'room','fix'" />
	<form:errors cssClass="error" path="keyWord" />
	<br/>
	
	<form:label path="category">

		<spring:message code="finder.category" />

	</form:label>
	
	<form:input path="category" placeholder="'kitchen','concrete work'" />
	<form:errors cssClass="error" path="category" />
	<br/>
	
	<form:label path="warranty">

		<spring:message code="finder.warranty" />

	</form:label>
	
	<form:input path="warranty" placeholder="'title'" />
	<form:errors cssClass="error" path="warranty" />
	<br/>
	
	<form:label path="priceMin">

		<spring:message code="finder.priceMin" />

	</form:label>
	
	<form:input path="priceMin" placeholder="0.0"/>
	<form:errors cssClass="error" path="priceMin" />
	<br/>
	
	<form:label path="priceMax">

		<spring:message code="finder.priceMax" />

	</form:label>
	
	<form:input path="priceMax" placeholder="0.0" />
	<form:errors cssClass="error" path="priceMax" />
	<br/>
		
		<form:label path="dateMin">

		<spring:message code="finder.dateMin" />

	</form:label>
	
	<form:input path="dateMin" placeHolder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="dateMin" />
	<br/>
	
	<form:label path="dateMax">

		<spring:message code="finder.dateMax" />

	</form:label>
	
	<form:input path="dateMax" placeHolder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="dateMax" />
	<br/>
	
	
	<input type="submit" name="save" value="<spring:message code="finder.save" />" />
</form:form>