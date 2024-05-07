/** Vue 설정 **/
// 검색
var searchResult = new Vue({
    el: '#search-result',
    data: {
        search_result: {},
    },
});
// 위시리스트
var wishListResult = new Vue({
    el: '#wish-list-result',
    data: {
        wish_list: {},
    },
    filters: {
        // filter로 쓸 filter ID 지정
        yyyyMMddHHmmss: function(value) {
            // 들어오는 value 값이 공백이면 그냥 공백으로 돌려줌
            if(!value) return '';

            // 현재 Date 혹은 DateTime 데이터를 javaScript date 타입화
            var js_date = new Date(value);

            // 연도, 월, 일 추출
            var year = js_date.getFullYear();
            var month = js_date.getMonth() + 1;
            var day = js_date.getDate();
            var hour = js_date.getHours();
            var minute = js_date.getMinutes();
            var second = js_date.getSeconds();

            // 월, 일, 시, 분, 초의 경우 한자리 수 값이 있기 때문에 공백에 0 처리
            if (month < 10)  { month = '0' + month; }
            if (day < 10)    { day = '0' + day; }
            if (hour < 10)   { hour = '0' + hour; }
            if (minute < 10) { minute = '0' + minute; }
            if (second < 10) { second = '0' + second; }

            // 최종 포맷 (ex - '2021-10-08 13:11:12')
            return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        },
    },
    methods: {
        visitCntAdd(idx) {
            visitCountAdd(idx);
        }, delWishList(idx) {
            WishListRemove(idx);
        }
    }
});


/** 검색 결과 조회 **/
function search() {
  // 검색어
  const query = $('#searchBox').val();

  // 실제 backend에 /api/search 요청해서 데이터 가져오기(ajax)
  $.get(`/api/search?searchQuery=${query}`, function(response) {
    console.log('search response값', response);
    searchResult.search_result = response;

    const title = document.getElementById('title');
    title.innerHTML = searchResult.search_result.title.replace(/<[^>]*>?/g, '');

    $('#search-result').show();
  });
};

/** 검색창에서 엔터키를 눌렀을 때 이벤트 **/
$('#searchBox').on("keyup", function(e) {
  if (e.keyCode == 13) {search();}
});
/** 검색 버튼을 눌렀을 때 이벤트 **/
$('#searchButton').click(function() {
  console.log('search btn click');
  search();
});

/** 위시리스트 목록 가져오기 **/
function getWishList() {
    $.get(`/api/wishall`, function(res) {
        console.log('wishall response', res);  // List<WishListVO>
        wishListResult.wish_list = res;

        $('#wish-list-result').show();
    });
}
/** 위시리스트 버튼을 눌렀을 때 이벤트 **/
$('#wishButton').click(function() {
  console.log('wish btn click');
  $.ajax({
      type: 'post',
      url: '/api/wishadd',
      data: JSON.stringify(searchResult.search_result),
      contentType : 'application/json',
      success: function(response, status, xhr) {
          console.log('위시리스트 추가 완료', response);

          getWishList();
      },
      error: function(request, status, error) {
          alert('위시리스트 추가를 실패하였습니다');
      }
  })
});

/** 방문추가 버튼을 눌렀을 때 이벤트 **/
function visitCountAdd(id) {
  console.log('visitCountAdd idx 값: '+ id);
  $.ajax({
      type: 'put',
      url: '/api/visitCountAdd/' + id,
      data: JSON.stringify(searchResult.search_result),
      contentType : 'application/json',
      success: function(response, status, xhr) {
          console.log('방문 횟수 증가 완료', response);
          getWishList();
      },
      error: function(request, status, error) {
          alert('방문 횟수 증가를 실패하였습니다');
      }
  })
}

/** 위시리스트 삭제 버튼을 눌렀을 때 이벤트 **/
function WishListRemove(id) {
  console.log('WishListRemove idx 값: '+ id);
  $.ajax({
      type: 'delete',
      url: '/api/wishRemove/'+ id ,
      data: JSON.stringify(searchResult.search_result),
      contentType : 'application/json',
      success: function(response, status, xhr) {
          console.log('위시리스트 삭제 완료', response);
          alert('위시리스트 삭제를 완료하였습니다');

          getWishList();
      },
      error: function(request, status, error) {
          alert('위시리스트 삭제를 실패하였습니다');
      }
  });
}


/** 처음페이지 로딩 시 호출되는 jquery **/
$(document).ready(function(){
  $('#search-result').hide();
  $('#wish-list-result').hide();

  getWishList();
});






/** 나의 위시리스트를 눌렀을 때 이벤트 **/
//$('#wishList').click(function() {
//  console.log('wishList btn click');
//  getWishList();
//
//  const wishList = document.getElementById('wish-list-result');
//  if(wishList.style.display === 'none') {
//    wishList.style.display = 'block';
//  }else {
//    wishList.style.display = 'none';
//  }
//});