<?php
include('connect.php');

$id=$_POST['id'];
$name=$_POST['UpdatedName'];
$mob=$_POST['UpdatedMob'];
$email=$_POST['UpdatedEmail'];
$address=$_POST['UpdatedAddress'];
$age=$_POST['UpdatedAge'];
$qur="Update register set Name='$name',Phone_No='$mob',Email='$email',address='$address',age='$age' where id=$id";
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