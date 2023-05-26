// 선택된 감정 이미지 표시
const emotionImgs = document.querySelectorAll('.emotion-img');
emotionImgs.forEach(img => {
  img.addEventListener('click', () => {
    emotionImgs.forEach(img => img.parentElement.classList.remove('selected'));
    img.parentElement.classList.add('selected');
  });
});