let valid = {
    username: {
        state: false,
        msg: ""
    },
    password: {
        state: false,
        msg: ""
    }
}

$("#username").focusout(() => {
    usernameSameCheck();
});

$("#password").focusout(() => {
    passwordSameCheck();
});

$("#same-password").focusout(() => {
    passwordSameCheck();
});

function validation() {
    if (valid.username.state && valid.password.state) {
        return true;
    } else {
        let errorMsgs = ``;

        errorMsgs += `${valid.username.msg}<br/>`;
        errorMsgs += `${valid.password.msg}<br/>`;

        $(".my_error_box").html(errorMsgs);
        $(".my_error_box").removeClass("my_hidden");
        return false;
    }
}

async function usernameSameCheck() {
    let username = $("#username").val();

    let response = await fetch(`/api/user/username-same-check?username=${username}`);
    let responseParse = await response.json();

    if (response.status === 200) {
        if (!responseParse) {
            valid.username.state = false;
            valid.username.msg = "유저네임이 중복되었습니다.";
        } else {
            valid.username.state = true;
            valid.username.msg = "";
        }
    } else {
        alert(responseParse);
    }
}

function passwordSameCheck() {
    let password = $("#password").val();
    let samePassword = $("#same-password").val();

    if (password === samePassword) {
        valid.password.state = true;
        valid.password.msg = "";
    } else {
        valid.password.state = false;
        valid.password.msg = "패스워드가 동일하지 않습니다.";
    }
}