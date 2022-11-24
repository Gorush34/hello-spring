package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;

import java.util.Optional;


public interface MemberRepository {
	
	// 멤버저장
	Member save(Member member);
	
	// 멤버 아이디로 찾기
	Optional<Member> findById(Long id);
	// Optional 의 경우 반환값이 null 일 때 처리하기 위해 추가함(나중에 자세히 알아봄, Java 8에서 추가된 기능)
	
	// 멤버 이름으로 찾기
	Optional<Member> findByName(String name);
	
	// 멤버 전체찾기
	List<Member> findAll();
	
}
