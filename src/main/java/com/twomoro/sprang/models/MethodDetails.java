package com.twomoro.sprang.models;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by cgarnier on 24/04/14.
 */
public class MethodDetails {
    public String url;
    public String httpMethod;
    public String action;
    public String configName;
    public ArrayList<ParamDetails> params;

    public MethodDetails(Method method, RequestMapping req, String base, String config){
        this.configName = config;
        String[] command = req.value();
        RequestMethod[] httpMethod = req.method();
        params = new ArrayList<ParamDetails>();
        if (command.length > 0)
            this.url = command[0];
        else this.url = "";

        if(httpMethod.length > 0)
            this.httpMethod = httpMethod[0].name();
        else this.httpMethod = "GET";

        formatAction();
        this.url = base + url;
        for(Annotation[] an: method.getParameterAnnotations()){
            for(Annotation a : an){
                if( a instanceof RequestParam){
                    ParamDetails pd = new ParamDetails((RequestParam)a);
                    this.params.add(pd);

                }
            }
        }
    }

    public String getAction() {
        return action;
    }

    private void formatAction() {
        if(url.length() < 1) return;
        String[] splitedUrl = this.url.split("/");
        this.action = splitedUrl[0];
        if(splitedUrl.length > 1)
            for (int i = 1; i < splitedUrl.length; i++) {
                if(i == 1 && this.url.startsWith("/")) this.action += splitedUrl[i];
                else this.action += splitedUrl[i].substring(0, 1).toUpperCase() + splitedUrl[i].substring(1);
            }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public ArrayList<ParamDetails> getParams() {
        return params;
    }

    public void setParams(ArrayList<ParamDetails> params) {
        this.params = params;
    }
    public void addParam(ParamDetails param){ this.params.add(param);}

    @Override
    public String toString() {
        return "\t\t Command: " + url +", Method: " + httpMethod;
    }

    public String toAngular(String baseUrl){
        String result = "";
        result += "      this." + this.action +" = function(params){\n";
        result += "        var defer = $q.defer();\n";
        result += "        $http({\n";
        result += "          method: '" +this.httpMethod + "',\n";
        result += "          url: '" + baseUrl + this.url + ".json',\n";
        result += "          params: params\n";
        result += "        }).success(function(data, status){\n";
        result += "          defer.resolve(data);\n";
        result += "        }).error(function(data,status){\n";
        result += "          defer.reject({status: status, data: data});\n";
        result += "        });\n";
        result += "        return defer.promise;\n";
        result += "      };\n";



        //result = "'" + this.action + "': { url: '" + baseUrl + this.url + ".json', method: '" + this.httpMethod +"'";
/*        if(this.params.size() > 0){
            result += ", params: {";
            for (int i = 0; i < this.params.size(); i++) {
                if(i > 0) result += ", ";
                result += this.params.get(i).toAngular();
            }
            result += "}";
        }*/
        //result += "};";
        return result;
    }
    public String toAngular(){
        return toAngular("");
    }
}
