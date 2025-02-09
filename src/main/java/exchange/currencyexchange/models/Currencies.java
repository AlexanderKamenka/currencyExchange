package exchange.currencyexchange.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FullName")
    private String name;

    @Column(name = "Code")
    private String code;

    @Column(name = "Sign")
    private String sign;


}
