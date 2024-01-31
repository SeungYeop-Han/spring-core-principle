package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*

        public 역할 역할() {
            return new 구현(
                    의존하는역할1(),
                    의존하는역할2(),
                    의존하는역할3(),
                    ...
            )
		}

     */

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
