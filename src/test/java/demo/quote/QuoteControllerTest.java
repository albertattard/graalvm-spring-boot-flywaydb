package demo.quote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerTest {

    @MockitoBean
    private QuoteService service;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate()
                .setRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Test
    void returnNotFoundWhenNoQuotesAreFound() {
        /* Given */
        when(service.random()).thenReturn(Optional.empty());

        /* When */
        final ResponseEntity<Quote> response = makeGetRandomQuoteRequest();

        /* Then */
        assertThat(response.getStatusCode())
                .describedAs("Return HTTP NOT_FOUND when a random quote is not found")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void returnFoundWithTheRandomQuote() {
        /* Given */
        final Quote quote = new Quote(1L, "Albert Einstein", "Learn from yesterday, live for today, hope for tomorrow. The important thing is not to stop questioning.");
        when(service.random()).thenReturn(Optional.of(quote));

        /* When */
        final ResponseEntity<Quote> response = makeGetRandomQuoteRequest();

        /* Then */
        assertThat(response.getStatusCode())
                .describedAs("Return HTTP OK when a random quote is found")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .describedAs("Return the random quote as a JSON object")
                .isEqualTo(quote);
    }

    private ResponseEntity<Quote> makeGetRandomQuoteRequest() {
        return restTemplate.getForEntity("/quote/random", Quote.class);
    }
}
