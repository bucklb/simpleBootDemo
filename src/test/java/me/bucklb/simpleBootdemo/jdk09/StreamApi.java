package me.bucklb.simpleBootdemo.jdk09;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.modelmapper.Converters.Collection.map;

/*
    Look at streams again & the tweaked approach from jdk9

    Added dropWhile & takeWhile that allow a different kind of filtering. Not sure how useful it might be generally, but
    presumably could have it skip past initialisation stuff (or ignore hutdown) in a stream

    GroupingBY might have uses.  A little like SQL's group by.
    FlatMapping my have uses in pulling out subsets in to some overall pool that can then be one (or more) streams


 */
public class StreamApi {

    // 2/7/19 : not got jdk11 on just yet

//
//    private void ditch(List<Integer> ints, final int i){
//        ints.stream()
//                .map(n -> n*i )
//                .filter(n -> n > 1000)
//                .forEach(n -> System.out.println(n));
//    }
//
//    // Check that streaming a list makes no odds to the list (so maintains its state and can be used to create further streams
//    @Test
//    public void test_Raw_Stream() {
//
//        List<Integer> ints = List.of(1,11,111,1111);
//
//        // get round lambda & effectively final by sticking in its own call
//        for (int i = 1; i<4; i++) {
//            ditch(ints,i);
//        }
//        System.out.println("size of ints = " + ints.size());
//    }
//
//    // Not sure how useful the drop/take While stuff is
//    @Test
//    public void testWhile() {
//        List<Integer> ints = List.of(1,11,111,1111,111,11,1);
//
//        ints.stream()
//                .dropWhile(n -> n<100)      // work past the initial tidllers
//                .takeWhile(n -> n>100)      // once past the tiddlers take the biggies (and not the latter tiddlers)
//                .forEach(n -> System.out.println(n));
//
//    }
//
//    // This time in to another collection/list
//    @Test
//    public void testWhileCollection() {
//        List<Integer> ints = List.of(1,11,111,1111,111,11,1);
//
//        List<Integer> outs = ints.stream()
//                .dropWhile(n -> n<100)      // work past the initial tidllers
//                .takeWhile(n -> n>100)      // once past the tiddlers take the biggies (and not the latter tiddlers)
//                .collect(toList());
//        System.out.println(outs);
//    }
//
//    // Effectively split a list/stream in to odd & even.  cf groupby in SQL
//    @Test
//    public void testGroupBy() {
//        List<Integer> ints = List.of(1,5,6,7,2,3,4);
//        Map<Integer, List<Integer>> outs = ints.stream().collect(groupingBy(i-> i%2, toList() ));
//
//        System.out.println(outs);
//    }
//
//    // Effectively split a list/stream in to odd & even.  Seems to order, even though using sets.  ?? Is is going via list in the background ??
//    @Test
//    public void testGroupByToo() {
//        Set<Integer> ints = Set.of(1,5,6,7,2,3,4);
//        Map<Integer, Set<Integer>> outs = ints.stream().collect(groupingBy(i-> i%2, toSet() ));
//
//        System.out.println(outs);
//    }
//
//    // can split in to groups & then filter within each group.
//    // seems can filter & then group too.
//    // Order can make a difference (as illustrated). If filter FIRST then may prevent a group ever being an option
//    // WHEREAS if filter second may get a further group, but it might end up empty.
//    // In terms of what values get through, apart from grouping I suspect it makes little odds
//    @Test
//    public void testGroupByFilter() {
//
//        Set<Integer> ints = Set.of(1,5,6,7,2,3,4);
//        // groupby precedes filter
//        Map<Integer, Set<Integer>> outs = ints.stream()
//                .collect(
//                        groupingBy(i-> i%2,
//                                filtering(n -> (n%2 <1), toSet())
//                        )
//                );
//        System.out.println(outs);
//
//        // filter then groupBy
//        outs = ints.stream()
//                .collect(
//                        filtering(n -> (n%2 <1),
//                                groupingBy(i-> i%2, toSet())
//                        )
//                );
//        System.out.println(outs);
//    }
//
//    // Something to attempt to flatmap
//    private class X {
//        public Integer id; public Integer getId() { return id; }
//        public List<String> v; public List<String> getV(){ return v;}
//        public String toString(){ return id + " " + v.toString();}
//    }
//
//    // Playing with a list in a number of places
//    private List<X> listX() {
//        X x=new X(); x.id=1; x.v=List.of("one","2","three");
//        X y=new X(); y.id=2; y.v=List.of("one","two","four");
//        X z=new X(); z.id=1; z.v=List.of("one","II","five");    // <- NB this has index 1 as well
//        return List.of(x,y,z);
//    }
//
//    // FlatMapping.  Bit more involved to write a test for this ...
//    @Test
//    public void testGroupByFlatMap() {
//
//        List<X> l = listX();
//        System.out.println(l);
//
//        Map<Integer, Set<String>> outs = l.stream()
//                .collect(
//                        groupingBy(
////                                X::getId,                                       // another way to get to id
//                                m->m.getId(),                                   // groupBy the id
//                                flatMapping(n -> n.getV().stream(), toSet())    // push all the corresponding V in to a set (eliminating duplicates)
//                        )
//                );
//        System.out.println(outs);
//    }
//
//    // FlatMapping.  Bit more involved to write a test for this ... Looks hard, so start with FlatMap
//    @Test
//    public void testFlatMap() {
//
//        Set<X> s = new HashSet<>(listX());
//        System.out.println(s);
//
//        // Set will exclude duplications.  End of the day we'll have all the combined unique V values in a single set
//        Set<String> outs = s.stream()       // at this point have a stream of X's
//                .map(m->m.getV())           // at this point have a stream of V's (listOf strings)
//                .flatMap(v->v.stream())     // convert each list in to a combined stream
//                .collect(toSet());          // collect them appropriately (we want a set here)
//        System.out.println(outs);
//
//        // List will be the lot, dupes & all.  End of the day we'll have all the combined V values in a single list
//        List<String> outl = s.stream()       // at this point have a stream of X's
//                .map(m->m.getV())           // at this point have a stream of V's (listOf strings)
//                .flatMap(v->v.stream())     // convert each list in to a combined stream
//                .collect(toList());          // collect them appropriately (we want a list here)
//        System.out.println(outl);
//    }
//
//    // Got vague handle on flatMap. flatMapping?
//    @Test
//    public void testFlatMapping() {
//
//        Set<X> s = new HashSet<>(listX());
//
//        // Might offer slightly reduced code
//        Set<String> outs = s.stream()
//                .collect(                               // other approach just ends with collect(toSet()) on final stream
//                        flatMapping(                    // map each X to a stream of its V, & then all streams to a simple, flat set
//                                x->x.getV().stream(),
//                                toSet()
//                        )
//                );
//        System.out.println(outs);
//
//        // WIthout flatMapPING.  Turns out can live without the map step & go stratight to fltMap of x.getV
//        List<String> outl = s.stream()              // at this point have a stream of X's
//                .flatMap(v->v.getV().stream())      // for each X get the V values as sream & combine
//                .collect(toList());                 // collect them appropriately (we want a list here)
//        System.out.println(outl);
//    }
//
//    /*
//        Tried (but not very hard) to do groupingBy with flatMap (not flatMapping) but failed
//     */
//

}
