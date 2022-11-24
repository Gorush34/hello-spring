package hello.hellospring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import hello.hellospring.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
	// 인터페이스가 인터페이스 받을때는 extends
	
	@Override
	Optional<Member> findByName(String name);
	
}
