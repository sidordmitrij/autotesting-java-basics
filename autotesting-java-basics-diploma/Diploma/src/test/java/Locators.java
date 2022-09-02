package test.java;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Locators {

//    public static WebDriver driver;
//    public static WebDriverWait wait;

    //Общие локаторы для всех страниц
    public By headerLocator = By.id("mastheads"); //хедер
    public By mainPageMenuLocator = By.xpath("//*[@class='store-menu']//a[.='Главная']"); //ГЛАВНАЯ основного меню
    public By mainPageFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Главная']"); //ГЛАВНАЯ футер
    public By catalogMenuLocator = By.xpath("//*[@class='store-menu']//a[.='Каталог']"); //переход в КАТАЛОГ из меню
    public By myAccountMenuLocator = By.xpath("//*[@class='store-menu']//a[.='Мой аккаунт']"); //переход в МОЙ АККАУНТ из меню
    public By myAccountFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Мой аккаунт']");  //переход в МОЙ АККАУНТ из футера
    public By allProductFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Все товары']"); //переход на страницу ВСЕ ТОВАРЫ
    public By basketMenuLocator = By.xpath("//*[@class='store-menu']//a[.='Корзина']"); //переход в КОРЗИНУ из меню
    public By basketFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Корзина']");  //переход в КОРЗИНУ из футера
    public By checkoutMenuLocator = By.id("menu-item-31");//xpath("//*[@class='store-menu']//a[.='Оформление заказа']"); //переход к ОФОРМЛЕНИЕ ЗАКАЗА из меню
    public By checkoutFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Оформление заказа']");  //переход к ОФОРМЛЕНИЕ ЗАКАЗА из футера
    public By lineSearchLocator = By.className("search-field");  //строка поиска
    public By buttonSearchLocator = By.className("searchsubmit"); //кнопка НАЙТИ
    public By buttonLogInLocator = By.className("login-woocommerce"); //кнопка ВОЙТИ
    public By buttonRegistrationFooterLocator = By.xpath("//*[@class='top-footer-block']//a[.='Регистрация']"); //регистрация из футера
    public By buttonReturnTopPageLocator = By.id("ak-top"); //кнопка возврата в начало страницы
    public By phoneHeaderLocator = By.cssSelector(".textwidget > a:nth-child(1)"); //номер телефона в шапке
    public By emailHeaderLocator = By.cssSelector(".textwidget > a:nth-child(2)"); //email в шапке
    public By messageWelcomeUserHeaderLocator = By.className("welcome-user"); //приветственное окно в шапке
    public By messageNameInWelcomeHeaderLocator = By.className("my-account"); //ссылка на МОЙ АККАУНТ в шапке
    public By footerLocator = By.id("colophon"); //футер
    public By phoneFooterLocator = By.cssSelector(".cta-desc_simple p:nth-child(1)"); //номер телефона в футере
    public By emailFooterLocator = By.cssSelector(".cta-desc_simple p:nth-child(2)"); //email в футере
    public String linkMainPage = "http://intershop5.skillbox.ru/"; //URL на ГЛАВНУЮ страницу
    public String linkCatalog = "http://intershop5.skillbox.ru/product-category/catalog/"; //URL на КАТАЛОГ
    public String linkMyAccount = "http://intershop5.skillbox.ru/my-account/";  //URL на МОЙ АККАУНТ
    public String linkBasket = "http://intershop5.skillbox.ru/cart/";  //URL на КОРЗИНУ
    public String linkCheckout = "http://intershop5.skillbox.ru/checkout/"; //URL на страницу ОФОРМЛЕНИЕ ЗАКАЗА
    public String linkAllProducts = "http://intershop5.skillbox.ru/shop/";  //URL на страницу ВСЕ ТОВАРЫ
    public String linkRegistration = "http://intershop5.skillbox.ru/register/";  //URL на страницу регистрации
    public String linkLostPassword = "http://intershop5.skillbox.ru/my-account/lost-password/"; //URL на страницу восстановления парлоля
    public String contactPhone = "+7-999-123-12-12";  //контактный телефон
    public String contactEmail = "skillbox@skillbox.ru";  //контактный email
    public String coupon = "sert500"; //купон на скидку
    public String messageCouponAdd = "Купон успешно добавлен."; //сообщение: купон на скидку добавлен
    public String messageCouponDelete = "Купон удален."; //сообщение: купон на скидку удален
    public String messageInvalidCouponAdd = "Неверный купон.";  //сообщение: неверный купон
    public double nominalCoupon = 500; //величина скидки
    public String emptyBasket = "Корзина пуста."; //сообщение Корзина Пуста
    public String messageRegistration = "Регистрация завершена"; //сообщение об успешной регистрации
    public By postTitleLocator = By.className("post-title"); //заголовок МОЙ АККАУНТ\РЕГИСТРАЦИЯ
    public By lastElementSiteTreeLocator = By.xpath("//*[@id='accesspress-breadcrumb']//*[last()] | //*[contains(@class, 'accesspress-breadcrumb')]//*[last()]");  //последний элемент в дереве перехода по страницам сайта
    public By productNameInProductCardLocator = By.cssSelector(".product_title.entry-title");  //название товара в карточке товара
    public By productIsMissingLocator = By.className("woocommerce-info"); //сообщение об отсутствии товара

    //ГЛАВНАЯ
    public By categorySectionLocator = By.id("promo-section1");  //раздел с категориями
    public By bookSectionLocator = By.id("accesspress_storemo-2"); //раздел КНИГИ
    public By headerPromoBookSectionLocator = By.cssSelector("#accesspress_storemo-2 .widget-title"); //заголовок промосекции КНИГИ
    public By podSectionLocator = By.id("accesspress_storemo-3");   //раздел ПЛАНШЕТЫ
    public By photoCameraSectionLocator = By.id("accesspress_storemo-4");   //раздел ФОТОАППАРАТЫ
    public By saleSectionLocator = By.id("product1");  //секция РАСПРОДАЖА
    public By firstProductInSliderLocator = By.cssSelector("#product1 .slick-slide:nth-child(5)");  //первая карточка товара в слайдере РАСПРОДАЖА
    public By labelSaleLocator = By.cssSelector("#product1 .slick-slide:nth-child(5) .onsale"); //лейбл СКИДКА в карточке товара блока распродажа
    public By buttonInBasketLocator = By.cssSelector("#product1 .slick-slide:nth-child(5) .item-img a"); //кнопка В КОРЗИНУ в блоке РАСПРОДАЖА
    public By titleProductSaleLocator = By.cssSelector("#product1 .slick-slide:nth-child(5) h3"); //название товара в карточке товара блока распродажа
    public By oldPriceSaleLocator = By.cssSelector(".price del .amount"); //старая цена
    public By newPriceSaleLocator = By.cssSelector(".price ins .amount"); //новая цена
    public By alreadySaleSectionLocator = By.id("promo-section2");  //раздел УЖЕ В ПРОДАЖЕ
    public By titleAlreadySaleSectionLocator = By.className("promo-desc-title"); //название товара в блоке УЖЕ В ПРОДАЖЕ
    public By newArrivalsSectionLocator = By.id("product2"); //раздел НОВЫЕ ПОСТУПЛЕНИЯ
    public By firstProductNewArrivalsLocator = By.cssSelector("#product2 .slick-slide:nth-child(4)");  //первая карточка товара в слайдере НОВЫЕ ПОСТУПЛЕНИЯ
    public By labelNewLocator = By.cssSelector("#product2 .slick-slide:nth-child(4) .label-new"); //пометка НОВЫЙ в разделе новые товары
    public By viewedProductsSectionLocator = By.className("ap-cat-list");  //блок ПРОСМОТРЕННЫЕ ТОВАРЫ
    public By nameViewedProductsLocator = By.cssSelector(".ap-cat-list .product_list_widget li:first-child .product-title");  //название первого товара в блоке ПРОСМОТРЕННЫЕ ТОВАРЫ

    //КАРТОЧКА ТОВАРА
    public By cardProductLocator = By.className("content-inner"); //карточка товара
    public By titleCardProductLocator = By.className("product_title"); //название товара в карточке товара

    //КАТАЛОГ
    public By firstProductCatalogLocator = By.className("product"); //первый товар в каталоге
    public By secondProductCatalogLocator = By.cssSelector(".product:nth-child(2)"); //второй товар в каталоге
    public By headerCatalogSectionLocator = By.className("entry-title"); //основной заголовок раздела
    public By nameFirstProductCatalogLocator = By.className("collection_title"); //название первого товара в КАТАЛОГЕ
    public By catalogButtonInBasketLocator = By.cssSelector(".wc-products .product:nth-of-type(1) .add_to_cart_button"); //кнопка В КОРЗИНУ товара в каталоге
    public By catalogButtonDetailedProductLocator = By.xpath("//*[@id='primary']//a[.='Подробнее']"); //кнопка ПОДРОБНЕЕ после клика по кнопке в корзину
    public By catalogSecondButtonInBasketLocator = By.cssSelector(".wc-products .product:nth-of-type(2) .add_to_cart_button"); //кнопка В КОРЗИНУ второго товара в каталоге
    public By categoryProductInCatalogLocator = By.className("cat-item:nth-child(1)");
    public By nameCategoryLocator = By.cssSelector(".cat-item:nth-child(1) a"); //ссылка на категорию товаров
    public By countProductLocator = By.cssSelector(".cat-item:nth-child(1) span"); //количество товаров в категории
    public By countProductInCategoryLocator = By.className("woocommerce-result-count"); //количество товаров в отрывшейся странице
    public By paginationNextLocator = By.className("next");  //пагинация стрелка вперед
    public By paginationBackLocator = By.className("prev");  //пагинация стрелка назад
    public By paginationCustomPageLocator = By.cssSelector(".page-numbers li:nth-child(4)"); //произвольная (4) страница каталога

    //КОРЗИНА
    public By emptyBasketLocator = By.className("cart-empty"); //пустая корзина
    public By buttonBackToStoreLocator = By.className("wc-backward"); //кнопка НАЗАД В МАГАЗИН
    public By deleteProductLocator = By.className("remove"); //кнопка удаления товара
    public By restoreProductLocator = By.className("restore-item"); //ссылка на восстановление в корзине удаленного товара
    public By firstNameProductInBasketLocator = By.cssSelector(".cart .cart_item:first-child td:nth-child(3) a");  //название первого товара в корзине
    public By firstPriceProductInBasketLocator = By.cssSelector(".cart .cart_item:first-child td:nth-child(4) bdi");  //цена первого товара в корзине
    public By countProductInBasketLocator = By.cssSelector(".cart .cart_item:first-child td:nth-child(5) input");  //количество первого товара в корзине (чтобы вывести значение - использовать .getAttribute("value")
    public By firstTotalPriceProductInBasketLocator = By.cssSelector(".cart .cart_item:first-child td:nth-child(6) bdi");  //общая цена первого товара в корзине
    public By secondTotalPriceProductInBasketLocator = By.cssSelector(".cart .cart_item:nth-child(2) td:nth-child(6) bdi");  //общая цена второго товара в корзине
    public By couponBasketLocator = By.id("coupon_code"); //поле ввода купона
    public By buttonCouponBasketLocator = By.cssSelector(".coupon button"); //кнопка ПРИМЕНИТЬ КУПОН
    public By messageCouponAddLocator = By.className("woocommerce-message"); //поле с сообщением о добавлении купона
    public By messageInvalidCouponAddLocator = By.className("woocommerce-error"); //поле с сообщением о невалидном купоне
    public By nominalCouponBasketLocator = By.cssSelector(".cart-discount .amount"); //номинальная стоимость купона
    public By deleteCouponBasketLocator = By.cssSelector(".cart-discount .woocommerce-remove-coupon");  //ссылка удалить купон
    public By priceAllProductBasketLocator = By.cssSelector(".order-total bdi");  //поле ИТОГО К ОПЛАТЕ
    public By buttonPlaceAnOrderBasketLocator = By.className("checkout-button") ;   //кнопка в корзине ОФОРМИТЬ ЗАКАЗ
    public By overlayLocator = By.className("blockOverlay"); //время на перерасчет суммы с учетом измененного количества товаров

    //РЕГИСТРАЦИЯ\АВТОРИЗАЦИЯ\ВОССТАНОВЛЕНИЕ ПАРОЛЯ
    public By userNameRegLocator = By.id("reg_username");  //поле ввода ИМЯ в форме регистрации
    public By emailRegLocator = By.id("reg_email");  //поле ввода EMAIL в форме регистрации
    public By passwordRegLocator = By.id("reg_password");  //поле ввода ПАРОЛЯ в форме регистрации
    public By buttonRegisterLocator = By.name("register");  //кнопка ЗАРЕГИСТРИРОВАТЬСЯ в форме регистрации
    public By userNameAutLocator = By.id("username");  //поле ввода ИМЯ в форме авторизации
    public By passwordAutLocator = By.id("password");  //поле ввода ПАРОЛЯ в форме авторизации
    public By buttonAuthorizationLocator = By.name("login");  //кнопка ВОЙТИ в форме авторизации
    public By buttonRegisterAutLocator = By.cssSelector(".lost_password button");  //кнопка ЗАРЕГИСТРИРОВАТЬСЯ в форме авторизации
    public By messageErrorLocator = By.className("woocommerce-error"); //сообщение об ошибке ввода данных
    public By messageErrorNameLocator = By.cssSelector(".woocommerce-error strong"); //Отображаемое имя в сообщении об ошибке ввода данных
    public By messageNameInWelcomeLocator = By.cssSelector(".woocommerce-MyAccount-content strong");  //имя в приветственном сообщении
    public By forgotPasswordAutLocator = By.cssSelector(".lost_password a"); //ссылка ЗАБЫЛИ ПАРОЛЬ
    public By userLoginPasswordRecoveryLocator = By.id("user_login"); //поле ввода имени или email в форме восстановления пароля
    public By messageRegistrationLocator = By.className("content-page"); //окно с сообщении об успешной регистрации
    public By linkShowloginLocator = By.className("showlogin"); //ссылка ПОЖАЛУЙСТА АВТОРИЗУЙТЕСЬ в форме регистрации
    public By messagePasswordRecoveryLocator = By.className("woocommerce-message"); //окно с сообщением о сбросе пароля
    public By buttonPasswordRecoveryLocator = By.className("woocommerce-Button"); //кнопка сброса пароля
    public String messageRegErrorName = "Error: Пожалуйста введите корректное имя пользователя.";  //сообщение о невалидном ИМЕНИ
    public String messageRegErrorEmail = "Error: Пожалуйста, введите корректный email.";  //сообщение о невалидном Email
    public String messageErrorDoubleEmail = "Error: Учетная запись с такой почтой уже зарегистировавана. Пожалуйста авторизуйтесь.";  //сообщение при регистрации имеющегося аккаунта
    public String messageAuthNameRequired = "Error: Имя пользователя обязательно.";  //сообщение о необходимости заполнения поля ИМЯ при авторизации
    public String messageAuthPasswordRequired = "Пароль обязателен.";  //сообщение о необходимости заполнения поля ПАРОЛЬ при авторизации
    public String messageAuthInvalidName = "Неизвестное имя пользователя. Попробуйте еще раз или укажите адрес почты.";  //сообщение при авторизации о неверно введенном имени
    public String messageAuthInvalidEmail = "Неизвестный адрес почты. Попробуйте еще раз или введите имя пользователя.";  //сообщение при авторизации о неверно введенном email
    public String messageAuthInvalidPassword = "Веденный пароль для пользователя 111 неверный. Забыли пароль?";  //сообщение при авторизации о неверно введенном пароле
    public String messagePasswordRecovery = "Password reset email has been sent."; //сообщение о сбросе пароля
    public String messageEmptyDataPasswordRecovery = "Введите имя пользователя или почту.";  //сообщение о необходимости ввода данных для сброса пароля
    public String messageInvalidDataPasswordRecovery = "Неверное имя пользователя или почта.";  //сообщение о невалидных данных при сбросе пароля

    //ОФОРМЛЕНИЕ ЗАКАЗА
    public By innerSectionLocator = By.className("inner");
    public By blockOverlayLocator = By.className(".blockUI");  //
    public By userNameCheckoutLocator = By.id("billing_first_name"); //имя в разделе оформление заказа
    public By userLastNameCheckoutLocator = By.id("billing_last_name"); //фамилия в разделе оформление заказа
    public By streetCheckoutLocator = By.id("billing_address_1"); //улица в разделе оформление заказа
    public By cityCheckoutLocator = By.id("billing_city"); //город в разделе оформление заказа
    public By stateCheckoutLocator = By.id("billing_state"); //область в разделе оформление заказа
    public By postcodeCheckoutLocator = By.id("billing_postcode"); //индекс в разделе оформление заказа
    public By phoneCheckoutLocator = By.id("billing_phone"); //телефон в разделе оформление заказа
    public By emailCheckoutLocator = By.id("billing_email"); //email в разделе оформление заказа
    public By orderTotalCheckoutLocator = By.cssSelector(".order-total bdi");  //итоговая сумма к оплате в разделе оформление заказа
    public By actualPriceCheckoutLocator = By.cssSelector(".woocommerce-Price-amount bdi");  // сумма к оплате в фрме ЗАКАЗ ПОЛУЧЕН \ ЗАКАЗ ОБРАБАТЫВАЕТСЯ
    public By buttonPlaceOrderCheckoutLocator = By.id("place_order");  //кнопка ОФОРМИТЬ ЗАКАЗ
    public By directBankTransferLocator = By.id("payment_method_bacs"); //радиобаттон - прямой банковский перевод
    public By paymentUponDeliveryLocator = By.id("payment_method_cod");  //радиобаттон - оплата при доставке
    public By buttonAddCouponCheckoutLocator = By.className("showcoupon");  //добавить купон
    public By buttonApplyCouponCheckoutLocator = By.cssSelector(".checkout_coupon button"); //кнопка ПРИМЕНИТЬ КУПОН
    public By errorUserNameCheckoutLocator = By.xpath("//li[@data-id='billing_first_name']"); //ошибка о пустом поле ИМЯ
    public By errorUserLastNameCheckoutLocator = By.xpath("//li[@data-id='billing_last_name']"); //ошибка о пустом поле ФАМИЛИЯ
    public By errorStreetCheckoutLocator = By.xpath("//li[@data-id='billing_address_1']"); //ошибка о пустом поле УЛИЦА
    public By errorCityCheckoutLocator = By.xpath("//li[@data-id='billing_city']"); //ошибка о пустом поле ГОРОД
    public By errorStateCheckoutLocator = By.xpath("//li[@data-id='billing_state']"); //ошибка о пустом поле ОБЛАСТЬ
    public By errorPostcodeCheckoutLocator = By.xpath("//li[@data-id='billing_postcode']"); //ошибка о пустом поле ИНДЕКС
    public By errorPhoneCheckoutLocator = By.xpath("//li[@data-id='billing_phone']"); //ошибка о пустом поле ТЕЛЕФОН
    public By errorEmailCheckoutLocator = By.xpath("//li[@data-id='billing_email']"); //ошибка о пустом поле EMAIL
    public String messageUserNameCheckoutLocator = "Имя для выставления счета обязательное поле."; //сообщение о пустом поле ИМЯ
    public String messageUserLastNameCheckoutLocator = "Фамилия для выставления счета обязательное поле."; //сообщение о пустом поле ФАМИЛИЯ
    public String messageStreetCheckoutLocator = "Адрес для выставления счета обязательное поле."; //сообщение о пустом поле УЛИЦА
    public String messageCityCheckoutLocator = "Город / Населенный пункт для выставления счета обязательное поле."; //сообщение о пустом поле ГОРОД
    public String messageStateCheckoutLocator = "Область для выставления счета обязательное поле."; //сообщение о пустом поле ОБЛАСТЬ
    public String messagePostcodeCheckoutLocator = "Почтовый индекс для выставления счета обязательное поле."; //сообщение о пустом поле ИНДЕКС
    public String messagePhoneCheckoutLocator = "неверный номер телефона."; //сообщение о пустом поле ТЕЛЕФОН
    public String messageEmailCheckoutLocator = "Адрес почты для выставления счета обязательное поле."; //сообщение о пустом поле EMAIL
    public String orderStatus = "Заказ получен"; //ЗАГОЛОВОК оформленого заказа

    //МОЙ АККАУНТ
    public By buttonDashboardMyAccountLocator = By.cssSelector(".woocommerce-MyAccount-navigation-link--dashboard a"); //кнопка ИНФОРМАЦИЯ
    public By buttonOrderMyAccountLocator = By.cssSelector(".woocommerce-MyAccount-navigation-link--orders a");  //кнопка ЗАКАЗЫ
    public By buttonEditMyAccountLocator = By.cssSelector(".woocommerce-MyAccount-navigation-link--edit-account a");  //кнопка ДАННЫЕ АККАУНТА
    public By buttonExitMyAccountLocator = By.cssSelector(".woocommerce-MyAccount-navigation-link--customer-logout a");  //кнопка выйти из аккаунта
    public String titleDashboardSection = "Мой аккаунт";  //заголовок раздела ИНФОРМАЦИЯ
    public String titleOrderSection = "Заказы";  //заголовок раздела ЗАКАЗЫ
    public String titleEditSection = "Данные учетной записи";  //заголовок раздела ДАННЫЕ АККАУНТА

}
