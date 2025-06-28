<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg fixed-top ">
    <a class="navbar-brand logo" href="${pageContext.request.contextPath}/mainpage">
        <img src="images/rakoedovlogo_nowhite.png" alt="Логотип" class="img-fluid">
    </a>
    <h1 class="main-label">
        Ракоедов
    </h1>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/mainpage">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="${pageContext.request.contextPath}/mainpage">Контакты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="${pageContext.request.contextPath}/order">Заказы</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">Корзина</a>
            </li>
            <li class="nav-item">
                <a class="phone nav-link" href="tel:+79003227770">+7 (900) 322-77-70</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">
                    <div class="icon-container">
                        <img src="images/cart-icon.png" alt="Корзина" class="img-fluid w-60">
                    </div>
                </a>
            </li>
            <li class="nav-item">
                <div class="icon-container-profile">
                    <a href="${pageContext.request.contextPath}/clientPage">
                        <img src="images/profile.png" alt="Профиль" class="img-fluid">
                    </a>
                </div>

            </li>
        </ul>

    </div>
</nav>
