package hello.core.singleton;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ConfigurationNonSingletonTest {

    static class AppConfig {

        @Bean
        public MemberService memberService() {
            System.out.println(">> AppConfig.memberService 호출");
            return new MemberServiceImpl(memberRepository());
        }

        @Bean
        public OrderService orderService() {
            System.out.println(">> AppConfig.orderService 호출");
            return new OrderServiceImpl(
                    memberRepository(),
                    discountPolicy()
            );
        }

        @Bean
        public MemberRepository memberRepository() {
            System.out.println(">> AppConfig.memberRepository 호출");
            return new MemoryMemberRepository();
        }

        @Bean
        public DiscountPolicy discountPolicy() {
            System.out.println(">> AppConfig.discountPolicy 호출");
            return new RateDiscountPolicy();
        }
    }

    @Test
    void 싱글톤_레지스트리를_적용하지_않은_빈_등록_테스트() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberServiceImpl
                = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderServiceImpl
                = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository
                = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository1 = memberServiceImpl.getMemberRepositoryForTest();
        MemberRepository memberRepository2 = orderServiceImpl.getMemberRepositoryForTest();

        System.out.println("팩토리 빈 = " + ac.getBeanDefinition("configurationNonSingletonTest.AppConfig"));
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
    }
}
