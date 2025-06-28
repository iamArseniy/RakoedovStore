<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:csrfInput />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/mainpage.css" rel="stylesheet">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
    <title>Ракоедов</title>
    <script src="js/main.js" defer></script>
    <script src="js/scroll.js" defer></script>
</head>
<body>
<nav class="navbar navbar-expand-lg fixed-top ">
    <a class="navbar-brand logo" href="${pageContext.request.contextPath}/mainpage">
        <img src="images/rakoedovlogo_nowhite.png" alt="Логотип" class="img-fluid">
    </a>
    <h1 class="main-label">
        Ракоедов
    </h1>
    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-4">

            <li class="nav-item">
                <a class="nav-link" href="#main">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link " href="#contacts">Контакты</a>
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
<c:set var="_csrf" value="${_csrf}" />
<header id="main" class="header">
    <div class="overlay">
        <div class="container">
            <div class="description text-center">
                <h3>Раки и морепродукты Казань</h3>
                <p>
                    Живые раки, варёные раки. Морепродукты.
                    Композиции и букеты из морепродуктов.
                </p>
                <button  class="btn btn-outline-secondary">Узнать больше</button>
            </div>
        </div>
    </div>
</header>
<div id="product" class="products">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki1.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Букет "Стандарт"</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="price"><strong>Цена: 5000 руб.</strong></p>

                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="1">-1</button>
                            <span class="product-count" id="product-count-1">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="1">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="1">
                            <input type="hidden" name="quantity" id="product-quantity-1" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>

                        <a class="btn btn-info" href="/product?product_id=1">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki2.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Букет "Раки и Лангустины"</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="card-text">Лангустины, 500 гр</p>
                        <p class="price"><strong>Цена: 7500 руб.</strong></p>

                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="2">-1</button>
                            <span class="product-count" id="product-count-2">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="2">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="2">
                            <input type="hidden" name="quantity" id="product-quantity-2" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>

                        <a class="btn btn-info" href="/product?product_id=2">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki3.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Букет "Стандарт с крабом Стригун"</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="card-text">Конечность краба Стригуна</p>
                        <p class="price"><strong>Цена: 5800 руб.</strong></p>

                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="3">-1</button>
                            <span class="product-count" id="product-count-3">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="3">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="3">
                            <input type="hidden" name="quantity" id="product-quantity-3" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>
                        <a class="btn btn-info" href="/product?product_id=3">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki4.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Букет с крабом, раками и лангустинами</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="card-text">Краб волосатик</p>
                        <p class="card-text">Лангустины, 500 гр</p>
                        <p class="price"><strong>Цена: 10000 руб.</strong></p>
                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="4">-1</button>
                            <span class="product-count" id="product-count-4">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="4">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="4">
                            <input type="hidden" name="quantity" id="product-quantity-4" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>
                        <a class="btn btn-info" href="/product?product_id=4">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki5.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Композиция "Раки, Лангустины, Стригун"</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="card-text">Конечность краба Стригуна</p>
                        <p class="card-text">Лангустины 400 гр</p>
                        <p class="price"><strong>Цена: 8000 руб.</strong></p>
                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="5">-1</button>
                            <span class="product-count" id="product-count-5">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="5">+1</button>

                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="5">
                            <input type="hidden" name="quantity" id="product-quantity-5" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>
                        <a class="btn btn-info" href="/product?product_id=5">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki6.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Композиция "Краб, Конечность стригуна, Раки"</h5>
                        <p class="card-text">Свежие раки, 1 кг</p>
                        <p class="card-text">Краб Волосатик</p>
                        <p class="card-text">Конечность стригуна 500 гр</p>
                        <p class="price"><strong>Цена: 9300 руб.</strong></p>
                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="6">-1</button>
                            <span class="product-count" id="product-count-6">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="6">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="6">
                            <input type="hidden" name="quantity" id="product-quantity-6" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>
                        <a class="btn btn-info" href="product?product_id=6">Информация</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-12 mb-4">
                <div class="card">
                    <div class="text-center">
                        <img align="center" src="images/products/raki7.jpeg" class="img-fluid">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Раки варёные</h5>
                        <p class="card-text">Вареные раки (30/40гр), 1 кг</p>
                        <p class="price"><strong>Цена: 1950 руб.</strong></p>
                        <div>
                            <button class="btn btn-secondary decrease-btn" data-target="7">-1</button>
                            <span class="product-count" id="product-count-7">0</span>
                            <button class="btn btn-secondary increase-btn" data-target="7">+1</button>
                        </div>
                        <form action="/cart" method="POST" class="addToCartForm">
                            <input type="hidden" name="product_id" value="7">
                            <input type="hidden" name="quantity" id="product-quantity-7" value="0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="btn btn-primary">В корзину</button>
                        </form>
                        <a class="btn btn-info" href="product?product_id=7">Информация</a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div id="contacts" class="contacts row futuristic-contacts">
    <div class="col-lg-5 col-md-12 text-center contact-left">
        <img src="images/rakoedovlogo.jpg" class="img-fluid contact-logo" alt="Логотип">
    </div>
    <div class="col-lg-7 col-md-12 contact-info">
        <h3>▍Контакты</h3>
        <p>
            <img src="images/phone-icon.png" class="icon" alt="Телефон"> Телефон: <a href="tel:+79003227770">8 (900) 322-77-70</a>
        </p>
        <p>
            <img src="images/telegram-icon.png" class="icon" alt="Telegram"> Telegram: <a href="https://t.me/Rakoedov116" target="_blank">@Rakoedov116</a>
        </p>
        <div class="contact-details-deco"></div>
    </div>
</div>



<jsp:include page="footer.jsp" />
</body>

</html>