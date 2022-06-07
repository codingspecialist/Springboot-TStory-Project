package site.metacoding.blogv3.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blogv3.web.dto.post.PostWriteReqDto;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    // @WithMockUser
    @Test
    public void write_테스트() throws Exception {
        // given
        PostWriteReqDto postWriteReqDto = PostWriteReqDto.builder()
                .categoryId(1) // 이거 분명히 터짐
                .title("스프링1강")
                .content("재밌음")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/s/post"));

        // then
        resultActions
                .andDo(MockMvcResultHandlers.print());
        //

    }

}
