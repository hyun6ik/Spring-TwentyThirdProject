package springeighthproject.spring_jpa.api.member.dto;

import lombok.Data;

@Data
public class UpdateMemberResponse {

    private Long id;
    private String name;
    private String result;

    public UpdateMemberResponse(Long id, String name, String result) {
        this.id = id;
        this.name = name;
        this.result = result;
    }

    public static UpdateMemberResponse of(Long id, String name, String result){
        return new UpdateMemberResponse(id, name, result);
    }
}
