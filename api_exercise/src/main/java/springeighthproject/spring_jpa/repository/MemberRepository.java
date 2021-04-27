package springeighthproject.spring_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springeighthproject.spring_jpa.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findAll();
    List<Member> findByName(String name);
}
