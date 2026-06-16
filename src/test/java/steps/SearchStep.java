package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SearchStep {

    WebDriver driver;

    @Given("booking search page is opened")
    public void bookingSearchPageIsOpened() {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
    }

    @When("user searches for {string}")
    public void userSearchesFor(String hotel) {
        driver.findElement(By.xpath("//input[@name='ss']")).sendKeys(hotel);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @Then("{string} hotel is shown")
    public void hotelIsShown(String expectedResult) {
        List<WebElement> titles = driver.findElements(By.cssSelector("[data-testid=title]"));
        boolean isHotelFound = false;
        for (WebElement title : titles) {
            if(title.getText().equals(expectedResult)) {
             isHotelFound = true;
             break;
            }
        }
        Assert.assertTrue(isHotelFound);
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public  void tearDown() {
        if (driver!=null) {
            driver.quit();
        }
    }

    @And("hotel rating is {string}")
    public void hotelRatingIs(String expectedScore) {
       String actualScore =
               driver.findElement(By.xpath("//div[@data-testid='review-score']/div[@aria-hidden='true']")).getText();
       Assert.assertEquals(expectedScore, actualScore);
    }

    @And("{string} is shown")
    public void hotelRatingIsShown(String expectedResult) {
        List<WebElement> ratings = driver.findElements(By.xpath("//div[@data-testid='review-score']/div[@aria-hidden='true']"));
        boolean isRatingFound = false;
        for (WebElement rating : ratings) {
            if(rating.getText().equals(expectedResult)) {
                isRatingFound = true;
                break;
            }
        }
        Assert.assertTrue(isRatingFound);
    }
}
