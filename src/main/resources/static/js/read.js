// 좋아요 구현
let bno = document.querySelector(".read__bno").value;
let likeTrigger = -1;
let likeIcon = document.querySelector(".fa-heart");

function mouseEnterEvent(e) {
    likeIcon.style.opacity = "0.5";
}

function mouseLeaveEvent(e) {
    likeIcon.style.opacity = "1";
}

likeIcon.addEventListener("mouseenter", mouseEnterEvent);
likeIcon.addEventListener("mouseleave", mouseLeaveEvent);

function addLikes() {
    fetch("/posting/" + bno + "/" + likeTrigger, {
        method: "PUT"
    })
        .then((res) => res.json())
        .then((data) => {
            let likes = data;
            alert("좋아요 누름 !");
            let read__like = document.querySelector(".read__like");
            read__like.innerHTML = "좋아요 " + likes + "개";
        })

    // .then((response) =>response.text())
    // .then((data)=>{
    //     let likes = data.likes;
    //     alert("좋아요 누름 !");
    //     console.log(data);
    //     let read__like = document.querySelector(".read__like");
    //     read__like.innerHTML = "좋아요 "+ likes +"개";
    // })
}


function like() {
    likeTrigger *= -1;
    if (likeTrigger == 1) {
        likeIcon.classList.replace("fa-regular", "fa-solid");
        likeIcon.classList.add("fa-bounce");
        likeIcon.style.color = "#ff3040";
        likeIcon.style.opacity = "1";
        likeIcon.removeEventListener("mouseenter", mouseEnterEvent);
        likeIcon.removeEventListener("mouseleave", mouseLeaveEvent);
        addLikes();
    } else {
        likeIcon.classList.replace("fa-solid", "fa-regular");
        likeIcon.classList.remove("fa-bounce");
        likeIcon.style.color = "black";
        likeIcon.style.opacity = "0.5";
        likeIcon.addEventListener("mouseenter", mouseEnterEvent);
        likeIcon.addEventListener("mouseleave", mouseLeaveEvent);
        addLikes();
    }
}


// 게시물 업데이트
const modifyBtn = document.querySelector("#modify__btn");
if (modifyBtn) {
    modifyBtn.addEventListener("click", event => {
        fetch("/posting/" + bno, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.querySelector(".read__title").innerHTML,
                content: document.querySelector(".read__content").innerHTML,
                writer: "test"
            })
        }).then(() => {
                alert("게시물이 수정되었습니다 !");
                location.replace("/board");
            }
        )
    })
}

// 게시물 삭제
const deleteBtn = document.querySelector("#delete__btn");
if (deleteBtn) {
    deleteBtn.addEventListener("click", event => {
        let bno = document.querySelector(".read__bno").value;

        fetch("/posting/" + bno, {
            method: "DELETE"
        }).then(() => {
                alert("게시물이 삭제되었습니다 !");
                location.replace("/board");
            }
        )
    })
}

// 리플 생성
const replyCreateBtn = document.querySelector(".inputbox__btn");
let replies = document.querySelector('.replies');
if (replyCreateBtn) {
    replyCreateBtn.addEventListener("click", event => {
        // let page = document.querySelector('.read__page').value;
        let content = document.querySelector(".inputbox__textarea");
        let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);
        fetch("/posting/reply", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content: content.value,
                writer: "testWriter",
                bno: bno
            })
        }).then((res) => res.json())
            .then((data) => {
                let reply = data;
                let writer = reply.writer;
                let createdDate = reply.createdAt;
                let createdContent = reply.content;
                let reply__no = reply.rno;
                let str = '';
                str += "<div class='reply' value=>";
                str += "<input type='hidden' class='reply__rno' value=";
                str += reply__no;
                str += ">";
                str += "<div class='reply__header'>";
                str += "<span class='reply__writer'>";
                str += writer;
                str += "</span>";
                str += "<div class='reply__rightsection'>";
                str += "<span class='reply__modify' style='color: #c08184'>수정</span>";
                str += "<span class='reply__delete' style='color: #c08184'>삭제</span>";
                str += "<span class='reply__date'>";
                str += createdDate;
                str += "</span></div></div>";
                str += "<div class='reply__content' contenteditable='true'>";
                str += createdContent;
                str += "</div></div>";
                replies.insertAdjacentHTML('afterbegin', str);
                let newReply = replies.firstChild;
                newReply__replycontent = newReply.querySelector('.reply__content');
                newReply__replycontent.addEventListener('click', event => nestedReply(event));
                newReply__replycontent.addEventListener('blur', event => nestedReply2(event));
                let newReply__modify = newReply.querySelector('.reply__modify');
                newReply__modify.addEventListener('click', event => modifyReply(event));
                let newReply__delete = newReply.querySelector('.reply__delete');
                newReply__delete.addEventListener('click', event => deleteReply(event));
                reply__cnt += 1;
                document.querySelector(".reply__cnt").innerHTML = reply__cnt;
                content.value = "";
            })
    })
}

// 리플 수정
let replyModify = document.querySelectorAll('.reply__modify');
for (let i = 0; i < replyModify.length; i++) {
    replyModify[i].addEventListener('click', event => modifyReply(event));
}
function modifyReply(event){
    let reply = event.target.closest('.reply');
    let rno = reply.querySelector('.reply__rno').value;
    console.log(rno);
    let content = reply.querySelector('.reply__content').innerHTML;
    console.log(content);

    fetch("/posting/reply", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            content: content,
            writer: "testWriter",
            rno : rno
        })
    }).then(() => {
            alert("댓글이 수정되었습니다 !");
        }
    )
}

// 리플 삭제
let replyDelete = document.querySelectorAll('.reply__delete');
for (let i = 0; i < replyDelete.length; i++) {
    replyDelete[i].addEventListener('click', event => deleteReply(event));
}
function deleteReply(event){
    let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);
    let reply = event.target.closest('.reply');
    let rno = reply.querySelector('.reply__rno').value;

    fetch("/posting/reply", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            rno : rno,
            bno : bno
        })
    }).then(() => {
            alert("댓글이 삭제되었습니다 !");
            reply__header = reply.querySelector('.reply__header');
            reply__header.remove();
            reply__content = reply.querySelector('.reply__content');
            reply__content.innerHTML = "삭제된 댓글입니다."
            reply__cnt -= 1;
        document.querySelector(".reply__cnt").innerHTML = reply__cnt;
        }
    )
}

// 대댓글

document.querySelector('.reply__write__nested').style.display= ('none');
let nested__reply = document.querySelector('.reply__write__nested');
let all__reply = document.querySelectorAll('.reply__content');
for (let i = 0; i < all__reply.length; i++) {
    all__reply[i].addEventListener('click', event => nestedReply(event));
    all__reply[i].addEventListener('blur', event => nestedReply2(event));
}
function nestedReply(event){
    let reply = event.target.closest('.reply');
    reply__content = reply.querySelector('.reply__content');
    if(reply.classList.toggle('reply__toggle')){
        nested__reply.style.display = 'flex';
        reply__content.after(nested__reply);
    }else{
        nested__reply.style.display = 'none'
    }
}

function nestedReply2(event){
    let reply = event.target.closest('.reply');
    reply.classList.remove('reply__toggle');
}

