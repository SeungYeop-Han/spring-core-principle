package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    private static ByteArrayOutputStream outputMessage;

    @BeforeEach
    void setUpStreams() {
        outputMessage = new ByteArrayOutputStream(); // OutputStream 생성
        System.setOut(new PrintStream(outputMessage)); // 생성한 OutputStream 으로 설정
    }

    @AfterEach
    void restoresStreams() {
        System.setOut(System.out); // 원상복귀
    }

    @Test
    void 빈_팩토리_메소드가_몇_번_호출되는_지_확인() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberServiceImpl
                = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderServiceImpl
                = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository
                = ac.getBean("memberRepository", MemberRepository.class);

        // 객체의 동일성 확인
        assertThat(memberServiceImpl.getMemberRepositoryForTest())
                .isSameAs(memberRepository);
        assertThat(orderServiceImpl.getMemberRepositoryForTest())
                .isSameAs(memberRepository);

        // 각 팩토리 메소드가 한 번씩만 호출됨을 확인
        assertThat(outputMessage.toString()).containsOnlyOnce("memberService 호출");
        assertThat(outputMessage.toString()).containsOnlyOnce("orderService 호출");
        assertThat(outputMessage.toString()).containsOnlyOnce("memberRepository 호출");
        assertThat(outputMessage.toString()).containsOnlyOnce("discountPolicy 호출");
    }
}
