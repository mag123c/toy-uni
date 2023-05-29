const req_img = document.querySelector(".req_img");
const req_nn = document.querySelector(".req_nn");
const req_main_con = document.querySelector(".req_main_con");
const req_x_btn = document.querySelector(".req_x_btn");
const req_ok_btn = document.querySelector(".req_ok_btn");
const req_no_btn = document.querySelector(".req_no_btn");

var f_req_list;
var req_list;
var req_list_idx = 0;
var reqres_sock = null;

window.onload = function(){
	connectFriendReqResWS();
}

function connectFriendReqResWS(){
	var ws = new SockJS("/reqres");
	reqres_sock = ws;
	
	ws.onopen = function(){
		console.log("연결완료");
		ws.send("OPEN" + identifier + user_idx.value);
	}
	
	ws.onmessage = function(e){		
		let msg = e.data;
		if(msg.split(identifier)[0] == "REQUEST"){
			if(msg.includes(" ///// ")) reqMoreThanOne(msg);
			
			else{
				let nn = msg.split(identifier)[1];
				let img = msg.split(identifier)[2];
				if(img == null || img == undefined || img.length < 1) img = "/img/person.svg";
				console.log(img);
	
				req_nn.textContent = "[" + nn + "] 님의 친구 신청입니다.";
				req_img.src = img;
				req_main_con.classList.add("show");
				setTimeout(modalAA(), 500);
			}

		}		
		
	}
	
	ws.onclose = function(){
		console.log("종료");
	}
}

function reqModalOff(){
	req_main_con.className = "req_main_con hide";
	modalII();
}

function reqMoreThanOne(msg){	
	req_list = msg.split(" ///// ");
	f_req_list = req_list.length;
	reqListGet(req_list);
}

function reqListGet(req_list){	
	let nn = req_list[req_list_idx].split(identifier)[1];
	let img = req_list[req_list_idx].split(identifier)[2];
	if(img == null || img == undefined || img.length < 1 || img == "null") img = "/img/person.svg";
	req_nn.textContent = "[" + nn + "] 님의 친구 신청입니다.";
	req_img.src = img;
	req_main_con.classList.add("show");
	modalAA();
	
	f_req_list--;
	req_list_idx++;
}

req_x_btn.addEventListener('click', function(){
	if(f_req_list > 0){
		reqListGet(req_list);
		return;
	}
	reqModalOff();
})

req_ok_btn.addEventListener('click', function(){
	if(f_req_list > 0){
		reqListGet(req_list);
		return;
	}	
	reqModalOff();
})

req_no_btn.addEventListener('click', function(){
	if(f_req_list > 0){
		reqListGet(req_list);
		return;
	}	
	reqModalOff();
})