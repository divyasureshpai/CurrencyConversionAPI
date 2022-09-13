package com.api.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader(Properties properties) {
        this.properties = properties;
        try{
            FileInputStream fileInputStream=new FileInputStream("src/test/resources/Config/config.properties");
            this.properties.load(fileInputStream);
        }
        catch (Exception e){
            System.err.println("Exception occurred while loading properties"+e);
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}
