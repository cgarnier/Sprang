package com.twomoro.sprang.generator;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.twomoro.sprang.models.ControllerDetails;
import com.twomoro.sprang.models.config.Config;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by cgarnier on 25/04/14.
 */
public class ServicesGenerator {
    private final Config config;
    private URLClassLoader cl;
    private ArrayList<String> classNames;
    private ArrayList<Class> controllersClasses;
    public ArrayList<ControllerDetails> controllers;


    public ServicesGenerator(Config config) {
        this.config = config;

        this.classNames = new ArrayList<String>();
        this.controllersClasses = new ArrayList<Class>();

        this.initClassLoader();

    }

    public void findJarsRecursivly(final File folder, ArrayList<URL> urls) {
        if (folder.isDirectory()) {

            for (final File fileEntry : folder.listFiles()) {

                if (fileEntry.isDirectory()) {
                    findJarsRecursivly(fileEntry, urls);
                } else {
                    if (fileEntry.getName().endsWith(".jar")) {

                        try {
                            urls.add(fileEntry.toURI().toURL());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("     - " + fileEntry.getName());
                }
            }
        }
    }

    private void initClassLoader() {
        ArrayList<URL> urls = new ArrayList<URL>();
        File file;
        System.out.println("  * Loading dependencies from classpath ...");
        for (String path : this.config.getDependencies().getClasspath()) {

            file = new File(path);
            try {
                // For .class
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            findJarsRecursivly(file, urls);
        }
        System.out.println("  * Loading jars dependencies ...");
        for (String path : this.config.getDependencies().getJars()) {

            file = new File(path);
            System.out.println("     - " + file.getName());
            try {
                // For .class
                urls.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        cl = URLClassLoader.newInstance(urls.toArray(new URL[0]));

    }

    public ControllerDetails fromClass(Class aClass) {
        for (Annotation a : aClass.getAnnotations()) {
            if (a instanceof Controller) {
                return processController(aClass);
            }
        }
        return null;
    }

    private void findControllers() {
        System.out.println("  * Looking for spring controllers ...");
        for (String name : classNames) {
            Class c = null;

            try {
                c = cl.loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            for (Annotation a : c.getAnnotations()) {
                if (a instanceof Controller) {
                    controllersClasses.add(c);

                    break;
                }
            }

        }
    }

    private ControllerDetails processController(Class c) {
        // Looking for mapping
        ControllerDetails cd = new ControllerDetails(c, this.config.getOutput().getConfigName());
        return cd;

    }

    public void loadJars(List<String> paths) {
        ArrayList<URL> urls = new ArrayList<URL>();

        File f = null;
        for (String p : paths) {
            JarFile jarFile = null;
            try {
                f = new File(p);
                jarFile = new JarFile(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                urls.add(f.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Enumeration e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry je = (JarEntry) e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String cName = je.getName().substring(0, je.getName().length() - 6);
                cName = cName.replace('/', '.');
                classNames.add(cName);
            }
        }

        this.cl = URLClassLoader.newInstance(urls.toArray(new URL[0]), this.cl);


    }

    public void go() {
        ArrayList<ControllerDetails> result = new ArrayList<ControllerDetails>();
        result.addAll(this.fromJars(this.config.getControllersJars()));

        for (String ctrlName : this.config.getControllers()) {
            Class ctrl = null;
            try {
                ctrl = cl.loadClass(ctrlName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (ctrl != null) {
                result.add(fromClass(ctrl));
            }
        }
        this.controllers = result;

    }

    public void render(PrintWriter pw, ControllerDetails ctrl) {
        System.out.println("  * Rendering " + ctrl.getServiceName() + "...");
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache;


        mustache = mf.compile("templates/serviceTpl.mustache");
        try {
            mustache.execute(pw, ctrl).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderAll(PrintWriter pw) {
        System.out.println("  * Rendering all services...");
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache;


        mustache = mf.compile("templates/allServiceTpl.mustache");
        try {
            mustache.execute(pw, this).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<ControllerDetails> fromJars(List<String> paths) {
        loadJars(paths);
        findControllers();
        System.out.println("       " + controllersClasses.size() + " Spring controllers found. ");
        return processLoadedClasses();
    }

    private ArrayList<ControllerDetails> processLoadedClasses() {
        ArrayList<ControllerDetails> result = new ArrayList<ControllerDetails>();
        for (Class c : this.controllersClasses) {
            result.add(processController(c));
        }
        return result;
    }
}
