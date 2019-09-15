package me.bucklb.simpleBootdemo.ObjectMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.bucklb.simpleBootdemo.model.ExtendableBean;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class OtherMapTest {


    // Tad bizarre. Returns the values in the "root", but also under a properties list of attributes
    @Test
    public void doGenericTest() throws Exception{
        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("first", "val1");
        bean.add("secnd", "val2");

        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);

        assertThat(result, containsString("first"));
        assertThat(result, containsString("val1"));
    }


}
