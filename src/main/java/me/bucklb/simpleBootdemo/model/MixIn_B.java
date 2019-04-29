package me.bucklb.simpleBootdemo.model;

/*
    Basically a dummy class to get experience of mixIn & similar.  Specifically do NOT want to annotate this class
 */
public class MixIn_B {
        public String id;
        public String size;
        public String height;
        public String nulCheck =null;

        public MixIn_B(String id, String size, String height) {
                this.id = id;
                this.size = size;
                this.height = height;
        }

        public MixIn_B() {}

}
