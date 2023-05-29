

// 달력 만들기
(function () {
    calendarMaker($("#calendarForm"), new Date());
})();

var nowDate = new Date();
function calendarMaker(target, date) {
    if (date == null || date == undefined) {
        date = new Date();
    }
    nowDate = date;
    if ($(target).length > 0) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1;
        $(target).empty().append(assembly(year, month));
    } else {
        console.error("custom_calendar Target is empty!!!");
        return;
    }

    var thisMonth = new Date(nowDate.getFullYear(), nowDate.getMonth(), 1);
    var thisLastDay = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, 0);


    var tag = "<tr>";
    var cnt = 0;
    //빈 공백 만들어주기
    for (i = 0; i < thisMonth.getDay(); i++) {
        tag += "<td></td>";
        cnt++;
    }

    //날짜 채우기
    for (i = 1; i <= thisLastDay.getDate(); i++) {
        if (cnt % 7 == 0) { tag += "<tr>"; }

        tag += "<td>" + i + "</td>";
        cnt++;
        if (cnt % 7 == 0) {
            tag += "</tr>";
        }
    }
    $(target).find("#custom_set_date").append(tag);
    calMoveEvtFn();

    function assembly(year, month) {
        var calendar_html_code =
            "<table class='custom_calendar_table'>" +
            "<colgroup>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "</colgroup>" +
            "<thead class='cal_date'>" +
            "<th><button type='button' class='prev'><</button></th>" +
            "<th colspan='5'><p><span>" + year + "</span>년 <span>" + month + "</span>월</p></th>" +
            "<th><button type='button' class='next'>></button></th>" +
            "</thead>" +
            "<thead  class='cal_week'>" +
            "<th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>" +
            "</thead>" +
            "<tbody id='custom_set_date'>" +
            "</tbody>" +
            "</table>";
        return calendar_html_code;
    }
  }
function calMoveEvtFn() {
    //전달 클릭
    $(".custom_calendar_table").on("click", ".prev", function () {
        nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() - 1, nowDate.getDate());
        calendarMaker($(target), nowDate);
    });
    //다음날 클릭
    $(".custom_calendar_table").on("click", ".next", function () {
        nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate());
        calendarMaker($(target), nowDate);
    });
    //일자 선택 클릭
    $(".custom_calendar_table").on("click", "td", function () {
        $(".custom_calendar_table .select_day").removeClass("select_day");
        $(this).removeClass("select_day").addClass("select_day");
        // 선택된 날짜 표시
        // selectedDate = $(this).text();
        // $("#firstBox").text(selectedDate);
    });

}
$(document).ready(function() {
    //오늘의 기록 불러오기
    var selectedDate = null; // 선택된 날짜 변수 초기화
    var $contentBox = $("#selected-content"); // 내용을 표시할 상자 요소
    var $deleteButton = $("#delete-button");
    var $editButton = $("#edit-button");
    // var $deleteButton = document.getElementById('delete-button');
    // var $editButton = document.getElementById('edit-button');
    // const $contentBox = document.getElementById('selected-content');
    // 날짜 클릭 이벤트 처리
    $(".custom_calendar_table").on("click", "td", function () {
      $(".custom_calendar_table .select_day").removeClass("select_day");
      $(this).removeClass("select_day").addClass("select_day");

      var selectedDate = $(this).text();
      loadDiary(selectedDate);
    });
    //--
    //1년전 오늘 기록 불러오기
    var selectedDate = null; // 선택된 날짜 변수 초기화
    var $contentBox = $("#selected-content"); // 내용을 표시할 상자 요소 선택
    var $pastBox = $("#past-content"); // 과거 기록을 표시할 상자 요소 선택

    // 날짜 클릭 이벤트 처리
    $(".custom_calendar_table").on("click", "td", function () {
      $(".custom_calendar_table .select_day").removeClass("select_day");
      $(this).removeClass("select_day").addClass("select_day");

      var selectedDate = $(this).text();
      ploadDiary(selectedDate);
  });
  //--
  // 수정 버튼 클릭 이벤트 처리
  $("#edit-button").click(function() {
    window.location.href = "http://localhost:63342/%EB%8B%A4%EC%9D%B4%EC%96%B4%EB%A6%AC%ED%94%8C%EC%A0%9D/src/main/resources/templates/Diary.html?_ijt=5osu5pgk8c3hr3srn860lji1fp&_ij_reload=RELOAD_ON_SAVE";
  });
  // 삭제 버튼 클릭 이벤트 처리
  $("#delete-button").click(function() {
    deleteDiary(diaryId);
  });
});

