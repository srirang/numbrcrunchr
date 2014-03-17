package com.numbrcrunchr.api;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class NumbrCrunchrClient {
    private static final URI URI = UriBuilder.fromUri(
            "http://localhost:8080/numbrcrunchr/").build();
    private static final WebResource SERVICE = Client.create(
            new DefaultClientConfig()).resource(URI);

    @Test
    public void checkApi() {
        // Fluent interfaces
        assertEquals("Hello Jersey", SERVICE.path("api").path("numbrcrunchr")
                .accept(MediaType.TEXT_PLAIN).get(String.class));
    }
}
