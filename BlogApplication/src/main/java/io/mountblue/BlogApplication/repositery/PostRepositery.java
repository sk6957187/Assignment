package io.mountblue.BlogApplication.repositery;

import io.mountblue.BlogApplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepositery extends JpaRepository<Post, Long> {

}
