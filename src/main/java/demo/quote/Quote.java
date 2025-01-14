package demo.quote;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
public class Quote implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String quote;

    protected Quote() {}

    protected Quote(final String author, final String quote) {
        this.author = checkAuthor(author);
        this.quote = checkQuote(quote);
    }

    public Quote(final long id, final String author, final String quote) {
        this.id = checkId(id);
        this.author = checkAuthor(author);
        this.quote = checkQuote(quote);
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public boolean equals(final Object object) {
        return object instanceof final Quote other
               && getClass() == other.getClass()
               && Objects.equals(id, other.id)
               && Objects.equals(author, other.author)
               && Objects.equals(quote, other.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, quote);
    }

    @Override
    public String toString() {
        return "Quote[id=" + id + ", author=" + author + ", quote=" + quote + ']';
    }

    private static Long checkId(final long id) {
        checkArgument(id > 0, "The quote id cannot be less than 1");
        return id;
    }

    private static String checkAuthor(final String author) {
        checkArgument(author != null && !author.isEmpty() && author.length() <= 32, "The quote author name cannot be null, empty and must be less than or equal to 32 letters");
        return author;
    }

    private static String checkQuote(final String quote) {
        checkArgument(quote != null && !quote.isEmpty() && quote.length() <= 255, "The quote cannot be null, empty and must be less than than or equal to 255 letters");
        return quote;
    }
}
