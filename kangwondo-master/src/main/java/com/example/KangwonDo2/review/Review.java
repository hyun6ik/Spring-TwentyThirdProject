package com.example.KangwonDo2.review;

import com.example.KangwonDo2.place.Place;
import com.example.KangwonDo2.user.User;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String reviewText;
    private int reviewGrade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public Review(String reviewText) {
        this.reviewText = reviewText;
    }

    @Builder
    public Review(String reviewText, int reviewGrade) {
        this.reviewText = reviewText;
        this.reviewGrade = reviewGrade;
    }

    /**
     * 연관 관계 편의 메소드
     */

    // Review and Place
    public void addPlace(Place place){
        this.place = place;
        place.setReview(this);
    }


    /**
     * 생성 메서드
     */
    public static Review createReview(String reviewText, int reviewGrade, Place place){
        Review review = new Review().builder()
                .reviewText(reviewText)
                .reviewGrade(reviewGrade)
                .build();
        review.addPlace(place);
        return review;
    }

}
