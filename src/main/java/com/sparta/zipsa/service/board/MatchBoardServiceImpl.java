package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.exception.BoardException;
import com.sparta.zipsa.exception.MatchException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.BoardRepository;
import com.sparta.zipsa.repository.HelpStatusRepository;
import com.sparta.zipsa.repository.MatchBoardRepository;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import static com.sparta.zipsa.entity.UserRoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class MatchBoardServiceImpl implements MatchBoardService {

    private final MatchBoardRepository matchBoardRepository;
    private final BoardRepository boardRepository;
    private final HelpStatusRepository helpStatusRepository;
    private final UserRepository userRepository;

   // MatchBoard 생성
    @Override
    public MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardException.BoardNotFoundException::new
        );

        // 유저 화인
         User user = userRepository.findByUsername(userDetails.getUsername()).get();


        MatchBoard matchBoard = new MatchBoard(user,requestDto,board);
        matchBoardRepository.save(matchBoard);
        return new MatchBoardResponseDto(matchBoard,board);
    }
    // MatchBoard 조회 (페이징 처리)
    @Override
    public Page<MatchBoard> getAllMatchBoard(int page, int size, boolean isAsc, String role) {
        // 페이징 처리
        // 삼항연산자로 true ASC / false DESC 정렬 설정
        // sortBy로 정렬 기준이 되는 property 설정 - id
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<MatchBoard> matchBoards;

        if (role.equals("customer")) {
            matchBoards = matchBoardRepository.findAll(pageable);
        } else if (role.equals("helper")) {
            matchBoards = matchBoardRepository.findAll(pageable);
        } else if (role.equals("admin")) {
            matchBoards = matchBoardRepository.findAll(pageable);
        } else {
            throw new BoardException.BoardNotFoundException();
        }
       return matchBoards;
    }
    // MatchBoard 선택 조회
    @Override
    public MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
               BoardException.BoardNotFoundException::new
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                MatchException.MatchNotFoundException::new
        );

        return new MatchBoardResponseDto(matchBoard,board);
    }

    // MatchBoard 수정
    @Override
    public MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchBoardId, MatchBoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardException.BoardNotFoundException::new
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                MatchException.AlreadyApplyMatchException::new
        );

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new UserException.AuthorityException();
        }
        matchBoard.update(requestDto);
        return new MatchBoardResponseDto(matchBoardRepository.save(matchBoard),board);
    }

    // MatchBoard 삭제
    @Override
    public ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardException.BoardNotFoundException::new
        );

        MatchBoard matchBoard = matchBoardRepository.findById(matchBoardId).orElseThrow(
                MatchException.MatchNotFoundException::new
        );

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new UserException.AuthorityException();
        }
        matchBoardRepository.deleteById(matchBoardId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    // 수락 기능
    @Override
    public ResponseEntity upStatus(Long boardId, Long matchboardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardException.BoardNotFoundException::new
        );
        MatchBoard matchBoard =matchBoardRepository.findById(matchboardId).orElseThrow(
                MatchException.MatchNotFoundException::new
        );

        // status가 모집중이면 수락된 게시물로 변경 및 help_cnt 1 증가
        if (matchBoard.status.equals("모집중")) {
            matchBoard.upStatus();
            matchBoard.addhelpCount();

            // 이미 status가 수락된 게시물이면 익셉션 출력
        } else if (matchBoard.status.equals("수락된 게시물")) {
            throw new MatchException.AlreadyApplyMatchException();

            // 이미 status가 거질 된 게시물이면 익셉션 출력
        } else if(matchBoard.status.equals("거절된 게시물")) {
            throw new MatchException();
        }
        return new ResponseEntity<>("수락 완료!",HttpStatus.OK);
    }

    // 거절 기능
    @Override
    public ResponseEntity downStatus(Long boardId, Long matchboardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                BoardException.BoardNotFoundException::new
        );
        MatchBoard matchBoard = matchBoardRepository.findById(matchboardId).orElseThrow(
                MatchException.MatchNotFoundException::new
        );

        // status가 모집중이면 거절된 게시물로 변경
        if (matchBoard.status.equals("모집중")) {
            matchBoard.downStatus();

            // status가 거절 완료인 상태면 익셉션 출력
        } else if (matchBoard.status.equals("거절 완료")) {
           throw new MatchException();

           // status가 수락된 게시물 상태이면 익셉션 출력
        } else if (matchBoard.status.equals("수락된 게시물")) {
            throw new MatchException.AlreadyApplyMatchException();
        }
        return new ResponseEntity<>("거절 완료!",HttpStatus.OK);
    }


}
