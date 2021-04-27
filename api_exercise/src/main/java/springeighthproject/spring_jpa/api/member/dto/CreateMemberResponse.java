package springeighthproject.spring_jpa.api.member.dto;

import lombok.Data;

@Data
public class CreateMemberResponse {

    private Long id;
    private String result;

    public CreateMemberResponse(Long id, String result) {
        this.id = id;
        this.result = result;
    }

    public static CreateMemberResponse of(Long id, String result){
        return new CreateMemberResponse(id, result);
    }
}
