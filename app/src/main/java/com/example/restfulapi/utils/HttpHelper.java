package com.example.restfulapi.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

    public static String downloadURL(String address) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //setting some properties on connection
            //15 sec tuk agr server se data na read kr saken to readtimeout ka error ajae ga
            connection.setReadTimeout(15000);
            //agr 10sec tuk server se connect na hu sake to ConnectTimeout ka error ajae ga
            connection.setConnectTimeout(10000);
            //setDoInput=> when we have to input/get data in our app
            connection.setDoInput(true);
            //because we are getting the request
            connection.setRequestMethod("GET");

            //server k aik dafa connect huga is ki waja se
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error: Got response code" + responseCode);
            }

            inputStream = connection.getInputStream();

            return readStream(inputStream);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                inputStream.close();
            }

        }
        return null;
    }

    private static String readStream(InputStream stream) throws IOException {

        //ByteArrayOutputStream==>This class implements an output stream in which the data is written into a byte array. The buffer automatically grows as data is written to it. The data can be retrieved using toByteArray() and toString() . Closing a ByteArrayOutputStream has no effect.
        //BufferedOutputStream==>The class implements a buffered output stream. By setting up such an output stream, an application can write bytes to the underlying output stream without necessarily causing a call to the underlying system for each byte written.
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        BufferedOutputStream out = null;

        try{
            int length=0;
            out=new BufferedOutputStream(byteArray);
            //jub tuk buffer k ander data ata rhe ga use hum read krte rhen gen, buffer men jo bytes ate hen wo integer form men ate hen
            //agr 0 ae ga to matlab data khatam hu chuka he, phr buffer out k ander write kerwa den gen then at the end byteArray ku return kr den gen jo data he
            while((length=stream.read(buffer))>0){
                out.write(buffer,0,length);
            }
            out.flush();
            //--->
            return  byteArray.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (out!=null){
                out.close();
            }
        }



    }
}
