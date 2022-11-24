package hello.hellospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

@Controller	// Spring Container가 MemberController 객체를 생성해서 담아놓는다 = Spring Bean이 관리된다
public class MemberController {
	
	private final MemberService memberService; 
	// new 로 생성시 여러 개의 객체를 생성하게 됨
	// 하나만 생성해서 쓰자!
	
	@Autowired	// 연관관계를 지정
	// 이 어노테이션을 쓰면 Spring Container에서 MemberService를 가져옴
	// 쓰게 되면 MemberController가 생성이 될 때 Spring Bean에 등록되어 있는 MemberService 객체를 가져다 넣어줌(이것이 의존성 주입, DI)
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	@PostMapping(value = "/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		
		memberService.join(member);
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		
		return "members/memberList";
	}
}
