package exchange.currencyexchange.models;

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
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "BASECURRENCYID")
    private Integer baseCurrencyId;

    @Column(name = "TARGETCURRENCYID")
    private Integer targetCurrencyId;

    @Column(name = "rate", nullable = false, precision = 6, scale = 2) // Decimal(6, 2)
    private BigDecimal rate;
}
