<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .footer {
        background-color: #ffffff;
        color: #000000;
        border-top: 1px solid #e0e0e0;
        padding: 20px 0;
    }

    .footer-container {
        padding: 0 50px;
    }

    .footer-logo {
        width: 40px;
        height: auto;
        vertical-align: middle;
        margin-right: 10px;
    }

    .footer-title {
        font-family: 'Brush Script MT', 'Verdana', sans-serif;
        font-size: 24px;
        vertical-align: middle;
    }

    .footer p, .footer a {
        font-size: 14px;
        margin-bottom: 5px;
        color: #000000;
        text-decoration: none;
    }

    .footer a:hover {
        color: #f97300;
        text-decoration: underline;
    }

    @media (max-width: 768px) {
        .footer-container {
            padding: 0 20px;
        }

        .footer .text-right,
        .footer .text-left {
            text-align: center !important;
        }

        .footer-title {
            display: block;
            margin-top: 10px;
        }
    }
</style>

<footer class="footer mt-auto py-3">
    <div class="container-fluid footer-container">
        <div class="row align-items-center justify-content-between">
            <div class="col-md-6 text-left footer-brand">
                <img src="images/rakoedovlogo_nowhite.png" alt="Логотип" class="footer-logo">
                <span class="footer-title">Ракоедов</span>
            </div>
            <div class="col-md-6 text-right">
                <p class="mb-0">Тел: <a href="tel:+79003227770">+7 (900) 322-77-70</a></p>
                <p class="mb-0">&copy; 2025 Ракоедов. Все права защищены.</p>
            </div>
        </div>
    </div>
</footer>
