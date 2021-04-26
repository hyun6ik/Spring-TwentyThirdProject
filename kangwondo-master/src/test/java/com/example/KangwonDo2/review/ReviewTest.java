package com.example.KangwonDo2.review;

import com.example.KangwonDo2.place.Place;
import com.example.KangwonDo2.place.PlaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void 리뷰_저장() throws Exception {
        //given
        Review review = new Review();
        review.setReviewText("good");
        review.setReviewGrade(10);

        reviewRepository.save(review);
        //when
        Review findReview = reviewRepository.findById(review.getId()).orElse(null);
        //then
        assertThat(findReview.getReviewText()).isEqualTo("good");
        assertThat(findReview.getReviewGrade()).isEqualTo(10);

     }

     @Test
     public void 리뷰_장소_연관관계() throws Exception {
         //given
         Place place = new Place("강원도민박1호점");
         place.setAddress("강원도 강릉시");
         placeRepository.save(place);

         Review review = new Review();
         review.setReviewText("good");
         review.addPlace(place);
         reviewRepository.save(review);

         //when
         Review findReview = reviewRepository.findById(review.getId()).orElse(null);
         Place findPlace = placeRepository.findById(place.getId()).orElse(null);
         //then
         assertThat(findReview.getReviewText()).isEqualTo("good");
         assertThat(findReview.getPlace().getName()).isEqualTo("강원도민박1호점");
         assertThat(findReview.getPlace().getAddress()).isEqualTo("강원도 강릉시");

         assertThat(findPlace.getName()).isEqualTo("강원도민박1호점");
         assertThat(findPlace.getReview().getReviewText()).isEqualTo("good");
      }

}