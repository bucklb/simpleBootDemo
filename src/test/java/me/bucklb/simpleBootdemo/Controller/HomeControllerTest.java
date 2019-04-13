package me.bucklb.simpleBootdemo.Controller;

import me.bucklb.simpleBootdemo.service.HomeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    Do it without using restAssured in this case
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeControllerTest {

    // Framework that the tests will hang off.  Instantiated in setUp
    private MockMvc mockMvc;

    // Not testing the service, so mock it
    @Mock
    private HomeService mockService;

    // Controller needs its mocks
    @InjectMocks
    HomeController homeController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }


    // Home page says "There's no place like it" or some such
    @Test
    public void homeShouldReturnHomeMessage() throws Exception {

        // Mock the service
        String greeting = "G1bber15h!!!!";
        when(mockService.greeting()).thenReturn(greeting);

        this.mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(greeting)));
    }

    // Ought to have "info redirects".  Perhaps should check where it redirects to ...
    @Test
    public void infoShouldReturnRedirect() throws Exception {
        this.mockMvc
                .perform(get("/info/"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost:0/swagger-ui.html"))
                ;
    }

    // Ought to have "info redirects".  Perhaps should check where it redirects to ...
    @Test
    public void swaggerShouldReturnRedirect() throws Exception {
        this.mockMvc
                .perform(get("/info/"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost:0/swagger-ui.html"))
        ;
    }

    // Need to work out how to get the swagger stuff, but not really something the controller is involved with !!!!
//    // Tested that stuff redirects to where swagger should be.  Is it actually where it should be though??
//    @Test
//    public void swaggerIsShown() throws Exception {
//        this.mockMvc
//                .perform(get("/swagger-ui.html"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Swagger UI")))
//        ;
//    }




}
