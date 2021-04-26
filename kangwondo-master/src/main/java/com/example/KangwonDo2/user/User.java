package com.example.KangwonDo2.user;

import com.example.KangwonDo2.reservation.Reservation;
import com.example.KangwonDo2.reservationList.ReservationList;
import com.example.KangwonDo2.review.Review;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String homePhone;
    private String mobilePhone;
    private LocalDateTime birthDate;
    private UserGrade userGrade;
    private int point;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private int emailAgree;
    private int mobileAgree;
    private int enabled;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public User(
            String name, String email, String password, String homePhone,
            String mobilePhone, LocalDateTime birthDate, UserGrade userGrade,
            int point, LocalDateTime registerDate, LocalDateTime modifyDate,
            int emailAgree, int mobileAgree, int enabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.birthDate = birthDate;
        this.userGrade = userGrade;
        this.point = point;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
        this.emailAgree = emailAgree;
        this.mobileAgree = mobileAgree;
        this.enabled = enabled;
    }

    /**
     * 연관관계 편의 메소드
     */
    // User and Review
    public void addReview(Review review){
        if(this.reviews != null){
            this.reviews.remove(this);
        }
        this.getReviews().add(review);
        review.setUser(this);
    }
    // User and Reservation
    public void addReservation(Reservation reservation) {
        if (this.reservations != null) {
            this.reviews.remove(this);
        }
        this.getReservations().add(reservation);
        reservation.setUser(this);
    }

    /**
     * 생성 메소드
     */
    public static User createReservation(User user, Reservation... reservations)
    {
        for (Reservation reservation : reservations) {
            user.addReservation(reservation);
        }
        return user;
    }

    public static User createReview(User user, Review... reviews)
    {
        for (Review review : reviews) {
            user.addReview(review);
        }
        return user;
    }


}
