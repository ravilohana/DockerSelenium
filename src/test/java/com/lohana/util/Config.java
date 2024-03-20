package com.lohana.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;


public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final String DEFAULT_PROPERTIES = "config/default.properties";
//    private static final String DEFAULT_PROPERTIES = System.getProperty("user.dir") + "\\src\\test\\resources\\config\\default.properties";

    private static  Properties properties;
    public static void initialize(){

        // load default properties

        properties = loadProperties();

        // check for any override

        for(String key:properties.stringPropertyNames()){
            if(System.getProperties().containsKey(key)){
                properties.setProperty(key, System.getProperty(key));
            }
        }

        // print
        logger.info(" #### Test Properties ####");
        logger.info("_________________________________________");
        for(String key:properties.stringPropertyNames()){
            logger.info("{}={}",key,properties.getProperty(key));
        }

        logger.info("___________________________________________");

        System.out.println(" #################");
        System.out.println(DEFAULT_PROPERTIES);
        System.out.println(" #################");

    }

    public static String getPropertyValue(String key){
        return properties.getProperty(key);
    }

    public static Properties loadProperties(){
        properties = new Properties();
        try(InputStream inputStream  = ResourceLoader.getResource(DEFAULT_PROPERTIES)){
            properties.load(inputStream);
        }catch (Exception e){
            logger.error("unable to read the properties file {}", DEFAULT_PROPERTIES,e);
        }
        return properties;
    }
}
