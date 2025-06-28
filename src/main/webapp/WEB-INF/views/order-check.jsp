<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Чек заказа #${order.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-check.css" />
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>
<h1>Чек заказа №${order.id}</h1>
<fmt:formatDate value="${order.createdAt}" pattern="dd.MM.yyyy HH:mm"/>

<p>Клиент: ${order.client.first_name}</p>

<table border="1" cellpadding="5">
    <thead>
    <tr>
        <th>Товар</th>
        <th>Количество</th>
        <th>Цена за шт.</th>
        <th>Сумма</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${productOrders}">
        <tr>
            <td>${item.product.name}</td>
            <td>${item.amount}</td>
            <td>${item.product.price}</td>
            <td>${item.totalPrice}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Итого: ${order.totalPrice}</h3>


<a href="${pageContext.request.contextPath}/order/check/${order.id}/download-pdf" target="_blank">
    Скачать чек (PDF)
</a>

<a href="${pageContext.request.contextPath}/mainpage">
    Вернуться в меню
</a>

</body>
</html>
