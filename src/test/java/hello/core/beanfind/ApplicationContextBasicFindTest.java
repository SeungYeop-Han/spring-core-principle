package hello.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFindTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    void 빈_이름으로_조회() {
        // 이름으로만 조회하는 경우 Object 타입으로 받아야 한다.
        Object bean = ac.getBean("memberService");
        assertThat(bean).isInstanceOf(MemberServiceImpl.class);
    }
    
    @Test
    void 빈_타입으로_조회() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    void 빈_이름과_타입으로_조회() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    void 빈을_구체_타입으로_조회() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    void 존재하지_않는_빈_조회() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> {
            try {
                Object bean = ac.getBean("noBean");
            } catch (Exception e) {
                System.err.println(e);
                throw e;
            }
        });
    }
}
