const text_con = document.querySelector(".text_con");

window.onload = function(){
	if(location.href.includes("success")){
		text_con.textContent = "회원가입 성공!";
	}
}