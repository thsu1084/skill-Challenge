package com.springboot.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Column
    LocalDateTime reservedTime;


    @Column(nullable = false)
    private boolean rejected = false;

    @Column
    private String email;

    /*
    예약 가능한 시간인지를 확인하는 기능을 제공합니다.
    */
    public static boolean ReservationAvailable(Reservation reservation, List<Reservation> existReservations) {
        LocalDateTime reservedTime = reservation.getReservedTime();

        for (Reservation existReservation : existReservations) {
            if (reservedTime.isEqual(existReservation.getReservedTime())) {
                return false;
            }
        }


         return true;
    }

    /*
    예약의 거절 상태(rejected)를 설정하는 기능을 제공합니다.
     */
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
