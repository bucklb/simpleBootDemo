package me.bucklb.simpleBootdemo.jdk09;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/*
    Look at streams again & the tweaked approach from jdk9
 */
public class StreamApi {

    private void ditch(List<Integer> ints, final int i){
        ints.stream()
                .map(n -> n*i )
                .filter(n -> n > 1000)
                .forEach(n -> System.out.println(n));
    }

    // Check that streaming a list makes no odds to the list (so maintains its state and can be used to create further streams
    @Test
    public void test_Raw_Stream() {

        List<Integer> ints = List.of(1,11,111,1111);

        // get round lambda & effectively final by sticking in its own call
        for (int i = 1; i<4; i++) {
            ditch(ints,i);
        }
        System.out.println("size of ints = " + ints.size());
    }

    // Not sure how useful the drop/take While stuff is
    @Test
    public void testWhile() {
        List<Integer> ints = List.of(1,11,111,1111,111,11,1);

        ints.stream()
                .dropWhile(n -> n<100)      // work past the initial tidllers
                .takeWhile(n -> n>100)      // once past the tiddlers take the biggies (and not the latter tiddlers)
                .forEach(n -> System.out.println(n));

    }

    // This time in to another collection/list
    @Test
    public void testWhileCollection() {
        List<Integer> ints = List.of(1,11,111,1111,111,11,1);

        List<Integer> outs = ints.stream()
                .dropWhile(n -> n<100)      // work past the initial tidllers
                .takeWhile(n -> n>100)      // once past the tiddlers take the biggies (and not the latter tiddlers)
                .collect(toList());
        System.out.println(outs);
    }

    // Effectively split a list/stream in to odd & even.  cf groupby in SQL
    @Test
    public void testGroupBy() {
        List<Integer> ints = List.of(1,5,6,7,2,3,4);
        Map<Integer, List<Integer>> outs = ints.stream().collect(groupingBy(i-> i%2, toList() ));

        System.out.println(outs);
    }

    // Effectively split a list/stream in to odd & even.  Seems to order, even though using sets.  ?? Is is going via list in the background ??
    @Test
    public void testGroupByToo() {
        Set<Integer> ints = Set.of(1,5,6,7,2,3,4);
        Map<Integer, Set<Integer>> outs = ints.stream().collect(groupingBy(i-> i%2, toSet() ));

        System.out.println(outs);
    }

    // can split in to groups & then filter within each group.
    // seems can filter & then group too.
    // Order can make a difference (as illustrated). If filter FIRST then may prevent a group ever being an option
    // WHEREAS if filter second may get a further group, but it might end up empty.
    // In terms of what values get through, apart from grouping I suspect it makes little odds
    @Test
    public void testGroupByFilter() {

        Set<Integer> ints = Set.of(1,5,6,7,2,3,4);
        // groupby precedes filter
        Map<Integer, Set<Integer>> outs = ints.stream()
                .collect(
                        groupingBy(i-> i%2,
                                filtering(n -> (n%2 <1), toSet())
                        )
                );
        System.out.println(outs);

        // filter then groupBy
        outs = ints.stream()
                .collect(
                        filtering(n -> (n%2 <1),
                                groupingBy(i-> i%2, toSet())
                        )
                );
        System.out.println(outs);
    }

    // Something to attempt to flatmap
    private class X {
        public Integer id; public Integer getId() { return id; }
        public List<String> v; public List<String> getV(){ return v;}
        public String toString(){ return id + " " + v.toString();}
    }


    // FlatMapping.  Bit more involved to write a test for this ...
    @Test
    public void testGroupByFlatMap() {

        X x=new X(); x.id=1; x.v=List.of("one","2","three");
        X y=new X(); y.id=2; y.v=List.of("one","two","four");
        X z=new X(); z.id=1; z.v=List.of("one","II","five");    // <- NB this has index 1 as well
        List<X> l = List.of(x,y,z);
        System.out.println(l);

        Map<Integer, Set<String>> outs = l.stream()
                .collect(
                        groupingBy(
//                                X::getId,                                       // another way to get to id
                                m->m.getId(),                                   // groupBy the id
                                flatMapping(n -> n.getV().stream(), toSet())    // push all the corresponding V in to a set (eliminating duplicates)
                        )
                );
        System.out.println(outs);
    }
}
