<?php

	if($mode == 'modify'){

		$view_datas = $modify_rows[0];

		foreach($view_datas as $key => $val){
			${$key} = $val;
		}

?>
<div class="container">
	<div class="row">
		<h2><a href="/index.php">CI board </a></h2>
		<form action="/index.php/main/action" method="POST" name="board" id="board" onsubmit="return check()">
			<input type="hidden" name="mode" value="<?=$mode?>">
			<input type="hidden" name="idx" value="<?=$idx?>">
			<table class="table table-bordered">
				<tr>
					<td>
						<label for="title">제목</label>
					</td>
					<td>
						<input type="text" name="title" id="title" class="form-control" value="<?=$title?>">
					</td>
					
				</tr>

				<tr>
					<td>
						<label for="contents">내용</label>
					</td>
					<td>
						<textarea name="contents" id="contents" class="form-control" cols="50" rows="10"><?=$contents?></textarea>
					</td>
				</tr>

				<tr>
					<td>
						<label for="pw">비밀번호</label>
					</td>
					<td>
						<input type="password" name="pw" id="pw" class="form-control" value="">
					</td>
				</tr>
				
			</table>

			<div class="btn_grp">
				<a onclick="move_back();" class="btn btn-default" value="뒤로가기">뒤로가기</a>
				
				<a href="#" onclick="move_del();" class="btn btn-default" value="삭제">삭제</a>
				<input type="submit" class="btn btn-default" value="수정">
			</div>

		</form>
	</div>
</div>

<?php
	}

	else if($mode == 'write'){
?>
		
<div class="container">
	<div class="row">
		<h2><a href="/index.php">CI board <?=$mode?></a></h2>
		<form action="/index.php/main/action" method="POST" name="board" id="board" onsubmit="return check2()">
			<input type="hidden" name="mode" value="<?=$mode?>">
			<table class="table table-bordered"> 
				<tr>
					<td>
						<label for="title">제목</label>
					</td>
					<td>
						<input type="text" name="title" class="form-control" id="">
					</td>
					
				</tr>

				<tr>
					<td>
						<label for="contents">내용</label>
					</td>
					<td>
						<textarea name="contents" id="contents" class="form-control" cols="50" rows="10"></textarea>
					</td>
				</tr>

				<tr>
					<td>
						<label for="pw">비밀번호</label>
					</td>
					<td>
						<input type="password" name="pw" id="pw" class="form-control" >
					</td>
				</tr>
	
			</table>
			<div class="btn_grp">
				<a onclick="move_back();" class="btn btn-default" value="뒤로가기">뒤로가기</a>
				<input type="submit" class="btn btn-default" value="작성">
			</div>
		</form>
	</div>
</div>
<?php
	}
?>