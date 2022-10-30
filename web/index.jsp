<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%

%>
<!DOCTYPE html>
<html>
<head>
  <style>
    #customers {
      font-family: Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    #customers td, #customers th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    #customers tr:nth-child(even){background-color: #f2f2f2;}

    #customers tr:hover {background-color: #ddd;}

    #customers th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: center;
      background-color: #04AA6D;
      color: white;
    }
  </style>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width", initial-scale="1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.12.1/datatables.min.css"/>
  <title>JSP wifi</title>
</head>
<body>
<div class="row">
  <div class="col-md-12">
    <h2>와이파이 정보 구하기</h2>
  </div>
  <div class="col-md-12">
    <div class="ml-1">
      <a href="index.jsp" class="ml-4">홈</a>
      <a href="historyView.jsp" class="ml-4">위치 히스토리목록</a>
      <a href="wifiGet.jsp" class="ml-4">Open API 와이파이 정보 가져오기</a><br>
    </div>
  </div>
  <div class="col-md-12">


      <label for="lat" class="ml-4">LAT:</label><input name="lat" type="text" class="ml-4" id="lat">
      <label for="lnt" class="ml-4">LNT:</label><input name="lnt" type="text" class="ml-4" id="lnt">
      <button id="currentGeoLocation" class="btn btn-primary">내 위치 가져오기</button>
      <button class="btn btn-primary" id="search" >근처 WIFI 정보 보기</button>

  </div>
</div>
<div class="row p-3">
  <table id="customers" class="table table-striped col-md-12" style="text-align: center; border: 1px solid #dddddd; width:100%;">
    <thead>
    <tr>
      <th class="bg-success" style="text-align:center;">거리(Km)</th>
      <th class="bg-success" style="text-align:center;">관리번호</th>
      <th class="bg-success" style="text-align:center;">자치구</th>
      <th class="bg-success" style="text-align:center;">와이파이</th>
      <th class="bg-success" style="text-align:center;">도로명주소</th>
      <th class="bg-success" style="text-align:center;">상세주소</th>
      <th class="bg-success" style="text-align:center;">설치위치</th>
      <th class="bg-success" style="text-align:center;">설치유형</th>
      <th class="bg-success" style="text-align:center;">설치기관</th>
      <th class="bg-success" style="text-align:center;">서비스구분</th>
      <th class="bg-success" style="text-align:center;">망종류</th>
      <th class="bg-success" style="text-align:center;">설치년도</th>
      <th class="bg-success" style="text-align:center;">실내외구분</th>
      <th class="bg-success" style="text-align:center;">와이파이접속환경</th>
      <th class="bg-success" style="text-align:center;">X좌표</th>
      <th class="bg-success" style="text-align:center;">Y좌표</th>
      <th class="bg-success" style="text-align:center;">작업일자</th>
    </tr>
    </thead>


  </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>



<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.12.1/datatables.min.js"></script>

<script>
  var lang_kor = {
    "decimal" : "",
    "emptyTable" : "데이터가 없습니다.",
    "info" : "_START_-_END_(총_TOTAL_개)",
    "infoEmpty" : "0개",
    "infoFiltered" : "(전체_MAX_개 중 검색결과)",
    "infoPostFix" : "",
    "thousands" : ",",
    "lengthMenu" : "_MENU_개씩보기",
    "loadingRecords" : "로딩중...",
    "processing" : "처리중...",
    "search" : "검색:",
    "zeroRecords" : "검색된 데이터가 없습니다.",
    "paginate" : {
      "first" : "첫페이지",
      "last" : "마지막페이지",
      "next" : "다음",
      "previous" : "이전"
    },
    "aria" : {
      "sortAscending" : " : 오름차순 정렬",
      "sortDescending" : " :내림차순 정렬"
    }
  };

    $("#currentGeoLocation").click(function(){
      navigator.geolocation.getCurrentPosition(function(pos) {
        var latitude = pos.coords.latitude;
        var longitude = pos.coords.longitude;

        $('input[name=lat]').attr('value',latitude);
        $('input[name=lnt]').attr('value',longitude);
      });
    });

    $("#search").click(function() {
        let lat = $('input[name=lat]').val();
        let lnt = $('input[name=lnt]').val();

        $.ajax({
          url:"/insertHistory",
          data:{"lat":lat,"lnt":lnt},
          dataType:"text",
          success:function(result){
            if(result == 1){
              $.ajax({
                url:"/getList",
                data:{"lat":lat,"lnt":lnt},
                dataType:"json",
                success:function(result){


                  $("#customers").DataTable({
                      language : lang_kor,
                      select:false,
                      destroy:true,
                      data: result,
                      order: [[0, 'asc']],
                      lengthChange:false,
                      pageLength:20,
                      paging:true,
                      columns:[
                        {data:'distance'},
                        {data:'X_SWIFI_MGR_NO'},
                        {data:'X_SWIFI_WRDOFC'},
                        {data:'X_SWIFI_MAIN_NM'},
                        {data:'X_SWIFI_ADRES1'},
                        {data:'X_SWIFI_ADRES2'},
                        {data:'X_SWIFI_INSTL_FLOOR'},
                        {data:'X_SWIFI_INSTL_TY'},
                        {data:'X_SWIFI_INSTL_MBY'},
                        {data:'X_SWIFI_SVC_SE'},
                        {data:'X_SWIFI_CMCWR'},
                        {data:'X_SWIFI_CNSTC_YEAR'},
                        {data:'X_SWIFI_INOUT_DOOR'},
                        {data:'X_SWIFI_REMARS3'},
                        {data:'LAT'},
                        {data:'LNT'},
                        {data:'WORK_DTTM'},
                      ]
                 });

                  $("#customers_wrapper").addClass("col-md-12");
                  $("#customers_wrapper").addClass("p-0");
                  $(".pagination").hide();
                }
              });
            }
          }
        });
    });

</script>
<script>

  $(document).ready(function(){
    $("#customers").DataTable({
      language : lang_kor
    });
    $("#customers_wrapper").addClass("col-md-12");
    $("#customers_wrapper").addClass("p-0");
  });

</script>
</body>
</html>
