// 좋아요 구현
let bno = document.querySelector(".read__bno").value;
let likeTrigger = -1;
let likeIcon = document.querySelector(".fa-heart");
function mouseEnterEvent(e) {
    likeIcon.style.opacity = "0.5";
}
function mouseLeaveEvent(e){
    likeIcon.style.opacity = "1";
}
likeIcon.addEventListener("mouseenter", mouseEnterEvent);
likeIcon.addEventListener("mouseleave", mouseLeaveEvent);

function addLikes(){
    fetch("/posting/"+bno+"/"+likeTrigger,{
        method: "PUT"})
        .then((res) => res.json())
        .then((data)=>{
            let likes = data;
            alert("좋아요 누름 !");
            let read__like = document.querySelector(".read__like");
            read__like.innerHTML = "좋아요 "+ likes +"개";
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


function like(){
    likeTrigger *= -1;
    if(likeTrigger == 1){
        likeIcon.classList.replace("fa-regular","fa-solid");
        likeIcon.classList.add("fa-bounce");
        likeIcon.style.color = "#ff3040";
        likeIcon.style.opacity = "1";
        likeIcon.removeEventListener("mouseenter", mouseEnterEvent);
        likeIcon.removeEventListener("mouseleave", mouseLeaveEvent);
        addLikes();
    }else{
        likeIcon.classList.replace("fa-solid","fa-regular");
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
        fetch("/posting/"+bno,{
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title : document.querySelector(".read__title").innerHTML,
                content : document.querySelector(".read__content").innerHTML,
                writer : "test"
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

        fetch("/posting/"+bno,{
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
if (replyCreateBtn) {
    replyCreateBtn.addEventListener("click", event => {
        // let page = document.querySelector('.read__page').value;
        let content = document.querySelector(".inputbox__textarea");
        fetch("/posting/reply",{
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content : content.value,
                writer : "testWriter",
                bno : bno
            })
        }).then(() => {
                alert("리플이 등록되었습니다 !");
                content.value = "";
            }
        )
    })
}

