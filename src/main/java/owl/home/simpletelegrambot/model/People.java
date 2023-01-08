package owl.home.simpletelegrambot.model;


import lombok.*;


@Data
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class People {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
