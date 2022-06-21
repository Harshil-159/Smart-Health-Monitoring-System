<?php
//echo phpinfo();
if ($_SERVER['REQUEST_METHOD']=='POST') {

    $email = $_POST['email'];
    $password = $_POST['password'];

    include ('connect.php');

    $sql = "SELECT * FROM register WHERE Email='$email' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) == 1 ) {
        
        $row = mysqli_fetch_assoc($response);
        //print_r($row);

        if ( $password == $row['Password'] ) {
            
            $index['Name'] = $row['Name'];
            $index['Email'] = $row['Email'];
            $index['Password'] = $row['Password'];
            $index['Phone_No'] = $row['Phone_No'];
            $index['id'] = $row['id'];
            $index['age'] = $row['age'];
            $index['address'] = $row['address'];
            $index['user_fcm_token']= $row['user_fcm_token'];
        
            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['responseDescription'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['responseDescription'] = "error";
            echo json_encode($result);
            
            mysqli_close($conn);

        }

    }

}

?>