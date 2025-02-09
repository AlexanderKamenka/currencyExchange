package exchange.currencyexchange.repository;

import exchange.currencyexchange.models.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Integer> {

    boolean existsByBaseCurrencyIdAndTargetCurrencyId(Integer baseCurrencyId, Integer targetCurrencyId);

    Optional<ExchangeRates> findByBaseCurrencyIdAndTargetCurrencyId(Integer baseCurrencyId, Integer targetCurrencyId);
}
