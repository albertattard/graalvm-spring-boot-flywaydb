package demo.quote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class QuoteServiceTest {

    @MockitoBean
    private QuoteRepository repository;

    @Autowired
    private QuoteService service;

    @Test
    void returnOptionalEmptyWhenRepositoryReturnsNoQuotes() {
        /* Given */
        when(repository.findAll()).thenReturn(List.of());

        /* When */
        final Optional<Quote> response = service.random();

        /* Then */
        assertThat(response)
                .describedAs("The repository returned no quotes")
                .isEmpty();
    }

    @Test
    void returnTheSingleQuote() {
        /* Given */
        final Quote quote = QUOTE_1;
        when(repository.findAll()).thenReturn(List.of(quote));

        /* When */
        final Optional<Quote> response = service.random();

        /* Then */
        assertThat(response)
                .describedAs("The single quote returned by the repository")
                .isEqualTo(Optional.of(quote));
    }

    @Test
    void returnARandomQuote() {
        /* Given */
        final List<Quote> quotes = List.of(QUOTE_1, QUOTE_2);
        when(repository.findAll()).thenReturn(quotes);

        /* When */
        final Optional<Quote> response = service.random();

        /* Then */
        assertThat(response)
                .describedAs("One of the quotes returned by the repository")
                .isPresent();
        assertThat(response.get())
                .describedAs("One of the quotes returned by the repository")
                .isIn(quotes);
    }

    private static final Quote QUOTE_1 = new Quote(1L, "Albert Einstein", "Learn from yesterday, live for today, hope for tomorrow. The important thing is not to stop questioning.");

    private static final Quote QUOTE_2 = new Quote(2L, "Eleanor Roosevelt", "The future belongs to those who believe in the beauty of their dreams.");
}
