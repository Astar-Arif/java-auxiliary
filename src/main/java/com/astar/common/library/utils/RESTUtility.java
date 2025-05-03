package com.astar.common.library.utils;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

// TODO IMPROVE ALL
@Service
public class RESTUtility {
    private final static Logger LOGGER = LoggerFactory.getLogger(RESTUtility.class);

    private final WebClient.Builder clientBuilder;

    public RESTUtility(WebClient.Builder builder) {
        this.clientBuilder = builder;
    }

    private WebClient getClient(
            String baseUrl,
            Map<String, String> headers,
            Map<String, String> cookies,
            Map<String, String> uriVariables,
            HttpClient http
    ) {
        WebClient.Builder builder = clientBuilder.clone()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if (headers != null) headers.forEach(builder::defaultHeader);
        if (cookies != null) cookies.forEach(builder::defaultCookie);
        if (uriVariables != null) builder.defaultUriVariables(uriVariables);
        if (http != null) builder.clientConnector(new ReactorClientHttpConnector(http));

        return builder.build();
    }

    public <T> T get(
            String baseUrl,
            String uri,
            Map<String, String> headers,
            Map<String, String> cookies,
            Map<String, String> uriVariables,
            Map<String, String> queryParams,
            int timeOutMilliseconds,
            Class<T> responseObj
    ) {
        Objects.requireNonNull(baseUrl, "BaseUrl cannot be null");
        if (uri == null) uri = "";
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeOutMilliseconds)
                .responseTimeout(Duration.ofMillis(timeOutMilliseconds))
                .doOnConnected(conn -> {
                    conn.addHandlerLast(
                                    new ReadTimeoutHandler(timeOutMilliseconds, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(timeOutMilliseconds,
                                                                    TimeUnit.MILLISECONDS));
                });
        WebClient client = this.getClient(baseUrl, headers, cookies, uriVariables, http);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        if (queryParams != null) {
            queryParams.forEach(uriBuilder::queryParam);
        }
        LOGGER.info("Sending Request To : " + (baseUrl + uriBuilder.toUriString()));
        return client
                .get()
                .uri(uriBuilder.toUriString())
                .retrieve()
                .bodyToMono(responseObj)
                .block(Duration.ofMillis(1000000));
    }

    public <T, S> T post(
            String baseUrl,
            String uri,
            Map<String, String> headers,
            Map<String, String> cookies,
            Map<String, String> uriVariables,
            Map<String, String> queryParams,
            int timeOutMilliseconds,
            Object requestBody,
            Class<T> responseObj
    ) {
        Objects.requireNonNull(baseUrl, "BaseUrl cannot be null");
        if (uri == null) uri = "";
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeOutMilliseconds)
                .responseTimeout(Duration.ofMillis(timeOutMilliseconds))
                .doOnConnected(conn -> {
                    conn.addHandlerLast(
                                    new ReadTimeoutHandler(timeOutMilliseconds, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(timeOutMilliseconds,
                                                                    TimeUnit.MILLISECONDS));
                });
        WebClient client = this.getClient(baseUrl, headers, cookies, uriVariables, http);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);
        if (queryParams != null){
            queryParams.forEach(uriBuilder::queryParam);
        }
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(uriBuilder.toUriString());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(requestBody);
//        TODO IMPROVE THIS
        LOGGER.info("Sending Request To : " + uriBuilder.toUriString());
        return headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()
                .bodyToMono(responseObj)
                .block(Duration.ofMillis(1000));
    }

//    public static void main(String[] args) {
////        !4.1
//        WebClient client = WebClient.create();
//
//        WebClient client1 = WebClient.create("http://localhost:8080");
//
//        WebClient client2 = WebClient.builder()
//                .baseUrl("http://localhost:8080")
//                .defaultCookie("cookieKey", "cookieValue")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
//                .build();
////        !4.2
//        HttpClient httpClient = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                .responseTimeout(Duration.ofMillis(5000))
//                .doOnConnected(conn ->
//                                       conn
//                                               .addHandlerLast(new ReadTimeoutHandler(5000,
//                                                                                      TimeUnit.MILLISECONDS))
//                                               .addHandlerLast(new WriteTimeoutHandler(5000,
//                                                                                       TimeUnit.MILLISECONDS))
//                );
//
//        WebClient client3 = WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
////        !4.3
//        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
//        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec1 = client.post();
//
////        !4.4
//        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/resource");
//        WebClient.RequestBodySpec bodySpec1 = uriSpec.uri(
//                uriBuilder -> uriBuilder.pathSegment("/resource").build()
//        );
////        !4.5
//        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");
//        WebClient.RequestHeadersSpec<?> headersSpec1 = bodySpec.body(
//                Mono.just(new Testing()), Testing.class
//        );
//        WebClient.RequestHeadersSpec<?> headersSpec2 = bodySpec.body(
//                BodyInserters.fromValue("body")
//        );
//        WebClient.RequestHeadersSpec<?> headersSpec3 = bodySpec.body(
//                BodyInserters.fromPublisher(Mono.just("data"), String.class)
//        );
//
//        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("k1", "value1");
//        map.add("k2", "value2");
//        WebClient.RequestHeadersSpec<?> headersSpec4 = bodySpec.body(
//                BodyInserters.fromMultipartData(map));
//
////        !4.6
//        WebClient.ResponseSpec responseSpec = headersSpec
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
//                .acceptCharset(StandardCharsets.UTF_8)
//                .ifNoneMatch("*")
//                .ifModifiedSince(ZonedDateTime.now())
//                .retrieve();
////        !4.7
//        Mono<String> response = headersSpec.exchangeToMono(resp -> {
//            if (resp.statusCode().equals(HttpStatus.OK)) {
//                return resp.bodyToMono(String.class);
//            } else if (resp.statusCode().is4xxClientError()) {
//                return Mono.just("Error Response");
//            } else {
//                return resp.createException().flatMap(Mono::error);
//            }
//        });
//
//        Mono<String> response1 = headersSpec1.retrieve().bodyToMono(String.class);
//    }

    public static class Testing {
        private int age;
        private String name;
    }
}
