<?php

if(isset($_GET['send_notification'])){
   send_notification ();
}

function send_notification()
{
	echo 'Hello';
define( 'API_ACCESS_KEY','AAAAeKDlgF8:APA91bEMurkS767gnUKTxqgN3EMPy8M9YRBU8eHdngSXtF1pi5KOHBPE83tQsnHZnhnc1RvRMXN-bU9hjgI1ngTJF_SRfWvkJhbD4PdU_4nE_o3LQgEX2MI8bMR--NKiOi7Knaz5JP6b');
 //   $registrationIds = ;
#prep the bundle
     $msg = array
          (
		'body' 	=> 'Firebase Push Notification',
		'title'	=> 'Vishal Thakkar',
             	
          );
	$fields = array
			(
				'to'=>$_REQUEST['token'],
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		curl_close( $ch );
}
?>