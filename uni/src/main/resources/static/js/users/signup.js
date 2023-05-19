const back_btn = document.querySelector('.back_btn');
const signup_btn = document.querySelector('.signup_btn');
const id = document.querySelector('input[name=id]');
const pw = document.querySelector('input[name=pw]');
const pw2 = document.querySelector('input[name=pw2]');
const nn = document.querySelector('input[name=nn]');
const email = document.querySelector('input[name=email]');
const phone = document.querySelector('input[name=phone]');
const msg_div = document.querySelector('.msg');
const input = document.querySelectorAll('.signup_input');
const balloon = document.querySelector('.balloon');

const autoHyphen = (e) => {
	e.value = e.value
			.replace(/[^0-9]/g, '')
			.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}

window.onload = function(){	
	if(balloon.textContent.includes("실패")){
		balloon.classList.add("show");
	}
}

function regExpCheck(text, type){
	var regExp = {
		id : /^[a-zA-Z0-9]{4,20}$/,
		pw : /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/,
		nn : /^[가-힣a-zA-Z0-9]{2,20}$/,
		email :  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
	}
	if(type=="id"){
		return regExp.id.test(text);
	}
	if(type=="email"){
		return regExp.email.test(text);
	}
	else if(type=="pw"){
		return regExp.pw.test(text);
	}
	else if(type=="pw2"){
		if(pwCheck(pw.value, pw2.value)) return true;
		else return false;
	}
	else if(type=="nn"){
		return regExp.nn.test(text);
	}
	else if(type=="phone"){
		if(text.length < 12) return false;
		else return true;
	}
	else return false;
}

function errorMsg(error){	
	var errMsg = {
		notext : "필수 정보입니다.",
		id : "아이디는 4~20자 사이이며, 한글, 특수문자, 공백을 포함할 수 없습니다.",
		pw : "비밀번호는 8~20자 사이의 특수문자, 문자, 숫자로 구성해주세요",
		pw2 : "비밀번호가 서로 일치하지 않습니다",
		email : "이메일 형식을 확인해주세요",
		nn : "닉네임은 2~20자 사이의 특수문자나 공백을 포함할 수 없습니다.",
		phone : "휴대폰 번호를 확인해주세요",			
	}	
	return errMsg[error];
}

function pwCheck(value1, value2){
	if(value1===value2) return true;
	else return false;
}

function errorMsgShow(msg, param){
	param.nextElementSibling.textContent = msg;
}

function errorMsgHide(param){
	param.nextElementSibling.textContent = "";
}

function dbValidation(param, text){
	$.ajax({
		url : "/users/validation",
		type : "post",
		data : {"param" : param.name, "text" : text},
		success : function(data){
			if(data.length > 0){
				param.nextElementSibling.textContent = data;
			}
		}
	})
}

function validation(){
	let chk = true;
	input.forEach(function(ip){
		let msg = "";
		if(ip.value.length==0){
			msg = errorMsg("notext");
			errorMsgShow(msg, ip);
			chk = false;
		}
		else if(!regExpCheck(ip.value, ip.name)){
			console.log(msg, ip);
			msg = errorMsg(ip.name);
			errorMsgShow(msg, ip);
			chk = false;
		}
	})
	if(chk) document.form1.submit();
}

id.addEventListener('blur', function(){
	let msg;	
	if(id.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, id);
	}
	else if(!regExpCheck(id.value, "id")){
		msg = errorMsg("id");		
		errorMsgShow(msg, id);
	}
	else {
		errorMsgHide(id);
		dbValidation(id, id.value);
	}
})

pw.addEventListener('keyup', function(){
	errorMsgHide(pw);
})

pw.addEventListener('blur', function(){
	let msg;
	if(pw.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, pw);
	}
	else if(!regExpCheck(pw.value, "pw")){
		msg = errorMsg("pw");
		errorMsgShow(msg, pw);
	}
	else errorMsgHide(pw);
});

pw2.addEventListener('keyup', function(){
	errorMsgHide(pw2);
})

pw2.addEventListener('blur', function(){
	let msg;
	console.log(pw.value, pw2.value);
	if(pw2.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, pw2);
	}
	else if(pw.value != pw2.value){
		msg = errorMsg("pw2");
		errorMsgShow(msg, pw2);
	}
	else errorMsgHide(pw2);
});

nn.addEventListener('blur', function(){
	let msg;
	if(nn.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, nn);
	}
	else if(!regExpCheck(nn.value, "nn")){
		msg = errorMsg("nn");
		errorMsgShow(msg, nn);
	}
	else {
		errorMsgHide(nn);
		dbValidation(nn, nn.value);
	}
});

email.addEventListener('blur', function(){
	let msg;
	if(email.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, email);
	}
	else if(!regExpCheck(email.value, "email")){
		msg = errorMsg("email");
		errorMsgShow(msg, email);
	}
	else {
		errorMsgHide(email);
		dbValidation(email, email.value);
	}
});

phone.addEventListener('blur', function(){
	let msg;
	if(phone.value.length == 0){
		msg = errorMsg("notext");
		errorMsgShow(msg, phone);
	}
	else if(!regExpCheck(phone.value, "phone")){
		msg = errorMsg("phone");
		errorMsgShow(msg, phone);
	}
	else errorMsgHide(phone);
})
	
back_btn.addEventListener('click', function(){
	location.href="/";
})

signup_btn.addEventListener('click', function(){
	validation();
})