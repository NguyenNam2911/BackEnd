/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.notification_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

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

    public static final String WEBSERVICE_URL = "dailycook.cloudapp.net:8998/dailycook/";

    public void notiBanUser(String userId) {
        try {
            String url = WEBSERVICE_URL + userId + "/ban";
            HttpPut put = new HttpPut(url);
            String result = getResponse(put);
            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void notiDeleteRecipe(String recipeId) {

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
