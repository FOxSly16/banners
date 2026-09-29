package ru.soliev.practice.banners.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Request")
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private Banner banner;

    @Column(name = "user_agent")
    @NotNull
    private String userAgent;

    @Column(name = "ip_address")
    @NotNull
    private String ip;

    @Column(name = "date")
    @NotNull
    private LocalDateTime time;
}
