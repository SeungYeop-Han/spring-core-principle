package hello.core.beanfind;

import static org.assertj.core.api.Assertions.*;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextDuplicatedTypeBeanFindTest {

    // 중복된 타입의 빈이 여러 개가 되도록 기존 구성 클래스(AppConfig)를 수정하는 대신,
    // 테스트에서만 사용할 static 구성 클래스 하나를 정의한다.
    ApplicationContext ac = new AnnotationConfigApplicationContext(DuplicatedTypeBeanConfig.class);

    @Configuration
    static class DuplicatedTypeBeanConfig {

        // 중복된 인터페이스 타입
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }


        // 중복된 인스턴스 타입
        // 실제 상황에서는, 다른 파라미터로 초기화된 객체를 반환하는 경우일 수 있다.
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Test
    void 중복된_인터페이스_타입으로_조회_시_예외_발생() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> {
            try {
                ac.getBean(DiscountPolicy.class);
            } catch (Exception e) {
                System.err.println(e);
                throw e;
            }
        });
    }

    @Test
    void 중복된_인스턴스_타입으로_조회_시_예외_발생() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> {
            try {
                ac.getBean(MemberRepository.class);
            } catch (Exception e) {
                System.err.println(e);
                throw e;
            }
        });
    }

    @Test
    void 같은_타입의_빈이_여럿인_경우_이름으로_조회() {
        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository2", MemberRepository.class);
        assertThat(memberRepository1).isInstanceOf(MemoryMemberRepository.class);
        assertThat(memberRepository2).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    void 같은_타입의_빈이_여럿인_경우_모두_조회() {
        // 주어진 인터페이스 타입의 빈을 모두 조회
        Map<String, DiscountPolicy> discountPolicyBeans = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : discountPolicyBeans.keySet()) {
            System.out.println("key = " + key + " value = " + discountPolicyBeans.get(key));
        }

        // 주어진 구상 클래스 타입의 빈을 모두 조회
        Map<String, MemoryMemberRepository> memoryMemberRepositoryBeans = ac.getBeansOfType(MemoryMemberRepository.class);
        for (String key : memoryMemberRepositoryBeans.keySet()) {
            System.out.println("key = " + key + " value = " + memoryMemberRepositoryBeans.get(key));
        }

        assertThat(discountPolicyBeans.size()).isEqualTo(2);
        assertThat(memoryMemberRepositoryBeans.size()).isEqualTo(2);
    }
}
