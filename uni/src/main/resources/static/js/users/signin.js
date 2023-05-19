const signin_btn = document.querySelector('.signin_btn');
const id_input = document.querySelector('input[name="id"]');
const pw_input = document.querySelector('input[name="pw"]');
const balloon = document.querySelector('.balloon');

window.onload = function(){
	if(balloon.textContent.includes("실패")){
		balloon.classList.add("show");
	}
	else if(document.location.href.includes("error")){
		balloon.append("로그인 실패");
		balloon.classList.add("show");
	}
}

function check(id, pw){
	if(id.length==0 || pw.length==0) return false;
	if(id.includes(" ") || pw.includes(" ")) return false;
	else return true;
}

signin_btn.addEventListener('click', function(){
	let id = id_input.value;
	let pw = pw_input.value;
	if(check(id, pw)){
		document.form1.submit();
	}
	else {
		balloon.textContent = "ID와 비밀번호를 확인해주세요";
		balloon.style.display = "block";
	}
})