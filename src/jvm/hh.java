package jvm;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class hh {
    public static void main(String[] args) throws IOException {
        List<Object> list= new ArrayList<>();
        int count =0;
        while(true){
            System.in.read();
            System.out.println(++count);
            list.add(new byte[1024*1024]);
        }

    }



}
