package springeighthproject.spring_jpa.api.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springeighthproject.spring_jpa.domain.Member;
import springeighthproject.spring_jpa.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

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
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,@RequestBody @Valid UpdateMemberRequestDto dto){
        memberService.update(id, dto.getName());
        Member findMember = memberService.findOne(id);
        return UpdateMemberResponse.of(findMember.getId(), findMember.getName(),"성공");

    }
}
