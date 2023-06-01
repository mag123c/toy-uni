const req_img = document.querySelector(".req_img");
const req_nn = document.querySelector(".req_nn");
const req_main_con = document.querySelector(".req_main_con");
const req_ok_btn = document.querySelector(".req_ok_btn");
const req_no_btn = document.querySelector(".req_no_btn");


window.onload = function(){
	friendReqCheck();
}

function friendReqCheck(){
	$.ajax({
		url : "/users/friends",
		method : 'get',
		data : {"idx" : user_idx.value},
		success : function(data){
			reqListGet();
		}
	})
}

function reqModalOff(){
	req_main_con.className = "req_main_con hide";
	modalII();
}

function reqListGet(){	
	req_main_con.classList.add("show");
	modalAA();

}

req_ok_btn.addEventListener('click', function(){
	reqModalOff();
})

req_no_btn.addEventListener('click', function(){
	reqModalOff();
})