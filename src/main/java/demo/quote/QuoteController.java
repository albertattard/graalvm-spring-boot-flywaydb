package demo.quote;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService service;

    public QuoteController(final QuoteService service) {
        this.service = requireNonNull(service, "The quote service cannot be null");
    }

    @GetMapping("/random")
    public ResponseEntity<Quote> random() {
        return service.random()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
