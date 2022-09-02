package test.java;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CheckoutPageTests {
    static Locators locators = new Locators();

    private static WebDriver driver;
    private static WebDriverWait wait;
    private String regex = "[^\\d,]";

    public void addProduct() {           //добавление в корзину первого товара из каталога
        driver.navigate().to(locators.linkCatalog);
        driver.findElement(locators.catalogButtonInBasketLocator).click();
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
        addProduct();
        driver.findElement(locators.myAccountMenuLocator).click();
    }

    @After
    public void Down(){
        driver.close();
    }

    //@AfterClass
    public static void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File("D:\\Downloads\\Scrin\\scrinshot.png"));
        driver.quit();
    }

    public void authorization(){  //авторизация и переход в раздел ОФОРМЛЕНИЕ ЗАКАЗА
        driver.navigate().to(locators.linkMyAccount);
        driver.findElement(locators.userNameAutLocator).sendKeys("111");
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        driver.findElement(locators.checkoutMenuLocator).click();
    }

    private void addData(){  //очистка полей и ввод новых данных пользователя
        driver.findElement(locators.userNameCheckoutLocator).clear();
        driver.findElement(locators.userNameCheckoutLocator).sendKeys("Вася");
        driver.findElement(locators.userLastNameCheckoutLocator).clear();
        driver.findElement(locators.userLastNameCheckoutLocator).sendKeys("Васильев");
        driver.findElement(locators.streetCheckoutLocator).clear();
        driver.findElement(locators.streetCheckoutLocator).sendKeys("ул. Центральная, д. 2");
        driver.findElement(locators.cityCheckoutLocator).clear();
        driver.findElement(locators.cityCheckoutLocator).sendKeys("Москва");
        driver.findElement(locators.stateCheckoutLocator).clear();
        driver.findElement(locators.stateCheckoutLocator).sendKeys("Московская");
        driver.findElement(locators.postcodeCheckoutLocator).clear();
        driver.findElement(locators.postcodeCheckoutLocator).sendKeys("123456");
        driver.findElement(locators.phoneCheckoutLocator).clear();
        driver.findElement(locators.phoneCheckoutLocator).sendKeys("89275846257");
        driver.findElement(locators.emailCheckoutLocator).clear();
        driver.findElement(locators.emailCheckoutLocator).sendKeys("111@111.ru");
    }

    @Test //позитивный сценарий оформления покупки по схеме "прямой банковский перевод"
    public void TestCheckoutPage_Checkout_DirectBankTransfer(){
        authorization();
        addData();

        driver.findElement(locators.directBankTransferLocator).click();
        var expectedPrice = driver.findElement(locators.orderTotalCheckoutLocator).getText();
        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.blockOverlayLocator));
        var actualTitle = driver.findElement(locators.postTitleLocator).getText();
        Assert.assertEquals("Заголовок формы не совпадает со способом оплаты", locators.orderStatus, actualTitle);

        var actualPrice = driver.findElement(locators.actualPriceCheckoutLocator).getText();
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", expectedPrice, actualPrice);
    }

    @Test //позитивный сценарий оформления покупки по сценарию "оплата при доставке"
    public void TestCheckoutPage_Checkout_PaymentUponDelivery(){

        authorization();
        addData();

        driver.findElement(locators.paymentUponDeliveryLocator).click();
        var expectedPrice = driver.findElement(locators.orderTotalCheckoutLocator).getText();
        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.blockOverlayLocator));

        var actualTitle = driver.findElement(locators.postTitleLocator).getText();
        Assert.assertEquals("Заголовок формы не совпадает со способом оплаты", locators.orderStatus, actualTitle);

        var actualPrice = driver.findElement(locators.actualPriceCheckoutLocator).getText();
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", expectedPrice, actualPrice);
    }

    @Test //оформления покупки с пустым полем ИМЯ
    public void TestCheckoutPage_Checkout_EmptyFirstName(){
        authorization();
        addData();

        driver.findElement(locators.userNameCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorUserNameCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorUserNameCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageUserNameCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем ФАМИЛИЯ
    public void TestCheckoutPage_Checkout_EmptyLasName(){
        authorization();
        addData();

        driver.findElement(locators.userLastNameCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorUserLastNameCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorUserLastNameCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageUserLastNameCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем АДРЕС
    public void TestCheckoutPage_Checkout_EmptyStreet(){
        authorization();
        addData();

        driver.findElement(locators.streetCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorStreetCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorStreetCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageStreetCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем ГОРОД
    public void TestCheckoutPage_Checkout_EmptyCity(){
        authorization();
        addData();

        driver.findElement(locators.cityCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorCityCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorCityCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageCityCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем ОБЛАСТЬ
    public void TestCheckoutPage_Checkout_EmptyState(){
        authorization();
        addData();

        driver.findElement(locators.stateCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorStateCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorStateCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageStateCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем ИНДЕКС
    public void TestCheckoutPage_Checkout_EmptyPostcode(){
        authorization();
        addData();

        driver.findElement(locators.postcodeCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorPostcodeCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorPostcodeCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messagePostcodeCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем ТЕЛЕФОН
    public void TestCheckoutPage_Checkout_EmptyPhone(){
        authorization();
        addData();

        driver.findElement(locators.phoneCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorPhoneCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorPhoneCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messagePhoneCheckoutLocator, actualResult);
    }

    @Test //оформления покупки с пустым полем EMAIL
    public void TestCheckoutPage_Checkout_EmptyEmail(){
        authorization();
        addData();

        driver.findElement(locators.emailCheckoutLocator).clear();

        driver.findElement(locators.buttonPlaceOrderCheckoutLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        var actualResult = driver.findElement(locators.errorEmailCheckoutLocator).getText();
        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.errorEmailCheckoutLocator).isDisplayed());
        Assert.assertEquals("Итоговая сумма не совпадает с суммой при оформлении заказа", locators.messageEmailCheckoutLocator, actualResult);
    }

    @Test //добавление купона
    public void TestCheckoutPage_Checkout_AddCoupon() {
        authorization();

        String toBePaidOld = driver.findElement(locators.orderTotalCheckoutLocator).getText().replaceAll(regex, "");
        double oldPrice = Double.parseDouble(toBePaidOld.replace(",", "."));
        double expectedResult = oldPrice - locators.nominalCoupon;

        driver.findElement(locators.buttonAddCouponCheckoutLocator).click();

        driver.findElement(locators.couponBasketLocator).sendKeys(locators.coupon);
        driver.findElement(locators.buttonApplyCouponCheckoutLocator).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));

        String toBePaidNew = driver.findElement(locators.orderTotalCheckoutLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(toBePaidNew.replace(",", "."));
        if (expectedResult < locators.nominalCoupon){
            var priceText = driver.findElement(locators.orderTotalCheckoutLocator).getText().replaceAll(regex, "");
            Assert.assertEquals("", priceText, "0,00");
        } else {
            var message = driver.findElement(locators.messageCouponAddLocator).getText();
            Assert.assertEquals("Не выводится сообщение о применении купона", message, locators.messageCouponAdd);
            Assert.assertTrue(expectedResult == actualResult);
        }
    }

    @Test   //переход по вкладке ЗАКАЗЫ
    public void TestMyAccountPage_Order_GoOrderSection(){
        authorization();
        driver.findElement(locators.myAccountMenuLocator).click();
        driver.findElement(locators.buttonOrderMyAccountLocator).click();
        var expectedResult = driver.findElement(locators.postTitleLocator).getText();

        Assert.assertEquals("Заголовок не соответствует разделу", expectedResult, locators.titleOrderSection);
    }

    @Test   //переход по вкладке ДАННЫЕ АККАУНТА
    public void TestMyAccountPage_Edit_GoEditAccountSection(){
        authorization();
        driver.findElement(locators.myAccountMenuLocator).click();

        driver.findElement(locators.buttonEditMyAccountLocator).click();
        var expectedResult = driver.findElement(locators.postTitleLocator).getText();

        Assert.assertEquals("Заголовок не соответствует разделу", expectedResult, locators.titleEditSection);
    }

    @Test   //переход по вкладке ИНФОРМАЦИЯ
    public void TestMyAccountPage_Information_GoInformationAccountSection(){
        authorization();
        driver.findElement(locators.myAccountMenuLocator).click();

        driver.findElement(locators.buttonEditMyAccountLocator).click();
        driver.findElement(locators.buttonDashboardMyAccountLocator).click();
        var expectedResult = driver.findElement(locators.postTitleLocator).getText();

        Assert.assertEquals("Заголовок не соответствует разделу", expectedResult, locators.titleDashboardSection);
    }
}
