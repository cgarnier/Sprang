package com.twomoro.sprang.sample;

import com.twomoro.sprang.generator.ServicesGenerator;
import com.twomoro.sprang.models.ControllerDetails;
import com.twomoro.sprang.models.config.Config;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by cgarnier on 24/04/14.
 */
public class Main {
    ArrayList<ControllerDetails> controllers;
    private Config config;
    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] paths) {
        this.config = Config.newInstance("config.json");
        controllers = new ArrayList<ControllerDetails>();


        System.out.println("[Starting sample app]");

        ServicesGenerator gen = new ServicesGenerator(this.config);
        gen.go();

        if (this.config.getOutput().isOneFile()) {
            try {
                gen.renderAll(new PrintWriter(this.config.getOutput().getPath() + this.config.getOutput().getConfigName() + "Services.js"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            for (ControllerDetails cd : gen.controllers)
                try {
                    gen.render(new PrintWriter(this.config.getOutput().getPath() + cd.getServiceName() + ".js"), cd);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }

        System.out.println("[Done]");

    }




}

