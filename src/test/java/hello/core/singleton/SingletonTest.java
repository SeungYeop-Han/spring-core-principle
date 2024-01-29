package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    void 원시적인_DI_컨테이너를_사용하면_조회할_때_마다_객체_생성(){

        AppConfig appConfig = new AppConfig();

        // MemberService 객체를 두 번 조회
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // 두 객체의 참조 값을 비교
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
}
