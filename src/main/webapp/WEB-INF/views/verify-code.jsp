<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Подтвердите код</title>
    <link rel="stylesheet" href="css/verify-code.css">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>

<div class="container">
    <h2>Введите код из email</h2>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/verify-code" method="post">
        <sec:csrfInput />
        <input type="hidden" name="email" value="${email}" />
        <label>Код:</label>
        <input type="text" name="code" required maxlength="6"/>
        <button type="submit">Подтвердить</button>
    </form>

    <div class="link-text">
        Не получили код? <a href="${pageContext.request.contextPath}/send-code">Отправить заново</a>
    </div>
</div>

</body>
</html>
