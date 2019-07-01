<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $groupId = isset($_POST['GroupId']) ? $_POST['GroupId'] : ' ';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
    $stmt = $con->prepare("SELECT * FROM tbl_groupComponent WHERE groupId = '$groupId'");
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($data,
                array('GroupId'=>$GroupId,
           'UserId'=>$UserId,
           'OrderNumber'=>$OrderNumber
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("groupComponents"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

        echo $json;
    }
?>

<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
if(!$android){
?>

<html>
        <body>
                <form action="<?php $_PHP_SELF ?>" method="POST">

                        그룹 아이디 <input type="text" name="GroupId"/>
                        <input type="submit"/>


                </form>
        </body>
</html>
<?php

}
?>









