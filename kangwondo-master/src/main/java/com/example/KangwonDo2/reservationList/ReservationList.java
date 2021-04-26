package com.example.KangwonDo2.reservationList;

import com.example.KangwonDo2.reservation.Reservation;
import com.example.KangwonDo2.room.Room;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationList {

    @Id @GeneratedValue
    @Column(name = "reservationList_id")
    private Long id;

    private Integer reservationIn;
    private Integer reservationOut;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne(fetch =  LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public ReservationList(Integer reservationIn) {
        this.reservationIn = reservationIn;
    }

    @Builder
    public ReservationList(Integer reservationIn, Integer reservationOut) {
        this.reservationIn = reservationIn;
        this.reservationOut = reservationOut;
    }

    /**
     * 연관관계 메소드
     */
    //ReservationList and Room
    public void addRoom(Room room){
        this.room = room;
        room.setReservationList(this);
    }

    /**
     * 생성 메소드
     */
    public static ReservationList createReservationList(int reservationIn, int reservationOut, Room room) {
        ReservationList reservationList = new ReservationList().builder()
                .reservationIn(reservationIn)
                .reservationOut(reservationOut)
                .build();
        reservationList.addRoom(room);
        return reservationList;
    }
}
