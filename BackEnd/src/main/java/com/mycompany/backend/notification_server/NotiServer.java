/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.notification_server;

import com.mycompany.backend.controller.AdminManagedBean;
import com.mycompany.backend.model.AdminModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dao.UserDAO;
import org.json.JSONObject;

/**
 *
 * @author KhanhDN
 */
public class NotiServer {

    private static NotiServer instance = new NotiServer();

    private NotiServer() {
        client = HttpClientBuilder.create().build();
    }

    public static NotiServer getInstance() {
        return instance;
    }

    CloseableHttpClient client;

    public static final String WEBSERVICE_URL = "http://dailycook.cloudapp.net:8998/dailycook/";

    public void notiBanUser(String userId) {
        try {
            String url = WEBSERVICE_URL + "user/" + userId + "/ban";
            
            
            HttpPut put = new HttpPut(url);
            // TODO
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", "admin@gmail.com");
            jsonObj.put("password", "12345678");
            
            put.setEntity(new StringEntity(jsonObj.toString()));
            String result = getResponse(put);
            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void notiRemoveRecipe(String recipeId) {
        try {
            String url = WEBSERVICE_URL + "recipe/" + recipeId + "/remove";
            
            
            HttpPut put = new HttpPut(url);
            // TODO
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", "admin@gmail.com");
            jsonObj.put("password", "12345678");
            
            put.setEntity(new StringEntity(jsonObj.toString()));
            String result = getResponse(put);
            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    private String getResponse(HttpUriRequest httpRequest) throws IOException {
        HttpResponse httpResponse = client.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("Noti fail");
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(httpResponse.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }
}
