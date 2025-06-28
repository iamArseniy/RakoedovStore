<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ошибка</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f5f5f5;
        }

        .error-container {
            width: 90%;
            max-width: 500px;
            text-align: center;
            padding: 2em;
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }

        .error-image {
            max-width: 100%;
            height: auto;
            margin-bottom: 1.5em;
            border-radius: 8px;
        }

        h1 {
            margin: 0 0 0.5em 0;
            font-size: 1.5em;
            color: #333;
        }

        .error-details {
            margin: 1.5em 0;
            padding: 1em;
            background-color: #f9f9f9;
            border-radius: 8px;
            font-size: 0.9em;

        }

        .error-code {
            font-weight: bold;
        }

        .home-link {
            display: inline-block;
            padding: 0.6em 1.2em;
            text-decoration: none;
            color: #333;
            border: 1px solid #ddd;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .home-link:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Что-то пошло не так...</h1>
    <img src="${pageContext.request.contextPath}/images/do_svyazi.JPG" alt="Ошибка" class="error-image">

    <div class="error-details">
        <p>Код ошибки: <span class="error-code">${errorCode}</span></p>
        <p><strong>Описание:</strong> ${errorMessage}</p>
    </div>

    <a href="${pageContext.request.contextPath}/mainpage" class="home-link">
        Вернуться на главную
    </a>
</div>
</body>
</html>