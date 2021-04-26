package com.example.KangwonDo2.reservationList;

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
class ReservationListTest {

    @Autowired
    ReservationListRepository reservationListRepository;
    @Autowired
    RoomRepository roomRepository;

    @Test
    public void 예약리스트_저장() throws Exception {
        //given
        ReservationList reservationList = new ReservationList();
        reservationList.setReservationIn(5);
        reservationList.setReservationOut(2);
        reservationListRepository.save(reservationList);
        //when
        ReservationList findReservationList = reservationListRepository.findById(reservationList.getId()).orElse(null);
        //then
        assertThat(findReservationList.getReservationIn()).isEqualTo(5);
        assertThat(findReservationList.getReservationOut()).isEqualTo(2);

     }

     @Test
     public void 예약리스트_방_연관관계() throws Exception {
         //given
         Room room = new Room("202호");
         room.setPrice(50000);
         room.setGrade("4성");
         roomRepository.save(room);

         ReservationList reservationList = new ReservationList();
         reservationList.setReservationIn(5);
         reservationList.setReservationOut(2);
         reservationList.addRoom(room);
         reservationListRepository.save(reservationList);
         //when
         ReservationList findReservationList = reservationListRepository.findById(reservationList.getId()).orElse(null);
         Room findRoom = roomRepository.findById(room.getId()).orElse(null);
         //then
         assertThat(findReservationList.getRoom().getName()).isEqualTo("202호");
         assertThat(findReservationList.getRoom().getPrice()).isEqualTo(50000);
         assertThat(findReservationList.getRoom().getGrade()).isEqualTo("4성");

         assertThat(findRoom.getReservationList().getReservationIn()).isEqualTo(5);
         assertThat(findRoom.getReservationList().getReservationOut()).isEqualTo(2);
      }

}