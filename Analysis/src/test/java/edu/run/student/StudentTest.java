package edu.run.student;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class StudentTest extends TestCase {
    public void test(){
        List<Float> a = new ArrayList<Float>(6) {};
        a.add(0, (float) 1.0);
        a.add(1, (float) 1.0);
        a.add(2, (float) 1.0);
        a.add(3, (float) 1.0);
        a.add(4, (float) 1.0);
        a.add(5, (float) 1.0);
        Iterable<Float> b = a;
        Iterable<Float> c = b;
        for (float i : b){
            System.out.println(i);
        }
        for(float i :c){
            System.out.println(i);
        }
     }
}