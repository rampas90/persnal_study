	<script>
		function move_back(){
			window.history.back();	
		}

		function move_del(){
			var form = document.board;
			form.mode.value = "delete";
			
			if(form.pw.value == ""){
				alert('비밀번호를 입력해주세요');
				form.pw.focus();
				return false;
			}
			form.submit();
		}


		function check(){	//modify
			var form = document.board;
			form.mode.value = "modify";

			if(form.title.value == ""){
				alert('제목을 입력해주세요');
				form.title.focus();
				return false;
			}

			if(form.title.value.length > 25){
				alert('제목은 25자 미만으로 작성해주세요');
				form.title.focus();
				return false;
			}
			
			if(form.contents.value == ""){
				alert('내용을 입력해주세요');
				form.contents.focus();
				return false;
			}

			if(form.pw.value == ""){
				alert('비밀번호를 입력해주세요');
				form.pw.focus();
				return false;
			}		


			return true;
		}

		function check2(){	//write
			var form = document.board;
			form.mode.value = "write";

			if(form.title.value == ""){
				alert('제목을 입력해주세요');
				form.title.focus();
				return false;
			}

			if(form.title.value.length > 25){
				alert('제목은 25자 미만으로 작성해주세요');
				form.title.focus();
				return false;
			}
			
			if(form.contents.value == ""){
				alert('내용을 입력해주세요');
				form.contents.focus();
				return false;
			}

			if(form.pw.value == ""){
				alert('비밀번호를 입력해주세요');
				form.pw.focus();
				return false;
			}		


			return true;
		}


	</script>	

	</body>
</html>

