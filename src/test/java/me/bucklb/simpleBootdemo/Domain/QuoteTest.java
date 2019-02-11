package me.bucklb.simpleBootdemo.Domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class QuoteTest {

    long ID      = 69l;
    String QUOTE = "pithy words";
    String TYPE  = "qwerty";

    @Test
    public void testConstructor() {

        Quote quote = new Quote( TYPE, QUOTE );

        assertThat( quote.getType(),   is( TYPE ) );
        assertThat( quote.getValue(),  is( QUOTE ) );

    }


}
