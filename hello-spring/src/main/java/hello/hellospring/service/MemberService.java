package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

@Transactional
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	
	public MemberService(MemberRepository memberRepository) {	// 외부에서 넣어주도록 설정 => 테스트도 같은 객체로 사용 가능(DI, 의존성 주입이라고 함!)
		this.memberRepository = memberRepository;
	}

	/**
	* 회원가입
	*/
	public Long join(Member member) {	
		
		long start = System.currentTimeMillis();
		
		try {
			validateDuplicateMember(member); //중복 회원 검증
			memberRepository.save(member);
			return member.getId();
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("join " + timeMs + "ms");
		}
	}
	
	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName())
				.ifPresent(m -> {
				// Optional 의 method이며 Optional 객체가 값을 가지고 있으면 실행 값이 없으면 넘어감 (Void  타입)
				// 이 경우에 값이 검출되면 예외 발생(IllegalStateException)
				// 즉, ifPresent() 메소드 = 값을 가지고 있는지 확인 후 예외처리 용도로 사용(더 많이 쓸 수도 있음)
						throw new IllegalStateException("이미 존재하는 회원입니다.");
				});
	}
	
	/**
	* 전체 회원 조회
	*/
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	/**
	* 한 명의 회원찾기
	*/
	public Optional<Member> findOne(Long memberId) {
		long start = System.currentTimeMillis();
		
		try {
			return memberRepository.findById(memberId);
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("join " + timeMs + "ms");
		}
		
		
	}
	
}
