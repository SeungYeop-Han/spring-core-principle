package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    // private final MemberService memberService = new MemberServiceImpl();
    MemberService memberService;
    // private final OrderService orderService = new OrderServiceImpl();
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    // 기존 정가 할인 정책에 대한 성공 테스트는 실패하게 된다.
//    @Test
//    void VIP_고객에_대해_1000원_고정_금액_할인_성공() {
//        // given
//        Long memberId = 1L;
//        Member member = new Member(memberId, "Hans", Grade.VIP);
//        memberService.join(member);
//
//        // when
//        Order order = orderService.createOrder(memberId, "Banana", 3000);
//
//        // then
//        Assertions.assertThat(order.getDiscountAmount()).isEqualTo(1000);
//    }

    @Test
    void VIP_고객에_대해_정률_할인_성공() {
        // given
        Long memberId = 1L;
        Member member = new Member(memberId, "Hans", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(memberId, "Banana", 3000);

        // then
        Assertions.assertThat(order.getDiscountAmount()).isEqualTo(300);
    }
}
