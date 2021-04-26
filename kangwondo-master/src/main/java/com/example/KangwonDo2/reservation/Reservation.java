package com.example.KangwonDo2.reservation;

import com.example.KangwonDo2.reservationList.ReservationList;
import com.example.KangwonDo2.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reservation {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY, mappedBy = "reservation")
    private ReservationList reservationList;



    /**
     * 연관관계 메소드
     */
    //Reservation and ReservationList
    public void addReservationList(ReservationList reservationList){
        this.reservationList = reservationList;
        reservationList.setReservation(this);
    }

    /**
     * 생성 메소드
     */
    public static Reservation createReservation(ReservationList reservationList){
        Reservation reservation = new Reservation();
        reservation.addReservationList(reservationList);
        return reservation;
    }
}
