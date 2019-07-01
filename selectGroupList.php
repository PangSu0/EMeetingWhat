<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $userId = isset($_POST['UserId']) ? $_POST['UserId'] : ' ';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $stmt = $con->prepare("select * from tbl_groupDetail as gd, tbl_groupComponent as gc where gd.GroupId = gc.GroupId and gc.UserId = '$userId'");
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($data,
                array('GroupId'=>$GroupId,
                'Name'=>$Name,
                'CreateDate'=>$CreateDate,
                'EndDate'=>$EndDate,
                'TargetAmount'=>$TargetAmount,
                'MonthlyPayment'=>$MonthlyPayment,
                'GroupType'=>$GroupType,
                'AccountHolderId'=>$AccountHolderId,
                'PaymentDay'=>$PaymentDay,
                'BankName'=>$BankName,
                'AccountNumber'=>$AccountNumber
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("groupList"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

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
                        ¾ÆÀÌµð <input type="text" name = "UserId"/>
                        <input type="submit"/>

                </form>
        </body>
</html>
<?php

}

?>

