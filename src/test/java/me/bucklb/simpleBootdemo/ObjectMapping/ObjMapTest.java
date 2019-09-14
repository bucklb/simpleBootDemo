package me.bucklb.simpleBootdemo.ObjectMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import me.bucklb.simpleBootdemo.ErrorHandling.ErrorMessage;
import org.junit.Test;

public class ObjMapTest {

    private ObjectMapper mapper = new ObjectMapper();

    private String errMsgAsText(String code, String msg) throws Exception {

        ErrorMessage errMsg = new ErrorMessage(code, msg     );
        String s=mapper.writeValueAsString(errMsg);
        System.out.println("s -> "+s);

        return s;
    }

    private ErrorMessage textAsErrMsg( String s) throws Exception {
        ErrorMessage e = mapper.readValue(s, ErrorMessage.class);
        System.out.println("e -> " + e.toString());
        return e;
    }



    @Test
    public void doTest() throws Exception{

        this.mapper.registerModule(new JodaModule());


        String s = errMsgAsText("200","Copacetic");

        ErrorMessage e = textAsErrMsg(s);

        System.out.println(s.equals(e.toString()));

    }

}
