<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $name = $_POST['name'];
    $email = $_POST['email'];
    $mobile_number=$_POST['mobile_number'];
    $password = $_POST['password'];
    // $c_password =$_POST['c_password'];
    $token =$_POST['token'];
    require_once 'connect.php';
    $qur="select * from doctorRegister where dr_email='$email'";
    $num_record=mysqli_query($conn,$qur);
    if(mysqli_num_rows($num_record)==1)
    {
    	$result= array("success"=>0,"responseDescription"=>"User already exist with same id ");
    	echo json_encode($result);
    }
    else
    {
   
  //  $password = password_hash($password, PASSWORD_DEFAULT);

    
    
    $sql = "INSERT INTO doctorRegister (dr_name,dr_email,dr_mobile,dr_password,dr_token) VALUES ('$name', '$email', '$mobile_number','$password','$token')";

    if ( mysqli_query($conn, $sql) ) {
       // $result["success"] = "1";
        //$result["message"] = "success";
           $result = array("success"=>1, "responseDescription"=>"Registration Successfull");
        echo json_encode($result);
        mysqli_close($conn);

    } else {

       $result = array("success"=>0, "responseDescription"=>"Something went Wrong");
        echo json_encode($result);
        mysqli_close($conn);
    }
}
}
?>