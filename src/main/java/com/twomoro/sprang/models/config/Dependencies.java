package com.twomoro.sprang.models.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgarnier on 07/05/14.
 */
public class Dependencies{
    private ArrayList<String> classpath;
    private List<String> jars;

    Dependencies() {
        jars = new ArrayList<String>();
    }

    public ArrayList<String> getClasspath() {
        return classpath;
    }

    public void setClasspath(ArrayList<String> classpath) {
        this.classpath = classpath;
    }

    public List<String> getJars() {
        return jars;
    }

    public void setJars(List<String> jars) {
        this.jars = jars;
    }
}