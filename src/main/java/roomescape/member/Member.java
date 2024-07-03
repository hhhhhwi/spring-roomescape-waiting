package roomescape.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.thymeleaf.util.StringUtils;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated
    private MemberRole role;

    public Member(Long id, String email, String password, String name, MemberRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Member(String email, String password, String name, MemberRole role) {
        this(null, email, password, name, role);
    }

    public Member() {
    }

    public boolean isMatchedPassword(String password) {
        return StringUtils.equals(this.password, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public MemberRole getRole() {
        return role;
    }
}
