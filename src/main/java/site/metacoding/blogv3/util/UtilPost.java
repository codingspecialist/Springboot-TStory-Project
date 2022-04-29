package site.metacoding.blogv3.util;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.post.PostRepository;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.handler.ex.CustomException;

@RequiredArgsConstructor
public class UtilPost {

    private final PostRepository postRepository;

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

}
