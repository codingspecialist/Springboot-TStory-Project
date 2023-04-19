# 스프링부트 JPA 티스토리 블로그

### 1. 의존성
- devtools
- spring web (mvc)
- mustache
- lombok
- jpa
- mariadb
- security
- validation

### 2. 에디터
- https://quilljs.com/

### 3. 댓글
- https://livere.com/

### 4. 모델링
```sql
User
id
username
password
createDate
updateDate

Post
id
title
content
thumnail
userId
categoryId
createDate
updateDate

Like
id
postId
userId
createDate
updateDate

Category
id
title
userId
createDate
updateDate
```

### 5. 기능정리
- 단위테스트 - API 문서 자동생성
- AOP 처리
- 배포
- firebase fcm

### 6. 추가 보완
- 글수정 (직접)
- 회원정보 수정 (직접)


### 7. 페이징 참고
```sql
-- currentPage, totalPages
SELECT TRUE last FROM dual;

SELECT 
true LAST,
false FIRST,
3 size, 
0 currentPage,
(SELECT COUNT(*) FROM post WHERE userId = 1) totalCount,
(SELECT CEIL(COUNT(*)/3) FROM post WHERE userId = 1) totalPages,
p.*
FROM post p
ORDER BY p.id DESC
LIMIT 0, 3;
-- LIMIT (0*3), 3;
```