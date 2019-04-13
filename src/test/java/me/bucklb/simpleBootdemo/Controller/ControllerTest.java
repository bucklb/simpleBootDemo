package me.bucklb.simpleBootdemo.Controller;

/*
    Controller tests should be about the CONTROLLER (rather than the MVC).  Ought to mock out service to return a known greeting ...

    THIS FLAVOUR WILL USE REST-ASSURED

 */

import me.bucklb.simpleBootdemo.service.HomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// RestAssured approach
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

    // Mock the service as it's not really being tested
    @Mock
    HomeService mockService;

    // Inject the mocked service in to the controller
    @InjectMocks
    HomeController testController;

    @Test
    public void NodAndSmile(){
    }

    @Test
    public void homeMessage() {

        String greeting = "G1bber15h!!!!";
        // Need to tell the mocked service what to respond with ...
        when(mockService.greeting()).thenReturn(greeting);

        given().
                standaloneSetup(testController).
                when().
                get("").
                then().
                statusCode(200).
                body(equalTo(greeting));
    }

    @Test
    public void swaggerRedirects() {
        given().
                standaloneSetup( testController ).
                when().
                get("/swagger").
                then().
                statusCode(302).
                header("Location",containsString("swagger-ui.html"));
    }

    @Test
    public void infoRedirects() {
        given().
                standaloneSetup( testController ).
                when().
                get("/info").
                then().
                statusCode(302).
                header("Location",containsString("swagger-ui.html"));
    }
}
