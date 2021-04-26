package com.example.KangwonDo2.place;

import com.example.KangwonDo2.room.Room;
import com.example.KangwonDo2.room.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaceTest {

    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    RoomRepository roomRepository;

    @Test
    public void 장소_저장() throws Exception {
        //given
        Place place = new Place();
        place.setName("강원도민박2호점");
        place.setAddress("강원도 강릉시");
        place.setPhone("123456789");
        placeRepository.save(place);
        //when
        Place findPlace = placeRepository.findById(place.getId()).orElse(null);
        //then
        assertThat(findPlace.getName()).isEqualTo("강원도민박2호점");
        assertThat(findPlace.getAddress()).isEqualTo("강원도 강릉시");
        assertThat(findPlace.getPhone()).isEqualTo("123456789");
     }

     @Test
     public void 장소_방_연관관계() throws Exception {
         //given
         Room room = new Room("101호");
         room.setPrice(70000);
         room.setGrade("3성");
         roomRepository.save(room);

         Place place = new Place();
         place.setName("강원도민박1호점");
         place.setAddress("강원도 강릉시");
         place.setPhone("123456");
         place.addRoom(room);
         placeRepository.save(place);
         //when
         Place findPlace = placeRepository.findById(place.getId()).orElse(null);
         Room findRoom = roomRepository.findById(room.getId()).orElse(null);
         //then
         assertThat(findPlace.getName()).isEqualTo("강원도민박1호점");
         assertThat(findPlace.getAddress()).isEqualTo("강원도 강릉시");
         assertThat(findPlace.getPhone()).isEqualTo("123456");
         assertThat(findPlace.getRooms().get(0).getName()).isEqualTo("101호");
         assertThat(findPlace.getRooms().get(0).getPrice()).isEqualTo(70000);
         assertThat(findPlace.getRooms().get(0).getGrade()).isEqualTo("3성");

         assertThat(findRoom.getName()).isEqualTo("101호");
         assertThat(findRoom.getPrice()).isEqualTo(70000);
         assertThat(findRoom.getGrade()).isEqualTo("3성");
         assertThat(findRoom.getPlace().getName()).isEqualTo("강원도민박1호점");
         assertThat(findRoom.getPlace().getAddress()).isEqualTo("강원도 강릉시");
         assertThat(findRoom.getPlace().getPhone()).isEqualTo("123456");

      }

}