// 토큰을 이용하여 닉네임 불러오기 함수




//오늘의 기록 불러오기 함수
function loadDiary(date) {
  // // 날짜에 해당하는 일기를 불러오는 요청을 보냅니다.
  // const $deleteButton = document.getElementById('delete-button');
  // const $editButton = document.getElementById('edit-button');
  // const $contentBox = document.getElementById('selected-content');
  fetch(`/api/calendar/${date}`)
    .then(response => response.json())
    .then(data => {
      // 일기 데이터를 받아온 후 처리하는 로직을 작성합니다.
      const selectedContent = document.getElementById('selected-content');
      selectedContent.innerHTML = data.content;
      $deleteButton.hide();
      if (data) {
        // 일기가 있는 경우

        $($deleteButton).show();
      } else {
        // 일기가 없는 경우
        $contentBox.text("1년 전 오늘의 기록이 없습니다.");
        $($deleteButton).hide();
      }
    })
    .catch(error => {
      // 오류 처리
      console.error('Error:', error);
    });
}
//1년전 오늘의 기록 불러오기 함수
function ploadDiary(date) {
  //  날짜에 해당하는 일기를 불러오는 요청보냄
  // const $pastBox = document.getElementById('past-content');

  fetch(`/api/calendar/${date}`)
    .then(response => response.json())
    .then(data => {
      // 일기 데이터를 받아와서 첫번째 상자에 출력
      const selectedContent = document.getElementById('selected-content');
      selectedContent.innerHTML = data.content;

      // 1년 전의 기록이 있는지 확인
      const oneYearAgo = new Date();
      oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
      const oneYearAgoDate = formatDate(oneYearAgo);

      fetch(`/api/calendar/${oneYearAgoDate}`)
        .then(response => {
          if (response.status === 200) {
            return response.json();
          } else {
            throw new Error("기록 없음");
          }
        })
        .then(data => {
          // 1년 전의 기록이 있는 경우 출력
          const pastContent = document.getElementById('past-content');
          pastContent.innerHTML = data.content;
          $($pastBox).show();
        })
        .catch(error => {
          // 1년 전의 기록이 없는 경우 문구 출력
          const pastContent = document.getElementById('past-content');
          pastContent.innerHTML = "1년전 오늘의 기록이 없습니다.";
          $($pastBox).show();
        });
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

// 날짜를 yyyy-mm-dd 형식으로 변환하는 함수
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}
function deleteDiary(diaryId) {
  // 삭제 요청을 보내는 fetch API 호출
  fetch(`/api/calendar/${diaryId}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (response.ok) {
        // 삭제가 성공적으로 이루어진 경우
        console.log('일기가 삭제되었습니다.');
      } else {
        // 삭제가 실패한 경우
        console.error('일기 삭제에 실패했습니다.');
      }
    })
    .catch(error => {
      // 오류 처리
      console.error('Error:', error);
    });
}

 //페이지이동

//월별 감정 통계로 이동
function avgmove(e){
  var link = 'http://127.0.0.1:5500/front/statistics/monthly_statistic.html';
  location.href=link;
}
//홈 화면으로 이동
function homemove(e){
  location.href=url+'/api/calendar';
}
//로그아웃
function logout(e){
  var link = 'http://127.0.0.1:5500/front/Sign-inup/Login.html';
  location.href=link;
}

//마이페이지
const mypageButton=document.getElementById("mypage");
mypageButton.addEventListener("click",function(){
    var userLoginId=data.userLoginId;
  location.href = url + '/api/user/mypage/'+userLoginId;
});
