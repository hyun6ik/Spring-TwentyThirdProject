package com.example.KangwonDo2.user;

import com.example.KangwonDo2.place.Place;
import com.example.KangwonDo2.place.PlaceRepository;
import com.example.KangwonDo2.reservation.Reservation;
import com.example.KangwonDo2.reservation.ReservationRepository;
import com.example.KangwonDo2.reservationList.ReservationList;
import com.example.KangwonDo2.reservationList.ReservationListRepository;
import com.example.KangwonDo2.review.Review;
import com.example.KangwonDo2.review.ReviewRepository;
import com.example.KangwonDo2.room.Room;
import com.example.KangwonDo2.room.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationListRepository reservationListRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    PlaceRepository placeRepository;


    @Test
    public void 유저_저장() throws Exception {
        //given
        User user = new User();
        user.setName("hyunsik");
        //when
        userRepository.save(user);
        User findMember = userRepository.findById(user.getId()).orElse(null);
        //then
        assertThat(findMember.getName()).isEqualTo("hyunsik");
     }

     @Test
     public void 유저_리뷰_연관관계() throws Exception {
         //given
         User user = new User();
         user.setName("hyunsik");

         Review review1= new Review("good");
         Review review2 = new Review("bad");
         reviewRepository.save(review1);
         reviewRepository.save(review2);
         user.addReview(review1);
         user.addReview(review2);

         userRepository.save(user);
         //when
         User findUser = userRepository.findById(user.getId()).orElse(null);
         Review findReview = reviewRepository.findById(review1.getId()).orElse(null);
         //then
         assertThat(findUser.getName()).isEqualTo("hyunsik");
         assertThat(findUser.getReviews().size()).isEqualTo(2);
         List<Review> reviews = findUser.getReviews();
         for (Review review : reviews) {
             System.out.println("review = " + review.getReviewText());
         }

         assertThat(findReview.getUser().getName()).isEqualTo("hyunsik");
      }

      @Test
      public void 유저_예약_연관관계() throws Exception {
          //given
          Reservation reservation = new Reservation();
          reservationRepository.save(reservation);

          User user = new User();
          user.addReservation(reservation);
          userRepository.save(user);
          //when
          User findUser = userRepository.findById(user.getId()).orElse(null);
          Reservation findReservation = reservationRepository.findById(reservation.getId()).orElse(null);
          //then
          assertThat(findUser.getReservations().get(0)).isEqualTo(reservation);
          assertThat(findReservation.getUser()).isEqualTo(user);

       }
       @Test
       public void 유저_예약_에약리스트_방_연관관계() throws Exception {
           //given

           Room room1 = Room.createRoom("101호", 70000, "3성");
           roomRepository.save(room1);

           Room room2 = Room.createRoom("202호",     50000, "2성");
           roomRepository.save(room2);

           ReservationList reservationList1 = new ReservationList(5);
           reservationList1.setReservationOut(2);
           reservationList1.addRoom(room1);
           reservationListRepository.save(reservationList1);

           ReservationList reservationList2 = new ReservationList(3);
           reservationList2.setReservationOut(0);
           reservationList2.addRoom(room2);
           reservationListRepository.save(reservationList2);

           Reservation reservation1 = new Reservation();
           reservation1.addReservationList(reservationList1);
           Reservation reservation2 = new Reservation();
           reservation2.addReservationList(reservationList2);

           User user = new User();
           user.setName("hyunsik");
           user.setRegisterDate(LocalDateTime.now());
           user.addReservation(reservation1);
           user.addReservation(reservation2);
           userRepository.save(user);

           //when
           User findUser = userRepository.findById(user.getId()).orElse(null);
           Room findRoom = roomRepository.findById(room1.getId()).orElse(null);
           //then
           assertThat(findUser.getReservations().get(0).getReservationList().getRoom().getName()).isEqualTo("101호");
           assertThat(findUser.getReservations().get(1).getReservationList().getRoom().getName()).isEqualTo("202호");
           assertThat(findRoom.getReservationList()).isEqualTo(reservationList1);
           assertThat(findRoom.getReservationList().getReservation()).isEqualTo(reservation1);
           assertThat(findRoom.getReservationList().getReservation().getUser()).isEqualTo(user);
           assertThat(findRoom.getReservationList().getReservation().getUser().getName()).isEqualTo("hyunsik");

        }

        @Test
        public void 유저_리뷰_장소_방_연관관계() throws Exception {
            //given
            Room room1 = Room.createRoom("101호", 70000, "3성");
            roomRepository.save(room1);
            Room room3 = Room.createRoom("303호", 100000, "4성");

            Room room2 = Room.createRoom("202호", 50000, "2성");
            room2.setPrice(50000);
            room2.setGrade("2성");
            roomRepository.save(room2);

            roomRepository.save(room3);

            Place place1 = new Place("강원도민박1호점");
            place1.setAddress("강원도 원주시");
            place1.setPhone("123456");
            place1.addRoom(room1);
            place1.addRoom(room3);
            placeRepository.save(place1);


            Place place2 = new Place("강원도민박2호점");
            place2.setAddress("강원도 강릉시");
            place2.setPhone("987654");
            place2.addRoom(room2);
            placeRepository.save(place2);

            Review review1 = new Review("good");
            review1.setReviewGrade(10);
            review1.addPlace(place1);
            reviewRepository.save(review1);

            Review review2 = new Review("bad");
            review2.setReviewGrade(1);
            review2.addPlace(place2);
            reviewRepository.save(review2);

            User user = new User();
            user.setName("hyunsik");
            user.addReview(review1);
            user.addReview(review2);
            userRepository.save(user);

            //when
            User findUser = userRepository.findById(user.getId()).orElse(null);
            Room findRoom1 = roomRepository.findById(room1.getId()).orElse(null);
            Room findRoom2 = roomRepository.findById(room2.getId()).orElse(null);
            //then
            assertThat(findUser.getReviews().size()).isEqualTo(2);
            assertThat(findUser.getReviews().get(0).getPlace().getRooms().get(0).getName()).isEqualTo("101호");
            assertThat(findUser.getReviews().get(0).getPlace().getRooms().get(1).getName()).isEqualTo("303호");
            assertThat(findUser.getReviews().get(0).getPlace().getName()).isEqualTo("강원도민박1호점");
            assertThat(findUser.getReviews().get(0).getReviewText()).isEqualTo("good");

            assertThat(findRoom1.getPlace().getReview().getUser().getName()).isEqualTo("hyunsik");
            assertThat(findRoom1.getPlace().getReview().getReviewText()).isEqualTo("good");
            assertThat(findRoom1.getPlace().getName()).isEqualTo("강원도민박1호점");

         }

         @Test
         public void 생성메소드_test() throws Exception {
             //given

             Room room1 = Room.createRoom("101호", 70000, "3성");
             Room room2 = Room.createRoom("202호", 50000, "2성");
             Room room3 = Room.createRoom("303호", 100000, "4성");
             roomRepository.save(room1);
             roomRepository.save(room2);
             roomRepository.save(room3);

             Place place1 = Place.createPlace("강원도민박1호점", "강원도 강릉시", "123456", room1, room3);
             Place place2 = Place.createPlace("강원도민박2호점", "강원도 원주시", "987654", room2);
             placeRepository.save(place1);
             placeRepository.save(place2);

             Review review1 = Review.createReview("good", 10, place1);
             Review review2 = Review.createReview("bad", 1, place2);
             reviewRepository.save(review1);
             reviewRepository.save(review2);

             ReservationList reservationList1 = ReservationList.createReservationList(5, 2, room1);
             ReservationList reservationList2 = ReservationList.createReservationList(4, 1, room2);
             ReservationList reservationList3 = ReservationList.createReservationList(7, 3, room3);
             reservationListRepository.save(reservationList1);
             reservationListRepository.save(reservationList2);
             reservationListRepository.save(reservationList3);

             Reservation reservation1 = Reservation.createReservation(reservationList1);
             Reservation reservation2 = Reservation.createReservation(reservationList2);
             Reservation reservation3 = Reservation.createReservation(reservationList3);
             reservationRepository.save(reservation1);
             reservationRepository.save(reservation2);
             reservationRepository.save(reservation3);

             User user1 = new User().builder()
                     .name("hyunsik")
                     .build();
             User updatedUser = User.createReview(user1, review1, review2);
             User user = User.createReservation(updatedUser, reservation1, reservation2, reservation3);
             userRepository.save(user);

             //when
             User findUser = userRepository.findById(user.getId()).orElse(null);
             Room findRoom1 = roomRepository.findById(room1.getId()).orElse(null);
             //then
             assertThat(user.getReviews().get(0).getPlace().getRooms().get(0).getName()).isEqualTo("101호");
             assertThat(user.getReviews().get(0).getPlace().getRooms().get(1).getName()).isEqualTo("303호");
             assertThat(user.getReviews().get(1).getPlace().getName()).isEqualTo("강원도민박2호점");
             assertThat(user.getReviews().get(0).getReviewText()).isEqualTo("good");
             assertThat(user.getReviews().get(1).getReviewGrade()).isEqualTo(1);
             assertThat(user.getName()).isEqualTo("hyunsik");

             assertThat(findRoom1.getPlace().getName()).isEqualTo("강원도민박1호점");
             assertThat(findRoom1.getPlace().getReview().getReviewText()).isEqualTo("good");
             assertThat(findRoom1.getPlace().getReview().getUser().getName()).isEqualTo("hyunsik");





          }

}