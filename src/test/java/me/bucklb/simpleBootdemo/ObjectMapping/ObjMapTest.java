package me.bucklb.simpleBootdemo.ObjectMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import me.bucklb.simpleBootdemo.ErrorHandling.ErrorMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Create text that is just an array of ErrorMessages
    private String getErrListString()throws Exception {
        List<ErrorMessage> l = new ArrayList<ErrorMessage>();
        l.add(new ErrorMessage("1","one"));
        l.add(new ErrorMessage("2","too"));
        String s = mapper.writeValueAsString(l);
        return s;
    }

    // Get list of errorMessages from a string (illustrate the TypeReference aspect)
    private List<ErrorMessage> getErrListFromText(String s) throws Exception {
        List<ErrorMessage> l = mapper.readValue(s, new TypeReference<List<ErrorMessage>>() {});
        return l;
    }

    // Why would you not get the array and then convert to list.  Avoids the whole TypeReference guff
    private ErrorMessage[] getErrArrayFromText(String s) throws Exception {
        ErrorMessage[] a = mapper.readValue(s, ErrorMessage[].class);
        return a;
    }


    @Test
    public void doGenericTest() throws Exception{

//        this.mapper.registerModule(new JodaModule());

        String s = errMsgAsText("200","Copacetic");
        ErrorMessage e = textAsErrMsg(s);
        System.out.println(s.equals(e.toString()));
    }

    @Test
    public void doArrayTest() throws Exception {
        String s = getErrListString();
        System.out.println(s);
        System.out.println("List .... ");

        List<ErrorMessage> l = getErrListFromText(s);
        for(ErrorMessage em:l) {
            System.out.println(em.toString());
        }
        System.out.println("Array ... ");

        ErrorMessage[] a = getErrArrayFromText(s);
        for(ErrorMessage em:a) {
            System.out.println(em.toString());
        }

        List<ErrorMessage> la = Arrays.asList(a);
        System.out.println("List from Array ");
        for(ErrorMessage em:la) {
            System.out.println(em.toString());
        }


    }


}
