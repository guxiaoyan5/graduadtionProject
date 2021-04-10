package edu.run.student;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StudentTest extends TestCase {
    public void test() {
        HashSet<String> a = new HashSet<>();
        a.add("string");
        a.add("string");
        a.add("aaa");
        for (String i : a) {
            System.out.println(i);
        }
    }
}