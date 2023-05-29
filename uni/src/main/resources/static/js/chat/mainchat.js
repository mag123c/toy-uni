const enter_btn = document.querySelector(".enter_btn");
const send_btn = document.querySelector(".send_btn");
const chat_con = document.querySelector(".chat_con");
const textarea = document.querySelector(".textarea");
const identifier = " /:/ ";
const cur_nn = document.querySelector(".my_nn");
var sock = null;


function connectMainChatWS(){
	var ws = new SockJS("/mainchatting");
	sock = ws;
	
	ws.onopen = function(){
		console.log("연결완료");
		ws.send("OPEN" + identifier + cur_nn.textContent.split(" ")[0]);
	}
	
	ws.onmessage = function(e){		
		let msg = e.data;
		console.log(msg);
		if(msg.split(identifier)[0] == "OPEN"){
			userOpenClose(msg);
			overflowScroll();
		}
		
		else if(msg.split(identifier)[0] == "SENDMSG"){
			userSendMsg(msg);
			overflowScroll();
		}
		
		else if(msg.split(identifier)[0] == "CLOSE"){
			userOpenClose(msg);
			overflowScroll();
		}
	}
	
	ws.onclose = function(){
		console.log("종료");
	}
}

function overflowScroll(){
	chat_con.scrollTop = chat_con.scrollHeight;
}

function enter(){
	connectMainChatWS();
}

function close(){
	sock.send("CLOSE" + identifier + cur_nn.textContent.split(" ")[0]);
	if(sock!=null) sock.close();
	chat_con.replaceChildren();	
}

function userOpenClose(msg){
	let div = document.createElement("div");
	let span = document.createElement("span");
	div.className = "chatting_con";
	div.classList.add("openclose");
	div.style.display = "block";
	span.textContent = msg.split(identifier)[1];
	div.append(span);
	chat_con.append(div);
}

function sendMsg(msg){
	if(sock!=null) sock.send(msg);
}

function userSendMsg(msg){
	let div1 = document.createElement("div");
	let div2 = document.createElement("div");
	let div3 = document.createElement("div");
	let div4 = document.createElement("div");
	let span_nn = document.createElement("span");
	let span_text = document.createElement("span");
	let img = document.createElement("img");
	div1.className = "chatting_con";
	div2.className = "chatting_img_con";
	div3.className = "chatting_nn_con";
	div4.className = "chatting_text_con";
	span_nn.textContent = msg.split(identifier)[1];
	span_text.textContent = msg.split(identifier)[2];
	img.src = msg.split(identifier)[3] == "null" ? "/img/person.svg" : msg.split(identifier)[3];	
	div1.dataset.idx = msg.split(identifier)[4];
		
	div2.append(img);
	div3.append(span_nn);
	div4.append(span_text);
	div1.append(div2);
	div1.append(div3);
	div1.append(div4);
	chat_con.append(div1);	
}

enter_btn.addEventListener('click', function(){
	enter_btn.classList.toggle("entering");
	if(enter_btn.className.includes("entering")){
		enter_btn.textContent = "나가기";
		enter();
	}
	else {
		enter_btn.textContent = "채팅방 입장하기";
		close();
	}
	send_btn.classList.toggle("show");
	textarea.classList.toggle("show");
});

send_btn.addEventListener('click', function(){
	console.log(textarea.value);
	if(textarea.value=="" || textarea.value.length==0) {
		alert("메세지를 입력한 후 전송버튼을 클릭해주세요");
		return;
	}
	else if(textarea.value.length > 100){
		alert("너무 많은 메세지를 입력하였습니다.");
		textarea.value = "";
		return;
	}
	let send_msg = "SENDMSG" + identifier + my_nn.textContent.split(" ")[0] + identifier + textarea.value;
	sendMsg(send_msg);
	textarea.value = "";
})