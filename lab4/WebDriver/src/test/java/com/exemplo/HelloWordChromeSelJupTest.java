package com.exemplo;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;


import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class HelloWordChromeSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(ChromeDriver driver){

        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.debug("The title of {} is {}", sutUrl, title);

        assert(title.equals("Hands-On Selenium WebDriver with Java"));
    }
}
