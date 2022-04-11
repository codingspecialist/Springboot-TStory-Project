package site.metacoding.blogv3.web.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv3.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, toString
public class JoinReqDto {

    @Pattern(regexp = "[a-zA-Z1-9]{4,20}", message = "유저네임은 한글이 들어갈 수 없습니다.")
    @Size(min = 4, max = 20)
    @NotBlank
    private String username;

    @Size(min = 4, max = 20)
    @NotBlank
    private String password;

    @Size(min = 8, max = 60)
    @NotBlank // @NotNull, @NotEmpty 두개의 조합
    @Email
    private String email;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
