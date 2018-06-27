package com.noeltest.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.map.ObjectMapper;

public class FlUrl {

    private static final String NOENCODE = "NOENCODE:";
    private  HttpURLConnection  conn;
    private  URL      url;

    private  int  respCode;
    private  String content = "";
    private  byte[] contentBytes = null;

    private  boolean sendDataAsSJON;

    private  boolean verbose = false;

    private  boolean encodeBodyData = false;

    private  boolean asByteArray = false;

    private Map<String, Object> data = new HashMap<>();

    
    public FlUrl(String loc) throws Exception {
        url = new URL(loc);

        if(loc.indexOf("https") != -1) {
            conn = (HttpsURLConnection)url.openConnection();
        } else {
            conn = (HttpURLConnection)url.openConnection();
        }
        
        conn.setUseCaches(false);
    }

    public FlUrl bytes() {
        asByteArray = true;
        return this;
    }

    public String getURLString() {
        return url.toString();
    }

    public FlUrl verbose() {
        verbose = true;
        return this;
    }

    public int getRespCode() {
        return respCode;
    }

    public String getContent() {
        return content;
    }

    public byte[] getBytes() {
        return contentBytes;
    }

    public FlUrl  chunkedStreamingMode(int i) {
        conn.setChunkedStreamingMode(i);
        return this;
    }

    public FlUrl  acceptAll() {
        return header("Accept", "*/*");
    }

    public FlUrl  accept(String a) {
        return header("Accept", a);
    }

    public FlUrl oauth2(String s) {
        return header("Authorization", "Bearer " + s);
    }

    public FlUrl header(String k, String v) {
        conn.setRequestProperty(k, v);
        return this;
    }

    public void post() throws Exception {
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        connect();
    }

    public void put() throws Exception {
        conn.setRequestMethod("PUT");
        conn.setDoInput(true);
        connect();
    }

    public void head() throws Exception {
        conn.setRequestMethod("HEAD");
        conn.setDoInput(true);
        connect();
    }

    public void delete() throws Exception {
        conn.setRequestMethod("DELETE");
        conn.setDoInput(true);
        connect();
    }

    public void options() throws Exception {
        conn.setRequestMethod("OPTIONS");
        conn.setDoInput(true);
        connect();
    }

    public void get() throws Exception {
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        connect();
    }

    public FlUrl timeout(int read, int con) {
        conn.setReadTimeout(read);
        conn.setConnectTimeout(con);
        return this;
    }

    public FlUrl acceptJSON() {
        return header("Accept", "application/json");
    }

    public FlUrl contentIsJSON() {
        sendDataAsSJON = true;
        return header("Content-Type", "application/json");
    }

    public FlUrl contentIsImage() {
        return header("Content-Type", "image/*");
    }

    public FlUrl contentIsFormURLEncoded() {
        encodeBodyData = true;
        return header("Content-Type", "application/x-www-form-urlencoded");
    }


    private void connect() {
        try {
            if(verbose) {
                System.out.println("----------HEADERS");
                for(String k : conn.getRequestProperties().keySet()) {

                    List<String> l = conn.getRequestProperties().get(k);

                    for(String v : l) {
                        System.out.println(k + " : " + v);
                    }

                }
                System.out.println("");

                System.out.println("Attempt url load of " + url.toString());
                System.out.println("Attempt conn with method " + conn.getRequestMethod());
            }

            conn.connect();

            if(data != null && !data.keySet().isEmpty()) {
                System.out.println("data " + data);
                String s = "";
                
                if(sendDataAsSJON) {
                    s = new ObjectMapper().writeValueAsString(data);
                } else {
                    for(String k : data.keySet()) {
                        if(encodeBodyData) {
                            s += k + "=" + URLEncoder.encode(data.get(k).toString(), "UTF-8") + "&";
                        } else {
                            s += k + "=" + data.get(k).toString() + "&";
                        }

                    }

                    s = s.substring(0, s.length() - 1);
                }

                if(verbose) {
                    System.out.println("data is " + s);
                }


                final PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(s);
                out.close();
            }

            respCode = conn.getResponseCode();

            if(respCode != 200) {
                InputStream in = new BufferedInputStream(conn.getErrorStream());
                content = readStream(in);

                if(verbose) {
                    System.out.println("error code => " + conn.getResponseCode());
                    System.out.println(content);
                }

            } else {
                if(!asByteArray) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    content = readStream(in);
                } else {

                }

                if(verbose) {
                    System.out.println("connect returned ok");
                    System.out.println(content);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public FlUrl data(String ...d) throws Exception {
        if((d.length % 2) != 0) {
            throw new Exception("Parameters should be key, value array, length is not divisible by 2");
        }

        for (int i = 0; i < d.length; i += 2) {
            data.put(d[i], d[i + 1]);
        } 

        conn.setDoInput(true);
        conn.setDoOutput(true);
        return this;
    }

    private  String  readStream(InputStream in) throws Exception {
        BufferedReader lcl_reader = new BufferedReader(new InputStreamReader(in));
        String lstr_line = "";
        String lstr_fetchedContent = "";

        while( (lstr_line = lcl_reader.readLine())!=null ){
            lstr_fetchedContent += lstr_line;
        }

        in.close(); //important to close the stream

        return lstr_fetchedContent;
    }

    //    private  byte[]  readStreamAsBytes(InputStream in) throws Exception {
    //        return IOUtils.toByteArray(new InputStreamReader(in));
    //    }

}