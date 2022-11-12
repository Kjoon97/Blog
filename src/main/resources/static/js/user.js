let index ={
     init: function(){
         $("#btn-save").on("click",()=>{   //'회원가입' 버튼 클릭하면 해당 함수 호출 됨.
             this.save();
         });
         $("#btn-update").on("click",()=>{   //'회원수정' 버튼 클릭하면 해당 함수 호출 됨.
              this.update();
         });
     },

     save: function(){
         //alert('user의 save 함수 호출')
         let data = {
               username: $("#username").val(),
               password: $("#password").val(),
               email: $("#email").val()
         };
         //console.log(data)

         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "POST",
            url: "/auth/join",
            data: JSON.stringify(data),                     //http body데이터
            contentType: "application/json; charset=utf-8", //body 데이터의 타입
            dataType: "json"                                //서버로부터의 응답을 json으로 받고 js로 변환
         }).done(function(resp){
            //alert(resp);   //컨트롤러 리턴 값 resp에 저장됨.
            alert("회원 가입이 완료 되었습니다.");
            location.href ="/";
         }).fail(function(error){
            alert(JSON.stringify(error));
         });    //ajax 통신으로 3개의 데이터 json으로 변환 후 insert 요청.
     },

      update: function(){
              let data = {
                    id: $("#id").val(),
                    username: $("#username").val(),
                    password: $("#password").val(),
                    email: $("#email").val()
              };

              $.ajax({
                 type: "PUT",
                 url: "/user",
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
                 alert("회원 수정이 완료 되었습니다.");
                 location.href ="/";
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
          }
}

index.init();