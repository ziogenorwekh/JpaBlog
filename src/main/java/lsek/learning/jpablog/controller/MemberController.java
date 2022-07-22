package lsek.learning.jpablog.controller;

import lombok.RequiredArgsConstructor;
import lsek.learning.jpablog.domain.Address;
import lsek.learning.jpablog.domain.Member;
import lsek.learning.jpablog.exception.AccessError;
import lsek.learning.jpablog.exception.DuplicateUniqueEmail;
import lsek.learning.jpablog.exception.NotFoundMember;
import lsek.learning.jpablog.service.ArticleService;
import lsek.learning.jpablog.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    final private MemberService memberService;


//    @GetMapping("members/memberList")
//    public String list(Model model) {
//        List<Member> members = memberService.findAll();
//        model.addAttribute("members", members);
//        return "members/memberList";
//    }

    @GetMapping("/members/memberList/ajax")
    @ResponseBody
    public List<Member> members(@PageableDefault(size = 4,sort = "id") Pageable pageable) {
        return memberService.findMembers(pageable);
//        return
    }

//    @GetMapping("/members/memberList")
//    public void memberList() {
////        return "members/memberList";
//    }


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

//    @PostMapping("members/login")
//    public String login(@ModelAttribute Member member,
//                        HttpSession session, RedirectAttributes attributes) {
//        try {
//            System.out.println("member = " + member);
//            String encPwd = bCryptPasswordEncoder.encode(member.getPassword());
//            System.out.println("encPwd = " + encPwd);
//            Member login = memberService.login(member.getEmail(), encPwd);
//            session.setAttribute("login", login.getId());
//            return "redirect:/members/memberList";
//        } catch (AccessError error) {
//            attributes.addFlashAttribute("infoError", true);
//            attributes.addFlashAttribute("error", member.getEmail());
//            return "redirect:/members/login";
//        }
//    }

    @GetMapping("/**")
    public void access() {
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
            member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
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
        Member one = memberService.findOne(id);
        System.out.println("one = " + one);
        model.addAttribute("member", one);
        return "members/myInfo";
    }

}
