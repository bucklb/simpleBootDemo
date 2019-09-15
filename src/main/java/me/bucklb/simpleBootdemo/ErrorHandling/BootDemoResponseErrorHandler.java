package me.bucklb.simpleBootdemo.ErrorHandling;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.io.CharStreams;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/*
    Deal with stuff coming back from a (http) response with status other than 200...
 */
public class BootDemoResponseErrorHandler implements ResponseErrorHandler {

    private final List<HttpStatus> acceptableResponseCodes;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Bespoke constructor (?? why no super() ??)
    public BootDemoResponseErrorHandler(List<HttpStatus> acceptableResponseCodes) {
        this.acceptableResponseCodes = acceptableResponseCodes;
////        this.objectMapper.registerModule(new JodaModule());
    }


    // What do we treat as an error (as opposed to Business As Usual)?
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {

        HttpStatus httpStatus = clientHttpResponse.getStatusCode();
        boolean isAcceptable = acceptableResponseCodes.contains(httpStatus);
        return !isAcceptable;
    }

    // Step II - turn an ErrorMessage (passed within clientHttpResponse) in to a BootDemoRTE
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        System.out.println("Entered error Handler");

        if (hasError(clientHttpResponse)) {

            System.out.println("Error in ViewNISP response: {}"+ clientHttpResponse.getStatusText());

            // Grab the details from the httpResponse in to a more usable form
            ErrorMessage errMsg = getNispResponseObject(clientHttpResponse);

            // TODO : need to throw a useful exception.  The generic one will do for now

            // There's a danger that we will get a null errorMessage (in testing especially)
            String msg = "Unable to retrieve error details from clientHttpResponse";

            // If we got a meaningful error message, then pull out the good stuff
            if( errMsg != null ) {
                // We get a code, message and status, but can only store a message in generic.  Combine for now
                msg = errMsg.getCode() + " : " + errMsg.getMessage();
            }

            System.out.println("Throw exception here");
            // Rather than create execption with contents of the message, create it with the message
            //            throw new BootDemoRunTimeException(errMsg.getCode(), errMsg.getMessage());
            throw new BootDemoRunTimeException(errMsg);
        }
    }

    private ErrorMessage getNispResponseObject(ClientHttpResponse clientHttpResponse) {
        ErrorMessage response = null;
        String sts="";

        try {
            InputStream is = clientHttpResponse.getBody();

            // Why would this be needed?  Allows cal to work, so happy days
            String s = CharStreams.toString(new InputStreamReader(is, "UTF-8"));


            if(1>0) {
                System.out.println("clientResponse -> " + s);
                ErrorMessage e = objectMapper.readValue(s,ErrorMessage.class);
                response = e;
                System.out.println("e -> " + e.toString());
            } else {
                sts=String.valueOf(clientHttpResponse.getStatusCode().value());
                response = objectMapper.readValue(is, ErrorMessage.class);
            }

        } catch (Exception e) {
            System.out.println("Error in processing ViewNISP response, returning generic message: {}" + e);
            // Avoid compounding error by passing back some form of response ...
            response = new ErrorMessage(sts,"details lost as cannot read httpClient");
        }

        return response;
    }

}
