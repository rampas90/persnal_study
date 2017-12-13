<?php
	//echo 'action page';
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>action</title>

</head>
<body>



<?php
	//print_r($_POST);
?>

<script>
	function check(){
		var form = document.modify;

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