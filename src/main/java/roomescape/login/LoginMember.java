package roomescape.login;

import roomescape.member.MemberRole;

public class LoginMember {

    public static String COOKIE_NAME_FOR_LOGIN = "token";

    private Long id;

    private String name;

    private MemberRole role;

    public LoginMember(Long id, String name, MemberRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public boolean hasSameRole(MemberRole role) {
        return this.role.equals(role);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberRole getRole() {
        return role;
    }
}
