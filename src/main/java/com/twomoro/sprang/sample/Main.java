package com.twomoro.sprang.sample;

import com.twomoro.sprang.generator.ResourceGenerator;
import com.twomoro.sprang.models.ControllerDetails;

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
        gen.setApiBaseUrl("http://pc59:8080/MFR/services");

        for(ControllerDetails cd :gen.fromJars(paths)){
            System.out.println("\n\n");
            System.out.println(cd.toAngular());
        }


    }


}
