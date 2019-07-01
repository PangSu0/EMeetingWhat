<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');
        

    $userId = isset($_POST['UserId']) ? $_POST['UserId'] : ' ';
    $groupId = isset($_POST['GroupId']) ? $_POST['GroupId'] : ' ';    
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
    $stmt = $con->prepare("SELECT * FROM tbl_groupDetail as gd, tbl_groupComponent as gc WHERE gd.groupId = gc.groupId and gc.UserId = '$UserId' and gd.groupId = '$groupId");
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
	   'PaymentDate'=>$PaymentDate,
	   'MonthlyPayment'=>$MonthlyPayment,
                'GroupType'=>$GroupType,
	   'AccountHolderId'=>$AccountHolderId,
	   'PaymentDay'=>$PaymentDay,
                'UserId'=>$UserId,
                'OrderNumber'=>$OrderNumber
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("groupDetailList"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

?>