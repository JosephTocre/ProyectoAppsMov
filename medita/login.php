<?php
header("Content-Type: application/json; charset=UTF-8");

$servername = "localhost";
$username = "root";
$password = "1234";
$dbname = "dbmedita";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    echo json_encode(["success" => false, "message" => "Error de conexión: " . $conn->connect_error]);
    exit;
}

$input = json_decode(file_get_contents("php://input"), true);
$usuario = trim($input["usuario"] ?? "");
$clave = trim($input["clave"] ?? "");

if ($usuario === "" || $clave === "") {
    echo json_encode(["success" => false, "message" => "Faltan datos"]);
    exit;
}

$stmt = $conn->prepare("SELECT clave, nombres FROM usuarios WHERE usuario = ?");
$stmt->bind_param("s", $usuario);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($hash_clave, $nombres);
    $stmt->fetch();

    if (password_verify($clave, $hash_clave)) {
        echo json_encode([
            "success" => true,
            "message" => "Acceso correcto",
            "usuario" => $usuario,
            "nombres" => $nombres
        ]);
    } else {
        echo json_encode(["success" => false, "message" => "Contraseña incorrecta"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Usuario no encontrado"]);
}

$stmt->close();
$conn->close();
?>

