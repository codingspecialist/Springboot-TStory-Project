function postLikeClick(id) {
    let isLike = $(`#heart-${id}`).hasClass("my_fake_like");
    if (isLike) {
        postUnLike(id);
    } else {
        postLike(id);
    }
}

function postLike(id) {
    // fetch();
    $(`#heart-${id}`).addClass("my_fake_like");
    $(`#heart-${id}`).removeClass("my_fake_un_like");
    $(`#heart-${id}`).removeClass("far");
    $(`#heart-${id}`).addClass("fa-solid");
}

function postUnLike(id) {
    // fetch();
    $(`#heart-${id}`).addClass("my_fake_un_like");
    $(`#heart-${id}`).removeClass("my_fake_like");
    $(`#heart-${id}`).removeClass("fa-solid");
    $(`#heart-${id}`).addClass("far");
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