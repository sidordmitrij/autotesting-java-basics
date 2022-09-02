package test.java;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasketPageTests {
    static Locators locators = new Locators();

    private static WebDriver driver;
    private static WebDriverWait wait;
    private String regex = "[^\\d,]";

    private static void clearBasket(){      //очистка корзины
        List rows = driver.findElements(By.className("cart_item"));
        int i = 1;
        while (i <= rows.size()){
            driver.findElement(locators.deleteProductLocator).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
            rows = driver.findElements(By.className("cart_item"));
        }
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();

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

    private void addProduct(){   //добавить товар и перейти в корзину
        driver.navigate().to(locators.linkCatalog);

        driver.findElement(locators.catalogButtonInBasketLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.catalogButtonInBasketLocator));
        driver.findElement(locators.basketMenuLocator).click();
    }

    private void addProductTwo(){   //добавить два разных товара и перейти в корзину
        driver.navigate().to(locators.linkCatalog);

        driver.findElement(locators.catalogButtonInBasketLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.catalogButtonInBasketLocator));
        driver.findElement(locators.catalogSecondButtonInBasketLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.catalogSecondButtonInBasketLocator));
        driver.findElement(locators.basketMenuLocator).click();
    }

    @Test  //клик по кнопке НАЗАД В МАГАЗИН
    public void TestBasketPage_ClickBackShop_GoBackShop(){
        driver.navigate().to(locators.linkBasket);
        driver.findElement(locators.buttonBackToStoreLocator).click();
        Assert.assertEquals("Не переходит обратно в магазин", locators.linkAllProducts, driver.getCurrentUrl());
    }

    @Test  //изменение количества товара
    public void TestBasketPage_UpdateCountProduct() {
        addProduct();
        driver.findElement(locators.countProductInBasketLocator).clear();
        driver.findElement(locators.countProductInBasketLocator).sendKeys("3");//получить значение value
        String inputText = driver.findElement(locators.countProductInBasketLocator).getAttribute("value"); //получить значение value
        int inputValue = Integer.parseInt(inputText);

        Assert.assertEquals(inputValue, 3);
        clearBasket();
    }

    @Test  //сравнение цены и общей цены на 1 товар
    public void TestBasketPage_EqualsPrice_EqualsPriseAndTotalPriceProduct(){
        //добавить товар в корзину
        addProduct();

        var expectedResult = driver.findElement(locators.firstPriceProductInBasketLocator).getText();
        var actualResult = driver.findElement(locators.firstTotalPriceProductInBasketLocator).getText();

        Assert.assertEquals("Цена и общая стоимость товара в корзине не совпадают", expectedResult, actualResult);
        clearBasket();
    }

    @Test  //расчет цены нескольких товаров, одного наименования
    public void TestBasketPage_TotalPrice_CalculationFinalProductPrice() {
        //добавить товар в корзину
        addProduct();
        driver.findElement(locators.countProductInBasketLocator).clear();
        driver.findElement(locators.countProductInBasketLocator).sendKeys("3"); //изменить значение value

        String textPrice = driver.findElement(locators.firstPriceProductInBasketLocator).getText().replaceAll(regex, "");
        double price = Double.parseDouble(textPrice.replace(",","."));

        //wait.until(ExpectedConditions.visibilityOfElementLocated(locators.countProductLocator));
        String textValue = driver.findElement(locators.countProductInBasketLocator).getAttribute("value");
        var countProduct = Integer.parseInt(textValue);

        var expectedResult = price * countProduct;

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String textTotalPrice = driver.findElement(locators.firstTotalPriceProductInBasketLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(textTotalPrice.replace(",","."));

        Assert.assertEquals("Неверный подсчет стоимости товара", expectedResult, actualResult, 0.00);
        clearBasket();
    }

    @Test  //итоговая стоимость всех товаров
    public void TestBasketPage_ToBePaid_TotalToBePaid() {
        //добавить в корзину два разных товара
        addProductTwo();

        String textTotalPrice1 = driver.findElement(locators.firstTotalPriceProductInBasketLocator).getText().replaceAll(regex, "");
        double price1 = Double.parseDouble(textTotalPrice1.replace(",","."));
        String textTotalPrice2 = driver.findElement(locators.secondTotalPriceProductInBasketLocator).getText().replaceAll(regex, "");
        double price2 = Double.parseDouble(textTotalPrice2.replace(",","."));
        var expectedResult = price1 + price2;

        String toBePaid = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(toBePaid.replace(",","."));

        Assert.assertEquals("Неверный подсчет стоимости покупки", expectedResult, actualResult, 0.00);
        clearBasket();
    }

    @Test //отображение сообщения о применении купона и размер скидки
    public void TestBasketPage_AddCoupon_messageAddCoupon(){
        //добавить товар в корзину
        addProduct();

        //driver.findElement(locators.basketMenuLocator).click();
        driver.findElement(locators.couponBasketLocator).sendKeys(locators.coupon);
        driver.findElement(locators.buttonCouponBasketLocator).click();
        var textMessage = driver.findElement(locators.messageCouponAddLocator).getText();
        var nominalTextSale = driver.findElement(locators.nominalCouponBasketLocator).getText().replaceAll(regex, "");
        double nominalSale = Double.parseDouble(nominalTextSale.replace(",","."));

        Assert.assertTrue("Не отображается сообщение о применении купона", driver.findElement(locators.messageCouponAddLocator).isDisplayed());
        Assert.assertEquals("Неверное сообщение о применении купона", locators.messageCouponAdd, textMessage);
        Assert.assertTrue("Номинал купона не соответствует размеру скидки", locators.nominalCoupon == nominalSale);
        driver.findElement(locators.deleteCouponBasketLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        clearBasket();
    }

    @Test  //отображение сообщения о применении невалидного купона
    public void TestBasketPage_AddInvalidCoupon_messageAddInvalidCoupon(){
        //добавить товар в корзину
        addProduct();

        //driver.findElement(locators.basketMenuLocator).click();
        driver.findElement(locators.couponBasketLocator).sendKeys("sertificat50000");
        driver.findElement(locators.buttonCouponBasketLocator).click();
        var textMessage = driver.findElement(locators.messageInvalidCouponAddLocator).getText();


        Assert.assertTrue("Не отображается сообщение о невалидном купона", driver.findElement(locators.messageInvalidCouponAddLocator).isDisplayed());
        Assert.assertEquals("Неверное сообщение о невалидном купоне", locators.messageInvalidCouponAdd, textMessage);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        clearBasket();
    }

    @Test   //ввод купона и перерасчет стоимости покупки
    public void  TestBasketPage_AddCoupon_MessageAndPriceUpdate(){
        //добавить товар в корзину
        addProduct();

        String toBePaidOld = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double oldPrice = Double.parseDouble(toBePaidOld.replace(",","."));

        driver.findElement(locators.couponBasketLocator).sendKeys(locators.coupon);
        driver.findElement(locators.buttonCouponBasketLocator).click();
        double expectedResult = oldPrice - locators.nominalCoupon;

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String toBePaidNew = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(toBePaidNew.replace(",","."));
        var message = driver.findElement(locators.messageCouponAddLocator).getText();

        Assert.assertEquals("Не выводится сообщение о применении купона", message, locators.messageCouponAdd);
        Assert.assertTrue(expectedResult == actualResult);
        driver.findElement(locators.deleteCouponBasketLocator).click();
        clearBasket();
    }

    @Test  //удаление купона и перерасчет стоимости покупки
    public void TestBasketPage_DeleteCoupon_DeleteAndPriceUpdate(){
        //добавляем товар в корзину
        addProduct();

        driver.findElement(locators.couponBasketLocator).sendKeys(locators.coupon);
        driver.findElement(locators.buttonCouponBasketLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String toBePaidOld = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double oldPrice = Double.parseDouble(toBePaidOld.replace(",","."));

        driver.findElement(locators.deleteCouponBasketLocator).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String toBePaidNew = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double newPrice = Double.parseDouble(toBePaidNew.replace(",","."));
        double actualResult = oldPrice + locators.nominalCoupon;
        var message = driver.findElement(locators.messageCouponAddLocator).getText();

        Assert.assertEquals( "Новая цена не соответствует изменениям", newPrice, actualResult, 0.00);
        Assert.assertEquals("Не выводится сообщение об удалении купона", message, locators.messageCouponDelete);
        clearBasket();
    }

    @Test  //удаление единственного товара и перерасчет стоимости покупки
    public void TestBasketPage_DeleteProduct_DeleteAndMessage() {
        //добавляем товар в корзину
        addProduct();

        driver.findElement(locators.basketMenuLocator).click();
        driver.findElement(locators.deleteProductLocator).click();
        var actualResult = driver.findElement(locators.emptyBasketLocator).getText();

        Assert.assertTrue("Отсутствует сообщение, что корзина пустая", driver.findElement(locators.emptyBasketLocator).isDisplayed());
        Assert.assertTrue("Отсутствует ссылка на возврат товара", driver.findElement(locators.restoreProductLocator).isDisplayed());
        Assert.assertEquals("Сообщение о пустой корзине не соответствует действительности", actualResult, locators.emptyBasket);
        clearBasket();
    }

    @Test  //удаление одного из товаров и перерасчет стоимости покупки
    public void TestBasketPage_DeleteProduct_DeleteAndPriceUpdate() {
        //добавляем несколько товаров в корзину
        addProductTwo();

        //driver.findElement(locators.basketMenuLocator).click();

        String textPriceProduct = driver.findElement(locators.firstTotalPriceProductInBasketLocator).getText().replaceAll(regex, "");
        double priceProduct = Double.parseDouble(textPriceProduct.replace(",","."));
        String textTotalPriceProduct = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double totalPriceProduct = Double.parseDouble(textTotalPriceProduct.replace(",","."));
        var expectedResult = totalPriceProduct - priceProduct;

        driver.findElement(locators.deleteProductLocator).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String textNewTotalPriceProduct = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(textNewTotalPriceProduct.replace(",","."));

        Assert.assertTrue("Отсутствует ссылка на возврат товара", driver.findElement(locators.restoreProductLocator).isDisplayed());
        Assert.assertTrue("После удаления товара сумма покупки не пересчиталась", actualResult == expectedResult);
        clearBasket();
    }

    @Test  //восстановить в корзине удаленный товар перерасчитать стоимости покупки
    public void TestBasketPage_RecoverProduct_RecoverAndPriceUpdate() {
        //добавляем несколько товаров в корзину
        addProductTwo();

        //driver.findElement(locators.basketMenuLocator).click();

        String textTotalPriceProduct = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double expectedResult = Double.parseDouble(textTotalPriceProduct.replace(",","."));

        driver.findElement(locators.deleteProductLocator).click();
        driver.findElement(locators.restoreProductLocator).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locators.overlayLocator));
        String textNewTotalPriceProduct = driver.findElement(locators.priceAllProductBasketLocator).getText().replaceAll(regex, "");
        double actualResult = Double.parseDouble(textNewTotalPriceProduct.replace(",","."));

        Assert.assertTrue("После восстановления товара сумма покупки не пересчиталась", actualResult == expectedResult);
        clearBasket();
    }

    @Test  //изменение количества товара и перерасчет стоимости покупки
    public void TestBasketPage_MakingPurchase_GoPageMakingPurchase() {
        //добавляем товар в корзину
        addProduct();
        driver.findElement(locators.buttonPlaceAnOrderBasketLocator).click();

        Assert.assertEquals("Не осуществлен переход на страницу ОФОРМЛЕНИЯ ЗАКАЗА", locators.linkCheckout, driver.getCurrentUrl());
        clearBasket();
    }

    /*todo  //1. Ввод купона
            //2. Ввод невалидного купона
            //3. расчет стоимости с учетом купона
            //4. удаление купона и перерасчет стоимости покупки
            //5. удаление товара (кнопка слева) и перерасчет стоимости покупки
            //6. изменение количества товара и перерасчет стоимости покупки
            //7. восстановить удаленный товар и пересчитать стоимость покупки
            //8. переход на оформление покупки
            9. проверка покупки товара не превышающего количества, на складе
*/
}
