<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealsDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>
        <br/>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meals">
                <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.Meal"/>
                <tr>
                    <td>fn:fun${meals.dateTime}</td>
                    <td>${meals.description}</td>
                    <td>${meals.calories}</td>
                    <td><input type="checkbox"
                               <c:if test="${user.enabled}">checked</c:if> id="${user.id}"/></td>
                    <td><fmt:formatDate value="${user.registered}" pattern="dd-MMMM-yyyy"/></td>
                    <td><a><span class="fa fa-pencil"></span></a></td>
                    <td><a class="delete" id="${user.id}"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>