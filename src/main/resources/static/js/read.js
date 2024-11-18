/// 스크롤 구현

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
fddf
function nothing(){

}

const nestedReply = event => nestedReplyFun(event);
const nestedReply2 = event => nestedReply2Fun(event);

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
                let reply__deleted = reply.deleted;
                let str = '';
                str += "<div class='reply' value=>";
                str += "<div class='reading__reply'>";
                str += "<div class='reply__comment' value=";
                str += reply__deleted;
                str += ">";
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
                str += "</div></div></div>";
                replies.insertAdjacentHTML('beforeend', str);
                let newReply = replies.lastChild;
                newReply__replycontent = newReply.querySelector('.reply__content');
                let deleted = newReply__replycontent.innerHTML;
                if(deleted.indexOf('삭제된') == -1){
                    newReply__replycontent.addEventListener('click', nestedReply);
                    newReply__replycontent.addEventListener('blur', nestedReply2);
                }
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
    if ( replyModify[i].closest('.nested__reply')){
        replyModify[i].addEventListener('click', event => nested__modifyReply(event));
    }else{
        replyModify[i].addEventListener('click', event => modifyReply(event));
    }
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
    if ( replyDelete[i].closest('.nested__reply')){
        replyDelete[i].addEventListener('click', event => nested__deleteReply(event));
    }else{
        replyDelete[i].addEventListener('click', event => deleteReply(event));
    }
}
function deleteReply(event){
    let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);
    let reply = event.target.closest('.reply');
    let all__nestedReply = reply.querySelectorAll('.reply__content');
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
            for (let i=0; i<all__nestedReply.length; i++) {
                all__nestedReply[i].removeEventListener('click', nestedReply);
                all__nestedReply[i].removeEventListener('blur', nestedReply2);
            }
            reply__content.removeEventListener('click', nestedReply);
            reply__content.removeEventListener('blur', nestedReply2);
            reply__content.innerHTML = "삭제된 댓글입니다."
            reply__cnt -= 1;
        document.querySelector(".reply__cnt").innerHTML = reply__cnt;
        }
    )
}

// 대댓글창
document.querySelector('.reply__write__nested').style.display= ('none');
let nested__reply = document.querySelector('.reply__write__nested');
let all__reply = document.querySelectorAll('.reply__content');
for (let i = 0; i < all__reply.length; i++) {
    let deleted = all__reply[i].innerHTML;
    if(deleted.indexOf('삭제된') == -1){
        all__reply[i].addEventListener('click', nestedReply);
        all__reply[i].addEventListener('blur', nestedReply2);
    }
}


function nestedReplyFun(event){
    let reply = event.target.closest('.reply');
    let reply__content = reply.querySelector('.reply__content');
    if(reply.classList.toggle('reply__toggle')){
        nested__reply.style.display = 'flex';
        reply__content.after(nested__reply);
    }else{
        nested__reply.style.display = 'none'
    }
}




function nestedReply2Fun(event){
    let reply = event.target.closest('.reply');
    reply.classList.remove('reply__toggle');
}



// 대댓글 작성
let reply__write__nested = document.querySelector('.reply__write__nested');
let nested__reply__input = reply__write__nested.querySelector('.inputbox__btn');
nested__reply__input.addEventListener('click', event => createReply(event));

function createReply(event){
    let reply = event.target.closest('.reply');
    let reply__rno = reply.querySelector('.reply__rno').value;
    let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);
    let content = reply.querySelector(".inputbox__textarea");
    // let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);
    fetch("/posting/reply", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            content: content.value,
            writer: "testWriter",
            bno: bno,
            prno : reply__rno
        })
    }).then((res) => res.json())
        .then((data) => {
            let reading__reply = data;
            let writer = reading__reply.writer;
            let createdDate = reading__reply.createdAt;
            let createdContent = reading__reply.content;
            let reply__no = reading__reply.rno;
            let reply__deleted = reading__reply.deleted;
            let str = '';
            str += "<div class='reading__reply'>";
            str += "<div class='nested__reply' value=";
            str += reply__deleted;
            str += ">";
            str += "<input type='hidden' class='reply__rno' value=";
            str += reply__no;
            str += ">";
            str += "<div class='reply__header'>";
            str += "<span class='reply__writer'>";
            str += writer;
            str += "</span>";
            str += "<span class='towriter'>@안*민&nbsp;&nbsp; </span>성*훈</span>"
            str += "<div class='reply__rightsection'>";
            str += "<span class='reply__modify' style='color: #c08184'>수정</span>";
            str += "<span class='reply__delete' style='color: #c08184'>삭제</span>";
            str += "<span class='reply__date'>";
            str += createdDate;
            str += "</span></div></div>";
            str += "<div class='reply__content' contenteditable='true'>";
            str += createdContent;
            str += "</div></div></div>";
            reply.insertAdjacentHTML('beforeend', str);
            let newReply = reply.lastChild;
            newReply__replycontent = newReply.querySelector('.reply__content');
            let deleted = newReply__replycontent.innerHTML;
            if(deleted.indexOf('삭제된') == -1){
                newReply__replycontent.addEventListener('click', nestedReply);
                newReply__replycontent.addEventListener('blur', nestedReply2);
            }
            let newReply__modify = newReply.querySelector('.reply__modify');
            newReply__modify.addEventListener('click', event => nested__modifyReply(event));
            let newReply__delete = newReply.querySelector('.reply__delete');
            newReply__delete.addEventListener('click', event => nested__deleteReply(event));
            reply__cnt += 1;
            document.querySelector(".reply__cnt").innerHTML = reply__cnt;
            let content = reply__write__nested.querySelector('.inputbox__textarea');
            content.value = "";
        })
}

function nested__modifyReply(event){
    let reply = event.target.closest('.nested__reply');
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


function nested__deleteReply(event){
    let reply = event.target.closest('.nested__reply');
    let rno = reply.querySelector('.reply__rno').value;
    let reply__cnt = parseInt(document.querySelector(".reply__cnt").innerHTML);

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
            reply__content.removeEventListener('click', nestedReply);
            reply__content.removeEventListener('blur', nestedReply2);
            reply__cnt -= 1;
            document.querySelector(".reply__cnt").innerHTML = reply__cnt;
        }
    )
}
