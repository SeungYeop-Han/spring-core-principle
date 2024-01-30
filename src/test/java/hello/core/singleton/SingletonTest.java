package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    @Test
    void 싱글톤_패턴을_적용한_객체_사용() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }

    @Test
    void 스프링_컨테이너를_사용하면_조회할_때_마다_동일한_객체_반환() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // MemberService 객체를 두 번 조회
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 두 객체의 참조 값을 비교
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
