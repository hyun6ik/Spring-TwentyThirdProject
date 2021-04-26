package com.example.KangwonDo2.place;

import com.example.KangwonDo2.reservation.Reservation;
import com.example.KangwonDo2.review.Review;
import com.example.KangwonDo2.room.Room;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id @GeneratedValue
    @Column(name = "place_id")
    private Long id;

    private String name;
    private String address;
    private String phone;

    @OneToOne(fetch = LAZY, mappedBy = "place")
    private Review review;

    @OneToMany(mappedBy = "place")
    private List<Room> rooms = new ArrayList<>();


    public Place(String name) {
        this.name = name;
    }

    @Builder
    public Place(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    /**
     * 연관관계 편의 메소드
     */
    //Place and Room
    public void addRoom(Room room){
        if(this.rooms != null){
            this.getRooms().remove(this);
        }
        this.getRooms().add(room);
        room.setPlace(this);
    }


    /**
     * 생성 메소드
     */
    public static Place createPlace(String name, String address, String phone, Room... room ){
        Place place = new Place().builder()
                .name(name)
                .address(address)
                .phone(phone)
                .build();
        for (Room r : room) {
            place.addRoom(r);
        }
        return place;
    }

}
