package exchange.currencyexchange.repository;

import exchange.currencyexchange.model.Currencies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrenciesRepository extends CrudRepository<Currencies, Long> {
    boolean existsByCode(String code);

}
