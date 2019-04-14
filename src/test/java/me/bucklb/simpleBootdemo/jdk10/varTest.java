package me.bucklb.simpleBootdemo.jdk10;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/*
    jdk10 means variablw types can be inferred (like in lambda) rather than always utterly explicit
 */
public class varTest {





    @Test
    public void testVar() {

        var v="textString";
        var x=listX();
        System.out.println(v.substring(2,3));
        System.out.println(v.getClass().getSimpleName());
        System.out.println(x.getClass().getSimpleName());


        x.stream().forEach(z-> System.out.println(z.toString()));

        // Use var to represent X.  If all we want is to splurge through a list, might be useful time saver
        for(var z: x) {
            System.out.println(z.toString());
        }
    }

    // Ought to have better scope to split streams (for debug & clarity) and maybe the mockMvc stuff could be improved
    @Test
    public void testVarBreakdown() {

        // Don't need to faff around defining our set, just populate it
        var s = new HashSet<>(listX());
        System.out.println(s);

        // Set will exclude duplications.  End of the day we'll have all the combined unique V values in a single set
        Set<String> outs = s.stream()       // at this point have a stream of X's
                .map(m->m.getV())           // at this point have a stream of V's (listOf strings)
                .flatMap(v->v.stream())     // convert each list in to a combined stream
                .collect(toSet());          // collect them appropriately (we want a set here)
        System.out.println(outs);

        // can create immutable copy of a collection now
        var t = Set.copyOf(s);
        s=null; // kill original and use copy

        // Can break down the steps without needing to know exactly what to define variable types as
        var strm = t.stream();
        var allV = strm.map(m->m.getV());
        var fltV = allV.flatMap(v->v.stream());
        var vSet = fltV.collect(toSet());
        System.out.println(vSet);

        System.out.println("vSet-> " + vSet.getClass().getName());
        System.out.println("fltV-> " + fltV.getClass().getName());
        System.out.println("allV-> " + allV.getClass().getName());
        System.out.println("strm-> " + strm.getClass().getName());




    }









        // Something to attempt to flatmap
    private class X {
        public Integer id; public Integer getId() { return id; }
        public List<String> v; public List<String> getV(){ return v;}
        public String toString(){ return id + " " + v.toString();}
    }

    // Playing with a list in a number of places
    private List<X> listX() {
        X x=new X(); x.id=1; x.v=List.of("one","2","three");
        X y=new X(); y.id=2; y.v=List.of("one","two","four");
        X z=new X(); z.id=1; z.v=List.of("one","II","five");    // <- NB this has index 1 as well
        return List.of(x,y,z);
    }

}
