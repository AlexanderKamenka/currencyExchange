package exchange.currencyexchange.repository;

import exchange.currencyexchange.models.Currencies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrenciesRepository extends CrudRepository<Currencies, Integer> {
    boolean existsByCode(String code);
    Currencies findByCode(String code);

}
