<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Страница клиента</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/clientpage.css">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>
<jsp:include page="menu.jsp" />
<div class="container">
    <h1>Информация об аккаунте</h1>
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>Имя</th>
            <td>${client.first_name}</td>
        </tr>
        <tr>
            <th>Телефон</th>
            <td>${client.phone}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${client.email}</td>
        </tr>

    </table>

    <h3>Информация о покупках</h3>
    <p>Количество заказов: ${ordersByClient != null ? ordersByClient.size() : "Заказы не найдены"}</p>


    <a class="logout" href="${pageContext.request.contextPath}/logout">Выйти</a>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
