<?php
class Speech_search_model extends CI_Model
{
	private $search; 	// variable for holding user speech input
	function __construct() //constructor call
	{
		$this->search= $this->input->post('search');	//get speech input and stores it in a variable
	}
	
	function get_search_result() 
	{
		$result = $this->db->query("SELECT keyword,description FROM engine where keyword like '$this->search%' "); //stores query result in avariable
		foreach($result->result() as $row ) 	//get each record
		{
			return $row; 	//return record
		}
	}
	
	function validate($data)
	{
		$search_result="";
		if($data) 	//checks if data is found in the database
		{
			$search_result="<h1>".$data->keyword."</h1>";
			$search_result.= $data->description;	//stores data retrieved from the database
		}
		else
		{
			$search_result= "<br><br><span style='color:red'>Result not found</span>" ; 	// stores result not found message
		}
		return $search_result;	//return search result
	}
}
?>