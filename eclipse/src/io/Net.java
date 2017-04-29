package io;

import config.Config;
import config.ConfigManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class Net {

    @SuppressWarnings("resource")
    public static Scanner getUrl(String url){
        InputStream is = Net.getUrlIS(url);
        if(is == null){
            return null;
        } else {
            return new Scanner(is, "UTF-8").useDelimiter("\n");
        }
    }

    public static InputStream getUrlIS(String url){
        Config config = ConfigManager.getConfig();
        int nb_try = 0;
        while(nb_try < config.getNetMaxDownloadTries()){
            try {
                URL netURL = new URL(url);
                String redirect = netURL.openConnection().getHeaderField("Location");
                if(redirect != null) {
                    netURL = new URL(redirect);
                }
                return netURL.openStream();

            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                if(nb_try >= config.getNetMaxDownloadTries() - 2){
                    System.out.println("Error while downloading : "+url+" (Try "+(nb_try + 1)+"/"+config.getNetMaxDownloadTries()+")");
                }
                nb_try ++;
                if(nb_try == config.getNetMaxDownloadTries()){
                    e.printStackTrace();
                }
                try {
                    long sleep_time = (long)Math.floor(Math.random() * config.getNetTimeBetweenTries());
                    Thread.sleep(sleep_time);
                } catch (InterruptedException e1) {
                }
            }
        }
        return null;
    }

    public static ReadableByteChannel getUrlAsByteChannel(String url){
        Config config = ConfigManager.getConfig();
        int nb_try = 0;
        while(nb_try < config.getNetMaxDownloadTries()){
            try {
                URL netURL = new URL(url);
                String redirect = netURL.openConnection().getHeaderField("Location");
                if(redirect != null) {
                    netURL = new URL(redirect);
                }
                ReadableByteChannel rbc = Channels.newChannel(netURL.openStream());
                return rbc;

            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                if(nb_try >= config.getNetMaxDownloadTries() - 2){
                    System.out.println("Error while downloading : "+url+" (Try "+(nb_try + 1)+"/"+config.getNetMaxDownloadTries()+")");
                }
                nb_try ++;
                if(nb_try == config.getNetMaxDownloadTries()){
                    e.printStackTrace();
                    throw new RuntimeException();
                }
                try {
                    long sleep_time = (long)Math.floor(Math.random() * config.getNetTimeBetweenTries());
                    Thread.sleep(sleep_time);
                } catch (InterruptedException e1) {
                }
            }
        }
        return null;
    }
}
