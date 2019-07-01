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
            $errMSG = "은행 이름을 입력하세요.";
        }
        else if(empty($accountNumber)){
            $errMSG = "계좌번호를 입력하세요.";
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
                    $successMSG = "새로운 계좌를 추가했습니다.";
                }
                else
                {
                    $errMSG = "새 계좌  추가 에러";
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



