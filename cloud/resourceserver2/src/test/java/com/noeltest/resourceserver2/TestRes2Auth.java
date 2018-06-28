package com.noeltest.resourceserver2;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestRes2Auth {
    private final static String AUTH_SERVER = "http://localhost:8083";
    private final static String RES1_SERVER = "http://localhost:8084";
    private final static String RES2_SERVER = "http://localhost:8085";
    
    private final static ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static void prettyPrint(String json) throws Exception {
        System.out.println(json);
        if(json == null || json.length() == 0) {
            return;
        }
        
        Object o = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
    }
    
    public static String getToken(String username, String password) throws Exception {
        System.out.println("\n**************** get token");
        FlUrl url = new FlUrl(AUTH_SERVER + "/oauth/token");
        
        url.header("Authorization","Basic " + 
                        Base64.getEncoder().encodeToString("SampleClientId:secret".getBytes()));
        
        url.header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        
        url.data("grant_type", "password", 
                        "username", username, 
                        "password", password
                        );
        
        url.post();
        System.out.println(url.getRespCode() + " : " + url.getContent());
        
        JsonNode node = mapper.readTree(url.getContent());
        
        String accToken = node.get("access_token").asText();
        System.out.println(accToken);
        
        return accToken;
    }
    
    public static void main(String[] args) {
        try {
            String accToken = getToken("john", "123");
            
            System.out.println("\n**************** get user info");
            FlUrl url = new FlUrl(AUTH_SERVER + "/user/me");
            url.oauth2(accToken);
            
            url.get();
            System.out.println(url.getRespCode());
            prettyPrint(url.getContent());
            
            System.out.println("\n**************** get res1 message WITH TOKEN");
            url = new FlUrl(RES1_SERVER + "/res1/message");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** get res2 message WITH TOKEN");
            url = new FlUrl(RES2_SERVER + "/res2/message");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** get res2 user");
            url = new FlUrl(RES2_SERVER + "/res2/user");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** get token from header");
            url = new FlUrl(RES2_SERVER + "/res1/inspectHeader");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** get res1 from res2 using header");
            url = new FlUrl(RES2_SERVER + "/res1/res1messageWithHeader");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
          System.out.println("\n**************** get res1 from res2");
          url = new FlUrl(RES2_SERVER + "/res1/res1message");
          url.oauth2(accToken);
          url.get();
          System.out.println(url.getRespCode() + " : " + url.getContent());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
