package me.bucklb.simpleBootdemo.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Home page says "There's no place like it" or some such
    @Test
    public void homeShouldReturnHomeMessage() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("no place")));
    }

    // Ought to have "info redirects".  Perhaps should check where it redirects to ...
    @Test
    public void infoShouldReturnRedirect() throws Exception {
        this.mockMvc
                .perform(get("/info/"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost:-1/swagger-ui.html"))
                ;
        System.out.println("X");
    }

    // Ought to have "info redirects".  Perhaps should check where it redirects to ...
    @Test
    public void swaggerShouldReturnRedirect() throws Exception {
        this.mockMvc
                .perform(get("/info/"))
                .andDo(print())
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("http://localhost:-1/swagger-ui.html"))
        ;
        System.out.println("X");
    }

    // Tested that stuff redirects to where swagger should be.  Is it actually where it should be though??
    @Test
    public void swaggerIsShown() throws Exception {
        this.mockMvc
                .perform(get("/swagger-ui.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Swagger UI")))
        ;
        System.out.println("X");
    }




}
