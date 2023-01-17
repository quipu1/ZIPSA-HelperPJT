package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.BoardRequest;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = true)
    private String modifiedAt;

    @Column(nullable = true)
    private String status;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private Long price;

    public Board(BoardRequest boardRequest,User user) {
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.title = boardRequest.getTitle();
        this.contents = boardRequest.getContents();
        this.price = boardRequest.getPrice();
    }
}
