package com.sparta.zipsa.entity;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MatchBoard extends TimeStamp{

    // 글 고유 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;

    @Column(nullable = false)
    public String content;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    public int help_cnt;

    @Column(nullable = false)
    public String status;

    // 한개의 게시글에 여러개의 신청을 할 수 있다..?
    @ManyToOne
    @JoinColumn(name = "board_Id")
    private Board board;

    @ColumnDefault("0")
    public void addhelpCount() {
        help_cnt += 1;
    }

//    @ColumnDefault("1")
//    @Column(nullable = false)
//    long status_list;
    @OneToMany(mappedBy = "Status" , cascade = CascadeType.ALL)
    private List<Status> statuses = new ArrayList<>();

//    public void addStatus() {
//        this.status_list += 1;
//    }
//
//    public void deleteStatus() {
//        this.status_list -= 1;
//    }

    public int getstatusList() {
        return this.statuses.size();
    }


    public void update(MatchBoardRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}


