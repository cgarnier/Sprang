package com.twomoro.sprang.models.config;


import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgarnier on 25/04/14.
 */
public class Config{
    private Dependencies dependencies;
    private Output output;
    private List<String> controllers;
    private List<String> controllersJars;

    public Config(String s) {
        this.dependencies = new Dependencies();
        this.output = new Output();
        this.controllers = new ArrayList<String>();
        this.controllersJars = new ArrayList<String>();

    }

    public List<String> getControllers() {
        return controllers;
    }

    public void setControllers(List<String> controllers) {
        this.controllers = controllers;
    }

    public List<String> getControllersJars() {
        return controllersJars;
    }

    public void setControllersJars(List<String> controllersJars) {
        this.controllersJars = controllersJars;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }

    public void setDependencies(Dependencies dependencies) {
        this.dependencies = dependencies;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }


    public static Config newInstance(String s) {
        String jsonStr = "";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            jsonStr = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Gson gson = new Gson();
        Config newConf = gson.fromJson(jsonStr, Config.class);

        return newConf;


    }



}
