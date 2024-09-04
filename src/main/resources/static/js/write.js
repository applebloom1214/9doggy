
// 게시물 등록
const writeBtn = document.querySelector(".write__btn");
if (writeBtn) {
    writeBtn.addEventListener("click", event => {
        fetch("/write",{
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title : document.querySelector(".write__title").value,
                content : document.querySelector(".write__content").value
            })
        }).then(() => {
                alert("게시물이 등록되었습니다 !");
                location.reload("/board");
            }
        )
    })
}

