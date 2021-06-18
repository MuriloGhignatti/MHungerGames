package me.murilo.ghignatti.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KitsPath {
    
    public String[] kitsPaths;

    public KitsPath(@JsonProperty("kitsPaths") String[] kitsPaths){
        this.kitsPaths = kitsPaths;
    }
}
