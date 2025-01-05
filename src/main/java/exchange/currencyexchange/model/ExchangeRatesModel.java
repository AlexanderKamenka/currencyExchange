package exchange.currencyexchange.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRatesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_currency_id", nullable = false) // Внешний ключ на Currencies.ID
    private Currencies baseCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id", nullable = false) // Внешний ключ на Currencies.ID
    private Currencies targetCurrency;

    @Column(name = "rate", nullable = false, precision = 6, scale = 2) // Decimal(6, 2)
    private BigDecimal rate;
}

