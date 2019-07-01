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
            $errMSG = "�̸��� �Է��ϼ���.";
        }
        else if(empty($monthlyPayment)){
            $errMSG = "�����Աݱݾ� �Է��ϼ���.";
        }
        else if(empty($accountHolderId)){
            $errMSG = "���־��̵� �Է��ϼ���.";
        }
        else if(empty($paymentDay)){
            $errMSG = "�Ա� ��¥�� �Է��ϼ���.";
        }
        else if(empty($bankName)){
            $errMSG = "���� �̸��� �Է��ϼ���.";
        }
        else if(empty($accountNumber)){
            $errMSG = "���� ��ȣ�� �Է��ϼ���.";
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
                    $successMSG = "���ο� ����(���� ����)�� �߰��߽��ϴ�.";
                }
                else
                {
                    $errMSG = "�� ���� �߰� ����";
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
                    $successMSG = "���� �߰� ����";
                }
                else
                {
                   $errMSG ="���� �߰� ����";
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



