<html>
	<head>
		<title>Speech Search</title>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
		<script>
			$(document).ready(function(){		//page ready event handler call
				$('#search').change(function(){ 	//onchange event handler call
					var search=$('#search').val();
					
					if(search.length < 1) 	//if nothing in the textbox
					{
						$('#search_result').html('');
						exit();
					}
					else{  	//if there is something in the textbox
						$.ajax({
							type: 'POST', 	//method to send data
							url: 'http://localhost:8086/CI_speech/index.php/speech_search/load_model',  //specifies url for post operation
							data: {search:search},	//data to be send
							success:function(response){		//incase of success
								$('#search_result').html(response);	//set the label value
							}
						});
					}
				});
			});
		</script>
	
		<style type='text/css'>
			#form_speech
			{
				background:#EAE4BC;
				margin: 100px auto;
				width:500px;
				height:250px;
				border-radius: 5px;
			}
		</style>
		
	</head>
	<body>
		<div id='form_speech'>
			<form name='speech' id='speech' style='margin: 150px auto; width:300px; padding:10px;'   >
				<input type='text' name='search' id='search' style='padding:5px;width:300px;font-size:20px; border-radius:5px;border: 1px solid #fff;' x-webkit-speech/>
				<label id='search_result' name='search_result' style='color:white'/>
			</form>
		</div>
	</body>
</html>