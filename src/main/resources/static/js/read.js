
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

