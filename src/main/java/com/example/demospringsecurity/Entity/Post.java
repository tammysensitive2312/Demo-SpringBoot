package com.example.demospringsecurity.Entity;

import com.example.demospringsecurity.ENUM.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String subject;
    private String description;
    private String userName;
    @Enumerated(EnumType.STRING)
    private PostStatus status;
}
