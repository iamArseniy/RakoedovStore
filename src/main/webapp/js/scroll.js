document.addEventListener('DOMContentLoaded', function () {
    const contactLink = document.querySelector('a[href="#contacts"]');

    contactLink.addEventListener('click', function (event) {
        event.preventDefault(); // Предотвращаем перезагрузку страницы

        const target = document.getElementById('contacts');
        target.scrollIntoView({
            behavior: 'smooth' // Анимация прокрутки
        });
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const contactLink = document.querySelector('a[href="#main"]');

    contactLink.addEventListener('click', function (event) {
        event.preventDefault(); // Предотвращаем перезагрузку страницы

        const target = document.getElementById('main');
        target.scrollIntoView({
            behavior: 'smooth' // Анимация прокрутки
        });
    });
});
