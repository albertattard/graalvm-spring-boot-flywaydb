package demo.quote;

import org.springframework.data.repository.ListCrudRepository;

public interface QuoteRepository extends ListCrudRepository<Quote, Long> {}
