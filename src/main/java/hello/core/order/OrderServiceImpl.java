package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 회원 조회
        Member member = memberRepository.findById(memberId);

        // 할인량 계산
        // 회원 등급에 따라 할인에 차등을 주는 것은 할인 정책의 책임
        int discountAmount = discountPolicy.discount(member, itemPrice);

        // 주문 생성 및 반환
        return new Order(memberId, itemName, itemPrice, discountAmount);
    }
}
