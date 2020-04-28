<?php
$dbHost = 'localhost';
$dbPort = '5432';
$dbUser = 'root';
$dbPassword = '12345aBc';

$connected = 'false';
try {
    $conn = new PDO('pgsql:host=postgres-db-service;dbname=postgres;', 'admin', '12345aBc');
    if ($conn->query('select 1+1')->fetchColumn() === 2) {
        $connected = 'true';
    }
} catch (\Throwable $exception) {
    var_dump($exception);
}

$connectedTo2 = 'false';
$url = 'http://php-service2:81/';
$content = file_get_contents($url);
$data = json_decode($content, true);
if ($data && is_array($data) && $data['success']) {
    $connectedTo2 = 'true';
}
?>

<html>
<head><title>TEST APP</title></head>
<body>
    <h1>Status:</h1>
    <ul>
        <li>
            DB: <?= $connected ?>
        </li>
        <li>
            Service: <?= $connectedTo2 ?>
        </li>
    </ul>
</body>
</html>
