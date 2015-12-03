/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.backend.notification_server;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.DCAHttpRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author KhanhDN
 */
public class NotiServer {

    private static final String HOST = "dailycook.cloudapp.net";
    private static final int PORT = 8998;
    Logger logger = LoggerFactory.getLogger(getClass());

    private static NotiServer instance = new NotiServer();

    private NotiServer() {
    }

    public static NotiServer getInstance() {
        return instance;
    }

    public void notiBanUser(String userId) {
        try {
            String path = "/dailycook/user/" + userId + "/ban";
            
            Map<String,String> parameters = new HashMap<>();
            parameters.put("flag", "ban");
            URI uri = DCAHttpRequest.getInstance().buildUrl(HOST, PORT, path, parameters);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", "admin@gmail.com");
            jsonObj.put("password", "12345678");
            int status = DCAHttpRequest.getInstance().put(uri, jsonObj.toString());
            if (status == 200) {
                logger.info("NotiServer -> notiBanUser successful");
            } else {
                logger.info("NotiServer -> notiBanUser fail");
            }
        } catch (Exception ex) {
            logger.error("NotiServer -> notiBanUser error", ex);
        }
    }

    public void notiUnbanUser(String userId) {
        try {
            String path = "/dailycook/user/" + userId + "/ban";
            
            Map<String,String> parameters = new HashMap<>();
            parameters.put("flag", "unban");
            URI uri = DCAHttpRequest.getInstance().buildUrl(HOST, PORT, path, parameters);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", "admin@gmail.com");
            jsonObj.put("password", "12345678");
            int status = DCAHttpRequest.getInstance().put(uri, jsonObj.toString());
            if (status == 200) {
                logger.info("NotiServer -> notiBanUser successful");
            } else {
                logger.info("NotiServer -> notiBanUser fail");
            }
        } catch (Exception ex) {
            logger.error("NotiServer -> notiBanUser error", ex);
        }
    }

    public void notiRemoveRecipe(String recipeId) {
        try {
            String path = "/dailycook/recipe/" + recipeId + "/remove";
            URI uri = DCAHttpRequest.getInstance().buildUrl(HOST, PORT, path, null);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", "admin@gmail.com");
            jsonObj.put("password", "12345678");
            int status = DCAHttpRequest.getInstance().put(uri, jsonObj.toString());
            if (status == 200) {
                logger.info("NotiServer -> notiRemoveRecipe successful");
            } else {
                logger.info("NotiServer -> notiRemoveRecipe fail");
            }
        } catch (Exception ex) {
            logger.error("NotiServer -> notiRemoveRecipe error", ex);
        }

    }

}
