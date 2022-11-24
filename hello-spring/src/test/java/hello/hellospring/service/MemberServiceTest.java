package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

class MemberServiceTest {
	
	MemberService memberService;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach	// 테스트 실행 전에
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();		// MemoryMemberRepository 객체 생성 후
		memberService = new MemberService(memberRepository);	// MemberService 객체를 생성하면서 그 안에 memberRepository 객체를 넣는다
																// 이렇게 하면 구현/테스트에서 같은 객체를 사용하여 테스트가 정확해진다!
	}
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
	public void 회원가입() throws Exception {
		//Given
		Member member = new Member();
		member.setName("hello");
		
		//When
		Long saveId = memberService.join(member);
		
		//Then
		Member findMember = memberRepository.findById(saveId).get();
		assertEquals(member.getName(), findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("spring");
		
		Member member2 = new Member();
		member2.setName("spring");
		
		//When
		memberService.join(member1);
		// assertThrows(예외명.클래스, () -> 실행할 코드 ); // 로직을 실행할 건데 예외가 발생해야 한다
		// assertThrows 는 값을 반환할 수 있음 => 메시지 확인 가능
		IllegalStateException e = assertThrows(IllegalStateException.class,	
		() -> memberService.join(member2));//예외가 발생해야 한다.
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
	
}
