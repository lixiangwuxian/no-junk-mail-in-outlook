package com.lxtend.junkmailcollector;
import java.io.*;
import java.util.Properties;
import javax.mail.*;


public class HelloController {
    Properties props=new Properties();
    public HelloController(Properties props){
        this.props=props;
    }

    public void checkMail(){
        Session mailSession=Session.getInstance(props);
        try{
            Store store=mailSession.getStore();
            store.connect(props.getProperty("username"),props.getProperty("password"));
            System.out.println("Connected to "+store.getURLName());
            Folder junkFolder=store.getFolder("Junk");
            Folder inboxFolder=store.getFolder("INBOX");
            junkFolder.open(Folder.READ_WRITE);
            inboxFolder.open(Folder.READ_WRITE);
            //place only new messages in the junk folder to inbox
            Message[] messages=junkFolder.getMessages();
            for(Message message:messages){
                if(message.getFlags().contains(Flags.Flag.SEEN)){
                    continue;
                }
                inboxFolder.appendMessages(new Message[]{message});
                //delete message from junk folder
                message.setFlag(Flags.Flag.DELETED,true);
                System.out.println("Moved message "+message.getSubject()+" to inbox");
            }
            store.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}