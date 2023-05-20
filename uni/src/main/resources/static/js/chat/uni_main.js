const user_img = document.querySelector(".user_img");
const img_upload = document.querySelector(".img_upload");
const input_file = document.querySelector("input[type='file']");
const filename = document.querySelector("input[name='img']");
const user_idx = document.querySelector("input[name='idx']");

window.onload = function(){	
	if(user_img.src.length==0){
		user_img.src = "/img/person.svg";
	}
}

function imgChange_click(){
	input_file.click();
}

function imgCheck(size, type){
	var msg;
	if(size > 5242880){
		msg = "5MB 이하의 이미지만 사용가능합니다."
	}
	else if(!type.includes("image")){
		msg = "이미지파일을 사용해주세요"; 
	}
	else msg = "성공"
	return msg;
}

function imgUpload(file, idx){
	var formData = new FormData();
	formData.append("imgfile", file);
	
	$.ajax({
		type : "PUT",
		url : "/users/img/"+idx,
		processData : false,
		contentType : false,
		data : formData,
		enctype : 'multipart/form-data',
		success : function(msg){
			if(msg.includes("실패")) alert(msg.split(":")[0]);
			else user_img.src = msg;
		},error : function(err){
			console.log(err);
		}
	})
}

img_upload.addEventListener('click', function(){
	imgChange_click();
});

input_file.addEventListener('change', function(){
	filename.value = this.files[0];	
	var msg = imgCheck(this.files[0].size, this.files[0].type);	
	if(msg === "성공") {
		imgUpload(this.files[0], user_idx.value);
	}
	else alert(msg);
});