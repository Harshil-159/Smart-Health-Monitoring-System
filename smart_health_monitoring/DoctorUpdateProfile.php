<?php
include('connect.php');

$id=$_POST['id'];
$name=$_POST['UpdatedName'];
$mob=$_POST['UpdatedMob'];
$email=$_POST['UpdatedEmail'];
$qur="Update doctorregister set dr_name='$name',dr_mobile='$mob',dr_email='$email' where dr_id=$id";
if(mysqli_query($conn,$qur)){
    $result= array("success"=>1,"responseDescription"=>"Updated SuccesFully ");
    echo json_encode($result);
}
else{
    $result= array("success"=>0,"responseDescription"=>"Error ");
    echo json_encode($result);
}
// echo($name+" "+$mob+" "+$oldPwd+" "+$newPwd+" "+$confirmPwd);

?>