/*
package com.docto.protechdoctolib.creneaux;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RESTAPIIntegrationTest {*/

    /***
     * Test if response in a json file
     * @throws ClientProtocolException
     * @throws IOException
     */
    /*@Test
    public void
    testResponseIsJson()
            throws ClientProtocolException, IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/creneaux" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }



}
*/