<?php
//echo phpinfo();
if ($_SERVER['REQUEST_METHOD']=='POST') {

    $email = $_POST['email'];
    $password = $_POST['password'];

    include ('connect.php');

    $sql = "SELECT * FROM doctorregister WHERE dr_email='$email' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) == 1 ) {
        
        $row = mysqli_fetch_assoc($response);
        //print_r($row);

        if ( $password == $row['dr_password'] ) {
            
            $index['Name'] = $row['dr_name'];
            $index['Email'] = $row['dr_email'];
            $index['Password'] = $row['dr_password'];
            $index['Phone_No'] = $row['dr_mobile'];
            $index['dr_id'] = $row['dr_id'];
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