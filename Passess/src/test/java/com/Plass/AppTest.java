package com.Plass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 * Unit test.
 * mvn clean test -Dwebdriver.chrome.driver="/Users/duolina/Documents/chromedriver_mac64/chromedriver"
 */
public class AppTest 
{
//------ private variables
	protected static WebDriver driver;
	protected static String planitURL = "https://jupiter.cloud.planittesting.com/";
	    	
//------ Lifecycle methods
	@BeforeAll
	   static void setup(){
            System.out.println("@BeforeAll");
		    driver = new ChromeDriver();		
		    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		    driver.get(planitURL);
		}
		
	@AfterAll
		static void tear(){
			System.out.println("@AfterAll executed");			
			//driver.close();
		}
	@BeforeEach
		void setupThis(){
			System.out.println("@BeforeEach executed");
		}
	@AfterEach
		void tearThis(){
	    	System.out.println("@AfterEach executed");
	        driver.manage().deleteAllCookies();
		}
 //------ Test cases
	 //------ Test case 1
@Test
    public void test01()
    {
    	System.out.println("@Test-01 executed");
    	
    	String expectedForeNameError = "Forename is required";
    	String expectedEMailError = "Email is required";
    	String expectedMessageError = "Message is required";
    	
	    driver.navigate().to(planitURL);  
        driver.manage().window().maximize();

        //Click submit button
        driver.findElement(By.xpath("//li[@id='nav-contact']/a")).click();        
        driver.findElement(By.xpath("//div[@class='form-actions']/a")).click();
          
	    //Get WebElements based on its xpath
        WebElement nameErorWE = driver.findElement(By.xpath("//span[@id='forename-err']"));
        WebElement mailErrorWE = driver.findElement(By.xpath("//span[@id='email-err']"));
        WebElement messageErrorWE = driver.findElement(By.xpath("//span[@id='message-err']"));
        
        //Retrieve error message
	    String resultName = nameErorWE.getText().trim();
	    String resultEmail = mailErrorWE.getText().trim();
	    String resultMessage = messageErrorWE.getText().trim();

	    //Verify error messages
	    Assertions.assertEquals(expectedForeNameError, resultName);
	    Assertions.assertEquals(expectedEMailError, resultEmail);
	    Assertions.assertEquals(expectedMessageError, resultMessage);
	    	    	    
	    //Populate mandatory fields
	    WebElement forenameWE = driver.findElement(By.xpath("//input[@id='forename']"));
	    WebElement emailWE = driver.findElement(By.xpath("//input[@id='email']"));
	    WebElement messageWE = driver.findElement(By.xpath("//textarea[@id='message']"));
	    forenameWE.sendKeys("John");
	    emailWE.sendKeys("John.example@planit.net.au");
	    messageWE.sendKeys("They are great!");	
	    
	    Assertions.assertEquals(forenameWE.getAttribute("value"), "John");
	    Assertions.assertEquals(emailWE.getAttribute("value"), "John.example@planit.net.au");
	    Assertions.assertEquals(messageWE.getAttribute("value"), "They are great!");
	    
	    //Verify error messages are gone
	    List<WebElement> foremaneErrors = driver.findElements(By.xpath("//*[contains(text(),'Forename is required')]"));
	    List<WebElement> emailErrors = driver.findElements(By.xpath("//*[contains(text(),'Email is required')]"));
	    List<WebElement> mesageErrors = driver.findElements(By.xpath("//*[contains(text(),'Message is required')]"));
	    Assertions.assertEquals(foremaneErrors.size(), 0);
	    Assertions.assertEquals(emailErrors.size(), 0);
	    Assertions.assertEquals(mesageErrors.size(), 0);
	    
	    //Click submit button
	    driver.findElement(By.xpath("//div[@class='form-actions']/a")).click();
    }    
   
//------ Test case 2
@Test
    public void test02()
    {
	    System.out.println("@Test-02 executed");
	
        //Launch website
        driver.navigate().to(planitURL);    
        driver.manage().window().maximize();

        //navigage to Contact panel
        driver.findElement(By.xpath("//li[@id='nav-contact']/a")).click();
    
        //Input the info
        driver.findElement(By.xpath("//input[@id='forename']")).sendKeys("John");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("John.example@planit.net.au");
        driver.findElement(By.xpath("//textarea[@id='message']")).sendKeys("They are great!");
     
        //Click submit button
        driver.findElement(By.xpath("//div[@class='form-actions']/a")).click();
     
        // Validate successful submission message
        // String greetingInfo = driver.findElement(By.xpath("//div[@class='alert alert-success']/strong[@class='ng-binding']")).getText().trim();
        List<WebElement> greetingInfo = driver.findElements(By.xpath("//div[@class='alert alert-success']"));
        Assertions.assertEquals(1, greetingInfo.size());    
}    

//------ Test case 3
@Test
    public void test03()
    {
	    System.out.println("@Test-03 executed");
	
	    String invalidForeName = "";
	    String invalidEMail = "xx";
	    String invalidMessage = "";
	
	    String expectedeErrorForeName = "Forename is required";
	    String expectedErrorEMail = "Please enter a valid email";
	    String expectedErrorMessage = "Message is required";
	
        //Launch website
        driver.navigate().to(planitURL);    
        driver.manage().window().maximize();

        //navigage to Contact panel
        driver.findElement(By.xpath("//li[@id='nav-contact']/a")).click();
    
        //Populate mandatory fields with invalidate data
        driver.findElement(By.xpath("//input[@id='forename']")).sendKeys(invalidForeName);
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(invalidEMail);
        driver.findElement(By.xpath("//textarea[@id='message']")).sendKeys(invalidMessage);
     
        //Click submit button
        driver.findElement(By.xpath("//div[@class='form-actions']/a")).click();        
    
        //Validate error messages
        String errorName = driver.findElement(By.xpath("//span[@id='forename-err']")).getText().trim();
        String errorEmail = driver.findElement(By.xpath("//span[@id='email-err']")).getText().trim();
        String errorMessage = driver.findElement(By.xpath("//span[@id='message-err']")).getText().trim();
     
        System.out.println("====> " + errorName);
        System.out.println("====> " + errorEmail);
        System.out.println("====> " + errorMessage);
    
        //Verify error messages
        Assertions.assertEquals(expectedeErrorForeName, errorName);
        Assertions.assertEquals(expectedErrorEMail, errorEmail);
        Assertions.assertEquals(expectedErrorMessage, errorMessage);
}    

//------ Test case 4
@Test
        public void test04()
        {
            System.out.println("@Test-04 executed");
    
            // set expected values:
            String expectedItem_FunnyCow = "Funny Cow";
            String expectedItem_FluffyBunny = "Fluffy Bunny";
            int expectedQuantity_FunnyCow = 2;
            int expectedQuantity_FluffyBunny = 1;
    
    
            //Launch website
            //driver.navigate().to(planitURL);
 
            //Maximize the browser
            driver.manage().window().maximize();
     
            //navigate to shop page
            driver.findElement(By.xpath("//a[@href='#/shop']")).click();
     
            //Buy 2 Funny Cow
            for (int i = 0; i < 2; i++) {
    	         driver.findElement(By.xpath("//li[@id='product-6']//a")).click();
         }
    
            // Fluggy Bunny: one
            driver.findElement(By.xpath("//li[@id='product-4']//a")).click();
     
            //navigate to cart page
            driver.findElement(By.xpath("//a[@href='#/cart']")).click();
     
            // find the quantity of Funny Cow
            WebElement cowWEName = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]"));
            WebElement cowWEQuantity = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/input"));
            String cowName = cowWEName.getText().trim();
            int cowQuantity = Integer.parseInt(cowWEQuantity.getAttribute("value"));
            System.out.println(" Cow Name -----> [" + cowName + "]");
            System.out.println(" Cow Quantity -----> " + cowQuantity);
    
            // find the quantity of Fluffy Bunny
            WebElement bunnyWEName = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
            WebElement bunnyWEQuantity = driver.findElement(By.xpath("//table/tbody/tr[2]/td[3]/input"));
            String bunnyName = bunnyWEName.getText().trim();
            int bunnyQuantity = Integer.parseInt(bunnyWEQuantity.getAttribute("value"));
            System.out.println(" Bunny Name -----> [" + bunnyName + "]");
            System.out.println(" Bunny Quantity -----> " + bunnyQuantity);
	
            // validate purchased items
        	Assertions.assertEquals(expectedItem_FluffyBunny, bunnyName);
        	Assertions.assertEquals(expectedQuantity_FluffyBunny, bunnyQuantity);
	
        	Assertions.assertEquals(expectedItem_FunnyCow, cowName);
         	Assertions.assertEquals(expectedQuantity_FunnyCow, cowQuantity);     
         }    	

}
