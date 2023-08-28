package by.wadikk.telegrambot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_zone", columnDefinition = "default 0")
    //sets the broadcast time of events for your time zone
    private int timeZone;

    @OneToMany(mappedBy = "user")
    private List<Event> events;

    @Column(name = "on_off")
    // on/off send event
    private boolean on;


}
