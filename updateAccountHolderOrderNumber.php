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
            $errMSG = "아이디를  입력하세요.";
        }
        else if(empty($groupId)){
            $errMSG ="그룹 아이디를 입력하세요.";
        }
        else if(empty($orderNumber)){
            $errMSG = "순서번호를  입력하세요.";
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
                    $successMSG = "순서 변경  성공";
                }
                else
                {
                   $errMSG ="순서 변경  에러";
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




