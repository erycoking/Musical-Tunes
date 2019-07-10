package com.erycoking.MusicStore.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SongControllerIntegrationTest {

    private static final String TAG = "SongControllerIntegrationTest";

    @LocalManagementPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testSong() {
        String token = getToken();
        headers.add("Authorization", "Bearer "+ token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/songs"), HttpMethod.GET,
                entity, String.class
        );

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    public String getToken(){

        String credentials = "{\"username\":\"erycoking360@gmail.com\", \"password\":\"password\"}";
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(credentials, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/auth/signin"), entity, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        try {
            JSONObject obj = new JSONObject(response.getBody());
            String token = obj.getString("token");
            return  token;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createURLWithPort(String s) {
        return "http://localhost:"+port+"/api/v1"+s;
    }
}
