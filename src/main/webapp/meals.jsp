<%--
  Created by IntelliJ IDEA.
  User: Alexey
  Date: 29.06.2018
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
    <style>
        tr[exceed="false"] {
            color: green;
        }

        tr[exceed="true"] {
            color: red;
        }
    </style>
</head>
<body>
<div>
    <h2>Meal of user</h2>
</div>
<div>
    <h3><a href="index.html">Home</a></h3>
</div>
<div>
    <button type="button" ><a href="meals?action=create">Добавить</a></button>
</div>
<br>
<div>
    <table cellspacing="2" border="1" cellpadding="5">
        <tr>
            <td>Время</td>
            <td>Еда</td>
            <td>Каллории</td>
            <td>Изменить</td>
            <td>Удалить</td>
        </tr>
        <c:forEach items="${meals}" var="meals">
            <jsp:useBean id="meals" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr exceed="${meals.exceed}">
                <td>${fn:replace(meals.dateTime, 'T', ' ')}</td>
                <td>${meals.description}</td>
                <td>${meals.calories}</td>
                <td><a href="meals?action=update&id=${meals.id}">Изменить</a></td>
                <td><a href="meals?action=delete&id=${meals.id}">Удалить</a></td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
