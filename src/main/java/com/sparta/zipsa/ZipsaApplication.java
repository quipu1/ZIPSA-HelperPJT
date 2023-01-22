package com.sparta.zipsa;

import com.sparta.zipsa.entity.Board;
import com.sparta.zipsa.entity.HelperBoard;
import com.sparta.zipsa.entity.MatchBoard;
import com.sparta.zipsa.entity.User;
import com.sparta.zipsa.repository.BoardRepository;
import com.sparta.zipsa.repository.HelperBoardRepository;
import com.sparta.zipsa.repository.MatchBoardRepository;
import com.sparta.zipsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.sparta.zipsa.entity.UserRoleEnum.CUSTOMER;
import static com.sparta.zipsa.entity.UserRoleEnum.HELPER;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class ZipsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipsaApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner test(UserRepository userRepository,
//								  PasswordEncoder passwordEncoder,
//								  BoardRepository boardRepository,
//								  MatchBoardRepository matchBoardRepository,
//								  HelperBoardRepository helperBoardRepository
//	) {
//
//		return (args) -> {
//
//			User user1 = new User("user1", passwordEncoder.encode("abcd1234"), "용인","b35a192-profileImage",CUSTOMER);
//			User user2 = new User("user2", passwordEncoder.encode("abcd1234"), "용인","920ba86-defaultImage",HELPER);
//			User user3 = new User("user3", passwordEncoder.encode("abcd1234"), "수원","6426825-Cat03.jpg",HELPER);
//			User user4 = new User("user4", passwordEncoder.encode("abcd1234"), "용인","92dd8ee-33.png",HELPER);
//			User user5 = new User("user5", passwordEncoder.encode("abcd1234"), "서울","920ba86-defaultImage",HELPER);
//			User user6 = new User("user6", passwordEncoder.encode("abcd1234"), "화성","920ba86-defaultImage",HELPER);
//			User user7 = new User("user7", passwordEncoder.encode("abcd1234"), "서울","920ba86-defaultImage",HELPER);
//			User user8 = new User("user8", passwordEncoder.encode("abcd1234"), "용인","920ba86-defaultImage",HELPER);
//			User user9 = new User("user9", passwordEncoder.encode("abcd1234"), "광주","920ba86-defaultImage",HELPER);
//			User user10 = new User("user10", passwordEncoder.encode("abcd1234"), "일산","920ba86-defaultImage",CUSTOMER);
//			userRepository.save(user1);
//			userRepository.save(user2);
//			userRepository.save(user3);
//			userRepository.save(user4);
//			userRepository.save(user5);
//			userRepository.save(user6);
//			userRepository.save(user7);
//			userRepository.save(user8);
//			userRepository.save(user9);
//			userRepository.save(user10);
//
//			Board board1 = new Board("user1","벌레 잡아주실 분!","급구합니다","용인",15000L,"모집중", user1);
//			Board board2 = new Board("user2","화장실 청소 해주실 분!!","고수만 구합니다","용인",35000L,"모집중", user2);
//			Board board3 = new Board("user3","대리 육아 해주실 분!","육아끝판왕 구해요","수원",60000L,"모집중", user3);
//			Board board4 = new Board("user4","맛집 음식 배달 해주실 분!","빠른 배송 가능하신 분!","용인",7000L,"모집중", user4);
//			Board board5 = new Board("user5","가구 설치해주실 분!","설치 잘하시는 분!!","서울",30000L,"모집중", user5);
//			Board board6 = new Board("user6","편의점 알바 대타해주실 분!","급구합니다","화성",100000L,"모집중", user6);
//			Board board7 = new Board("user7","집안일 도와 주실 분!","꼼꼼히 해주실분!","서울",45000L,"모집중", user7);
//			Board board8 = new Board("user8","운전 대신해주실 분!","장농면허 ㄴㄴ","용인",65000L,"모집중", user8);
//			Board board9 = new Board("user9","바퀴벌레 퇴치자 급구!","급구급구급구!","광주",20000L,"모집중", user9);
//			Board board10 = new Board("user10","화장실청소 급구!","깨끗히 해주실 분!","일산",35000L,"모집중", user10);
//			boardRepository.save(board1);
//			boardRepository.save(board2);
//			boardRepository.save(board3);
//			boardRepository.save(board4);
//			boardRepository.save(board5);
//			boardRepository.save(board6);
//			boardRepository.save(board7);
//			boardRepository.save(board8);
//			boardRepository.save(board9);
//			boardRepository.save(board10);
//
//			MatchBoard matchBoard1 = new MatchBoard("저 벌레 잘 잡습니다","user2");
//			MatchBoard matchBoard2 = new MatchBoard("벌레 잡기 고수입니다","user3");
//			MatchBoard matchBoard3 = new MatchBoard("벌레퇴치 1초컷","user4");
//			MatchBoard matchBoard4 = new MatchBoard("청소의왕입니다","user5");
//			MatchBoard matchBoard5 = new MatchBoard("화장실청소 고수임!","user6");
//			MatchBoard matchBoard6 = new MatchBoard("애기돌보기 마스터입니다","user7");
//			MatchBoard matchBoard7 = new MatchBoard("어디든 10초안에 배달가능","user8");
//			MatchBoard matchBoard8 = new MatchBoard("조립마스터입니다","user9");
//			MatchBoard matchBoard9 = new MatchBoard("총알배송가능","user10");
//			MatchBoard matchBoard10 = new MatchBoard("1초만에 조립가능","user1");
//			matchBoardRepository.save(matchBoard1);
//			matchBoardRepository.save(matchBoard2);
//			matchBoardRepository.save(matchBoard3);
//			matchBoardRepository.save(matchBoard4);
//			matchBoardRepository.save(matchBoard5);
//			matchBoardRepository.save(matchBoard6);
//			matchBoardRepository.save(matchBoard7);
//			matchBoardRepository.save(matchBoard8);
//			matchBoardRepository.save(matchBoard9);
//			matchBoardRepository.save(matchBoard10);
//
//
//			HelperBoard helperBoard1 = new HelperBoard("user1","용인","b35a192-profileImage",0,"헬퍼 권한 신청합니다! 벌레 잘잡음!");
//			HelperBoard helperBoard2 = new HelperBoard("user2","용인","920ba86-defaultImage",0,"헬퍼되고 싶어요 청소 잘함!");
//			HelperBoard helperBoard3 = new HelperBoard("user3","수원","6426825-Cat03.jpg",0,"준비된 헬퍼입니다 신청! 조립 잘함!");
//			HelperBoard helperBoard4 = new HelperBoard("user4","용인","92dd8ee-33.png",0,"헬퍼 신청해요! 빠른 배송 가능!");
//			HelperBoard helperBoard5 = new HelperBoard("user5","서울","920ba86-defaultImage",0,"헬퍼 권한 신청! 운전 잘함!");
//			HelperBoard helperBoard6 = new HelperBoard("user6","화성","920ba86-defaultImage",0,"헬퍼 될래요!! 각종 대타 가능!");
//			HelperBoard helperBoard7 = new HelperBoard("user7","서울","920ba86-defaultImage",0,"헬퍼! 잘할 수 있습니다! 육아 고수입니다");
//			HelperBoard helperBoard8 = new HelperBoard("user8","용인","920ba86-defaultImage",0,"헬퍼 시켜주세요 ㅠㅠ 조립 잘해요");
//			HelperBoard helperBoard9 = new HelperBoard("user9","광주","920ba86-defaultImage",0,"헬퍼 권한 원합니다!!!! 벌레퇴치전문가임!");
//			HelperBoard helperBoard10 = new HelperBoard("user10","일산","920ba86-defaultImage",0,"헬퍼 please!!! 뭐든지 잘함!");
//
//			helperBoardRepository.save(helperBoard1);
//			helperBoardRepository.save(helperBoard2);
//			helperBoardRepository.save(helperBoard3);
//			helperBoardRepository.save(helperBoard4);
//			helperBoardRepository.save(helperBoard5);
//			helperBoardRepository.save(helperBoard6);
//			helperBoardRepository.save(helperBoard7);
//			helperBoardRepository.save(helperBoard8);
//			helperBoardRepository.save(helperBoard9);
//			helperBoardRepository.save(helperBoard10);
//
//
//
//		};
//	}
}



