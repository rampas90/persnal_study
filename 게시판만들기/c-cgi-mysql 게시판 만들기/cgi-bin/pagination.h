int page, page_num, limit;
int block, block_start_page, block_end_page, total_page, total_block;
int query_stat;
int list = 10;
int block_page_num_list = 5;	
int total_count = 0;
int i, j ,k;		// 반복문에 사용할 int 변수

char search_query[MAX_LEN];


void pagingInit(page, list, block_page_num_list, total_count){

	if(&page == NULL){
		page_num = 1;
	}else{
		page_num = page;
	}
	block = ceil((float)page_num/block_page_num_list);
	if(block < 1){
		block = 1;
	}
	block_start_page = ( (block - 1) * block_page_num_list )+1;
	block_end_page = (block_start_page + block_page_num_list) -1;
	total_page = ceil( (float)total_count/list );
	total_block = ceil( (float)total_page/block_page_num_list );
	if( block_end_page > total_page ){
		block_end_page = total_page;
	}
	limit = (page -1)*list;
}

void pagingData(){
	// 처음으로 가기 버튼
	if(page_num <= 1){
		printf("		<li class='disabled'> \n");
		printf("			<a href='#' aria-label='Previous'> \n");
		printf("				<span aria-hidden='true'>처음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=&list=%d%s' aria-label='Previous'> \n", SITE_URL,  list, search_query);
		printf("				<span aria-hidden='true'>처음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}

	// 이전 블록으로 이동
	if(block <= 1){
		
	}else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Previous'> \n", SITE_URL, block_start_page-1, list, search_query);
		printf("				<span aria-hidden='true'>이전</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}

	// 페이징 출력 부분
	for( j=block_start_page; j <= block_end_page; j++ ){
		if(page_num == j) {
			printf("<li class='active'><a href='%s/list.cgi?page=%d&list=%d%s '>%d</a></li> \n", SITE_URL, j, list, search_query, j );
		}else{
			printf("<li><a href='%s/list.cgi?page=%d&list=%d%s'>%d</a></li> \n", SITE_URL, j, list, search_query, j );
		}
	}
	

	// 다음 블록으로 이동
	if( block >= total_block ){	}

	else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Next'> \n", SITE_URL, block_end_page+1, list, search_query);
		printf("				<span aria-hidden='true'>다음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
	
	// 마지막으로 가기 버튼
	if(page_num >= total_page){
		printf("		<li class='disabled'> \n");
		printf("			<a href='' aria-label='Next'> \n");
		printf("				<span aria-hidden='true'>마지막</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
	else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Next'> \n", SITE_URL, total_page, list, search_query);
		printf("				<span aria-hidden='true'>마지막</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
}