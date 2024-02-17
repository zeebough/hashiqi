package com.ghtt.socialplatform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MyHttpRequestWrapper extends HttpServletRequestWrapper {

    public MyHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        this.headerMap=new HashMap<>();
    }

    private Map<String,String> headerMap;

    public void addHeader(String name,String value){
        this.headerMap.put(name,value);
    }

    @Override
    public String getHeader(String name) {
        String value= super.getHeader(name);
        if(headerMap.containsKey(name)){
            value=headerMap.get(name);
        }
        return value;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names= Collections.list(super.getHeaderNames());
        names.addAll(headerMap.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values=Collections.list(super.getHeaders(name));
        if(headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}
