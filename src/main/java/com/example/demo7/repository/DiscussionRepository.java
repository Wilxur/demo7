// repository/DiscussionRepository.java
package com.example.demo7.repository;

import com.example.demo7.model.Discussion;
import com.example.demo7.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Page<Discussion> findAllByOrderByCreateTimeDesc(Pageable pageable);
    List<Discussion> findAllByAuthorOrderByCreateTimeDesc(User author);
    long countByAuthor(User author);

    @Query("SELECT d FROM Discussion d WHERE d.title LIKE %:keyword% OR d.content LIKE %:keyword%")
    List<Discussion> searchByKeyword(@Param("keyword") String keyword);
}