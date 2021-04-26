package com.example.KangwonDo2.room;

import com.example.KangwonDo2.reservationList.ReservationList;
import com.example.KangwonDo2.reservationList.ReservationListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoomTest {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationListRepository reservationListRepository;

    @Test
    public void 방_저장() throws Exception {
        //given
        Room room = new Room();
        room.setName("101호");
        room.setPrice(70000);
        room.setGrade("3성");
        roomRepository.save(room);
        //when
        Room findRoom = roomRepository.findById(room.getId()).orElse(null);
        //then
        assertThat(findRoom.getName()).isEqualTo("101호");
        assertThat(findRoom.getPrice()).isEqualTo(70000);
        assertThat(findRoom.getGrade()).isEqualTo("3성");

     }

     @Test
     public void 방_예약리스트_연관관계() throws Exception {
         //given
         ReservationList reservationList = new ReservationList(5);
         reservationList.setReservationOut(2);
         reservationListRepository.save(reservationList);

         Room room = new Room();
         room.setName("202호");
         room.setPrice(50000);
         room.setGrade("2성");
         room.addReservationList(reservationList);
         roomRepository.save(room);
         //when
         Room findRoom = roomRepository.findById(room.getId()).orElse(null);
         ReservationList findReservationList = reservationListRepository.findById(reservationList.getId()).orElse(null);
         //then
         assertThat(findRoom.getReservationList().getReservationIn()).isEqualTo(5);
         assertThat(findRoom.getReservationList().getReservationOut()).isEqualTo(2);

         assertThat(findReservationList.getRoom().getName()).isEqualTo("202호");
         assertThat(findReservationList.getRoom().getPrice()).isEqualTo(50000);
         assertThat(findReservationList.getRoom().getGrade()).isEqualTo("2성");

      }

}