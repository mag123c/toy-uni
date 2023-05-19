const user_img = document.querySelector(".user_img");
const img_upload = document.querySelector(".img_upload");
const input_file = document.querySelector("input[type='file']");
const filename = document.querySelector("input[name='img']");

window.onload = function(){	
	if(user_img.src.length==0){
		user_img.src = "/img/person.svg";
	}
}

function imgUpload(){
	input_file.click();
}

img_upload.addEventListener('click', function(){
	imgUpload();
});

input_file.addEventListener('change', function(){
	filename.value = this.files[0];	
	document.imgchange.submit();
});