package me.bucklb.simpleBootdemo.Controller;

// Needed for annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import me.bucklb.simpleBootdemo.service.HomeService;

import javax.servlet.http.HttpServletResponse;

/**
 *  @Author     me
 *  @Purpose    handle stuff around home (could add actuator later?)
*/
@RequestMapping("")
@RestController
public class HomeController {

    @Autowired
    HomeService homeService;

    @Value("${server.port}")
    int APP_PORT;

    // Need to push people to where swagger lives ... use redirect
    String REDIRECT_RTE="http://localhost:";
    String REDIRECT_PTH="/swagger-ui.html";
    int    REDIRECT_CDE=302;

    String BR="<br>";

    @RequestMapping(value="/info",method = RequestMethod.GET)
    public void getInfo(HttpServletResponse httpServletResponse) {
        reDirectToSwagger(httpServletResponse);
    }

    @RequestMapping(value="/swagger",method = RequestMethod.GET)
    public void getSwagger(HttpServletResponse httpServletResponse) {
        reDirectToSwagger(httpServletResponse);
    }

    // Get a greeting from the service an return that
    @RequestMapping(value="",method = RequestMethod.GET)
    public String getHome() {
        return homeService.greeting();
    }

    // Generate the response that will redirect caller to where swagger stuff is
    private void reDirectToSwagger(HttpServletResponse httpServletResponse) {
        String redirect=REDIRECT_RTE + APP_PORT + REDIRECT_PTH;
        httpServletResponse.setHeader("Location", redirect);
        httpServletResponse.setStatus(REDIRECT_CDE);
    }

}
