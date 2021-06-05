package com.example.currentplacedetailsonmap;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketException;

public class ServerRequestHandler {

    public static final String url ="http://34.70.250.44:34601/";
    public static int sendHttpPOSTRequest(String route, String data)
    {
        try
        {
            if(data == null || data.length() == 0)
                return -1;
            int timeout = 5;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();
            CloseableHttpClient client =
                    HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpPost httpPost = new HttpPost(url+route);
            StringEntity entity = new StringEntity(data);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            int responseCode = response.getStatusLine().getStatusCode();
            client.close();
            return responseCode == 200 ? 0 : -1;
        }
        catch (SocketException e)
        {
            e.printStackTrace();
            return -1;
        }
        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String sendHttpGetRequest(String route, String params)
    {
        try {
            int timeout = 5;
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();
            CloseableHttpClient client =
                    HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            String _url = "";
            if(route.length() == 0)
            {
                _url = url;
            }
            else
            {
                _url = url+route+"/"+params;
            }
            CloseableHttpResponse response = client.execute(new HttpGet(_url));
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return bodyAsString;
        }
        catch (SocketException e)
        {
            e.printStackTrace();
            return "";
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
