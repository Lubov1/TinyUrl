package com.example.tinyurl;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UrlsService {
    private HashMap<String, String> tinyUrls = new HashMap<>();
    public String getTinyUrl(String url) {
        return String.valueOf(url.hashCode());
    }
    public String getLongUrl(String url) {
        return tinyUrls.get(url);
    }

    public void put(String tinyUrl, String longUrl) {
        tinyUrls.put(tinyUrl, longUrl);
    }
}
