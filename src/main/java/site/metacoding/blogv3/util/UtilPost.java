package site.metacoding.blogv3.util;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.post.PostRepository;
import site.metacoding.blogv3.domain.visit.Visit;
import site.metacoding.blogv3.domain.visit.VisitRepository;
import site.metacoding.blogv3.handler.ex.CustomApiException;
import site.metacoding.blogv3.handler.ex.CustomException;

@Slf4j
public class UtilPost {

    private static VisitRepository visitRepository;
    private static PostRepository postRepository;

    // 책임 : img 태그 제거
    public static String getContentWithoutImg(String content) {
        Document doc = Jsoup.parse(content);
        // System.out.println(doc);

        // 2. 실행
        Elements els = doc.select("img");
        for (Element el : els) {
            el.remove();
        }

        return doc.select("body").text();
    }

    // 책임 : 아이디로 게시글 찾기
    public static Post postFindById(Integer postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            return postEntity;
        } else {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다");
        }
    }

    // 책임 : 로그인 유저가 게시글의 주인인지 확인.
    public static boolean Authcheck(Integer postId, Integer principalId) {
        boolean isAuth = false;
        if (principalId == postId) {
            isAuth = true;
        } else {
            isAuth = false;
        }
        return isAuth;
    }

    // 책임 : 방문자수 증가
    public static void visitIncrease(Integer pageOwnerId) {
        Optional<Visit> visitOp = visitRepository.findById(pageOwnerId);
        if (visitOp.isPresent()) {
            Visit visitEntity = visitOp.get();
            Long totalCount = visitEntity.getTotalCount();
            visitEntity.setTotalCount(totalCount + 1);
        } else {
            log.error("미친 심각", "회원가입할때 Visit이 안 만들어지는 심각한 오류가 있습니다.");
            // sms 메시지 전송
            // email 전송
            // file 쓰기
            throw new CustomException("일시적 문제가 생겼습니다. 관리자에게 문의해주세요.");
        }
    }
}
