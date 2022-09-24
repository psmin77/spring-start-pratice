package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("jpaTest");

        Long saveId = memberService.join(member);

        Member findMember = memberRepository.findById(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
        // assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 중복회원() throws Exception {
        Member member1 = new Member();
        member1.setName("jpaTest");

        Member member2 = new Member();
        member2.setName("jpaTest");

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2)); // 예외가 발생해야 함

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}