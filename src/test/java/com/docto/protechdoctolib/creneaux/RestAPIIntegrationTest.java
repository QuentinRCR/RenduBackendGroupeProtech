package com.docto.protechdoctolib.creneaux;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RestAPIIntegrationTest {

    /**
     * Test que si on envoie une requette où il trouve pas le creneau, le code de réponse est 404
     * @throws ClientProtocolException
     * @throws IOException
     */
    @Test
    public void givenSlotDoesNotExists_whenSlotIsRetrieved_then404IsReceived()
            throws ClientProtocolException, IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/creneaux/20");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        Assertions.assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND); //test that the status code is correct

    }

    /**
     * Test que la réponse du body est bien un json
     * @throws ClientProtocolException
     * @throws IOException
     */
    @Test
    public void
    givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws ClientProtocolException, IOException {

        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/creneaux/20" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType(); //test that the returned document is a JSON
        Assertions.assertThat( jsonMimeType).isEqualTo(mimeType );
    }

    /**
     * Test that the json sent is the correct one (correct id)
     * @throws ClientProtocolException
     * @throws IOException
     */
    @Test
    public void
    givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect()
            throws ClientProtocolException, IOException {

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/creneaux/1000" ); //create the request

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request ); //execute the request
        String jsonFromResponse = EntityUtils.toString(response.getEntity()); //transforme the response to string
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CreneauDeserialisation a= mapper.readValue(jsonFromResponse, CreneauDeserialisation.class); //map the response to the class CreneauDeserialisation
        Assertions.assertThat(a.getId()).isEqualTo(1000); //test that the id is equal to 1000
    }
}
