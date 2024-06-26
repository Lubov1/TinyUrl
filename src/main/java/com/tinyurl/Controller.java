package com.tinyurl;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@RestController
public class Controller {
    @Autowired UrlsService urlsService;
    RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value="/tinyurl/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<String> register(@RequestBody JsonNode jsonNode){
        String longUrl = jsonNode.get("url").asText();
        String tinyUrl = urlsService.getTinyUrl(longUrl);
        urlsService.put(tinyUrl, longUrl);
        return new ResponseEntity<>(String.format("{\"tinyUrl\":\"%s\"}",tinyUrl), HttpStatusCode.valueOf(200));
    }

    @PostMapping(value = "/tinyurl/{value}")
    public void forward(@PathVariable String value,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        String longUrl = urlsService.getLongUrl(value);
        if (longUrl == null) {
            response.sendError(400);
        }
        response.sendRedirect(longUrl);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
