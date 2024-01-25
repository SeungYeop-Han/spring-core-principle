package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        // MemberService memberService = new MemberServiceImpl();
        MemberService memberService = appConfig.memberService();
        Member newbie = new Member(1L, "Apple", Grade.VIP);

        // 회원 가입
        memberService.join(newbie);

        // 회원 가입이 정상적으로 되었는 지 확인
        Member memberFound = memberService.findMember(newbie.getId());
        System.out.println(newbie);
        System.out.println(memberFound);
    }
}
