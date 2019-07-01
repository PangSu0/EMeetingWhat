
<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $userId = isset($_POST['UserId']) ? $_POST['UserId'] : ' ';
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $stmt = $con->prepare("select * from tbl_accountDetail where UserId = '$userId'");
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array();

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);

            array_push($data,
                array('AccountId'=>$AccountId,
                'AccountNumber'=>$AccountNumber,
                'BankName'=>$BankName
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("bankList"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
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





