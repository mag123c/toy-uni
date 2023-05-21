const x_btn = document.querySelector(".x_btn");
const modify_request_btn = document.querySelector(".modify_request_btn");
const modify_info = document.querySelectorAll(".modify_input_con > input");
const arrow = document.querySelector(".modify_arrow");
const pw = document.querySelector("input[name='pw']");
var id;
var before_nn;
var before_email;
var before_phone;
var tf = false;

function setModify(){
	id = document.querySelector("input[name='id']");	
	before_nn = document.querySelector("input[name='nn']");	
	before_email = document.querySelector("input[name='email']");
	before_phone = document.querySelector("input[name='phone']");
	id = id.value;
	before_nn = before_nn.value;
	before_email = before_email.value;
	before_phone = before_phone.value;
}

/* regEXP and validation */
const autoHyphen = (e) => {
	e.value = e.value
			.replace(/[^0-9]/g, '')
			.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}

function regExpCheck(text, type){
	if(text.length==0) return false;
	var regExp = {
		pw : /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/,
		nn : /^[가-힣a-zA-Z0-9]{2,20}$/,
		email :  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
	}
	if(type=="email"){
		return regExp.email.test(text);
	}
	else if(type=="pw"){
		return regExp.pw.test(text);
	}
	else if(type=="pw2"){
		if(pwCheck(pw.value, text)) return true;
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

function pwCheck(value1, value2){
	if(value1===value2) return true;
	else return false;
}

function dbValidation(param){
	$.ajax({
		url : "/users/validation",
		type : "post",
		data : {"param" : param.name, "text" : param.value},
		success : function(data){
			if(data.length > 0){
				param.style.border = "solid 3px red";
				tf = false;
			}
			else {
				param.style.border = "solid 1px #222";
				tf = true;
			}
		}
	})
}
/* regEXP and validation end*/

function modify(info){
	$.ajax({
		url : "/users/"+user_idx.value,
		data : {"id" : id, "pw" : info[1].value, "nn" : info[3].value, "email" : info[4].value, "phone" : info[5].value},
		type : "PUT",
		success : function(){
			alert("수정완료");
			modal("inactive");
			info[1].value="";
			info[2].value="";
		},error : function(err){
			console.log(err);
		}
	})
}

x_btn.addEventListener('click', function(){
	modal("inactive");	
});

modify_request_btn.addEventListener('click', function(){	
	for(var i=1; i<modify_info.length; i++){
		if(!regExpCheck(modify_info[i].value, modify_info[i].name)){
			alert("수정 실패. 확인 후 다시 시도해주세요.");
			return;
		}
		if(i==3 || i==4){
			var text;
			switch(i){			
				case 3: text = before_nn;
				break;
				case 4: text = before_email;
				break;
			}
			
			if(text != modify_info[i].value){
				dbValidation(modify_info[i])
				if(!tf){
					alert("수정 실패. 확인 후 다시 시도해주세요...");
					return;	
				}						
			}				
			
		}			
	}
	modify(modify_info);
});

modify_info[1].addEventListener('blur', function(){
	if(this.value.length == 0){		
		this.style.border = "solid 3px red";
	}
	else if(!regExpCheck(this.value, this.name)){
		this.style.border = "solid 3px red";
	}
	else this.style.border = "solid 1px #222"; 
});

modify_info[2].addEventListener('blur', function(){
	if(this.value.length == 0){		
		this.style.border = "solid 3px red";
	}
	else if(!regExpCheck(this.value, this.name)){
		this.style.border = "solid 3px red";
	}
	else this.style.border = "solid 1px #222"; 
});

modify_info[3].addEventListener('blur', function(){
	if(this.value.length == 0){		
		this.style.border = "solid 3px red";
	}
	else if(!regExpCheck(this.value, this.name)){
		this.style.border = "solid 3px red";
	}
	else if(this.value != before_nn) {
		dbValidation(this, this.value);
	}
	else if(this.value == before_nn) this.style.border = "solid 1px #222";
	else this.style.border = "solid 1px #222"; 
});

modify_info[4].addEventListener('blur', function(){
	if(this.value.length == 0){		
		this.style.border = "solid 3px red";
	}
	else if(!regExpCheck(this.value, this.name)){
		this.style.border = "solid 3px red";
	}
	else if(this.value != before_email) {
		dbValidation(this, this.value);
	}
	else if(this.value == before_email) this.style.border = "solid 1px #222";
	else this.style.border = "solid 1px #222"; 
});
