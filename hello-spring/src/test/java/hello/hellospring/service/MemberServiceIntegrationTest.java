package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

@SpringBootTest
@Transactional // 이 어노테이션을 사용하면 트랜잭션을 실행한 뒤 테스트가 끝나면 롤백을 실행한다(테스트 내용 반영하지 않음)
class MemberServiceIntegrationTest {
	
	@Autowired	// 테스트시 기존에 있는 것을 써도 되기 때문에 이 어노테이션 사용(필드 기반의 것을 사용해서 편리함)
	MemberService memberService;				
	
	@Autowired
	MemberRepository memberRepository;	// MemoryMemberRepository 가 아니라 MemberRepository로 하면 configuration한 곳에서 올라올 것임
	
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
