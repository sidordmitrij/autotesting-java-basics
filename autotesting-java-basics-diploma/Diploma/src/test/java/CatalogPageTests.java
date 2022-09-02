package test.java;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CatalogPageTests {
    Locators locators = new Locators();

    private static WebDriver driver;
    private static WebDriverWait wait;
    //Actions actionProvider = new Actions(driver);
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

    @Test    //переход на главную страницу
    public void TestForCatalogPage_GoMainPage(){
        driver.navigate().to(locators.linkCatalog);

        driver.findElement(locators.mainPageMenuLocator).click();
        Assert.assertEquals("Не осуществлен переход на главную страницу", locators.linkMainPage, driver.getCurrentUrl());
    }

    @Test   //переход в выбранную категори товаров
    public void TestForCatalogPage_GoCategory_TransitionSelectedCategory(){
        driver.navigate().to(locators.linkCatalog);

        var expectedResult = driver.findElement(locators.nameCategoryLocator).getText().toLowerCase();
        driver.findElement(locators.nameCategoryLocator).click();

        var actualResult = driver.findElement(locators.headerCatalogSectionLocator).getText().toLowerCase();
        Assert.assertEquals("Заголовок не соответствует выбранной категории товара", expectedResult, actualResult);
    }

    @Test  //проверка соответствия количества товаров в выбранной категории
    public void TestForCatalogPage_MatchingQuantityOfGoods_CounterCorrespondsQuantityOfProduct(){
        driver.navigate().to(locators.linkCatalog);

        driver.findElement(locators.nameCategoryLocator).click();
        String regexExpected = "[^\\d*]";
        var expectedResult = driver.findElement(locators.countProductLocator).getText().replaceAll(regexExpected, "");
        int count = Integer.parseInt(expectedResult);

        if (count == 0){
            Assert.assertEquals("Найденный товар не соответствует заданному в строке поиска", "По вашему запросу товары не найдены.",
                    driver.findElement(locators.productIsMissingLocator).getText());
            return;
        }

        String str = driver.findElement(locators.countProductInCategoryLocator).getText();
        var actualResult1 = driver.findElement(locators.countProductInCategoryLocator).getText().replaceAll(regexExpected, "");
        String actualResult2 = str.substring(str.lastIndexOf(" ")+1);

        if (count > 0 && count <= 12){
            Assert.assertEquals("Количество товара не соответствует показаниям счетчика", expectedResult, actualResult1);
        }
        if (count > 12) {
            Assert.assertEquals("Количество товара не соответствует показаниям счетчика", expectedResult, actualResult2);
        }
    }

    @Test  //пагинация на следующую страницу
    public void TestForCatalogPage_Pagination_NextPage(){
        driver.navigate().to(locators.linkCatalog);

        driver.findElement(locators.paginationNextLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText();
        Assert.assertEquals("Page 2", actualResult);
    }

    @Test  //пагинация на предыдущую страницу
    public void TestForCatalogPage_Pagination_PrevPage(){
        driver.navigate().to(locators.linkCatalog);

        var expectedResult = driver.findElement(locators.lastElementSiteTreeLocator).getText();
        driver.findElement(locators.paginationNextLocator).click();

        driver.findElement(locators.paginationBackLocator).click();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test  //пагинация на произвольную страницу
    public void TestForCatalogPage_Pagination_ArbitraryPage(){
        driver.navigate().to(locators.linkCatalog);

        String regex = "[^\\d]";
        var expectedResult = driver.findElement(locators.paginationCustomPageLocator).getText();
        driver.findElement(locators.paginationCustomPageLocator).click();
        //var expectedResult = driver.findElement(By.cssSelector(".page-numbers li:nth-child(4) span")).getText();
        var actualResult = driver.findElement(locators.lastElementSiteTreeLocator).getText().replaceAll(regex, "");
        Assert.assertEquals(actualResult, expectedResult);

    }
}
