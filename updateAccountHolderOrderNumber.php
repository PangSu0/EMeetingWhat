<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $userId=$_POST['userId'];
        $groupId=$_POST['groupId'];
        $orderNumber=$_POST['orderNumber'];

        if(empty($userId)){
            $errMSG = "���̵�  �Է��ϼ���.";
        }
        else if(empty($groupId)){
            $errMSG ="�׷� ���̵� �Է��ϼ���.";
        }
        else if(empty($orderNumber)){
            $errMSG = "������ȣ��  �Է��ϼ���.";
        }
        if(!isset($errMSG))
        {
            try{

                $stmt = $con->prepare("UPDATE tbl_groupComponent SET OrderNumber=:orderN$
                $stmt->bindParam(':orderNumber', $orderNumber);
                $stmt->bindParam(':userId', $userId);
                $stmt->bindParam(':groupId', $groupId);
                if($stmt->execute())
                {
                    $successMSG = "���� ����  ����";
                }
                else
                {
                   $errMSG ="���� ����  ����";
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
            UserID: <input type = "text" name = "userId" />
           GroupID: <input type="text" name="groupId"/>
                OrderNumber<input type="text" name="orderNumber"/>

            <input type = "submit" name = "submit" />
        </form>

   </body>
</html>

<?php
        }
?>




