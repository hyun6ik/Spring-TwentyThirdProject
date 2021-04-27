package springeighthproject.spring_jpa.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import springeighthproject.spring_jpa.domain.Address;

@Data
@AllArgsConstructor
public class MemberDto {

    private String name;
    private Address address;
}
