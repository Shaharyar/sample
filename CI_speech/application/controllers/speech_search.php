<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Speech_search extends CI_Controller
{
	function index() //default method that loads when user browse to CI_speech
	{
		$this->load->view('speech_search_view'); //loads CI_speech_view 
		
	}
	
	function load_model() //call of method when user speech is recognized
	{
		$this->load->model('speech_search_model'); 		//load model speech_search_model
		$data=$this->speech_search_model->get_search_result();		//call to get_search_result()
		$search_result=$this->speech_search_model->validate($data); 	//validates the data and returns the string to be shown in view
		echo $search_result; 	//echo search result in view
	}	
}
?>