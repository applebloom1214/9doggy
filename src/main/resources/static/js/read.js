// 좋아요 구현

let likeTrigger = false;
let likeIcon = document.querySelector(".fa-heart");
function mouseEnterEvent(e) {
    likeIcon.style.opacity = "0.5";
}
function mouseLeaveEvent(e){
    likeIcon.style.opacity = "1";
}
function heartBeatEvent(e) {

}
likeIcon.addEventListener("mouseenter", mouseEnterEvent);
likeIcon.addEventListener("mouseleave", mouseLeaveEvent);


function like(){
    likeTrigger = !likeTrigger;
    if(likeTrigger){
        likeIcon.classList.replace("fa-regular","fa-solid");
        likeIcon.classList.add("fa-bounce");
        likeIcon.style.color = "#ff3040";
        likeIcon.style.opacity = "1";
        likeIcon.removeEventListener("mouseenter", mouseEnterEvent);
        likeIcon.removeEventListener("mouseleave", mouseLeaveEvent);
    }else{
        likeIcon.classList.replace("fa-solid","fa-regular");
        likeIcon.classList.remove("fa-bounce");
        likeIcon.style.color = "black";
        likeIcon.style.opacity = "0.5";
        likeIcon.addEventListener("mouseenter", mouseEnterEvent);
        likeIcon.addEventListener("mouseleave", mouseLeaveEvent);
    }
}


// 게시물 업데이트
const modifyBtn = document.querySelector("#modify__btn");
if (modifyBtn) {
    modifyBtn.addEventListener("click", event => {
        let bno = document.querySelector(".read__bno").value;



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

