package hello.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextExtendsFindTest {
    ApplicationContext ac1 = new AnnotationConfigApplicationContext(TestConfig1.class);
    ApplicationContext ac2 = new AnnotationConfigApplicationContext(TestConfig2.class);

    // 바로 위에서 살펴봤던, 중복 인터페이스 타입으로 조회에서와 같다.
    // 어떤 타입이든지 간에 조회된 빈이 두 개 이상이면 NoUniqueBeanDefinitionException 예외가 발생한다.
    @Test
    void 해당_타입의_빈이_둘_이상_조회되면_예외가_발생() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> {
            try {
                ac1.getBean(DiscountPolicy.class);
            } catch (NoUniqueBeanDefinitionException e) {
                System.err.println(e);
                throw e;
            } catch (Exception e) {
                System.err.println(e);
                fail();
            }
        });
    }

    // 따라서 해결 방법도 위에서와 동일하다
    @Test
    void 빈이_둘_이상_조회되는_경우_이름을_지정() {
        DiscountPolicy fixDiscountPolicy = ac1.getBean("fixDiscountPolicy", DiscountPolicy.class);
        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    // 중복 타입 빈 조회 테스트의 같은_타입의_빈이_여럿인_경우_모두_조회() 테스트 메소드와 동일하다
    @Test
    void 상위_타입으로_모두_조회() {
        // 주어진 인터페이스 타입의 빈을 모두 조회
        Map<String, DiscountPolicy> discountPolicyBeans = ac1.getBeansOfType(DiscountPolicy.class);
        for (String key : discountPolicyBeans.keySet()) {
            System.out.println("key = " + key + " value = " + discountPolicyBeans.get(key));
        }

        assertThat(discountPolicyBeans.size()).isEqualTo(2);
    }

    @Test
    void Object_타입으로_모두_조회() {
        // 주어진 인터페이스 타입의 빈을 모두 조회
        Map<String, Object> beans = ac1.getBeansOfType(Object.class);
        for (String key : beans.keySet()) {
            System.out.println("key = " + key + " value = " + beans.get(key));
        }
    }

    @Test
    void 구성_클래스의_팩토리_메소드_반환_타입이_하위_타입이어도_상위_타입으로_조회_가능() {
        DiscountPolicy fixDiscountPolicy = ac2.getBean("fixDiscountPolicy", DiscountPolicy.class);
        RateDiscountPolicy rateDiscountPolicy = ac2.getBean("rateDiscountPolicy", RateDiscountPolicy.class);

        assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Configuration
    static class TestConfig1 {
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
    }

    @Configuration
    static class TestConfig2 {
        @Bean
        public FixDiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }

        @Bean
        public RateDiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
    }
}
