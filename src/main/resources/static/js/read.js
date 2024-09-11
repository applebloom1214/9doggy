
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

