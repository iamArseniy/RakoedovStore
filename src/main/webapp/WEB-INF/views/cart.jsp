<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Ваша корзина</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/cart.css">
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="content">
    <div class="table-container">
        <h1>Ваша корзина</h1>

        <c:choose>
            <c:when test="${empty cartItems}">
                <p>Ваша корзина пуста.</p>
            </c:when>

            <c:otherwise>
                <table border="1" class="table table-striped">
                    <thead>
                    <tr>
                        <th>Название</th>
                        <th>Количество</th>
                        <th>Цена</th>
                        <th>Сумма</th>
                        <th>Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="totalSum" value="0.00" scope="page"/>

                    <c:forEach var="item" items="${cartItems}">
                        <c:set var="product" value="${products[item.product.id - 1]}"/>
                        <c:set var="total" value="${item.amount * product.price}"/>
                        <c:set var="totalSum" value="${totalSum + total}" scope="page"/>

                        <tr>
                            <td>${product.name}</td>
                            <td>${item.amount}</td>
                            <td>${product.price}</td>
                            <td class="item-total">${total}</td>
                            <td>
                                <button
                                        type="button"
                                        class="remove-from-cart-btn btn btn-danger btn-sm"
                                        data-product-id="${item.product.id}"
                                        data-quantity="${item.amount}">
                                    Убрать из корзины
                                </button>
                            </td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td colspan="4" style="text-align:right;"><strong>Итого:</strong></td>
                        <td id="total-sum-cell"><strong>${totalSum}</strong></td>
                    </tr>
                    </tbody>
                </table>

                <div class="create-order mt-3">
                    <form action="${pageContext.request.contextPath}/order" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="action" value="makeOrder">
                        <input type="hidden" name="totalSum" id="totalSumInput" value="${totalSum}">
                        <button type="submit" class="btn btn-primary">Оформить заказ</button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="footer.jsp"/>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const buttons = document.querySelectorAll('.remove-from-cart-btn');

        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        buttons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.dataset.productId;
                const quantity = this.dataset.quantity;
                const row = this.closest("tr");
                const totalCell = row.querySelector(".item-total");
                const itemTotal = parseFloat(totalCell.textContent);

                fetch('/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest',
                        [csrfHeader]: csrfToken
                    },
                    body: new URLSearchParams({
                        action: 'remove',
                        quantity: quantity,
                        product_id: productId
                    })
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(err => {
                                throw new Error(err.message || "Ошибка удаления");
                            });
                        }

                        row.remove();

                        const totalSumElement = document.querySelector("#total-sum-cell strong");
                        const totalSumInput = document.querySelector("#totalSumInput");

                        let currentTotal = parseFloat(totalSumElement.textContent);
                        let newTotal = (currentTotal - itemTotal).toFixed(2);

                        totalSumElement.textContent = newTotal;
                        totalSumInput.value = newTotal;

                        if (document.querySelectorAll('.remove-from-cart-btn').length === 0) {
                            document.querySelector('.table-container').innerHTML = '<p>Ваша корзина пуста.</p>';
                        }
                    })
                    .catch(error => {
                        alert("Ошибка: " + error.message);
                    });
            });
        });
    });
</script>
</body>
</html>
