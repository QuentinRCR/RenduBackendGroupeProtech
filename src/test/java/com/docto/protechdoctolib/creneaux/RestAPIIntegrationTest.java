package com.docto.protechdoctolib.creneaux;

import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
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
        Assertions.assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND);

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
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assertions.assertThat( jsonMimeType).isEqualTo(mimeType );
    }

    /*@Test
    public void
    givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect()
            throws ClientProtocolException, IOException {

        // Given
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/creneaux/1000" );

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        HttpEntity a = response.getEntity();
        // Then

        GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(
                response, GitHubUser.class);
        assertThat( "eugenp", Matchers.is( resource.getLogin() ) );
    }*/
}
