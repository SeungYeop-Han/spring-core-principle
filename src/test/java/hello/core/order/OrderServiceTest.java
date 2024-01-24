package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    private final MemberService memberService = new MemberServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    @Test
    void VIP_고객에_대해_1000원_고정_금액_할인_성공() {
        // given
        Long memberId = 1L;
        Member member = new Member(memberId, "Hans", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(memberId, "Banana", 3000);

        // then
        Assertions.assertThat(order.getDiscountAmount()).isEqualTo(1000);
    }
}
