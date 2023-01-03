let index ={
     init: function(){
         $("#btn-save").on("click",()=>{   //'글 작성' 버튼 클릭하면 해당 함수 호출 됨.
             this.save();
         });
          $("#btn-delete").on("click",()=>{   //'글 삭제' 버튼 클릭하면 해당 함수 호출 됨.
              this.deleteById();
          });
           $("#btn-update").on("click",()=>{   //'글 수정' 버튼 클릭하면 해당 함수 호출 됨.
               this.update();
           });
           $("#btn-reply-save").on("click",()=>{   //'댓글 등록' 버튼 클릭하면 해당 함수 호출 됨.
               this.replySave();
           });
           $("#likeImg").on("click",()=>{   //'좋아요' 버튼 클릭하면 해당 함수 호출 됨.
               this.boardLike();
           });
     },

     save: function(){
         //alert('user의 save 함수 호출')
         let data = {
               title: $("#title").val(),
               content: $("#content").val()
         };
         //console.log(data)

         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "POST",
            url: "/api/board",      // 컨트롤러 /api/board 로 데이터 전송.
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
         }).done(function(resp){
            alert("글 작성이 완료 되었습니다.");
            location.href ="/";
         }).fail(function(error){
            alert(JSON.stringify(error));
         });
     },

     deleteById: function(){
              var id = $("#id").text();     //detail.html에서의 ${board.id} 값 가져옴.
              //ajax 호출 default가 비동기 호출.
              $.ajax({
                 type: "DELETE",
                 url: "/api/board/"+id,
                 dataType: "json"
              }).done(function(resp){
                 alert("글 삭제가 완료 되었습니다.");
                 location.href ="/";
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
          },

     update: function(){
              let id = $("#id").val();
              let data = {
                    title: $("#title").val(),
                    content: $("#content").val()
              };
              //console.log(data)

              //ajax 호출 default가 비동기 호출.
              $.ajax({
                 type: "PUT",
                 url: "/api/board/"+id,      // 컨트롤러 /api/board 로 데이터 전송.
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
                 alert("글 수정이 완료 되었습니다.");
                 location.href ="/";
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
          },

     replySave: function(){
              let data = {
                    userId: $("#userId").val(),
                    content: $("#reply-content").val(),
                    boardId: $("#boardId").val()
              };

              //console.log(data)

              $.ajax({
                 type: "POST",
                 url: `/api/board/${data.boardId}/reply`,
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
                 alert("댓글 작성이 완료 되었습니다.");
                 location.href =`/board/${data.boardId}`;
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
          },

     replyDelete: function(boardId, replyId){
          $.ajax({
                    type: "DELETE",
                    url: `/api/board/${boardId}/reply/${replyId}`
         }).done(function(resp){
                    alert("댓글이 삭제 되었습니다.");
                    location.href =`/board/${boardId}`;
         }).fail(function(error){
                    alert(JSON.stringify(error));
         });
     },

     boardLike: function(){

              //alert('좋아요 함수 호출')
              let data = {
                    userId: $("#userId").val(),
                    boardId: $("#boardId").val()
              };
              console.log(data)

              //ajax 호출 default가 비동기 호출.
              $.ajax({
                 type: "POST",
                 url: `/api/board/${data.boardId}/like`,      // 컨트롤러 /api/board 로 데이터 전송.
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
                 location.reload();
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });

     }
}
const clickLikeUrl = "/img/like_click.png";
const emptyLikeUrl = "/img/like_empty.png";
// 현재 로그인한 유저가 해당 게시물을 좋아요 했다면 likeVal = true,
// 좋아요하지 않았다면 false
let likeVal = $('#like-check').val(); // 데이터가 있으면 true
const likeImg = $('#likeImg');

console.log("likeVal : " + likeVal);

if(likeVal === 'true'){
    // 데이터가 존재하면 화면에 채워진 하트 보여줌
    $('#likeImg').attr("src", clickLikeUrl);
} else if(likeVal === 'false'){
     // 데이터가 없으면 화면에 빈 하트 보여줌
    $('#likeImg').attr("src", emptyLikeUrl);
}

index.init();