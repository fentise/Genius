package com.example.Genius.model;

import java.util.HashMap;
import java.util.Map;

public class viewObject {
    private Map<String,Object> map = new HashMap<>();
    public void set(String key,Object value){
        map.put(key,value);
    }
    public Object get(String key){
        return map.get(key);
    }
}
