package com.twomoro.sprang.generator;

import com.twomoro.sprang.models.ControllerDetails;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by cgarnier on 25/04/14.
 */
public class ResourceGenerator {
    private URLClassLoader cl;
    private ArrayList<String> classNames;
    private ArrayList<Class> contollers;
    private String apiBaseUrl;



    public ResourceGenerator() {



    }

    private void findControllers() {
        for (String name : classNames) {
            Class c = null;

            try {
                c = cl.loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            for(Annotation a : c.getAnnotations()){
                if(a instanceof Controller){
                    contollers.add(c);

                    break;
                }
            }

        }
    }

    private void processController(Class c) {
        // Looking for mapping
        ControllerDetails cd = new ControllerDetails(c, this.apiBaseUrl);
        System.out.println(cd.toAngular());
    }

    public void loadJars(String[] paths) {
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
                System.out.println(urls.get(urls.size() - 1));
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


        cl = URLClassLoader.newInstance(urls.toArray(new URL[0]));



    }

    public void fromJars(String[] paths) {
        this.apiBaseUrl = "http://pc59:8080/MFR/services";
        classNames  = new ArrayList<String>();
        contollers = new ArrayList<Class>();
        loadJars(paths);
        findControllers();
        System.out.println(contollers.size() + "Spring controllers found. ");
        processLoadedClasses();
    }

    private void processLoadedClasses() {
        for(Class c : this.contollers){
            processController(c);
        }
    }
}
