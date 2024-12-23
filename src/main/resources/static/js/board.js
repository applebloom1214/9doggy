// 검색 기능
const searchBtn = document.querySelector('.board__search');

if(searchBtn) {
    searchBtn.addEventListener('click', () => {
        // let formData = new FormData();
        let condition = document.querySelector('.board__select').value;
        let keyword = document.querySelector('.board__text').value;
        // formData.append('page', page);
        // formData.append('condition', condition);
        // formData.append('keyword', keyword);

        location.replace("/board/1?"+"condition="+condition+"&keyword="+keyword);

        // for (const x of formData.entries()) {
        //     console.log(x);
        // }
})}