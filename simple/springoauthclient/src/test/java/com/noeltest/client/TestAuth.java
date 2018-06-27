package com.noeltest.client;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestAuth {
    private final static ObjectMapper mapper = new ObjectMapper();
    
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static void prettyPrint(String json) throws Exception {
        Object o = mapper.readValue(json, Object.class);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
    }
    
    public static String getToken(String username, String password) throws Exception {
        System.out.println("\n**************** get token");
        FlUrl url = new FlUrl("http://localhost:8081/oauth/token");
        
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
            System.out.println("\n**************** test NO TOKEN");
            FlUrl url = new FlUrl("http://localhost:8082/test");
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            String accToken = getToken("john", "123");
            
            System.out.println("\n**************** get user info");
            url = new FlUrl("http://localhost:8081/user/me");
            url.oauth2(accToken);
            
            url.get();
            System.out.println(url.getRespCode());
            prettyPrint(url.getContent());
            
            System.out.println("\n**************** test end point in auth service");
            url = new FlUrl("http://localhost:8081/user/hello");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test");
            url = new FlUrl("http://localhost:8082/test");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test with read perm");
            url = new FlUrl("http://localhost:8082/testREAD");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test with write perm");
            url = new FlUrl("http://localhost:8082/testWRITE");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test with USER auth");
            url = new FlUrl("http://localhost:8082/testREAD_auth");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test with ADMIN auth");
            url = new FlUrl("http://localhost:8082/testADMIN_auth");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            accToken = getToken("tom", "123");
            
            System.out.println("\n**************** test with ADMIN auth");
            url = new FlUrl("http://localhost:8082/testADMIN_auth");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test with USER auth");
            url = new FlUrl("http://localhost:8082/testREAD_auth");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
            System.out.println("\n**************** test get USER");
            url = new FlUrl("http://localhost:8082/getUser");
            url.oauth2(accToken);
            url.get();
            System.out.println(url.getRespCode() + " : " + url.getContent());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
