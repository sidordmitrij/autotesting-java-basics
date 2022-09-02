package test.java;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RegistrationPageTests {
    static Locators locators = new Locators();

    private static WebDriver driver;
    private static WebDriverWait wait;
    //Actions actionProvider = new Actions(driver);
    //private String regex = "[^\\d,]";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
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

    @Test  //Успешная регистрация (ввод валидных данных)
    public void TestRegistrationPage_ValidDateRegistration_SuccessfulRegistration(){
        driver.navigate().to(locators.linkRegistration);
        driver.findElement(locators.userNameRegLocator).sendKeys("111");
        driver.findElement(locators.emailRegLocator).sendKeys("111@111.ru");
        driver.findElement(locators.passwordRegLocator).sendKeys("111");
        driver.findElement(locators.buttonRegisterLocator).click();
        var actualResult = driver.findElement(locators.messageRegistrationLocator).getText();
        Assert.assertTrue("Отсутствует сообщение об успешной регистрации", driver.findElement(locators.messageRegistrationLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения о регистрации не соответствует действительности", locators.messageRegistration, actualResult);
    }

    @Test  //регистрация имеющегося аккаунта
    public void TestRegistrationPage_Registration_DoubleName() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.emailRegLocator).sendKeys("111@111.ru");
        driver.findElement(locators.passwordRegLocator).sendKeys("111");
        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о регистрации имеющегося аккаунта", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageErrorDoubleEmail, actualResult);
    }

    @Test  //переход в форму авторизации при вводе имеющегося логина
    public void TestRegistrationPage_Registration_GoAuthorizationPage() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.emailRegLocator).sendKeys("111@111.ru");
        driver.findElement(locators.passwordRegLocator).sendKeys("111");
        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        driver.findElement(locators.linkShowloginLocator).click();
        //var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertEquals("Ссылка в форме регистрации не перенаправляет к форме авторизации",locators.linkMyAccount, driver.getCurrentUrl());
    }

    @Test   //регистрация с пустыми полями ввода
    public void TestRegistrationPage_Registration_EmptyData() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поля", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения об ошибке не соответствует действительности", locators.messageRegErrorEmail, actualResult);
    }

    @Test   //регистрация с пустым полем ИМЯ
    public void TestRegistrationPage_Registration_EmptyName() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.emailRegLocator).sendKeys("123@111.ru");
        driver.findElement(locators.passwordRegLocator).sendKeys("111");
        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поле ИМЯ", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения об ошибке не соответствует действительности", locators.messageRegErrorName, actualResult);
    }

    @Test   //регистрация с пустым полем Email
    public void TestRegistrationPage_Registration_EmptyEmail() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.userNameRegLocator).sendKeys("111");
        driver.findElement(locators.passwordRegLocator).sendKeys("111");
        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поле EMAIL", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения об ошибке не соответствует действительности", locators.messageRegErrorEmail, actualResult);
    }

