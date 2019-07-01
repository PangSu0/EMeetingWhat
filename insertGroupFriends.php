<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $userId=$_POST['userId'];
        $nickName=$_POST['nickName'];
        $groupId=$_POST['groupId'];
        $thumbnailImagePath=$_POST['thumbnailImagePath'];
        $profileImagePath=$_POST['profileImagePath'];

        if(empty($userId)){
            $errMSG = "아이디를  입력하세요.";
        }
        else if(empty($nickName)){
            $errMSG = "닉네임을  입력하세요.";
        }
        else if(empty($groupId)){
            $errMSG ="그룹 아이디를 입력하세요.";
        }
        else if(empty($thumbnailImagePath)){
            $errMSG = "섬네일 이미지 경로를  입력하세요.";
        }


        if(!isset($errMSG))
        {
            try{

                $stmt = $con->prepare("INSERT INTO tbl_groupComponent values (:userId, :groupId, 0, :nickName, :thumbnailImagePath, :profileImagePath)");
                $stmt->bindParam(':userId', $userId);
                $stmt->bindParam(':groupId', $groupId);
                $stmt->bindParam(':nickName', $nickName);
                $stmt->bindParam(':thumbnailImagePath', $thumbnailImagePath);
                $stmt->bindParam(':profileImagePath', $profileImagePath);
                if($stmt->execute())
                {
                    $successMSG = "친구  추가 성공";
                }
                else
                {
                   $errMSG ="친구  추가 에러";
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
            UserID: <input type = "text" name = "UserId" />
           GroupID: <input type="text" name="GroupId"/>
                nickName: <input type="text" name="NickName"/>
                thumbnailImagePath <input type="text" name="ThumbnailImagePath"/>
                profileImagePath : <input type="text" name="ProfileImagePath"/>

            <input type = "submit" name = "submit" />
        </form>

   </body>
</html>

<?php
        }
?>





