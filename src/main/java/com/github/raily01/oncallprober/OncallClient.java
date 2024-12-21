package com.github.raily01.oncallprober;

import com.github.raily01.oncallprober.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OncallClient {

    private final ApplicationProperties applicationProperties;

    public int listUsers() {
        var path = "/api/v0/users";
        var request = new HttpGet(applicationProperties.getOncallApiUrl() + path);
        request.setHeader(HttpHeaders.AUTHORIZATION, String.format("hmac %s:%s",
                applicationProperties.getApiUser(),
                buildHMACDigest("GET", path)));
        request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)
        ) {
            return response.getCode();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private String buildHMACDigest(String method, String path) {
        long window = System.currentTimeMillis() / 5;
        var format = String.format("%s %s %s", window, method, path);
        try {
            return HMACUtil.hmacWithJava(applicationProperties.getHmacAlgorithm(), format, applicationProperties.getApiKey());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error while signing request", e);
            throw new RuntimeException(e);
        }
    }
}
