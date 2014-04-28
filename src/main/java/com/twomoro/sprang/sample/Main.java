package com.twomoro.sprang.sample;

import com.twomoro.sprang.generator.ResourceGenerator;

/**
 * Created by cgarnier on 24/04/14.
 */
public class Main {


    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] paths) {
        System.out.println("Starting sample app.");
        ResourceGenerator gen = new ResourceGenerator();
        gen.fromJars(paths);


    }


}
