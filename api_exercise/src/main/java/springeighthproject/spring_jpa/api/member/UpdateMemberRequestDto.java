package springeighthproject.spring_jpa.api.member;

import lombok.Data;
import springeighthproject.spring_jpa.domain.Address;
import springeighthproject.spring_jpa.domain.Member;
import springeighthproject.spring_jpa.domain.Order;

import java.util.List;

@Data
public class UpdateMemberRequestDto {

    private String name;
    private Address address;
    private List<Order> orders;


    public Member toEntity(){
        return new Member().builder()
                .name(name)
                .address(address)
                .orders(orders)
                .build();
    }
}
