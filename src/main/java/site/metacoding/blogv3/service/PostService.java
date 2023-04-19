package site.metacoding.blogv3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.blogv3.domain.category.Category;
import site.metacoding.blogv3.domain.category.CategoryRepository;
import site.metacoding.blogv3.domain.love.Love;
import site.metacoding.blogv3.domain.love.LoveRepository;
import site.metacoding.blogv3.domain.post.Post;
import site.metacoding.blogv3.domain.post.PostRepository;
import site.metacoding.blogv3.domain.user.User;
import site.metacoding.blogv3.handler.ex.CustomApiException;
import site.metacoding.blogv3.handler.ex.CustomException;
import site.metacoding.blogv3.util.UtilFileUpload;
import site.metacoding.blogv3.dto.love.LoveRespDto;
import site.metacoding.blogv3.dto.love.LoveRespDto.PostDto;
import site.metacoding.blogv3.dto.post.PostDetailRespDto;
import site.metacoding.blogv3.dto.post.PostRespDto;
import site.metacoding.blogv3.dto.post.PostWriteReqDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    @Value("${file.path}")
    private String uploadFolder;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final LoveRepository loveRepository;
    private final EntityManager em;

    @Transactional
    public LoveRespDto 좋아요(Integer postId, User principal) {

        // 숙제 Love를 Dto에 옮겨서 비영속화된 데이터를 응답하기
        Post postEntity = postFindById(postId);

        Love love = new Love();
        love.setUser(principal);
        love.setPost(postEntity);

        Love loveEntity = loveRepository.save(love);
        // 1. DTO 클래스 생성
        // 2. 모델메퍼 함수 호출!! 내가 만든 DTO = 모델메퍼메서드호출(loveEntity, 내가만든DTO.class)
        LoveRespDto loveRespDto = new LoveRespDto();
        loveRespDto.setLoveId(loveEntity.getId());
        PostDto postDto = loveRespDto.new PostDto();
        postDto.setPostId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        loveRespDto.setPost(postDto);

        return loveRespDto;
    }

    @Transactional
    public void 좋아요취소(Integer loveId, User principal) {
        // 권한체크
        loveFindById(loveId);
        loveRepository.deleteById(loveId);
    }

    @Transactional(rollbackFor = CustomApiException.class)
    public void 게시글삭제(Integer id, User principal) {

        // 게시글 확인.
        Post postEntity = postFindById(id);

        // 권한 체크
        if (authCheck(postEntity.getUser().getId(), principal.getId())) { // 이 부분 페이지 주인 아이디

            // 게시글 삭제
            postRepository.deleteById(id);
        } else {
            throw new CustomApiException("삭제 권한이 없습니다");
        }

    }

    // 비로그인 상태일 때 상세보기
    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id) {
        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 찾기
        Post postEntity = postFindById(id);

        // 리턴값 만들기.
        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(false);

        // 좋아요 유무 추가하기 (로그인한 사람이 해당 게시글을 좋아하는지)
        postDetailRespDto.setLove(false);
        postDetailRespDto.setLoveId(0);

        return postDetailRespDto;
    }

    // 로그인 상태일 때 상세보기
    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id, User principal) {

        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 찾기
        Post postEntity = postFindById(id);

        // 권한체크
        boolean isAuth = authCheck(postEntity.getUser().getId(), principal.getId());

        // 리턴값 만들기
        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(isAuth);

        // 좋아요 유무 추가하기 (로그인한 사람이 해당 게시글을 좋아하는지)
        // (1) 로그인한 사람의 userId와 상세보기한 postId로 Love 테이블에서 select해서 row가 있으면 true
        Optional<Love> loveOp = loveRepository.mFindByUserIdAndPostId(principal.getId(), id);
        if (loveOp.isPresent()) {
            Love loveEntity = loveOp.get();
            postDetailRespDto.setLoveId(loveEntity.getId());
            postDetailRespDto.setLove(true);
        } else {
            postDetailRespDto.setLove(false);
        }

        return postDetailRespDto;
    }

    public List<Category> 게시글쓰기화면(User principal) {
        return categoryRepository.findByUserId(principal.getId());
    }

    // 하나의 서비스는 여러가지 일을 한번에 처리한다. (여러가지 일이 하나의 트랜잭션이다.)
    @Transactional
    public void 게시글쓰기(PostWriteReqDto postWriteReqDto, User principal) {

        // 1. UUID로 파일쓰고 경로 리턴 받기
        String thumbNail = null;

        // getThumnailFile() 이 null 이거나, 공백이 들어오면 실행할 필요가 없음
        if (!(postWriteReqDto.getThumbnailFile() == null || postWriteReqDto.getThumbnailFile().isEmpty())) {
            thumbNail = UtilFileUpload.write(uploadFolder,
                    postWriteReqDto.getThumbnailFile());
        }

        // 2. 카테고리 있는지 확인
        Optional<Category> categoryOp = categoryRepository.findById(postWriteReqDto.getCategoryId());

        // 3. post DB 저장
        if (categoryOp.isPresent()) {
            Post post = postWriteReqDto.toEntity(thumbNail, principal, categoryOp.get());
            postRepository.save(post);
        } else {
            throw new CustomException("해당 카테고리가 존재하지 않습니다.");
        }

    }

    @Transactional
    public PostRespDto 게시글목록보기(Integer pageOwnerId, Pageable pageable) {

        Page<Post> postsEntity = postRepository.findByUserId(pageOwnerId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(pageOwnerId);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categorysEntity,
                pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1,
                pageNumbers);

        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer pageOwnerId, Integer categoryId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(pageOwnerId, categoryId, pageable);
        List<Category> categorysEntity = categoryRepository.findByUserId(pageOwnerId);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categorysEntity,
                pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1,
                pageNumbers);

        return postRespDto;
    }

    // 좋아요 한건 찾기
    private Love loveFindById(Integer loveId) {
        Optional<Love> loveOp = loveRepository.findById(loveId);
        if (loveOp.isPresent()) {
            Love loveEntity = loveOp.get();
            return loveEntity;
        } else {
            throw new CustomApiException("해당 좋아요가 존재하지 않습니다");
        }
    }

    // 게시글 한건 찾기
    private Post postFindById(Integer postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            return postEntity;
        } else {
            throw new CustomApiException("해당 게시글이 존재하지 않습니다");
        }
    }

    // 로그인 유저가 게시글 주인인지 확인하는 메서드
    private boolean authCheck(Integer principalId, Integer pageOwnerId) {
        boolean isAuth = false;
        if (principalId == pageOwnerId) {
            isAuth = true;
        } else {
            isAuth = false;
        }
        return isAuth;
    }

}
