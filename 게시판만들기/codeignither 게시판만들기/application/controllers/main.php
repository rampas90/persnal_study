<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');


	class Main extends CI_Controller{
		
		function __construct(){
			parent::__construct();
			$this->load->database();
			$this->load->model('board_m');
			$this->load->helper('url');
		}

	

		public function index() {

			$this->load->model('board_m');
				
			$total_rows = $this->board_m->CountAll();   //총 레코드수
			$per_page = 5;								//한페이지에 몇개의 글을 보여줄지 정ㅇ의
			$page_num = $this->uri->segment(3,1);       // 현재 페이지 세그먼트 두번째인자는 디폴트값
			$b_page_num_list = 5;	                    // 블럭에 나타낼 페이지 번호 갯수
			$block = ceil($page_num/$b_page_num_list);
			$b_start_page = ( ( $block-1 ) * $b_page_num_list)+1; // 현재 블럭에서 시작페이지 번호
			$b_end_page = $b_start_page + $b_page_num_list - 1; // 현재 블럭에서 끝페이지 번호
			$total_page = ceil($total_rows/$per_page);
			
			if($b_end_page > $total_page)
				$b_end_page = $total_page;

			$prev_page = $page_num-1;
			$next_page = $page_num+1;

			if($prev_page<1){
				$prev_page = 1;
			}

			if($next_page > $total_page){
				$next_page = $total_page;
			}

			$limit = ($page_num-1) * $per_page;
			
			$data['total_rows'] = $total_rows;
			$data['per_page'] = $per_page;
			$data['page_num'] = $page_num;
			$data['b_page_num_list'] = $b_page_num_list;
			$data['block'] = $block;
			$data['b_start_page'] = $b_start_page;
			$data['b_end_page'] = $b_end_page;
			$data['total_page'] = $total_page;
			$data['prev_page'] = $prev_page;
			$data['next_page'] = $next_page;
			$data['limit'] = $limit;

			
			$data['result'] = $this->board_m->GetLimit( $per_page , $limit ) ;

			$config1 = $this->uri->segment(3);


			$this->load->view('main',$data);

		}

		public function _remap($method) {

			$this->load->view('header');
	 
			if (method_exists($this, $method)) {
				$this -> {"{$method}"}();
			}
	 
			$this->load->view('footer');
		}

		public function modify() {
			$this->load->model('board_m');
			$page_idx = $this->uri->segment(3);
			
			$data['modify_rows'] = $this->board_m->GetRows($page_idx);
			$data['mode'] = "modify";
			
			$this->load->view('view',$data);
		}


		public function write() {
			$this->load->model('board_m');

			$data['mode'] = "write";
			
			$this->load->view('view',$data);
		}


		public function action(){

			$this->load->model('board_m');
			$mode = $_POST['mode'];

			if($mode == 'write'){
				$cur_date = date('Y-m-d');
				$this->db->set('title', $_POST['title']);
				$this->db->set('contents', $_POST['contents']);
				$this->db->set('register_date', $cur_date);
				$this->db->set('password', $_POST['pw']);
				$this->db->insert('board');
				//$this->load->view('action');
				echo '<script>alert("글이 작성되었습니다.")</script>';
				echo '<script>window.location="/index.php/main/"</script>';
			}
			else if($mode == 'modify'){

				$get_idx = $_POST['idx'];
				$get_pw = $_POST['pw'];

				$del_rows = $this->board_m->GetRows($get_idx);
				$db_pw = $del_rows[0]['password'];

				if($get_pw == $db_pw){
					
					$this->db->set('title', $_POST['title']);
					$this->db->set('contents', $_POST['contents']);
					$this->db->where('idx',$get_idx);
					$this->db->update('board');

					echo '<script>alert("글이 수정 되었습니다.");</script>';
					echo '<script>window.location="/index.php/main/modify/'.$get_idx.'"</script>';

				}else{
					echo '<script>alert("비밀번호를 확인해주세요");</script>';
					echo '<script>window.location="/index.php/main/modify/'.$get_idx.'"</script>';
				}
			}

			else if($mode == 'delete'){
				//print_r($_POST);
				$get_idx = $_POST['idx'];
				$get_pw = $_POST['pw'];

				$del_rows = $this->board_m->GetRows($get_idx);
				$db_pw = $del_rows[0]['password'];


				if($get_pw == $db_pw ){

					$this->db->delete('board',array('idx' => $get_idx));
					echo '<script>alert("글이 삭제되었습니다.");</script>';
					echo '<script>window.location="/index.php/main/"</script>';

				}else{
					echo '<script>alert("비밀번호를 확인해주세요");</script>';
					echo '<script>window.location="/index.php/main/modify/'.$get_idx.'"</script>';
				}


				
			}
			else{
				$this->load->view('main');
			}

		}

	}
?>