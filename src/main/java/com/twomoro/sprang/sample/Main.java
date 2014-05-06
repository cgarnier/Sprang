package com.twomoro.sprang.sample;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.twomoro.sprang.generator.ResourceGenerator;
import com.twomoro.sprang.models.ControllerDetails;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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
        String dir = "D:\\DEV\\AirCobot\\client\\app\\SprangOutput\\";
        String configName = "bflyApi";
        File file = new File(dir + "test.txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintStream stream = new PrintStream(out);

        stream.println("test1");
        stream.println("test2");

        controllers = new ArrayList<ControllerDetails>();
        System.out.println("Starting sample app.");
        ResourceGenerator gen = new ResourceGenerator();
        gen.setConfigName(configName);

        this.controllers = gen.fromJars(paths);
/*        for(ControllerDetails cd :this.controllers){
            System.out.println("\n\n");
            System.out.println(cd.toAngular());
        }*/

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("templates/serviceTpl.mustache");

        for (ControllerDetails c : this.controllers) {
            try {
                mustache.execute(new PrintWriter(dir + c.getServiceName() +".js"), c).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mustache = mf.compile("templates/allServiceTpl.mustache");
        try {
            mustache.execute(new PrintWriter(dir + configName + "Services.js"), this).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

