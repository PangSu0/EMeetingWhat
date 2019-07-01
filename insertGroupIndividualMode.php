<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $name=$_POST['name'];
        $monthlyPayment=$_POST['monthlyPayment'];
        $accountHolderId=$_POST['accountHolderId'];
        $paymentDay=$_POST['paymentDay'];
        $bankName=$_POST['bankName'];
        $accountNumber=$_POST['accountNumber'];
        $nickName=$_POST['nickName'];
        $thumbnailImagePath=$_POST['thumbnailImagePath'];
        $profileImagePath=$_POST['profileImagePath'];

        if(empty($name)){
            $errMSG = "이름을 입력하세요.";
        }
        else if(empty($monthlyPayment)){
            $errMSG = "월별입금금액 입력하세요.";
        }
        else if(empty($accountHolderId)){
            $errMSG = "계주아이디를 입력하세요.";
        }
        else if(empty($paymentDay)){
            $errMSG = "입금 날짜를 입력하세요.";
        }
        else if(empty($bankName)){
            $errMSG = "은행 이름을 입력하세요.";
        }
        else if(empty($accountNumber)){
            $errMSG = "계좌 번호를 입력하세요.";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare("INSERT INTO tbl_groupDetail  VALUES (null, :name, now(), now(), 0, :monthlyPayment, 'individual', :accountHolderId, :paymentDay, :bankName, :$accountNumber)");
                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':monthlyPayment', $monthlyPayment);
                $stmt->bindParam(':accountHolderId', $accountHolderId);
                $stmt->bindParam(':paymentDay', $paymentDay);
                $stmt->bindParam(':bankName', $bankName);
                $stmt->bindParam(':accountNumber', $accountNumber);
                if($stmt->execute())
                {
                    $successMSG = "새로운 모임(개인 수령)을 추가했습니다.";
                }
                else
                {
                    $errMSG = "새 모임 추가 에러";
                }


                $result = $con->lastInsertId();
                $stmt = $con->prepare("INSERT INTO tbl_groupComponent values (:accountHolderId, :result, 0, :nickName, :thumbnailImagePath, :profileImagePath)");
                $stmt->bindParam(':result', $result);
                $stmt->bindParam(':accountHolderId', $accountHolderId);
                $stmt->bindParam(':nickName', $nickName);
                $stmt->bindParam(':thumbnailImagePath', $thumbnailImagePath);
                $stmt->bindParam(':profileImagePath', $profileImagePath);
                
                if($stmt->execute())
                {
                    $successMSG = "모임 추가 성공";
                }
                else
                {
                   $errMSG ="모임 추가 에러";
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
            Name: <input type = "text" name = "name" />
            monthlyPayment: <input type = "text" name = "monthlyPayment" />
            accountHolderId: <input type = "text" name = "accountHolderId" />
            paymentDay: <input type = "text" name = "paymentDay" />
            bankName: <input type="text" name="bankName"/>
            accountNumber: <input type="text" name="accountNumber"/>
                nickName: <input type="text" name="nickName"/>
                thumbnailImagePath <input type="text" name="thumbnailImagePath"/>
                profileImagePath : <input type="text" name="profileImagePath"/>

            <input type = "submit" name = "submit" />
        </form>

   </body>
</html>

<?php
        }
?>



