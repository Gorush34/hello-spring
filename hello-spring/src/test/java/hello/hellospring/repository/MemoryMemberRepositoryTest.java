package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {	// 테스트케이스는 굳이 public 할 필요 없음(다른데서 가져다 쓸 것이 아니니까)

	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	// TDD(테스트 주도개발) : 테스트를 먼저 만들고, 구현을 만들어서 테스트하는 것
	@AfterEach						// 각 테스트가 끝난 후 실행하게 하는 어노테이션
	public void afterEach() {
		repository.clearStore();	// store를 비운다
	}								// 즉, 하나의 테스트가 끝날 때 마다 데이터를 비워줌 => 순서에 의존적이지 않게 설계!(매우 중요)
	
	@Test	
	public void save() {
	
		//given
		Member member = new Member();
		member.setName("spring");
		
		//when
		repository.save(member);
		
		//then
		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);	// 결과가 가져온 member랑 똑같은지 비교
		// Assertions.assertThat로도 가능
		// 프로그램에서 assert는 변수가 원하는 값을 가지고 있는지 확인하는 코드를 의미합니다
		// 실무에서는 build툴이랑 엮어서 테스트케이스 통과못하면 저장안되게 막아버림
	}
	
	@Test
	public void findByName() {
		
		//given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		//when
		Member result = repository.findByName("spring1").get();	// 이름이 spring1인 것의 객체 member의 정보를  가져온다
		
		//then
		assertThat(result).isEqualTo(member1);
	}
	
	@Test
	public void findAll() {
		
		//given
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		//when
		List<Member> result = repository.findAll();
		
		//then
		assertThat(result.size()).isEqualTo(2);
	}
	
	
	
	
}
