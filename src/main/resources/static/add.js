// Обработчик увеличения и уменьшения чисел для спиннингиста и дончика
document.getElementById("spinning-plus").addEventListener("click", function() {
    let current = parseInt(document.getElementById("spinning").value);
    document.getElementById("spinning").value = current + 1;
    updatePrice();
});

document.getElementById("spinning-minus").addEventListener("click", function() {
    let current = parseInt(document.getElementById("spinning").value);
    if (current > 1) {
        document.getElementById("spinning").value = current - 1;
        updatePrice();
    }
});

document.getElementById("donchik-plus").addEventListener("click", function() {
    let current = parseInt(document.getElementById("donchik").value);
    document.getElementById("donchik").value = current + 1;
    updatePrice();
});

document.getElementById("donchik-minus").addEventListener("click", function() {
    let current = parseInt(document.getElementById("donchik").value);
    if (current > 1) {
        document.getElementById("donchik").value = current - 1;
        updatePrice();
    }
});

// Функция для обновления общей цены
function updatePrice() {
    let spinning = parseInt(document.getElementById("spinning").value);
    let donchik = parseInt(document.getElementById("donchik").value);
    let price = 5000; // Цена за одну запись, можно изменить на другую логику
    let totalPrice = price * (spinning + donchik);
    document.getElementById("total-price").value = totalPrice;
}
