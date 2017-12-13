
<?php

/*
echo "총 게시물 - total_rows : ".$total_rows."</br>";
echo "페이지 리스트 노출 갯수 - b_page_num_list : ".$b_page_num_list."</br>";
echo "현재 페이지 번호 - page_num : ".$page_num."</br>";
echo "블록 - block : ".$block."</br>";
echo "블록 시작 페이지 - b_start_page : ".$b_start_page."</br>";
echo "블록 끝 페이지 - b_end_page : ".$b_end_page."</br>";
echo "총 페이지 - total_page : ".$total_page."</br>";
echo "이전페이지번호 - prev_page : ".$prev_page."</br>";
echo "다음페이지번호 - next_page : ".$next_page."</br>";
echo "리미트 - limit : ".$limit."</br>";
echo "오프셋 - per_page : ".$per_page."</br>";
*/

//print_r($result);
?>

<body>
	<div class="container">
		<h2>CI board List</h2>
		<div class="row">
			<div class="col-md-10">
				<table class="table table-bordered table-hover" style="margin-bottom: 0;" >
					<tr>
						<th class="idx tx_c">번호</th>
						<th class="title">제목</th>
						<th class="date">작성일</th>
					</tr>
					<?php
						foreach($result as $row){
					?>
					<tr>
						<td class="tx_c"><?=$row->idx?></td>
						<td><a href="/index.php/main/modify/<?=$row->idx?>"><?=$row->title?></a></td>
						<td class=""><?=$row->register_date?></td>
					</tr>
					<?php
						}
					?>

				</table>
				<?php
					//echo $page_links
				?>
			
			<nav style="text-align:center;height:55px;">
				<ul class="pagination">

					<li>
						<a href="/index.php/main/index/1">
							<span>처음</span>
						</a>
					</li>

					<li>
						<a href="/index.php/main/index/<?=$prev_page?>" aria-label="Previous">
							<span aria-hidden="true">이전</span>
						</a>
					</li>
					<?php
						for($j=$b_start_page; $j <= $b_end_page; $j++){
							if($page_num == $j){
					?>
								<li class="active">
									<a href="#">
										<span><?=$j?></span>
									</a>
								</li>		
					<?php
							}
							else{
					?>
								<li class="">
									<a href="/index.php/main/index/<?=$j?>">
										<span><?=$j?></span>
									</a>
								</li>		
					<?php
							}
						}
					?>

					<li>
						<a href="/index.php/main/index/<?=$next_page?>" aria-label="Next">
							<span aria-hidden="true">다음</span>
						</a>
					</li>
					<li>
						<a href="/index.php/main/index/<?=$total_page?>">
							<span>마지막</span>
						</a>
					</li>
				</ul>
			</nav>
			<!-- <button type="button" class="btn btn-default " id="paging-btn" >?</button> -->
			

			<a href="/index.php/main/write" class="btn btn-default pull-right ">글쓰기</a>



		</div>
	</div>



	  

   
