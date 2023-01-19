package com.sparta.zipsa.service.board;

import com.sparta.zipsa.dto.DeleteRequestDto;
import com.sparta.zipsa.dto.HelperBoardRequestDto;
import com.sparta.zipsa.dto.HelperBoardResponseDto;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.BoardException;
import com.sparta.zipsa.exception.HelperException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.repository.HelperBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HelperBoardServiceImpl implements HelperBoardService {

    private final HelperBoardRepository helperBoardRepository;

    @Override
    @Transactional
    public ResponseEntity createHelperBoard(HelperBoardRequestDto helperBoardRequestDto, User user) {
        HelperBoard helperBoard = new HelperBoard(helperBoardRequestDto, user);
        //이미 집사인 경우, 집사 신청글을 이미 쓴 경우
        if (user.getRole().equals(UserRoleEnum.HELPER)) {
            new HelperException.AlreadyHelperException();
        } else if (alreadyApplyHelper(user)) {
            new HelperException.AlreadyApplyHelperException();
        } else {
            helperBoardRepository.save(helperBoard);
        }
        return new ResponseEntity("집사 권한을 신청했습니다.", HttpStatus.OK);
    }

    public boolean alreadyApplyHelper(User user) {
        return helperBoardRepository.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public ResponseEntity deleteHelperBoard(Long helperBoardId, User user) {
        HelperBoard helperBoard = helperBoardRepository.findById(helperBoardId).orElseThrow(BoardException.BoardNotFoundException::new);
        if (user.getUsername().equals(helperBoard.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            helperBoardRepository.delete(helperBoard);
            return new ResponseEntity("집사 권한 신청글이 삭제 되었습니다.", HttpStatus.OK);
        }
        throw new UserException.AuthorityException();
    }

    public void deleteUsername(String username) {
        helperBoardRepository.deleteByUsername(username);
    }

    @Override
    public ResponseEntity updateHelperBoard(Long helperBoardId, HelperBoardRequestDto helperBoardRequestDto, User user) {
        HelperBoard helperBoard = helperBoardRepository.findById(helperBoardId).orElseThrow(BoardException.BoardNotFoundException::new);
        if (user.getUsername().equals(helperBoard.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            helperBoard.update(helperBoardRequestDto);
            return new ResponseEntity("집사 권한 신청글이 수정 되었습니다.", HttpStatus.OK);
        }
        throw new UserException.AuthorityException();
    }
}

