const detail_x_btn = document.querySelector(".detail_x_btn");
const detail_main_con = document.querySelector(".detail_main_con");
const detail_h_title_con = document.querySelector(".detail_h_title_con");
const detail_img = document.querySelector('.detail_img');
const freind_request_btn = document.querySelector(".freind_request_btn");
const my_nn = document.querySelector(".my_nn");
const my_idx = document.querySelector("input[name='idx']");

var nickname;
var imgsrc;

function modifyModal(status){
	if(status==="activate"){
		activateModifyModal();
	}
	else removeModifyModal();
}

function activateModifyModal(){
	main_con.style.pointerEvents = "none";
	main_con.classList.add("modal");
	right_con.classList.add("modal");
	user_img.classList.add("modal");
	detail_h_title_con.textContent = nickname;
	detail_img.src = imgsrc;
	detail_main_con.className = "detail_main_con show";
}

function removeModifyModal(){
	main_con.classList.remove("modal");
	right_con.classList.remove("modal");
	user_img.classList.remove("modal");
	detail_main_con.className = "detail_main_con hide";
	detail_h_title_con.textContent = "";
	main_con.style.pointerEvents = "auto";
}

function friendReq(req_nn, res_nn){
	$.ajax({
		url : '/users/friends',
		method : 'post',
		data : {"from" : req_nn, "to" : res_nn},
		success : function(msg){
			console.log(msg);
		}
	})
}

chat_con.addEventListener('click', function(e){
	nickname = e.target.parentElement.nextElementSibling.textContent;
	var idx = e.target.parentElement.parentElement.dataset.idx;	
	if(e.target.tagName == "IMG" && my_idx.value != idx) {		
		imgsrc = e.target.src;		
		modifyModal("activate");			
	}
})

detail_x_btn.addEventListener('click', function(){
	modifyModal("inactive");	
});

freind_request_btn.addEventListener('click', function(){
	friendReq(my_nn.textContent, nickname);
})