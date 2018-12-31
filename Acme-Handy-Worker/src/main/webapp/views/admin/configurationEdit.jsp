<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

 
<form:form action="configuration/administrator/edit.do" modelAttribute="config">
	
	<form:hidden path="id" />
	<form:hidden path="version" /> 
	
	
	<form:label path="patternPN">
	<spring:message code="config.patternPN"/>:	
	</form:label>
	<form:input path="patternPN" readonly="${toShow}"/>:
	<form:errors cssClass="error" path="patternPN" />
	<br />
	
	<form:label path="finderCacheTime">
	<spring:message code="config.finderCacheTime"/>:	
	</form:label>
	<form:input path="finderCacheTime" readonly="${toShow}"/>
	<form:errors cssClass="error" path="finderCacheTime" />
	<br />
	
	<form:label path="numberOfResult">
	<spring:message code="config.numberOfResult"/>:
	</form:label>
	<form:input path="numberOfResult" readonly="${toShow}"/>
	<form:errors cssClass="error" path="numberOfResult" />
	<br />
	
    <form:label path="spamWords">
	<spring:message code="config.spamWords"/>:	
	</form:label>
	<form:textarea path="spamWords" readonly="${toShow}" />
	<form:errors cssClass="error" path="spamWords" />
	<br />
	
 	
	
<jstl:if test="${!toShow}">


	<form:label path="positiveWordsE">
	<spring:message code="config.positiveWordsE"/>:	
	</form:label>
	<form:textarea path="positiveWordsE"  />
	<form:errors cssClass="error" path="positiveWordsE" />
	<br />
	
	<form:label path="negativeWordsE">
	<spring:message code="config.negativeWordsE"/>:	
	</form:label>
	<form:textarea path="negativeWordsE" />
	<form:errors cssClass="error" path="negativeWordsE" />
	<br />
	
	<form:label path="positiveWordsS">
	<spring:message code="config.positiveWordsS"/>:	
	</form:label>
	<form:textarea path="positiveWordsS"  />
	<form:errors cssClass="error" path="positiveWordsS" />
	<br />
	
	<form:label path="negativeWordsS">
	<spring:message code="config.negativeWordsS"/>:	
	</form:label>
	<form:textarea path="negativeWordsS" />
	<form:errors cssClass="error" path="negativeWordsS" />
	<br />
	
</jstl:if>	

	
<jstl:if test="${toShow}">	

<spring:message code="config.positiveWordsE"/>:	
<jstl:forEach var="i"  items="listPositiveWordsE">

<jstl:out value="${listPositiveWordsE}"></jstl:out>

</jstl:forEach>
<br/>

<spring:message code="config.negativeWordsE"/>:	
<jstl:forEach var="i"  items="listNegativeWordsE">

<jstl:out value="${listNegativeWordsE}"></jstl:out>

</jstl:forEach>
<br/>

<spring:message code="config.positiveWordsS"/>:	
<jstl:forEach var="i"  items="listPositiveWordsS">

<jstl:out value="${listPositiveWordsS}"></jstl:out>

</jstl:forEach>
<br/>

<spring:message code="config.negativeWordsS"/>:	
<jstl:forEach var="i"  items="listNegativeWordsS">

<jstl:out value="${listNegativeWordsS}"></jstl:out>

</jstl:forEach>
<br/>

</jstl:if>	


	<form:label path="nameSystem">
	<spring:message code="config.nameSystem"/>:	
	</form:label>
	<form:input path="nameSystem" readonly="${toShow}" />
	<form:errors cssClass="error" path="nameSystem" />
	<br />
	
	<form:label path="banner">
	<spring:message code="config.banner"/>:	
	</form:label>
	<form:input path="banner" readonly="${toShow}" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="welcomeMessageE">
	<spring:message code="config.welcomeMessageE"/>:	
	</form:label>
	<form:textarea path="welcomeMessageE" readonly="${toShow}" />
	<form:errors cssClass="error" path="welcomeMessageE" />
	<br />
	
	<form:label path="welcomeMessageS">
	<spring:message code="config.welcomeMessageS"/>:	
	</form:label>
	<form:textarea path="welcomeMessageS" readonly="${toShow}" />
	<form:errors cssClass="error" path="welcomeMessageS" />
	<br />
	
	<form:label path="VAT">
	<spring:message code="config.VAT"/>:	
	</form:label>
	<form:input path="VAT" readonly="${toShow}" />
	<form:errors cssClass="error" path="VAT" />
	<br />
	
	<form:label path="countryCode">
	<spring:message code="config.countryCode"/>:	
	</form:label>
	<form:input path="countryCode" readonly="${toShow}" />
	<form:errors cssClass="error" path="countryCode" />
	<br />
	
	<form:label path="creditCardMakes">
	<spring:message code="config.creditCardMakes" />:	
	</form:label>
	<form:textarea path="creditCardMakes" readonly="${toShow}"/>
	<form:errors cssClass="error" path="creditCardMakes" />
	<br />
	
	
	<spring:message code="config.warning"/>	
	<br/>
	
<jstl:if test="${toShow}">
	
	<input type="button" name="edit"

		value="<spring:message code="config.edit" />"

		onclick="javascript: relativeRedir('configuration/administrator/list.do?edit=1');" />
	
</jstl:if>	


<jstl:if test="${!toShow}">
	
	<input type="submit" name="save" value="<spring:message code="config.save" />" /> 
	
</jstl:if>		

	
<input type="button" name="cancel"

		value="<spring:message code="config.cancel" />"

		onclick="javascript: relativeRedir('#');" />
	
</form:form>
	