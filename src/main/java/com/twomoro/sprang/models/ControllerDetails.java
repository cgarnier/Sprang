package com.twomoro.sprang.models;

import com.twomoro.sprang.models.MethodDetails;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by cgarnier on 24/04/14.
 */
public class ControllerDetails {


    private Class contClass;
    private String base;
    private ArrayList<MethodDetails> methods;

    public void setClass(Class c) {
        this.contClass = c;
        System.out.println("-- Controller: " + c.getName());
        for (Annotation a : c.getAnnotations()) {
            //System.out.println("@: "+ a.toString());
            if (a instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) a;

                this.base += requestMapping.value()[0].replace("*", "");
                if(this.base.endsWith("/"))
                    this.base = this.base.substring(0, this.base.length() -1);
                System.out.println("\t Mapping: " + this.base);

                processMethods();
            }
        }

    }

    private void processMethods() {
        for (Method method : contClass.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof RequestMapping) {
                    RequestMapping req = (RequestMapping) annotation;
                    MethodDetails md = new MethodDetails(method, req);
                    this.methods.add(md);
                    System.out.println(md);
                }
            }
        }
    }

    public ControllerDetails(Class c, String baseUrl) {
        this.base = baseUrl;
        this.methods = new ArrayList<MethodDetails>();
        this.setClass(c);
    }

    public String toAngular() {
        String js = "'use strict'\n\n";
        js += "angular.module('" + contClass.getName() +"', [])\n";
        js += ".factory('" + contClass.getSimpleName().replace("Controller", "Service") + "', ['$resource',\n";
        js += "    function ($resource) {\n";
        js += "        return $resource('" + this.base + ".json', {}, {\n";

        boolean first = true;
        for (MethodDetails md : this.methods) {
            if(!first) js += ", \n";
            first = false;

            js += "            " + md.toAngular(this.base);
        }
        js +=
                "\n        });\n" +
                        "    }]);\n";


        return js;
    }

}
