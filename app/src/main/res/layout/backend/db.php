<?php
	$username="epiz_252480";
	$password="";//password
	$servername="sql304.epizy.com";
	$database="epiz_252480_db";

	//Create connection
	$conn = new mysqli ($servername, $username, $password, $database);

	//Check connection
	if ($conn->connect_error){
		die("Connection failed: " . $conn->connect_error);
	}

	$veiksmas = $_POST['action'];
	if (!strcmp("insert", $veiksmas)) {
		$dinner_type = $_POST['dinner_type'];
		$city = $_POST['city'];
		$makeModel = $_POST['makeModel'];
		$payment = $_POST['payment'];

		$sql = "INSERT INTO auto (dinner_type, city, makeModel, payment) VALUES ('$dinner_type', '$city', '$makeModel', '$payment')";
		if($conn->query($sql) === TRUE){
			echo "New entry created successfully";
		}else{
			echo "Error creating new entry " . $sql . "<br>" . $conn ->error;
		}
	}
	$conn->close();
?>