//    @Test   //ввод невалидного Email
//    public void TestRegistrationPage_Registration_InvalidEmail() {
//        driver.navigate().to(locators.linkRegistration);
//        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//
//        driver.findElement(locators.emailRegLocator).sendKeys("111");
//        driver.findElement(locators.passwordRegLocator).sendKeys("111");
//        driver.findElement(locators.buttonRegisterLocator).click();
//        //wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
//        //var actualResult = driver.findElement(locators.messageErrorLocator).getText();
//
//        //Assert.assertTrue(driver.findElement(locators.messageErrorLocator).isDisplayed());
//        //Assert.assertEquals(locators.messageRegErrorEmail, actualResult);
//        Assert.assertEquals(regexEmail, "111");
//    }  //todo доработать проверку валидности email


    @Test   //регистрация с пустым полем Пароль
    public void TestRegistrationPage_Registration_EmptyPassword() {
        driver.navigate().to(locators.linkRegistration);

        driver.findElement(locators.userNameRegLocator).sendKeys("111");
        driver.findElement(locators.emailRegLocator).sendKeys("111@111.ru");
        driver.findElement(locators.buttonRegisterLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поле ПАРОЛЬ", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения об ошибке не соответствует действительности", locators.messageErrorDoubleEmail, actualResult);
    }

    @Test   //переход к форме регистрации из формы авторизации
    public void TestRegistrationPage_Authorization_GoRegistrationForm(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.buttonRegisterAutLocator).click();
        Assert.assertEquals("Не осуществлен переход к форме регистрации", locators.linkRegistration, driver.getCurrentUrl());
    }

    @Test    //авторизация по имени
    public void TestRegistrationPage_AuthorizationByName_SuccessfulAuthorization(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("111");
        //var expectedResult = name.getText();
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageNameInWelcomeLocator));
        var actualResult = driver.findElement(locators.messageNameInWelcomeLocator).getText();

        Assert.assertTrue("В шапке отстутствует приветственное сообщение", driver.findElement(locators.messageNameInWelcomeLocator).isDisplayed());
        Assert.assertEquals("Имя, в приветственном сообщении не соответствует введенному при авторизации", "111", actualResult);
    }

    @Test    //авторизация по EMAIL
    public void TestRegistrationPage_AuthorizationByEmail_SuccessfulAuthorization(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("111@111.ru");
        //var expectedResult = email.getText();
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageNameInWelcomeLocator));
        var actualResult = driver.findElement(locators.messageNameInWelcomeLocator).getText();

        Assert.assertTrue("В шапке отстутствует приветственное сообщение", driver.findElement(locators.messageNameInWelcomeLocator).isDisplayed());
        Assert.assertEquals("Имя, в приветственном сообщении не соответствует введенному при регистрации", "111", actualResult);
    }

    @Test    //авторизация с пустыми полями
    public void TestRegistrationPage_Authorization_EmptyData(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поля для авторизации", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageAuthNameRequired, actualResult);
    }

    @Test    //авторизация с пустым полем ИМЯ
    public void TestRegistrationPage_Authorization_EmptyName(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поле ИМЯ для авторизации", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageAuthNameRequired, actualResult);
    }

    @Test    //авторизация с пустым полем ПАРОЛЬ
    public void TestRegistrationPage_Authorization_EmptyPassword(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о необходимости заполнить поле ПАРОЛЬ для авторизации", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageAuthPasswordRequired, actualResult);
    }

    @Test    //авторизация с неверным именем
    public void TestRegistrationPage_Authorization_InvalidName(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("222");
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о неверном имени", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageAuthInvalidName, actualResult);
    }

    @Test    //авторизация с неверным email
    public void TestRegistrationPage_Authorization_InvalidEmail(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("222@222.ru");
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о неверном имени", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageAuthInvalidEmail, actualResult);
    }

    @Test    //авторизация с неверным паролем
    public void TestRegistrationPage_Authorization_InvalidPassword(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("111");
        //var expectedResult = driver.findElement(locators.userNameAutLocator).getText();
        driver.findElement(locators.passwordAutLocator).sendKeys("222");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorNameLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о неверном имени", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", "111", actualResult);
    }

    @Test    //запомнить меня
    public void TestRegistrationPage_Authorization_SelectRememberMe(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.userNameAutLocator).sendKeys("111");
        driver.findElement(locators.passwordAutLocator).sendKeys("111");
        driver.findElement(locators.buttonAuthorizationLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageNameInWelcomeLocator));
        driver.findElement(locators.buttonExitMyAccountLocator).click();
        driver.findElement(locators.buttonLogInLocator).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(locators.userNameAutLocator));
        var actualResultName = driver.findElement(locators.userNameAutLocator).getText();
        var actualResultPassword = driver.findElement(locators.passwordAutLocator).getText();

        Assert.assertEquals("Имя не сохранено", "111", actualResultName);
        Assert.assertEquals("Пароль не сохранен", "111", actualResultPassword);
    }

    @Test  //переход к форме восстановления пароля
    public void TestRegistrationPage_Authorization_FormPasswordRecovery(){
        driver.navigate().to(locators.linkMyAccount);

        driver.findElement(locators.forgotPasswordAutLocator).click();
        var actualResult = driver.findElement(locators.postTitleLocator).getText();

        Assert.assertEquals("Не осуществлен переход к форме восстановления пароля", locators.linkLostPassword, driver.getCurrentUrl());
        Assert.assertEquals("Не отобразилась форма восстановления пароля", actualResult, "Восстановление пароля");
    }

    @Test   //сброс пароля
    public void TestRegistrationPage_PasswordRecovery (){
        driver.navigate().to(locators.linkLostPassword);

        driver.findElement(locators.userLoginPasswordRecoveryLocator).sendKeys("111");
        driver.findElement(locators.buttonPasswordRecoveryLocator).click();
        var actualResult = driver.findElement(locators.messagePasswordRecoveryLocator).getText();

        Assert.assertTrue("Не отобразилось сообщение о сбросе пароля", driver.findElement(locators.messagePasswordRecoveryLocator).isDisplayed());
        Assert.assertEquals("Сообщение о сбросе пароля не соответствует действительности", locators.messagePasswordRecovery, actualResult);
    }

    @Test   //сброс пароля без введения имени или email
    public void TestRegistrationPage_PasswordRecovery_EmptyData (){
        driver.navigate().to(locators.linkLostPassword);

        driver.findElement(locators.buttonPasswordRecoveryLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о пустом поле", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageEmptyDataPasswordRecovery, actualResult);
    }

    @Test   //сброс пароля при вводе невалидных данных
    public void TestRegistrationPage_PasswordRecovery_InvalidData (){
        driver.navigate().to(locators.linkLostPassword);

        driver.findElement(locators.userLoginPasswordRecoveryLocator).sendKeys("222");
        driver.findElement(locators.buttonPasswordRecoveryLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.messageErrorLocator));
        var actualResult = driver.findElement(locators.messageErrorLocator).getText();

        Assert.assertTrue("Отсутствует сообщение о неверном имени", driver.findElement(locators.messageErrorLocator).isDisplayed());
        Assert.assertEquals("Текст сообщения не соответствует действительности", locators.messageInvalidDataPasswordRecovery, actualResult);
    }
}
