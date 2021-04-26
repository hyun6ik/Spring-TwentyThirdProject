package com.example.KangwonDo2.reservation;

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
class ReservationTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationListRepository reservationListRepository;

    @Test
    public void 예약_예약리스트_연관관계() throws Exception {
        //given
        Reservation reservation = new Reservation();

        ReservationList reservationList1 = new ReservationList(5);
        reservationListRepository.save(reservationList1);

        reservation.addReservationList(reservationList1);
        reservationRepository.save(reservation);
        //when
        Reservation findReservation = reservationRepository.findById(reservation.getId()).orElse(null);
        ReservationList findReservationList = reservationListRepository.findById(reservationList1.getId()).orElse(null);
        //then
        assertThat(findReservation.getReservationList().getReservationIn()).isEqualTo(5);
        assertThat(findReservationList.getReservation()).isEqualTo(reservation);

     }

}