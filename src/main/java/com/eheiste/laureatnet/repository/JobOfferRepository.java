package com.eheiste.laureatnet.repository;

import com.eheiste.laureatnet.model.JobOffer;
import com.eheiste.laureatnet.model.Post;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    @Query("SELECT j " +
 	       "FROM JobOffer j " +
 	       "LEFT JOIN Subscription s ON j.poster.id = s.subscribedTo.id AND s.subscriber.id = :userId " +
 	       "ORDER BY " +
 	       "CASE WHEN s.subscriber.id IS NOT NULL THEN 1 ELSE 0 END DESC, " +
 	       "FUNCTION('DATE', j.createdAt) DESC, " +
 	       "CASE WHEN j.poster.id = :userId THEN 1 ELSE 0 END ASC")
    Page<JobOffer> findAllSortedByRecommendationGrade(@Param("userId") Long userId, Pageable pageable);
}