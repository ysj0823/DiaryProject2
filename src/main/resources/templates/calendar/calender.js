

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
                selectedDate = $(this).text();
                $("#firstBox").text(selectedDate);
            });

        }
        $(document).ready(function() {
            var selectedDate = null; // 선택된 날짜 변수 초기화

            // 날짜 클릭 이벤트 처리
            $(".custom_calendar_table").on("click", "td", function () {
              $(".custom_calendar_table .select_day").removeClass("select_day");
              $(this).removeClass("select_day").addClass("select_day");

              // 선택된 날짜의 데이터 불러오기 로직 구현해야됨
              selectedDate = $(this).text();
              $("#firstBox").text(selectedDate);
            });
          });
    }

    // 과거의 오늘 기능
        $(document).ready(function() {
        var selectedDate = null; // 선택된 날짜 변수 초기화

        // 날짜 클릭 이벤트 처리
        $(".custom_calendar_table").on("click", "td", function () {
          $(".custom_calendar_table .select_day").removeClass("select_day");
          $(this).removeClass("select_day").addClass("select_day");

          // 선택된 날짜 표시
          selectedDate = $(this).text();
          $(".today-box").text(selectedDate);

          // 1년 전의 기록 확인 및 표시
          var oneYearAgo = new Date(nowDate.getFullYear() - 1, nowDate.getMonth(), selectedDate);
          if (hasRecord(oneYearAgo)) {
            var record = getRecord(oneYearAgo); // 1년 전의 기록 가져오기
            $("#secondBox").text(record); // 두 번째 상자에 기록 표시
          } else {
            $("#secondBox").text(""); // 두 번째 상자 초기화
          }
        });

        // 기록이 있는지 확인하는 함수 (임의로 작성)
        function hasRecord(date) {
          // 해당 날짜에 대한 기록이 있는지 확인하는 로직을 구현해야 합니다.
          // 예를 들어, 데이터베이스에 저장된 기록을 조회하는 등의 작업이 필요합니다.
          // 항상 false 반환
          return false;
        }

        // 기록을 가져오는 함수 (임의로 작성)
        function getRecord(date) {
          // 해당 날짜에 대한 기록을 가져오는 로직구현
          // 예를 들어, 데이터베이스에서 해당 날짜의 기록을 조회하는 작업이 필요합니다.
          // 빈 문자열 반환
          return "";
        }
      });

    // 그날의 감정기록 보이기 기능
    $(document).ready(function() {
        var selectedDate = null; // 선택된 날짜 변수 초기화
        var $contentBox = $("#contentBox"); // 내용을 표시할 상자 요소 선택

        // 날짜 클릭 이벤트 처리
        $(".custom_calendar_table").on("click", "td", function () {
          $(".custom_calendar_table .select_day").removeClass("select_day");
          $(this).removeClass("select_day").addClass("select_day");

          // 선택된 날짜 표시
          selectedDate = $(this).text();
          $("#firstBox").text(selectedDate);

          // 내용 가져오기 및 표시
          getContent(selectedDate, function(content) {
            $contentBox.text(content);
            $contentBox.show(); // 상자 표시
          });
        });

        // 내용 가져오기 함수 (임의로 작성)
        function getContent(date, callback) {
          // 해당 날짜에 대한 내용을 가져오는 로직을 구현해야 합니다.
          // 예를 들어, 데이터베이스에서 해당 날짜의 내용을 조회하는 등의 작업이 필요합니다.
          // 이 예시에서는 임의의 내용을 반환하도록 처리했습니다.
          var content = ""; // 가져온 내용을 저장할 변수
          // 데이터를 비동기적으로 가져오는 예시
          setTimeout(function() {
            content = "날짜 " + date + "의 내용입니다.";
            callback(content); // 가져온 내용을 콜백 함수에 전달
          }, 1000); // 1초 후에 가져오는 것을 가정
        }
      });

      $(document).ready(function() {
        // 기존 코드 생략

        // 수정 버튼 클릭 이벤트 처리
        $("#editButton").click(function() {
          // 수정 버튼 클릭 시 동작할 코드를 작성합니다.
          // 예를 들어, 해당 날짜의 일기를 수정하는 팝업을 띄우는 등의 동작을 구현할 수 있습니다.
          // 이 예시에서는 경고창을 띄우는 코드로 대체하였습니다.
          alert("일기 수정 버튼을 클릭했습니다.");
        });

        // 삭제 버튼 클릭 이벤트 처리
        $("#deleteButton").click(function() {
          // 삭제 버튼 클릭 시 동작할 코드를 작성합니다.
          // 예를 들어, 해당 날짜의 일기를 삭제하는 기능을 구현할 수 있습니다.
          // 이 예시에서는 경고창을 띄우는 코드로 대체하였습니다.
          alert("일기 삭제 버튼을 클릭했습니다.");
        });
      });
      //페이지이동

      //월별 감정 통계로 이동
      function avgmove(e){
        var link = 'http://127.0.0.1:5500/front/statistics/monthly_statistic.html';
        location.href=link;
      }
      //홈 화면으로 이동
      function homemove(e){
        var link = 'http://127.0.0.1:5500/front/calender/calender.html';
        location.href=link;
      }
      //로그아웃
      function logout(e){
        var link = 'http://127.0.0.1:5500/front/Sign-inup/Login.html';
        location.href=link;
      }
      //마이페이지
      function mpmove(e){
        var link = 'http://127.0.0.1:5501/front/mypage/MyPage/MyPage.html';
        location.href=link;}