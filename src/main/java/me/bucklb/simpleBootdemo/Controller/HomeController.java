package me.bucklb.simpleBootdemo.Controller;

// Needed for annotation
import me.bucklb.simpleBootdemo.ErrorHandling.BootDemoRunTimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    BootDemoRestTemplate restTemplate;


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
//    public String getPing(HttpServletResponse httpServletResponse) {
    public ResponseEntity<String> getPing(HttpServletResponse httpServletResponse) {

//        logger.info("pinged");
        showSleuth("ping");

        if(2>1) {
            // Let system do our headers
            ResponseEntity<String> re = restTemplate.getForEntity(REDIRECT_RTE + APP_PORT + "/pong", String.class);
            String pong = re.getBody();
            return new ResponseEntity<String>("ping " + pong, HttpStatus.OK);
        } else {


            // Kick off to further end point so I can see the effect on headers
            String pong = restTemplate.getForObject(REDIRECT_RTE + APP_PORT + "/pong", String.class);
//        return "ping " + pong;
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Sleuth", "Strewth");
            return new ResponseEntity<String>("ping " + pong, httpHeaders, HttpStatus.OK);
        }


    }

    /*
        Primarily want to check that sleuth headers get detected
     */
    @RequestMapping(value="/pong",method = RequestMethod.GET)
    public String getPong(HttpServletResponse httpServletResponse) {

        showSleuth("pong");

//        String pang = restTemplate.getForObject(REDIRECT_RTE+APP_PORT+"/pang" ,String.class);

        ResponseEntity<String> re= restTemplate.getForEntity(REDIRECT_RTE+APP_PORT+"/pang" ,String.class);
        String pang = re.getBody();


        return "ponged " + pang;




    }

    /*
        Primarily want to check that sleuth headers get detected
     */
    @RequestMapping(value="/pang",method = RequestMethod.GET)
    public ResponseEntity<String> getPang(HttpServletResponse httpServletResponse) {

//        logger.info("panged");
        showSleuth("pang");

        // Force returning with headers
//        return "panged";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Sleuth","Strewth");

        int flg=2;


        if (flg>3) {
            // Throw a non-special exception and see how things cope.
            throw new RuntimeException("Bang!");
        } else {

            if (flg>1){
                // Step I - throw BootDemoRTE that will become an ErrMsg & in turn another BootDemoRTE, etc

                // throw "special" exception to test how we can elegantly handle stuff

                // This should get picked up by exception handler
                // and then restTemplate's errorHandler,
                // that will in turn raise an exception
                // and repeat ....
                // Key bit is to throw exception, to trigger returning an ErrorMessage in theresponse
                System.out.println("getPang throwing an exception ...");
                if(0>1) {
                    throw new BootDemoRunTimeException("418", "Tea Time!!");
                } else {
                    // two values back from a service (in the way that view-nisp-stub doesn't)
                    throw new BootDemoRunTimeException(homeService.getPangErrorResponse());
                }
            } else {
                // a benign response
                return new ResponseEntity<String>("panged", httpHeaders, HttpStatus.OK );
            }
        }
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
