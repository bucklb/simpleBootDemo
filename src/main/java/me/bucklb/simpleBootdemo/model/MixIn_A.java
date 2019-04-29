package me.bucklb.simpleBootdemo.model;

/*
    Basically a dummy class to get experience of mixIn & similar.  Specifically do NOT want to annotate this class
 */
public class MixIn_A {
    public String id ;
    public String name;
    public String[] color;
    public int sal;
    public MixIn_B[] bclass ;


    public MixIn_A(String id, String name, String[] color, int sal, MixIn_B[] bclass) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sal = sal;
        this.bclass = bclass;
    }

    public MixIn_A() {}

}
