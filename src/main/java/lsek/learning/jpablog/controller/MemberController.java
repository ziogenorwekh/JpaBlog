package lsek.learning.jpablog.controller;

import lsek.learning.jpablog.domain.Address;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.AccessError;
import lsek.learning.jpablog.exception.DuplicateUniqueEmail;
import lsek.learning.jpablog.exception.NotFoundMember;
import lsek.learning.jpablog.service.ArticleService;
import lsek.learning.jpablog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {

    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("members/memberList")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("members/memberInfo/{id}")
    public String memberInfo(@PathVariable("id") Long id, Model model) {
        try {
            Member member = memberService.findOne(id);
            model.addAttribute("member", member);
            return "members/memberInfo";
        } catch (NotFoundMember error) {
            return "redirect:/members/memberList";
        }
    }

    @GetMapping("members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("members/login")
    public String login(@ModelAttribute Member member,
                        HttpSession session, RedirectAttributes attributes) {
        try {
            Member login = memberService.login(member.getEmail(), member.getPassword());
            session.setAttribute("login", login.getId());
            return "redirect:/members/memberList";
        } catch (AccessError error) {
            attributes.addFlashAttribute("infoError", true);
            attributes.addFlashAttribute("error", member.getEmail());
            return "redirect:/members/login";
        }
    }

    @GetMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/members/memberList";
    }

    @GetMapping("members/create")
    public String joinForm() {

        return "members/createForm";
    }

    @PostMapping("members/create")
    public String join(@ModelAttribute Member member, @ModelAttribute Address address
            , RedirectAttributes attributes) {
        try {
            member.setAddress(address);
            memberService.save(member);
            return "redirect:/members/memberList";
        } catch (DuplicateUniqueEmail e) {
            attributes.addFlashAttribute("duplicated", true);
            attributes.addFlashAttribute("duplicatedEmail", member.getEmail());
            return "redirect:/members/create";
        }
    }

    @GetMapping("members/myInfo")
    public String myInfoForm(Model model,HttpSession session) {
        Long id = (Long) session.getAttribute("login");
        // Spring security X

        if (id == null) {
            return "redirect:/members/login";
        }
        Member one = memberService.findOne(id);
        System.out.println("one = " + one);
        model.addAttribute("member", one);
        return "members/myInfo";
    }


//    @PostConstruct
//    public void createMember() {
//        for (int i = 0; i < 30; i++) {
//            Member member = new Member("lsek" + i, "lsek@arc" + i + "d");
//            memberService.save(member);
//        }
//        Member member = new Member();
//        member.setName("lsek");
//        member.setPassword("1");
//        member.setEmail("ad@ad.com");
//        Address address = new Address();
//        address.setCity("서울");
//        address.setStreet("경리단길");
//        member.setAddress(address);
//        memberService.save(member);
//    }
}
