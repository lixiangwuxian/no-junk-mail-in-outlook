package com.lxtend.junkmailcollector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailApplication {

    static Properties props=new Properties();
    public static void main(String[] args) {
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "outlook.office365.com");
        if(args!=null){
            argParser(args);
        }
        else {
            loadPropertiesFromFile();
        }
        if (!checkProperties()){
            System.out.println("Please provide a username and password. Use -h for help");
            System.exit(1);
        }
        HelloController ctl=new HelloController(props);
        while (true){
            try{
                ctl.checkMail();
                System.out.println("Sleeping for 5 minutes");
                Thread.sleep(1000*60*5);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static void argParser(String[] args){
        for (int i=0;i<args.length;i++){
            if (args[i].equals("-h")){
                System.out.println("Usage: java -jar junkmailcollector.jar -u <username> -p <password>\n" +
                        "Or create ./resources/application.properties with the following contents:\n" +
                        "username=<username>\n" +
                        "password=<password>");
            }
            if(args[i].equals("-u")&&i+1<args.length){
                props.setProperty("username",args[i+1]);
            }
            else if(args[i].equals("-p")&&i+1<args.length){
                props.setProperty("password",args[i+1]);
            }
        }
    }
    static private void loadPropertiesFromFile(){
        try{
            InputStream input=new FileInputStream("src/main/resources/application.properties");
            props.load(input);
        }
        catch(IOException e){
            System.out.println("No application.properties file found, please create one in the same directory as the jar file");
        }
    }
    static boolean checkProperties(){
        return props.getProperty("username") != null && props.getProperty("password") != null;
    }
}