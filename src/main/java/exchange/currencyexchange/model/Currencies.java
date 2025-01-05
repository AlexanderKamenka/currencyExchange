package exchange.currencyexchange.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currencies {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FullName")
    private String name;

    @Column(name = "Code")
    private String code;

    @Column(name = "Sign")
    private String sign;


}
