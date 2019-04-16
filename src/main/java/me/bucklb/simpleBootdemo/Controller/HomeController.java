package me.bucklb.simpleBootdemo.Controller;

// Needed for annotation
import me.bucklb.simpleBootdemo.BootRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import me.bucklb.simpleBootdemo.service.HomeService;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RestTemplate restTemplate;


    @Value("${server.port}")
    int APP_PORT;

    Logger logger = LoggerFactory.getLogger(HomeController.class);



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

    /*
        Primarily want to check that sleuth headers get detected
     */
    @RequestMapping(value="/ping",method = RequestMethod.GET)
    public String getPing(HttpServletResponse httpServletResponse) {

//        logger.info("pinged");
        showSleuth("ping");

        // Kick off to further end point so I can see the effect on headers
        String pong = restTemplate.getForObject(REDIRECT_RTE+APP_PORT+"/pong" ,String.class);
        return "ping " + pong;
    }

    /*
        Primarily want to check that sleuth headers get detected
     */
    @RequestMapping(value="/pong",method = RequestMethod.GET)
    public String getPong(HttpServletResponse httpServletResponse) {

        showSleuth("pong");

        String pang = restTemplate.getForObject(REDIRECT_RTE+APP_PORT+"/pang" ,String.class);
        return "ponged " + pang;
    }

    /*
        Primarily want to check that sleuth headers get detected
     */
    @RequestMapping(value="/pang",method = RequestMethod.GET)
    public String getPang(HttpServletResponse httpServletResponse) {

//        logger.info("panged");
        showSleuth("pang");

        return "panged";
    }

    /*
        Show what's going on with sleuth as we pass through (specifically what happens with parent/span
     */
    private void showSleuth(String msg) {

        // Might as well do the logging as we dump the debug
        logger.info(msg);


        // Ideally want null to be displayed as same length string ...
//        String parentId = MDC.get("parentId")==null ? "null            " : MDC.get("parentId");
//        System.out.println("sleuth  ->   trace=" + MDC.get("traceId") + "   parent=" + parentId    + "   span=" + MDC.get("spanId"));

    }

/*
    To seed the situation need to pass in BOTH X-B3_TraceId & X-B3-SpanId, which need to be hex (up to 16 characters)

    Interplay of trace & spanId demonstrated via ping-pong-pang.  The original span becomes the parent of the next span
        sleuth  ->   trace=005b115a92999999   parent=null               span=005b115a92666666
        sleuth  ->   trace=005b115a92999999   parent=005b115a92666666   span=32d806e5f27ee63e
        sleuth  ->   trace=005b115a92999999   parent=32d806e5f27ee63e   span=95a0a0998927ead9


    !! INTERESTINGLY !! Looks like the sleuth logging happens regardless of any requested logging !!!
    BUT this is a function of the sleuthConfig & tracer stuff.  If they get removed the extra logging goes with them. See effect by commenting out the bean

    EXAMPLE of important bits of the logging.
    The start part of the logs are in order :
        2019-04-14 22:34:41.144  INFO [-,,,] 15012 --- [nio-8090-exec-2] brave.Tracer
        2019-04-14 22:34:41.145  INFO [-,,,] 15012 --- [nio-8090-exec-3] brave.Tracer
        2019-04-14 22:34:41.149  INFO [-,,,] 15012 --- [nio-8090-exec-2] brave.Tracer

    BUT THE PAYLOADS are not (payloads appear as 2,3,1.  Look like:

        {
           "traceId": "acca6d9a1b5bf929",
           "parentId": "acca6d9a1b5bf929",
           "id": "6ea65201854a3310",
           "kind": "CLIENT",
           "name": "get",
           "timestamp": 1555277681098171,
           "duration": 36452,
           "localEndpoint": {
              "serviceName": "\"simplebootdemonstration\"",
              "ipv4": "192.168.1.11"
           },
           "tags": {
              "http.method": "GET",
              "http.path": "/pong"
           }
        }


        {
           "traceId": "acca6d9a1b5bf929",
           "parentId": "acca6d9a1b5bf929",
           "id": "6ea65201854a3310",
           "kind": "SERVER",
           "name": "get /pong",
           "timestamp": 1555277681113589,
           "duration": 23049,
           "localEndpoint": {
              "serviceName": "\"simplebootdemonstration\"",
              "ipv4": "192.168.1.11"
           },
           "remoteEndpoint": {
              "ipv4": "127.0.0.1",
              "port": 54070
           },
           "tags": {
              "http.method": "GET",
              "http.path": "/pong",
              "mvc.controller.class": "HomeController",
              "mvc.controller.method": "getPong"
           },
           "shared": true
        }

        {
           "traceId": "acca6d9a1b5bf929",
           "id": "acca6d9a1b5bf929",
           "kind": "SERVER",
           "name": "get /ping",
           "timestamp": 1555277681059019,
           "duration": 90779,
           "localEndpoint": {
              "serviceName": "\"simplebootdemonstration\"",
              "ipv4": "192.168.1.11"
           },
           "remoteEndpoint": {
              "ipv6": "::1",
              "port": 54069
           },
           "tags": {
              "http.method": "GET",
              "http.path": "/ping",
              "mvc.controller.class": "HomeController",
              "mvc.controller.method": "getPing"
           }
        }








 */


}
