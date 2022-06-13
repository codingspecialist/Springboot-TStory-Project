package site.metacoding.blogv3.util.email;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class MockEmailUtil implements EmailUtil {

    @Override
    public void sendEmail(String toAddress, String subject, String body) {

    }

}
