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

public class OrderApp {

    public static void main(String[] args) {
        // 회원 가입 요청
        Long memberId = 1L;
        Member member = new Member(memberId, "Hans", Grade.VIP);
        MemberService memberService = new MemberServiceImpl();
        memberService.join(member);

        // 주문 생성 요청
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.createOrder(memberId, "Banana", 3000);

        // 반환된 주문 출력
        System.out.println("order: " + order);
        System.out.println("order.calculatePrice(): " + order.calculatePrice());
    }
}
