package com.twomoro.sprang.sample;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.twomoro.sprang.generator.ResourceGenerator;
import com.twomoro.sprang.models.ControllerDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by cgarnier on 24/04/14.
 */
public class Main {
    ArrayList<ControllerDetails> controllers;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] paths) {
        controllers = new ArrayList<ControllerDetails>();
        System.out.println("Starting sample app.");
        ResourceGenerator gen = new ResourceGenerator();
        gen.setApiBaseUrl("http://pc59:8080/MFR/services");

        this.controllers = gen.fromJars(paths);
/*        for(ControllerDetails cd :this.controllers){
            System.out.println("\n\n");
            System.out.println(cd.toAngular());
        }*/

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("templates/template.mustache");
        try {
            mustache.execute(new PrintWriter(System.out), this).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

