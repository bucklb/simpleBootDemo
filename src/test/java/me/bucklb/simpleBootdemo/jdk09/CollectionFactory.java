package me.bucklb.simpleBootdemo.jdk09;

import io.swagger.models.auth.In;
import org.junit.Test;

import java.util.*;

/*
    Play with some jdk 9 changes
 */
public class CollectionFactory {

    // Get to create collections and populate them in one go with Java 9
    @Test
    public void test_Collections_List() {

        // The immediately created list is a different creature ...
        List<Integer> ints = List.of(1,11,111,1111);
        System.out.println(ints.getClass().getSimpleName());

        List<Integer> inTs = new ArrayList<>();
        inTs.add(1);
        inTs.add(2);
        System.out.println(inTs.getClass().getSimpleName());

        try {
            ints.add(69);
        } catch (Exception e) {
            System.out.println("Immediately created list is IMMUTABLE");
            System.out.println("Exception was : " + e.getClass().getSimpleName());
        }
    }
    @Test
    public void test_Collections_Set() {

        // The immediately created list is a different creature ...
        Set<Integer> ints = Set.of(1,11,111,1111);
        System.out.println(ints.getClass().getSimpleName());

        Set<Integer> inTs = new HashSet<>() ;
        inTs.add(1);
        inTs.add(2);
        System.out.println(inTs.getClass().getSimpleName());

        try {
            ints.add(69);
        } catch (Exception e) {
            System.out.println("Immediately created set is IMMUTABLE");
            System.out.println("Exception was : " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void test_Collections_Map() {

        // The immediately created list is a different creature ...
        Map<String,Integer> ints = Map.of("1",11,"111",1111);
        System.out.println(ints.getClass().getSimpleName());

        Map<String, Integer> inTs = new HashMap<>() ;
        inTs.put("one",1);
        inTs.put("two",2);
        System.out.println(inTs.getClass().getSimpleName());

        try {
            ints.put("soixante",69);
        } catch (Exception e) {
            System.out.println("Immediately created map is IMMUTABLE");
            System.out.println("Exception was : " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void test_Collections_MapOfEntries() {

        // The immediately created list is a different creature ...
        Map<String,Integer> ints = Map.ofEntries(Map.entry("1",11),Map.entry("111",1111));
        System.out.println(ints.getClass().getSimpleName());

        Map<String, Integer> inTs = new HashMap<>() ;
        inTs.put("one",1);
        inTs.put("two",2);

        System.out.println(inTs.getClass().getSimpleName());

        // Rattle through the collections so I can see Map.entry in anger
        ints.entrySet().stream().forEach(e -> System.out.print(e.getKey() +" : " + e.getValue() + "     "));
        System.out.println("");

        inTs.entrySet().stream().forEach(e -> System.out.print(e.getKey() +" : " + e.getValue() + "     "));
        System.out.println("");

        // Less stream based version
        ints.forEach((k,v) -> System.out.println(k +" : " + v));
        inTs.forEach((k,v) -> System.out.println(k +" : " + v));

        try {
            ints.put("soixante",69);
        } catch (Exception e) {
            System.out.println("Immediately created map is IMMUTABLE");
            System.out.println("Exception was : " + e.getClass().getSimpleName());
        }
    }




}
