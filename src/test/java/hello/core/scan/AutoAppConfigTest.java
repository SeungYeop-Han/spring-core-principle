package hello.core.scan;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void 컴포넌트_스캔_테스트() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberServiceImpl = ac.getBean(MemberServiceImpl.class);
        OrderServiceImpl orderServiceImpl = ac.getBean(OrderServiceImpl.class);
        System.out.println("memberServiceImpl.getMemberRepositoryForTest() = " + memberServiceImpl.getMemberRepositoryForTest());
        System.out.println("orderServiceImpl.getMemberRepositoryForTest() = " + orderServiceImpl.getMemberRepositoryForTest());

        assertThat(memberServiceImpl).isInstanceOf(MemberService.class);
    }
}
