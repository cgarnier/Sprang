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

    private final String config;
    private  String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    private  String serviceName;

    private Class contClass;
    private String base;
    public ArrayList<MethodDetails> methods;

    public void setClass(Class c) {
        this.contClass = c;
        this.serviceName = contClass.getSimpleName().replace("Controller", "Service");
        this.fullName = contClass.getName();
        for (Annotation a : c.getAnnotations()) {
            if (a instanceof RequestMapping) {
                RequestMapping requestMapping = (RequestMapping) a;

                this.base += requestMapping.value()[0].replace("*", "");
                if(this.base.endsWith("/"))
                    this.base = this.base.substring(0, this.base.length() -1);

                processMethods();
            }
        }

    }

    private void processMethods() {
        for (Method method : contClass.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof RequestMapping) {
                    RequestMapping req = (RequestMapping) annotation;
                    MethodDetails md = new MethodDetails(method, req, this.base, this.config);
                    this.methods.add(md);
                }
            }
        }
    }

    public ControllerDetails(Class c, String config) {
        this.config = config;
        this.base = "";
        this.methods = new ArrayList<MethodDetails>();
        this.setClass(c);
    }



}
