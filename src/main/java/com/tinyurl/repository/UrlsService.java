package com.tinyurl.repository;

import com.tinyurl.repository.Dao.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UrlsService {
    @Autowired
    private UrlsRepository urlsRepository;

    public String createTinyUrl(String urlValue){
        Url url = new Url(urlValue);
        urlsRepository.saveAndFlush(url);
        return urlsRepository.findByUrl(url).toString();
    }
    public String getTinyUrl(String url) {

        return urlsRepository.findByUrl(url).toString();
    }
    public String getLongUrl(String url) {
        return urlsRepository.findById(Long.parseUnsignedLong(url)).orElseThrow().getUrl();
    }

}
