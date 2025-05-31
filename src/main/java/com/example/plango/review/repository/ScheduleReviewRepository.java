package com.example.plango.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.plango.review.model.ScheduleReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleReviewRepository extends JpaRepository<ScheduleReview, Long> {

}
