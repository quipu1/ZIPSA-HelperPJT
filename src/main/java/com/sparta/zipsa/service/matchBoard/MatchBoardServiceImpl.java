package com.sparta.zipsa.service.matchBoard;

import com.sparta.zipsa.dto.MatchBoardRequestDto;
import com.sparta.zipsa.dto.MatchBoardResponseDto;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.MatchException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.MatchBoardRepository;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.board.BoardService;
import com.sparta.zipsa.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.zipsa.entity.UserRoleEnum.ADMIN;

@Service
@RequiredArgsConstructor
public class MatchBoardServiceImpl implements MatchBoardService {

    private final MatchBoardRepository matchBoardRepository;
    private final BoardService boardService;
    private final UserService userService;

    // MatchBoard 생성
    @Override
    @Transactional
    public MatchBoardResponseDto createMatchBoard(Long boardId, MatchBoardRequestDto requestDto, UserDetailsImpl userDetails) {
        Board board = boardService.getBoard(boardId);
        // 유저 화인
        User user = userService.getUser(userDetails.getUserId());

        // 헬퍼인지를 확인, 이미 작성했으면 못쓰게 한다
        if (user.getRole().equals(UserRoleEnum.CUSTOMER)) {
            throw new UserException.AuthorityException();
        } else if (matchBoardRepository.existsByUsernameAndBoard(user.getUsername(), board)) {
            throw new MatchException.AlreadyMatchBoardFoundExcption();
        } else {
            MatchBoard matchBoard = new MatchBoard(user, board, requestDto);
            matchBoardRepository.save(matchBoard);
            return new MatchBoardResponseDto(matchBoard, board);
        }

    }

    // MatchBoard 조회 (페이징 처리)
    @Override
    @Transactional
    public Page<MatchBoardResponseDto> getAllMatchBoard(int page, int size) {
        // 페이징 처리
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MatchBoard> matchBoards = matchBoardRepository.findAll(pageable);
        Page<MatchBoardResponseDto> matchBoardListDto = matchBoards.map(MatchBoardResponseDto::toMatchBoardResponseDto);
        return matchBoardListDto;

    }

    // MatchBoard 선택 조회
    @Override
    @Transactional
    public MatchBoardResponseDto getMatchBoard(Long boardId, Long matchBoardId) {
        Board board = boardService.getBoard(boardId);

        MatchBoard matchBoard = getMatchBoard(matchBoardId);

        return new MatchBoardResponseDto(matchBoard, board);
    }

    // MatchBoard 수정
    @Override
    @Transactional
    public MatchBoardResponseDto updateMatchBoard(Long boardId, Long matchBoardId, MatchBoardRequestDto requestDto, User user) {
        Board board = boardService.getBoard(boardId);

        MatchBoard matchBoard = getMatchBoard(matchBoardId);

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new UserException.AuthorityException();
        }
        matchBoard.update(requestDto);
        return new MatchBoardResponseDto(matchBoardRepository.save(matchBoard), board);
    }

    // MatchBoard 삭제
    @Override
    @Transactional
    public ResponseEntity deleteMatchBoard(Long boardId, Long matchBoardId, User user) {
        MatchBoard matchBoard = getMatchBoard(matchBoardId);

        if (matchBoard.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new UserException.AuthorityException();
        }
        matchBoardRepository.deleteById(matchBoardId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    private MatchBoard getMatchBoard(Long matchBoardId) {
        return matchBoardRepository.findById(matchBoardId).orElseThrow(
                MatchException.MatchNotFoundException::new
        );
    }

    // 수락 기능
    @Override
    @Transactional
    public ResponseEntity upStatus(Long boardId, Long matchboardId, Long userId) {
        Board board = boardService.getBoard(boardId);
        MatchBoard matchBoard = getMatchBoard(matchboardId);
        User user = userService.getUser(board.getUser().getId());


        if (userId != board.getUser().getId()) {
            throw new UserException.AuthorityException();
        }

        // status가 모집중이면 수락된 게시물로 변경 및 helpCnt 증가
        switch (matchBoard.status) {
            case "신청중" -> {
                matchBoard.upStatus();
                board.changeStatus();
                user.addHelpCnt();
                List<MatchBoard> matchBoards = matchBoardRepository.findByBoardAndStatus(board, "신청중");
                for (MatchBoard m : matchBoards) {
                    m.downStatus();
                }
            }

            // 이미 status가 수락된 게시물이면 익셉션 출력
            case "수락😊" -> throw new MatchException.AlreadyApproveMatchException();

            // 이미 status가 거절된 게시물이면 익셉션 출력
            case "거절😢" -> throw new MatchException.AlreadyRejectMatchException();
        }
        return new ResponseEntity<>("수락 완료!", HttpStatus.OK);
    }

    // 거절 기능
    @Override
    @Transactional
    public ResponseEntity downStatus(Long boardId, Long matchboardId, Long userId) {
        Board board = boardService.getBoard(boardId);
        MatchBoard matchBoard = getMatchBoard(matchboardId);
        User user = userService.getUser(matchBoard.getUser().getId());
        if (userId != board.getUser().getId()) {
            throw new UserException.AuthorityException();
        }

        // status가 모집중이면 거절된 게시물로 변경
        if (matchBoard.status.equals("신청중")) {
            matchBoard.downStatus();

            // status가 거절 완료인 상태면 익셉션 출력
        } else if (matchBoard.status.equals("거절😢")) {
            throw new MatchException.AlreadyRejectMatchException();
            // status가 수락된 게시물 상태이면 익셉션 출력
        } else if (matchBoard.status.equals("수락😊")) {
            throw new MatchException.AlreadyApproveMatchException();
        }
        return new ResponseEntity<>("거절 완료!", HttpStatus.OK);
    }


}
