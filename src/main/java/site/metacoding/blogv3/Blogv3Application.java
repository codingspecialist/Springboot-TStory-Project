package site.metacoding.blogv3;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.category.CategoryRepository;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.domain.user.UserRepository;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class Blogv3Application {

	private final BCryptPasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner init(UserRepository userRepository, CategoryRepository categoryRepository) {

		return (args) -> {
			User principal = User.builder()
					.username("ssar")
					.password(passwordEncoder.encode("1234"))
					.email("ssar@nate.com")
					.build();

			userRepository.save(principal);

			Category category = Category.builder()
					.title("스프링특강")
					.user(principal)
					.build();
			categoryRepository.save(category);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Blogv3Application.class, args);

	}

}
