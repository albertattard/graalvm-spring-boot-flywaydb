package demo.quote;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class QuoteService {

    private final QuoteRepository repository;

    public QuoteService(final QuoteRepository repository) {
        this.repository = requireNonNull(repository, "The quote repository cannot be null");
    }

    public Optional<Quote> random() {
        /* Returns a random quote, inefficiently, by loading all the quotes from
            the database and then picking one at random. A more efficient way
            would have fetched the count of quotes in the table and then return
            the one found at a random index using the SQL LIMIT and OFFSET. */
        final List<Quote> quotes = new ArrayList<>(repository.findAll());

        if (quotes.isEmpty()) {
            return Optional.empty();
        }

        Collections.shuffle(quotes);
        return Optional.of(quotes.getFirst());
    }
}
