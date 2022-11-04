let index ={
     init: function(){
         $("#btn-save").on("click",()=>{   //'글 작성' 버튼 클릭하면 해당 함수 호출 됨.
             this.save();
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
     }
}

index.init();