<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/registration.css" rel="stylesheet">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center">Регистрация</h1>

    <form action="registration" method="POST" class="mt-4">
        <sec:csrfInput />

        <div class="mb-3">
            <label for="firstName" class="form-label">Имя:</label>
            <input type="text" id="firstName" name="firstName" class="form-control" value="${param.firstName}">
            <c:if test="${not empty errorFirstName}">
                <span class="text-danger">${errorFirstName}</span>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="lastName" class="form-label">Фамилия:</label>
            <input type="text" id="lastName" name="lastName" class="form-control" value="${param.lastName}">
            <c:if test="${not empty errorLastName}">
                <span class="text-danger">${errorLastName}</span>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Телефон:</label>
            <input type="text" id="phone" name="phone" class="form-control" value="${param.phone}">
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input type="email" id="email" name="email" class="form-control" value="${param.email}">
            <c:if test="${not empty errorEmail}">
                <span class="text-danger">${errorEmail}</span>
            </c:if>
        </div>

        <div class="mb-3">
            <label for="password" class="form-label">Пароль:</label>
            <input type="password" id="password" name="password" class="form-control">
            <c:if test="${not empty errorPassword}">
                <span class="text-danger">${errorPassword}</span>
            </c:if>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">${errorMessage}</div>
        </c:if>

        <div class="button-container">
            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
        </div>

    </form>
    <p class="text-center already-registered">
        Уже зарегистрированы? <a href="${pageContext.request.contextPath}/login" class="text-danger fw-bold link-login">Вход</a>.
    </p>
</div>
</body>
</html>
