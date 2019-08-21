package com.bharat23.HTTPBackendListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import com.google.gson.Gson;
import java.util.zip.GZIPOutputStream;

class Fetch {

    private String enableCompression = "false";

    Fetch (String enableCompression) {
        this.enableCompression = enableCompression;
    }

    String post(String url, Map<String, Object> data) {
        try {
            URL apiURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) apiURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            if (this.enableCompression.equals("true")) {
                con.setRequestProperty("Content-Encoding", "gzip");
                GZIPOutputStream wr = new GZIPOutputStream(con.getOutputStream());
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(data);
                wr.write(jsonStr.getBytes());
                wr.flush();
                wr.close();
            } else {
                con.setRequestProperty("Content-Encoding", "utf-8");
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(data);
                wr.writeBytes(jsonStr);
                wr.flush();
                wr.close();
            }

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            System.out.println(response.toString());
            con.disconnect();
            return response.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "{}";
    }

    String post(String url, ArrayList data) {
        try {
            URL apiURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) apiURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            if (this.enableCompression.equals("true")) {
                con.setRequestProperty("Content-Encoding", "gzip");
                GZIPOutputStream wr = new GZIPOutputStream(con.getOutputStream());
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(data);
                System.out.println(jsonStr);
                wr.write(jsonStr.getBytes());
                wr.flush();
                wr.close();
            } else {
                con.setRequestProperty("Content-Encoding", "utf-8");
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                Gson gsonObj = new Gson();
                String jsonStr = gsonObj.toJson(data);
                wr.writeBytes(jsonStr);
                wr.flush();
                wr.close();
            }
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            con.disconnect();
            return response.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "{}";
    }

    Map get(String url) {
        try {
            URL apiURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) apiURL.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            con.disconnect();
            Gson gsonObj = new Gson();
            Map map = gsonObj.fromJson(response.toString(), Map.class);
            return map;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return new HashMap<>();
    }
}