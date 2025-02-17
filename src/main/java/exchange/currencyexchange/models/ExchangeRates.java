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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BASECURRENCYID", referencedColumnName = "id")
    private Currencies baseCurrency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TARGETCURRENCYID", referencedColumnName = "id")
    private Currencies targetCurrency;

    @Column(name = "rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal rate;
}
