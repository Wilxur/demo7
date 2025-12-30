// repository/ReplyRepository.java
package com.example.demo7.repository;

import com.example.demo7.model.Discussion;
import com.example.demo7.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByDiscussionOrderByCreateTimeAsc(Discussion discussion);
    long countByDiscussion(Discussion discussion);
    List<Reply> findByAuthorIdOrderByCreateTimeDesc(Long authorId);
}