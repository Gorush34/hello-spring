package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

import org.springframework.stereotype.Repository;


public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;
	
	@Override
	public Member save(Member member) {
		member.setId(++sequence);				// 회원고유번호(시퀀스로) 저장
		store.put(member.getId(), member);		// 고유번호, 아이디, 이름이 담긴 객체 member를 store에 저장
		return member;							// 객체 member를 반환
	}
	
	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id));
		// Optional.ofNullable(store.get(id)) => null이 반환될 경우 Optional.ofNullable를 쓰면 클라이언트쪽에서 무엇을 할 수 있음
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream()
						.filter(member -> member.getName().equals(name))	// 멤버의 name이 파라미터로 넘긴 name하고 값이 같는지 확인
						.findAny();											// 맞는 것을 반환
	}
	
	@Override
	public List<Member> findAll() {
		return new ArrayList<>(store.values());	// store에 있는 value들(member)을 반환
	}
	
	public void clearStore() {
		store.clear();		// store를 비운다
	}
	
}
