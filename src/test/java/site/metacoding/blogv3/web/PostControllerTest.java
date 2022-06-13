package site.metacoding.blogv3.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import site.metacoding.blogv3.web.dto.post.PostWriteReqDto;

@ActiveProfiles("test")
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

        // WithMockUser는 간단한 인증만 통과하고 싶을 때 사용!!
        // 만약에 세션에 있는 값을 내부에서 사용해야 하면? -> 다른 것을 써야 함.
        // @WithMockUser // username="username" password="password"

        @WithUserDetails("ssar")
        @Test
        public void write_테스트() throws Exception {

                // Authentication authentication =
                // SecurityContextHolder.getContext().getAuthentication();
                // LoginUser loginUser = (LoginUser) authentication.getPrincipal();

                // given

                File file = new File(
                                "C:\\green_workspace\\tstory_lab\\Springboot-Blog-V3\\src\\main\\resources\\static\\images\\dog.jpg");

                MockMultipartFile image = new MockMultipartFile("profileImgFile", "dog.jpg", "image/jpeg",
                                Files.readAllBytes(file.toPath()));

                PostWriteReqDto postWriteReqDto = PostWriteReqDto.builder()
                                .categoryId(1) // 이거 분명히 터짐
                                .title("스프링1강")
                                .content("재밌음")
                                .thumnailFile(image)
                                .build();

                // when

                ResultActions resultActions = mockMvc.perform(
                                MockMvcRequestBuilders.multipart("/s/post")
                                                .file((MockMultipartFile) postWriteReqDto.getThumnailFile())
                                                .param("title", postWriteReqDto.getTitle())
                                                .param("content", postWriteReqDto.getContent())
                                                .param("categoryId", postWriteReqDto.getCategoryId() + ""));

                // then

                resultActions
                                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                                .andDo(MockMvcResultHandlers.print());
        }

}
