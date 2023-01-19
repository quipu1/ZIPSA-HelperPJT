package com.sparta.zipsa.service.user;

import com.sparta.zipsa.dto.*;
import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.entity.UserRoleEnum;
import com.sparta.zipsa.exception.AdminException;
import com.sparta.zipsa.exception.UserException;
import com.sparta.zipsa.jwt.JwtUtil;
import com.sparta.zipsa.repository.BoardRepository;
import com.sparta.zipsa.repository.UserRepository;
import com.sparta.zipsa.security.UserDetailsImpl;
import com.sparta.zipsa.service.board.HelperBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final HelperBoardService helperBoardService;

    private final FileService fileServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    @Transactional
    public ResponseEntity signup(SignupRequestDto signupRequestDto, MultipartFile multipartFile) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String address = signupRequestDto.getAddress();
        String uuid = UUID.randomUUID().toString();
        String uniqueInfo = uuid.substring(0, 7);
        String fileName = uniqueInfo + "-" + multipartFile.getOriginalFilename();

        Optional<User> foundUser = userRepository.findByUsername(username); //.isPresent같은 메소드 사용가능해서 optional
        if (foundUser.isPresent()) {
            throw new UserException.UsernameDuplicateException();
        }
        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new AdminException.AdminPasswordNotMatchException();
            }
            role = UserRoleEnum.ADMIN;
        }
        fileServiceImpl.upload(multipartFile,fileName); //업로드할 사진, uuid적용한 이름
        String userImage = fileName;
        User user = new User(username, password, address, userImage, role);
        userRepository.save(user);
        return new ResponseEntity("회원 가입 완료", HttpStatus.OK);

    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(UserException.UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException.PasswordNotMatchException();
        }
        return new LoginResponseDto(user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    @Transactional
    public ResponseEntity delete(DeleteRequestDto deleteRequestDto, User user) {

        if (user.getRole().equals(UserRoleEnum.ADMIN)
                || user.getUsername().equals(deleteRequestDto.getUsername())
                && passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
            userRepository.deleteByUsername(deleteRequestDto.getUsername());
            helperBoardService.deleteUsername(deleteRequestDto.getUsername()); // 이렇게 하는게 맞나 싶음?? 헬퍼보드레포지토리 안들어가려고 거기 서비스에 일단 해뒀는데..
            return new ResponseEntity("회원 탈퇴 처리 되었습니다", HttpStatus.OK);
        }
        throw new UserException.UserNotFoundException();
    }

    @Override
    @Transactional
    public ProfileResponseDto getProfile(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserException.UserNotFoundException::new);
            return new ProfileResponseDto(user);
    }

    @Override
    @Transactional
    public ResponseEntity updateProfile(Long userId, ProfileRequestDto profileRequestDto, User user) {
        if(user.getId()== userId || user.getRole().equals(UserRoleEnum.ADMIN)){
            user.update(profileRequestDto);
            return new ResponseEntity("프로필 수정이 완료됐습니다.",HttpStatus.OK);
        }
        throw new UserException.AuthorityException();


    }

    @Override
    @Transactional
    public Page<Board> getPageBoardByUser(Long userId, int page, int size, boolean isAsc, User user) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "Id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boards;

        if(user.getId() == userId){
            boards = boardRepository.findAllById(userId, pageable);
            //Page<Board> findAllById(Long userId, Pageable pagable); 이걸 boardRepository에 추가하면 끝인가..?
            //근데 유저서비스에 boardRepository까지 의존성주입하는건 이상한데.. 보드서비스에 이걸 넣고 그걸 불러와야하나?
            return boards;
        }else{
            throw  new UserException.AuthorityException();
        }
    }

}

