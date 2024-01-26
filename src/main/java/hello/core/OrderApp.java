package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
        // AppConfig를 직접 사용하는 것이 아니라 스프링 컨테이너를 사용할 것임
        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();
        // OrderService orderService = appConfig.orderService();

        // 스프링은 ApplicationContext로 부터 시작한다.
        // ApplicationContext는 객체를 빈으로 등록하여 관리하는 요소로 스프링 컨테이너라고 불린다.
        // AnnotationConfigApplicationContext는 개발자가 작성한 소스 코드에 명시된 어노테이션을 기준으로,
        // 자바 리플렉션 기술을 이용하여 객체를 컨테이너에 빈으로 등록한다.
        // 더 정확하게는, @Configuration으로 명시된 구성 정보를 가지고 있는 클래스를 전달받아,
        // 구성 클래스에 @Bean으로 명시된 팩토리 메소드에 대해 메소드명을 키로, 반환 객체를 값으로 하여 스프링 컨테이너에 빈으로 등록한다.
        // 빈으로 등록한다는 것은 스프링 컨테이너가 해당 클래스 객체를 생성하고 관리한다는 의미이다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // ApplicationContext 객체의 getBean 메소드에 빈의 키(팩토리 메소드 이름)와 빈 클래스 타입에 대한 리플렉션 정보를 전달하여,
        // 스프링 컨테이너에 등록된 빈 객체를 가져올 수 있다.
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        // 회원 가입 요청
        Long memberId = 1L;
        Member member = new Member(memberId, "Hans", Grade.VIP);
        memberService.join(member);

        // 주문 생성 요청
        Order order = orderService.createOrder(memberId, "Banana", 3000);

        // 반환된 주문 출력
        System.out.println("order: " + order);
        System.out.println("order.calculatePrice(): " + order.calculatePrice());
    }
}
