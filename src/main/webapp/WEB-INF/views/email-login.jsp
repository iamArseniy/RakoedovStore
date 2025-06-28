<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" href="css/email-login.css">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>

<div class="container">
    <h2>Вход</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label>Email:</label>
        <input type="email" name="username" required />
        <label>Пароль:</label>
        <input type="password" name="password" required />
        <button type="submit">Войти</button>
    </form>

    <hr/>

    <form action="${pageContext.request.contextPath}/send-code" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label>Email:</label>
        <input type="email" name="email" required />
        <button type="submit">Отправить код на почту</button>
    </form>

    <div class="link-text">
        Еще нет аккаунта? <a href="${pageContext.request.contextPath}/registration">Зарегистрироваться</a>
    </div>
</div>

</body>
</html>
