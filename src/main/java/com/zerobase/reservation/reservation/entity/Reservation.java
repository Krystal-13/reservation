package com.zerobase.reservation.reservation.entity;

import com.zerobase.reservation.reservation.type.State;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;
    LocalTime time;

    @ColumnDefault("1")
    Integer num;

    String userName;
    String userEmail;
    String phone;
    String specialRequest;
    Long restaurantId;
    Long userId;

    boolean visited;
    State state;

}
