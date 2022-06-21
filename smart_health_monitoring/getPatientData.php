<?php 
include ('connect.php');
$qur="Select * From register";
$response=mysqli_query($conn,$qur);

$array=array();
if($response)
{
	$i=0;
	while($row=mysqli_fetch_assoc($response))
	{
		$array[$i]=$row;
		$i++; 
	}
echo json_encode($array);
}
else
{
	 echo array("errorCode"=>405, "errorDescription"=>"Error with cat.");
}

?>