package com.example.demospringsecurity.Repository;

import com.example.demospringsecurity.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
