<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/product.css" rel="stylesheet">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${not empty product}">
            <div class="card">
                <img src="data:image/jpeg;base64,${fn:escapeXml(product.imageBase64)}" alt="Product Image">
                <div class="card-body">
                    <h2 class="product-title">${product.name}</h2>
                    <p class="product-description">${product.description}</p>
                    <p class="product-price">
                        Цена: <fmt:formatNumber value="${product.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/> руб.
                    </p>
                    <a href="${pageContext.request.contextPath}/mainpage" class="btn-back">Назад</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-center text-danger">Продукт не найден</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
