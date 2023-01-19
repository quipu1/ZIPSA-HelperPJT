package com.sparta.zipsa.service.helperBoard;

import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.BoardException;
import com.sparta.zipsa.exception.HelperException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.HelperBoardRepository;
import com.sparta.zipsa.service.helperBoard.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HelperBoardServiceImpl implements HelperBoardService {

    private final HelperBoardRepository helperBoardRepository;

    // 집사 신청글 작성
    @Override
    @Transactional
    public ResponseEntity createHelperBoard(HelperBoardRequestDto helperBoardRequestDto, User user) {
        HelperBoard helperBoard = new HelperBoard(helperBoardRequestDto, user);
        if (user.getRole().equals(UserRoleEnum.HELPER)) {   //이미 집사 권한을 가지고 있는 경우 예외 처리
            new HelperException.AlreadyHelperException();
        } else if (helperBoardRepository.findByUsername(user.getUsername()).isPresent()) {
            new HelperException.AlreadyApplyHelperException(); // 집사 신청글은 유저당 1번만 쓸 수 있기에 신청글 존재하면 예외처리
        } else {
            helperBoardRepository.save(helperBoard);
        }
        return new ResponseEntity("집사 권한을 신청했습니다.", HttpStatus.OK);
    }
    //집사 신청글 삭제
    @Override
    public ResponseEntity deleteHelperBoard(Long helperBoardId, User user) {
        HelperBoard helperBoard = helperBoardRepository.findById(helperBoardId).orElseThrow(BoardException.BoardNotFoundException::new);
        //권한 있는지 확인
        if (isAuthorized(user, helperBoard)) {
            helperBoardRepository.delete(helperBoard);
            return new ResponseEntity("집사 권한 신청글이 삭제 되었습니다.", HttpStatus.OK);
        }
        throw new UserException.AuthorityException();
    }
    //집사 신청글 업데이트
    @Override
    public ResponseEntity updateHelperBoard(Long helperBoardId, HelperBoardRequestDto helperBoardRequestDto, User user) {
        HelperBoard helperBoard = helperBoardRepository.findById(helperBoardId).orElseThrow(BoardException.BoardNotFoundException::new);
        //권한 있는지 확인
        if (isAuthorized(user,helperBoard)) {
            helperBoard.update(helperBoardRequestDto);
            return new ResponseEntity("집사 권한 신청글이 수정 되었습니다.", HttpStatus.OK);
        }
        throw new UserException.AuthorityException();
    }
    //현재 로그인한 유저의 유저네임이 일치하거나 ADMIN 권한 가지고 있는지 확인
    public boolean isAuthorized(User user, HelperBoard helperBoard) {
        return user.getUsername().equals(helperBoard.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }
    //UserServiceImpl에서 회원 탈퇴 시 helperBoard도 같이 삭제하기 위해 만든 메소드
    public void deleteByUsername(String username) {
        helperBoardRepository.deleteByUsername(username);
    }
}