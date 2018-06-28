package com.noeltest.resourceserver1;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestRes1Auth {
    private final static String AUTH_SERVER = "http://localhost:8083";
    private final static String RES_SERVER = "http://localhost:8084";
    
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
            
            System.out.println("\n**************** get res1 message NO TOKEN");
            url = new FlUrl(RES_SERVER + "/res1/message");
//            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** get res1 message WITH TOKEN");
            url = new FlUrl(RES_SERVER + "/res1/message");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
