<?php

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $name = $_POST['name'];
    $id = $_POST['id'];
    $rating = $_POST['rating'];
    $comment = $_POST['comment'];
    require_once 'connect.php';
    $qur="select * from feedback where u_id='$id'";
    $num_record=mysqli_query($conn,$qur);
    if(mysqli_num_rows($num_record)==1)
    {
    	$result= array("success"=>0,"responseDescription"=>"You have already given feedback thank you");
    	echo json_encode($result);
    }
    else
    {
   
  //  $password = password_hash($password, PASSWORD_DEFAULT);

    
    
    $sql = "INSERT INTO feedback VALUES ('$id', '$name','$rating', '$comment')";

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