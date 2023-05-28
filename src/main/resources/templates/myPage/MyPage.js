myPageButton.addEventListener("click", function() {
    var id = document.getElementById("id").value;
    var pw = document.getElementById("pw").value;

    if (id == "" || pw == "") {
        alert("아이디 및 비밀번호를 입력해주세요.");
    } else {
        const userData = {
            userLoginId: id,
            userPassword: pw
        };

        fetch(url + '/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.jwtToken) {
                // 토큰을 서버에서 전달받아 로컬 스토리지에 저장
                localStorage.setItem('token', data.jwtToken);
                var userLoginId = data.userLoginId;

                // 마이페이지 API 요청을 보낼 때 헤더에 JWT 토큰을 포함하여 전송
                fetch(url + '/api/user/mypage/'+userLoginId {
                    headers: {
                        'Authorization': 'Bearer '+ localStorage.getItem('token')
                    }
                })
                .then(response => response.json())
                .then(data => {
                    // 마이페이지 정보를 가져와서 화면에 출력
                    const nickname = document.getElementById('nickname');
                    const userId = document.getElementById('userId');
                    nickname.innerText = 'Nickname: ' + data.nickname;
                    userId.innerText = 'User ID: ' + data.userId;
                })
                .catch(error => console.error('Error:', error));

                // 마이페이지로 이동
                location.href = url + '/api/mypage/'+userLoginId;
            } else {
                alert('로그인에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('로그인에 실패했습니다.');
        });
    }
});