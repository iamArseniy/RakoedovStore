<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои заказы</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/order.css">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="menu.jsp" />
<div class="container">
    <h1>Мои заказы</h1>

    <c:if test="${not empty ordersByClient}">
        <ul>
            <c:forEach var="order" items="${ordersByClient}">

                <strong>Номер заказа:</strong> ${order.id}<br>
                <strong>Заказ оформлен:</strong> ${order.createdAt}<br>
                <strong>Стадия заказа:</strong> ${stageMap[order.stage.id].name}<br>
                ${stageMap[order.stage.id].description}<br>
                <strong>Товары в заказе:</strong>

                <c:if test="${not empty order.productOrders}">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Название</th>
                            <th>Количество</th>
                            <th>Сумма</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="productOrder" items="${order.productOrders}">
                            <tr>
                                <td>${productOrder.product.name}</td>
                                <td>${productOrder.amount}</td>
                                <td>${productOrder.totalPrice}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2" style="text-align:right;"><strong>Итого:</strong></td>
                            <td><strong>${order.totalPrice}</strong></td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${empty order.productOrders}">
                    <li>Нет товаров в заказе.</li>
                </c:if>

            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty ordersByClient}">
        <p>Заказы не найдены.</p>
    </c:if>

    <a class="menu-button" href="${pageContext.request.contextPath}/mainpage">Меню</a>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
