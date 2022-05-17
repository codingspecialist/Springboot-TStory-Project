function postLikeClick(postId) {

    let principalId = $("#principal-id").val();
    console.log(principalId);

    if (principalId == undefined) {
        alert("로그인이 필요합니다");
        location.href = "/login-form";
        return;
    }

    let isLike = $(`#my-heart`).hasClass("my_fake_like");
    if (isLike) {
        postUnLike(postId);
    } else {
        postLike(postId);
    }
}

async function postLike(postId) {
    let response = await fetch(`/s/api/post/${postId}/love`, {
        method: 'POST'
    });

    let responseParse = await response.json();


    if (response.status == 201) {
        $(`#my-heart`).addClass("my_fake_like");
        $(`#my-heart`).removeClass("my_fake_un_like");
        $(`#my-heart`).removeClass("far");
        $(`#my-heart`).addClass("fa-solid");

        $("#my-loveId").val(responseParse.loveId); // 이게 제일 중요한 코드이다.
    } else {
        alert("통신실패");
    }
}

async function postUnLike(postId) {
    let loveId = $("#my-loveId").val();
    console.log(loveId);
    let response = await fetch(`/s/api/post/${postId}/love/${loveId}`, {
        method: 'DELETE'
    });

    if (response.status == 200) {
        $(`#my-heart`).addClass("my_fake_un_like");
        $(`#my-heart`).removeClass("my_fake_like");
        $(`#my-heart`).removeClass("fa-solid");
        $(`#my-heart`).addClass("far");
    } else {
        alert("통신실패");
    }
}

// 게시글 삭제, 권한체크 후 삭제 /s/api/post/postId
$("#btn-delete").click(() => {
    postDelete();
});

let postDelete = async () => {

    let postId = $("#postId").val();
    let pageOwnerId = $("#pageOwnerId").val();

    let response = await fetch(`/s/api/post/${postId}`, {
        method: "DELETE"
    });

    if (response.status == 200) {
        alert("삭제성공");
        location.href = `/user/${pageOwnerId}/post`;
    } else {
        alert("삭제실패");
    }
};