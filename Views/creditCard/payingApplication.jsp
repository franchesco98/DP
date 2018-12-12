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


<script type="text/javascript">
function switchDivs(){
	
	var div1 = document.getElementById('chooseCD');
	var div2 = document.getElementById('createCD');
	
	if (div2.style.display == "none") {
		div1.style.display = "none";
		div2.style.display = "block";
	} else {
		div1.style.display = "block";
		div2.style.display = "none";
	}

}
</script>

<spring:message code="payingApplication.title" />

<input type="button" id="boton" value="Click aqu�" onclick="mostrar()">

<div id="ChooseCD">
<form:form action="" modelAttribute="creditCard">


    <form:label path="number">
		<spring:message code="" />:
	</form:label>
    
    <form:select path="number">
    	 <form:options items="${hisCreditCards}" />
   	</form:select>


	<input type="submit" name="save" value="<spring:message code="" />" />; 



	<input type="button" name="cancel"

		value="<spring:message code="" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />


</form:form>
</div>





<div id="CreateCD" style="display: none;">
<form:form action="creditCard/create.do" modelAttribute="creditCard">

    <form:label path="name">
		<spring:message code="creditCard.name" />:
	</form:label>
		<form:input  path="name"/>
		<form:errors cssClass="error" path="name" />
	<br />

    <form:label path="brandName">
		<spring:message code="creditCard.brandName" />:
	</form:label>
    
    <form:select path="brandName">
    	 <form:options items="${creditCardBrands}" />
   	</form:select>
   	<br />

  <form:label path="number">
		<spring:message code="creditCard.number" />:
	</form:label>
		<form:input  path="number"/>
		<form:errors cssClass="error" path="name" />
	<br />
	
  	<form:label path="expirationMonth">
		<spring:message code="creditCard.expirationMonth" />:
	</form:label>
		<form:input  path="expirationMonth" placeholder="mm"/>
		<form:errors cssClass="error" path="expirationMonth" />
	<br />
	
    <form:label path="expirationYear">
		<spring:message code="creditCard.expirationYear" />:
	</form:label>
		<form:input  path="expirationYear" placeholder="YY"/>
		<form:errors cssClass="error" path="expirationYear" />
	<br />
	
  <form:label path="CVVCode">
		<spring:message code="creditCard.CVVCodeCVVCode" />:
	</form:label>
		<form:input  path="CVVCode"/>
		<form:errors cssClass="error" path="CVVCode" />
	<br />			

	<input type="submit" name="save" value="<spring:message code="creditCard.save" />" />; 

	<input type="button" name="cancel"

		value="<spring:message code="creditCard.cancel" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />


</form:form>
	
</div>























<%--Meter en el controlador de donde venga la fixUpTask asociada y el HW asociado en el controlador si hay que crearla (si es para editarla ya estara introducido)--%>


<%--Vemos el rol del usuario logueado para poner un titulo u otro--%>


	<security:authorize access="hasRole('HANDYWORKER')">

		<spring:message code="create" />:
		<br />
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">

		<spring:message code="update" />:
		<br />
	</security:authorize>
	
<%--FixUpTask relacionada--%>

		<spring:message code="relatedFUT" />
		<jstl:out value="${application.fixUpTask.ticker}" /> 
		
		
		
<form:form action="application/edit.do" modelAttribute="application">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="creditCard" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice" />
	<form:hidden path="customerComments" />

	

    <%--Precio, si es un Customer no podrÃ¡ editarlo--%>
    <form:label path="offeredPrice">
		<spring:message code="application.offeredPrice" />:
	</form:label>
		<form:input  path="offeredPrice"  placeholder = "0.00"/>
		<form:errors cssClass="error" path="offeredPrice" />
	<br />
    
    <%--Comentario del HW, si es un Customer no podrÃ¡ editarlo--%>
    <form:label path="handyWorkerComments">
		<spring:message code="application.handyWorkerComments" />:*
	</form:label>
		<form:textarea  path="handyWorkerComments"  />
		<form:errors cssClass="error" path="handyWorkerComments" />
	<br />
    
    <spring:message code="application.commentAdvice" />
    
  


	<input type="submit" name="save" value="<spring:message code="application.save" />" />; 



	<input type="button" name="cancel"

		value="<spring:message code="application.cancel" />"

		onclick="javascript: relativeRedir('welcome/index.do');" />



</form:form>

	