<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');
 

 
class Board_m extends CI_Model {
    function __construct() {
        parent::__construct();
    }



 
	function CountAll() {
		return $this->db->count_all_results('board');
	}

	// 전체 게시물에서 몇번째부터 몇개(페이지당 표시 개수)가져오기

	function GetLimit($limit,$offset) {

		$this->db->from('board')->order_by("idx","desc")->limit($limit,$offset);
		$query = $this->db->get();

	
		if( $query->num_rows() > 0 ){
			return $query->result();
		}
		else{
			return FALSE;
		}
	}

	function GetRows($idx){
		$this->db->from('board')->where('idx',$idx);
		$query = $this->db->get();

		return $query->result_array();

	}
 
}