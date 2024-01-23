package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    private final MemberService memberService = new MemberServiceImpl();

    @Test
    void 가입_후_가입한_회원의_id로_회원을_조회하면_성공() {
        // given: ~ 가 주어졌을 때
        Member member = new Member(1L, "Apple", Grade.VIP);

        // when : ~ 하면
        memberService.join(member);
        Member memberFound = memberService.findMember(1L);

        // then : ~ 되어야 한다.
        Assertions.assertThat(member).isEqualTo(memberFound);
    }

    @Test
    void 존재하지_않는_회원_id로_회원을_조회하면_실패() {
        // given: ~ 가 주어졌을 때
        Member member = new Member(1L, "Apple", Grade.VIP);

        // when : ~ 하면
        memberService.join(member);
        Member memberFound = memberService.findMember(2L);  // <- 존재하지 않는 회원 id

        // then : ~ 되어야 한다.
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(memberFound).isNull();
    }
}
