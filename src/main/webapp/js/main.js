
function changeCount(targetId, value) {

    const countElement = document.getElementById(`product-count-${targetId}`);
    let currentCount = parseInt(countElement.textContent);

    currentCount += value;

    if (currentCount < 0) currentCount = 0;

    countElement.textContent = currentCount;

    const hiddenInput = document.getElementById(`product-quantity-${targetId}`);
    if (hiddenInput) {  // Проверка, если скрытое поле найдено
        hiddenInput.value = currentCount;  // Обновляем значение скрытого поля
        console.log(`Updated hidden input value for ${targetId}:`, hiddenInput.value);  // Логируем значение
    } else {
        console.error(`Hidden input with id 'product-quantity-${targetId}' not found!`);  // Логируем ошибку
    }

}


document.querySelectorAll(".increase-btn").forEach(button => {
    button.addEventListener("click", function() {
        const targetId = button.getAttribute("data-target");
        changeCount(targetId, 1);
    });
});

document.querySelectorAll(".decrease-btn").forEach(button => {
    button.addEventListener("click", function() {
        const targetId = button.getAttribute("data-target");
        changeCount(targetId, -1);
    });
});

// Обработчик отправки формы
document.querySelectorAll(".addToCartForm").forEach(form => {
    form.addEventListener("submit", function(event) {
        const quantityInput = form.querySelector("input[name='quantity']");  // Скрытое поле с количеством
        const quantity = quantityInput.value;  // Получаем значение из скрытого поля
        console.log(`Submitting form with quantity: ${quantity}`);
        if (quantity == 0) {
            event.preventDefault();  // Если количество 0, не отправляем форму
            alert("Добавьте товар в корзину перед отправкой!");
        } else {
            // Если все ок, можем отправить форму
            alert(`Товар добавлен в корзину. Количество: ${quantity}`);
        }
    });
});