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
                    content: $("#reply-content").val()
              };
              let boardId = $("#boardId").val();
              //console.log(data)

              $.ajax({
                 type: "POST",
                 url: `/api/board/${boardId}/reply`,     
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
                 alert("댓글 작성이 완료 되었습니다.");
                 location.href =`/board/${boardId}`;
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
          },
}

index.init();