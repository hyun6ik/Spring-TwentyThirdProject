package springeighthproject.spring_jpa.api.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springeighthproject.spring_jpa.api.Result;
import springeighthproject.spring_jpa.api.member.dto.*;
import springeighthproject.spring_jpa.domain.Member;
import springeighthproject.spring_jpa.service.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getAddress()))
                .collect(Collectors.toList());
        return new Result(collect);

    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return CreateMemberResponse.of(id, "성공");
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequestDto dto){

        Long id = memberService.join(dto.toEntity());
        return CreateMemberResponse.of(id,"DTO로 성공");
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequestDto dto){
        memberService.update(id, dto.getName());
        Member findMember = memberService.findOne(id);
        return UpdateMemberResponse.of(findMember.getId(), findMember.getName(),"성공");

    }


}
