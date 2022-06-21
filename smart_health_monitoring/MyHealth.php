<?php 
include ('connect.php');
$id=$_POST['id'];
$qur="Select * From register where id='$id'";
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