<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $userId = $_POST['userId'];
        $bankName=$_POST['bankName'];
        $accountNumber=$_POST['accountNumber'];
        if(empty($bankName)){
            $errMSG = "���� �̸��� �Է��ϼ���.";
        }
        else if(empty($accountNumber)){
            $errMSG = "���¹�ȣ�� �Է��ϼ���.";
        }


        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare("INSERT INTO tbl_accountDetail  VALUES(null, :userId, :accountNumber, :bankName)");
                $stmt->bindParam(':userId', $userId);
                $stmt->bindParam(':accountNumber', $accountNumber);
                $stmt->bindParam(':bankName', $bankName);
                if($stmt->execute())
                {
                    $successMSG = "���ο� ���¸� �߰��߽��ϴ�.";
                }
                else
                {
                    $errMSG = "�� ����  �߰� ����";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

     } 
?>
<?php


        if (isset($errMSG)) echo $errMSG;
        if (isset($successMSG)) echo $successMSG;
                $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

        if( !$android )
        {
?>
<html>
    <body>
        <form action="<?php $_PHP_SELF ?>" method="POST">
            UserId: <input type = "text" name = "userId" />
            accountNumber: <input type = "text" name = "accountNumber" />
            bankName: <input type = "text" name = "bankName" />
            <input type = "submit" name = "submit" />
        </form>

   </body>
</html>
<?php
        }
?>



