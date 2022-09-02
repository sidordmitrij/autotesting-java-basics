package test.java;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainPageTests {
    Locators locators = new Locators();

    private static WebDriver driver;
    private static WebDriverWait wait;
    Actions actionProvider = new Actions(driver);
    @BeforeClass
    public static void setUp()
    {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();

//        System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
//        driver = new FirefoxDriver();

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("D:\\Downloads\\Scrin\\scrinshot.png"));
        driver.quit();
    }

    public void addProduct() {           //добавление в корзину первого товара из каталога
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.catalogMenuLocator).click();
        driver.findElement(locators.catalogButtonInBasketLocator).click();
    }

    public void openProductCard(){   //просмотреть карточку товара из каталога товаров
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.catalogMenuLocator).click();
        driver.findElement(locators.firstProductCatalogLocator).click();
    }

    @Test  //проверка наличия всех блоков на главной странице
    public void TestForMainPage_PageComposition_AvailabilityOfAllBlocks(){
        driver.navigate().to(locators.linkMainPage);
        Assert.assertTrue("Отсутствует хедер", driver.findElement(locators.headerLocator).isDisplayed());
        Assert.assertTrue("Отсутствует раздел с категориями товаров (промоблок 1)", driver.findElement(locators.categorySectionLocator).isDisplayed());
        Assert.assertTrue("Отсутствует раздел РАСПРОДАЖА", driver.findElement(locators.saleSectionLocator).isDisplayed());
        Assert.assertTrue("Отсутствует раздел УЖЕ В ПРОДАЖЕ", driver.findElement(locators.alreadySaleSectionLocator).isDisplayed());
        Assert.assertTrue("Отсутствует раздел НОВЫЕ ПОСТУПЛЕНИЯ", driver.findElement(locators.newArrivalsSectionLocator).isDisplayed());
        Assert.assertTrue("Отсутствует КОНТАКТНАЯ ИНФОРМАЦИЯ", driver.findElement(locators.footerLocator).isDisplayed());
    }

    @Test  //проверка номера телефона в хедере
    public void TestForMainPage_CheckingContactPhoneNumberHeader(){
        driver.navigate().to(locators.linkMainPage);
        String regex = "[^0-9]"; //задаем шаблон (регулярное выражение) на отсечение всех символов, кроме цифр

        var expectedResult = locators.contactPhone.replaceAll(regex, ""); //форматируем номер телефона по шаблону, т.е. оставляем только цифры
        var actualResult = driver.findElement(locators.phoneHeaderLocator).getText().replaceAll(regex, "");

        Assert.assertEquals("Номер телефона в хедере не соответствует действующему контактному номеру", expectedResult, actualResult);
    }

    @Test  //проверка email в хедере
    public void TestForMainPage_CheckingContactEmailHeader(){
        driver.navigate().to(locators.linkMainPage);

        var actualResult = driver.findElement(locators.emailHeaderLocator).getText();

        Assert.assertEquals("Email в хедере не соответствует действующему адресу электронной почты", locators.contactEmail, actualResult);
    }

    @Test           //поиск отсутствующего товара из строки поиска
    public void TestForMainPage_SearchBar_SearchForMissingProduct(){
        driver.navigate().to(locators.linkMainPage);
        var str = "абырвалг";
        driver.findElement(locators.lineSearchLocator).sendKeys(str);
        driver.findElement(locators.buttonSearchLocator).click();

        var actualResult = driver.findElement(locators.productIsMissingLocator).getText();

        Assert.assertEquals("Найденный товар не соответствует даданному в строке поиска", "По вашему запросу товары не найдены.",
                actualResult);
    }

    @Test           //поиск имеющегося товара из строки поиска
    public void TestForMainPage_SearchBar_SearchForExistingProduct(){
        driver.navigate().to(locators.linkMainPage);
        var str = "Apple Watch 6";
        driver.findElement(locators.lineSearchLocator).sendKeys(str);
        driver.findElement(locators.buttonSearchLocator).click();

        var actualResult = driver.findElement(locators.productNameInProductCardLocator).getText();

        Assert.assertEquals("Найденный товар не соответствует даданному в строке поиска", str, actualResult);
    }

    @Test    //проверка кнопки ВОЙТИ
    public void TestForMainPage_ButtonLogIn_goMenuMyAccountSection(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.buttonLogInLocator).click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(lastElementSiteTreeLocator));
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().toLowerCase();

        Assert.assertEquals("Не осуществлен переход на страницу МОЙ АККАУНТ", "мой аккаунт", actualResult);
    }

    @Test      //проверка добавления в корзину первого товара из каталога
    public void TestForMainPage_AddToBasket(){
        addProduct();
        var expectedResult = driver.findElement(locators.nameFirstProductCatalogLocator).getText(); //название первого товара в КАТАЛОГЕ
        driver.findElement(locators.basketMenuLocator).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.firstNameProductInBasketLocator));
        var actualResult = driver.findElement(locators.firstNameProductInBasketLocator).getText();

        Assert.assertEquals("Не соответствует название выбранного товара и помещенного в корзину", expectedResult, actualResult);
    }

    @Test          //переход в ОФОРМЛЕНИЕ ЗАКАЗА при пустой корзине
    public void TestForMainPage_goMenuCheckoutSection_EmptyBasket() {
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.checkoutMenuLocator).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(locators.emptyBasketLocator));
        var actualResult = driver.getCurrentUrl();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки",  locators.linkBasket, actualResult);
    }

    @Test           //переход в ОФОРМЛЕНИЕ ЗАКАЗА при наличии товара в корзине
    public void TestForMainPage_goMenuCheckoutSection_ProductInBasket(){
        addProduct();

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.catalogButtonDetailedProductLocator));
        driver.findElement(locators.checkoutMenuLocator).click();

        Assert.assertTrue("Не открывается окно ОФОРМЛЕНИЯ ЗАКАЗА", driver.findElement(locators.innerSectionLocator).isDisplayed());
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkCheckout, driver.getCurrentUrl());
    }

    @Test     //проверка перехода из меню на страницу КАТАЛОГ по соответствию URL
    public void TestForMainPage_GoMenuCatalogSection(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.catalogMenuLocator).click();

        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkCatalog, driver.getCurrentUrl());
    }

    @Test     //проверка перехода на страницу КАТАЛОГ по названию раздела
    public void TestForMainPage_GoCatalogSection(){
        driver.navigate().to(locators.linkMainPage);

        var expectedResult = driver.findElement(locators.catalogMenuLocator).getText().toLowerCase();
        driver.findElement(locators.catalogMenuLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().toLowerCase();
        //(By.xpath("(//*[@class='baseCard__conditions'])[last()]"))
        Assert.assertEquals("Название страницы не совпадает с выбранным пунктом меню", expectedResult, actualResult);
    }

    @Test     //проверка перехода из футера на ГЛАВНУЮ страницу
    public void TestForMainPage_GoFooterMenuCatalogSection(){
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.footerLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока РАСПРОДАЖА
        driver.findElement(locators.mainPageFooterLocator).click();

        Assert.assertTrue(driver.findElement(locators.headerLocator).isDisplayed());
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkMainPage, driver.getCurrentUrl());
    }

    @Test    //проверка перехода из меню на страницу МОЙ АККАУНТ
    public void TestForMainPage_GoMenuMyAccountSection(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.myAccountMenuLocator).click();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkMyAccount, driver.getCurrentUrl());
    }

    @Test         //проверка перехода а рубрику КНИГИ
    public void TestForMainPage_GoBooksSection() {
        driver.navigate().to(locators.linkMainPage);

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.headerPromoBookSectionLocator));
        var expectedResult = driver.findElement(locators.headerPromoBookSectionLocator).getText().toLowerCase();
        driver.findElement(locators.bookSectionLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().toLowerCase();

        Assert.assertEquals("Не соответствуют название категории товара и страница перехода",
                expectedResult, actualResult);
        //"книги", actualResult);
    }

    @Test        //проверка перехода в рубрику ПЛАНШЕТЫ
    public void TestForMainPage_GoPodSection() {
        driver.navigate().to(locators.linkMainPage);
        var expectedResult = "планшеты";

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.podSectionLocator));

        driver.findElement(locators.podSectionLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().toLowerCase();

        Assert.assertEquals("Не соответствуют название категории товара и страница перехода",
                expectedResult, actualResult);
    }

    @Test         //проверка перехода а рубрику ФОТОАППАРАТЫ
    public void TestForMainPage_GoPhotoSection() {
        driver.navigate().to(locators.linkMainPage);
        var expectedResult = "фото/видео";

        driver.findElement(locators.photoCameraSectionLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().toLowerCase();

        Assert.assertEquals("Не соответствуют название категории товара и страница перехода",
                expectedResult, actualResult);
    }

    @Test       //Проверка наличия пометки СКИДКА
    public void TestForMainPage_SaleSection_AvailabilitySaleMark() {
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.saleSectionLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока РАСПРОДАЖА

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.firstProductInSliderLocator));
        var actualResult = driver.findElement(locators.labelSaleLocator);

        Assert.assertTrue("В распродажном товаре отсутствует информация о скидке", actualResult.isDisplayed());
        Assert.assertEquals("Изменен текст информации о скидке", "Скидка!", actualResult.getText());
    }

    @Test    //сверка старой и новой цен в товарах со скидкой
    public void TestForMainPage_SaleSection_ComparisonNewAndOldPrices() {
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.saleSectionLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока РАСПРОДАЖА

        driver.findElement(locators.buttonInBasketLocator).click();

        String regex =  "[^0-9]";
        var expectedText = driver.findElement(locators.oldPriceSaleLocator).getText().replaceAll(regex, "");

        double expectedResult = Double.parseDouble(expectedText);
        var actualText = driver.findElement(locators.newPriceSaleLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(actualText);
        Assert.assertFalse("Старая и новая цены одинаковые", expectedText == actualText);
        Assert.assertTrue("Цена до скидки ниже, чем после скидки", expectedResult > actualResult);
    }

    @Test    //переход по клику кнопки
    public void TestForMainPage_SaleSection_clickThrough() {
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.saleSectionLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока РАСПРОДАЖА

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.firstProductInSliderLocator));
        var nameProduct = driver.findElement(locators.titleProductSaleLocator).getText().toLowerCase();
        var button = driver.findElement(locators.buttonInBasketLocator);

        actionProvider.moveToElement(button).build().perform();  //наведение курсора на элемент

        var buttonText = button.getText();
        button.click();

        switch (buttonText) {
            case "В корзину":
                Assert.assertEquals("Подробнее", button.getText());
                break;

            case "Read more":
                Assert.assertTrue(driver.findElement(locators.cardProductLocator).isDisplayed());
                Assert.assertEquals(nameProduct, driver.findElement(locators.titleCardProductLocator).getText().toLowerCase());
                break;
        }
    }

    @Test    //проверка соответсвия перехода промоблока УЖЕ В ПРОДАЖЕ
    public void TestForMainPage_GoAlreadySaleSection() {
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.alreadySaleSectionLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока УЖЕ В ПРОДАЖЕ

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.titleAlreadySaleSectionLocator));
        var expectedResult = driver.findElement(locators.titleAlreadySaleSectionLocator).getText().toLowerCase();

        driver.findElement(locators.alreadySaleSectionLocator).click();

        var nameProduct = driver.findElement(locators.titleCardProductLocator);
        var actualResult = nameProduct.getText().toLowerCase();
        Assert.assertEquals("название товара в разделе УЖЕ В ПРОДАЖЕ не соотвтетствует открываемому товару",
                expectedResult, actualResult);
    }

    @Test        //Проверка наличия пометки НОВЫЙ
    public void TestForMainPage_NewProduct_AvailabilityNewMark() {
        driver.navigate().to(locators.linkMainPage);

        var scrollPage = driver.findElement(locators.newArrivalsSectionLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до блока РАСПРОДАЖА

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.firstProductNewArrivalsLocator));
        var actualResult = driver.findElement(locators.labelNewLocator);

        Assert.assertTrue("В карточке товара отсутствует информация о том, что он НОВИНКА", actualResult.isDisplayed());
        Assert.assertEquals("Изменен текст информации о НОВИНКЕ", "Новый!", actualResult.getText());
    }

    @Test  //проверка номера телефона в футере
    public void TestForMainPage_CheckingContactPhoneNumberFooter() {
        driver.navigate().to(locators.linkMainPage);
        String regex = "[^0-9]"; //задаем шаблон (регулярное выражение) на отсечение всех символов, кроме цифр
        var expectedResult = locators.contactPhone.replaceAll(regex, ""); //форматируем номер телефона по шаблону, т.е. оставляем только цифры

        var scrollPage = driver.findElement(locators.phoneFooterLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до номера телефона в футере

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.phoneFooterLocator)); //явное ожидание появления элемента
        var actualResult = driver.findElement(locators.phoneFooterLocator).getText().replaceAll(regex, "");

        Assert.assertEquals("Номер телефона в футере не соответствует действующему контактному номеру", expectedResult, actualResult);
    }


    @Test  //проверка email в футере
    public void TestForMainPage_CheckingContactEmailFooter() {
        driver.navigate().to(locators.linkMainPage);
        String regex = "^(Email:\\s)"; //задаем шаблон (регулярное выражение) на отсечение слова Email

        var scrollPage = driver.findElement(locators.phoneFooterLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до номера телефона в футере

        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.emailFooterLocator));
        var actualResult = driver.findElement(locators.emailFooterLocator).getText().replaceAll(regex, "");

        Assert.assertEquals("Email в футере не соответствует действующему адресу электронной почты", locators.contactEmail, actualResult);
    }

    @Test          //проверка перехода из футера на страницу ВСЕ ТОВАРЫ
    public void TestForMainPage_GoAllProductsSectionFooter(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.allProductFooterLocator).click();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkAllProducts, driver.getCurrentUrl());
    }

    @Test          //проверка перехода из футера в КОРЗИНУ
    public void TestForMainPage_GoBasketFooter(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.basketFooterLocator).click();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkBasket, driver.getCurrentUrl());
    }

    @Test           //проверка перехода из футера на страницу МОЙ АККАУНТ
    public void TestForMainPage_GoMyAccountFooter(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.myAccountFooterLocator).click();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkMyAccount, driver.getCurrentUrl());
    }

    @Test           //проверка перехода из футера на страницу РЕГИСТРАЦИИ
    public void TestForMainPage_GoRegistrationPageFooter(){
        driver.navigate().to(locators.linkMainPage);
        driver.findElement(locators.buttonRegistrationFooterLocator).click();
        Assert.assertEquals("Не совпадает URL открытой страницы и ссылки", locators.linkRegistration, driver.getCurrentUrl());
    }

    @Test     //наличие блока ПРОСМОТРЕННЫЕ ТОВАРЫ
    public void TestForMainPage_ViewedProductsSection_DisplaySection(){
        openProductCard();
        var expectedResult = driver.findElement(locators.productNameInProductCardLocator).getText().toLowerCase();
        driver.findElement(locators.mainPageMenuLocator).click();
        Assert.assertTrue("Не отображается блок ПРОСМОТРЕННЫЕ ТОВАРЫ", driver.findElement(locators.viewedProductsSectionLocator).isDisplayed());
        var actualResult = driver.findElement(locators.nameViewedProductsLocator).getText().toLowerCase();
        Assert.assertEquals("Первый в списке товар не соответствует последнему просмотренному товару", expectedResult, actualResult);
    }

    @Test       //кнопка возврата в начало страницы
    public void TestForButtonUp_ReturnStartPage() {
        driver.navigate().to(locators.linkMainPage);
        var scrollPage = driver.findElement(locators.phoneFooterLocator); //создаем маяк для скролла страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", scrollPage); //скроллим страницу до номера телефона в футере

        driver.findElement(locators.buttonReturnTopPageLocator).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(locators.headerLocator));

        Assert.assertTrue(driver.findElement(locators.headerLocator).isDisplayed());
    }
}
