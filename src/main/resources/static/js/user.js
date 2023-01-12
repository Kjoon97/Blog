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
         var data = {
               username: $("#username").val(),
               password: $("#password").val(),
               email: $("#email").val()
         };
         console.log(data)

         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "POST",
            url: "/auth/join",
            data: JSON.stringify(data),                     //http body데이터
            contentType: "application/json; charset=utf-8", //body 데이터의 타입
         }).done(function(resp){
            if(resp.statusCode == 400){
                //alert("회원 가입 입력 정보를 다시 확인해주십시오")
                if(resp.data.hasOwnProperty('valid_username')){
                    $('#valid_username').text(resp.data.valid_username);
                    $('#valid_username').css('color', 'red');
                }
                else $('#valid_username').text('');

                if(resp.data.hasOwnProperty('valid_password')){
                	$('#valid_password').text(resp.data.valid_password);
                	$('#valid_password').css('color', 'red');
                }
                else $('#valid_password').text('');

                if(resp.data.hasOwnProperty('valid_email')){
                	$('#valid_email').text(resp.data.valid_email);
                	$('#valid_email').css('color', 'red');
                }
                else $('#valid_email').text('');
            }
            else if (resp.statusCode==500 && resp.data=="유저네임중복오류"){
                $('#valid_username').text("이미 사용중인 유저 네임입니다.");
                $('#valid_username').css('color', 'red');
            }
            else{
            //alert(resp);   //컨트롤러 리턴 값 resp에 저장됨.
                alert("회원 가입이 완료 되었습니다.");
                location.href ="/";
            }
         }).fail(function(error){
            alert(JSON.stringify(error));
         });    //ajax 통신으로 3개의 데이터 json으로 변환 후 insert 요청.
     },

     update: function(){
         //alert('user의 update 함수 호출')
         var data = {
               id: $("#id").val(),
               username: $("#username").val(),
               password: $("#password").val(),
               email: $("#email").val()
         };
         console.log(data)

         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),                     //http body데이터
            contentType: "application/json; charset=utf-8", //body 데이터의 타입
         }).done(function(resp){
            if(resp.statusCode == 400){
                //alert("회원 가입 입력 정보를 다시 확인해주십시오")
                if(resp.data.hasOwnProperty('valid_password')){
                	$('#valid_password').text(resp.data.valid_password);
                	$('#valid_password').css('color', 'red');
                }
                else $('#valid_password').text('');

                if(resp.data.hasOwnProperty('valid_email')){
                	$('#valid_email').text(resp.data.valid_email);
                	$('#valid_email').css('color', 'red');
                }
                else $('#valid_email').text('');
            }
            else{
            //alert(resp);   //컨트롤러 리턴 값 resp에 저장됨.
                alert("회원 수정이 완료 되었습니다.");
                location.href ="/user/updateForm";
            }
         }).fail(function(error){
            alert(JSON.stringify(error));
         });    //ajax 통신으로 3개의 데이터 json으로 변환 후 insert 요청.
     }
}

index.init();