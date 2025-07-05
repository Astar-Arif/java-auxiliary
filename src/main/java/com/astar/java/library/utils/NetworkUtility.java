package com.astar.java.library.utils;
import com.astar.java.library.enums.HttpMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public abstract class NetworkUtility {
    //    IMPLEMENT
    public static int CONNECT_TIMEOUT_MILLISECOND = 5000;
    public static int READ_TIMEOUT_MILLISECOND = 5000;

    public static Object send(String uri, HttpMethod method, Map<String, Object> headers, Object requestBody)
            throws IOException {
        URL url = URI.create(uri).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method.toString());
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            conn.setRequestProperty(header.getKey(), header.getValue().toString());
        }
        conn.setConnectTimeout(CONNECT_TIMEOUT_MILLISECOND);
        conn.setReadTimeout(READ_TIMEOUT_MILLISECOND);
        return null;
    }

}
