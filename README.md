# 스프링부트 JPA 블로그 V3 - GREEN Computer

### 1. 의존성
- devtools
- spring web (mvc)
- mustache
- lombok
- jpa
- mariadb
- security
- validation

### 2. DB설정
```sql
CREATE USER 'green'@'%' IDENTIFIED BY 'green1234';
CREATE DATABASE greendb;
GRANT ALL PRIVILEGES ON greendb.* TO 'green'@'%';
```

### 3. 에디터
- https://quilljs.com/

### 4. 댓글
- https://livere.com/

### 5. 주소 요청 이런식으로 해볼까?
```txt
localhost:8080/ (메인페이지 - 글 있는 곳 아님)
localhost:8080/user/{userId}/post
localhost:8080/user/{userId}/post/{postId}
localhost:8080/user/{userId}/category/{title}
```

### 6. 모델링
```sql
Visit
id
userId
totalCount
createDate
updateDate

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

### 7. 기능정리
- 댓글 (라이브러리 사용)
- 좋아요
- AOP 처리
- 로그관리 (Log4j)
- 단위테스트
- 배포
- 검색
- 프로필 사진 변경
- 글수정
- 회원정보 수정

### Gradle depenency update
'''txt
./gradlew --refresh-dependencie
'''


### 페이징 참고
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

### 로그변경 테스트