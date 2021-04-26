package com.example.KangwonDo2.room;

import com.example.KangwonDo2.place.Place;
import com.example.KangwonDo2.reservationList.ReservationList;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String name;
    private int price;
    private String grade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToOne(fetch = LAZY, mappedBy = "room")
    private ReservationList reservationList;

    public Room(String name) {
        this.name = name;
    }

    @Builder
    public Room(String name, int price, String grade) {
        this.name = name;
        this.price = price;
        this.grade = grade;
    }

    /**
     * 연관관계 편의 메소드
     */
    //Room and ReservationList
    public void addReservationList(ReservationList reservationList){
        this.reservationList = reservationList;
        reservationList.setRoom(this);
    }

    /**
     * 생성 메서드
     */
    public static Room createRoom(String name, int price, String grade){
        return new Room().builder()
                .name(name)
                .price(price)
                .grade(grade)
                .build();
    }
}
