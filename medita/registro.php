<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");

include 'conexion.php';

$response = array("success" => false, "message" => "");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $nombres = $_POST['nombres'] ?? '';
    $usuario = $_POST['usuario'] ?? '';
    $clave = $_POST['clave'] ?? '';

    if (!empty($nombres) && !empty($usuario) && !empty($clave)) {
        $claveHash = password_hash($clave, PASSWORD_BCRYPT);

        $sql = "INSERT INTO usuarios (nombres, usuario, clave) VALUES (?, ?, ?)";
        $stmt = $conexion->prepare($sql);
        $stmt->bind_param("sss", $nombres, $usuario, $claveHash);

        if ($stmt->execute()) {
            $response["success"] = true;
            $response["message"] = "Usuario registrado correctamente.";
        } else {
            $response["message"] = "Error al registrar usuario: " . $stmt->error;
        }

        $stmt->close();
    } else {
        $response["message"] = "Faltan datos.";
    }
} else {
    $response["message"] = "Método no permitido.";
}

echo json_encode($response, JSON_UNESCAPED_UNICODE);
$conexion->close();
?>

