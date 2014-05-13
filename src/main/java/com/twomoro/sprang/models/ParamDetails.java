package com.twomoro.sprang.models;

import org.springframework.web.bind.annotation.RequestParam;

import javax.print.attribute.standard.PDLOverrideSupported;

/**
 * Created by cgarnier on 25/04/14.
 */
public class ParamDetails {
    private String name;
    private boolean required;

    public ParamDetails(RequestParam requestParam){
        this.name = requestParam.value();
        this.required = requestParam.required();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }


}
