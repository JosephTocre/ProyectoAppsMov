<?php
// login.php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

require_once 'conexion.php';

// ACEPTAR FORM-DATA (lo que envía Android con StringRequest)
$usuario = trim($_POST['usuario'] ?? '');
$clave = trim($_POST['clave'] ?? '');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(["success" => false, "message" => "Método no permitido"]);
    exit;
}

if ($usuario === '' || $clave === '') {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Faltan datos"]);
    exit;
}

// Preparar consulta
$stmt = $conexion->prepare("SELECT id_usuario, clave, nombres FROM usuarios WHERE usuario = ?");
if (!$stmt) {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error en la consulta: " . $conexion->error]);
    exit;
}

$stmt->bind_param("s", $usuario);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows === 0) {
    echo json_encode(["success" => false, "message" => "Usuario no encontrado"]);
    $stmt->close();
    $conexion->close();
    exit;
}

$stmt->bind_result($id_usuario, $hash_clave, $nombres);
$stmt->fetch();

// Verificar contraseña
if (password_verify($clave, $hash_clave)) {
    echo json_encode([
        "success" => true,
        "message" => "Acceso correcto",
        "id_usuario" => $id_usuario,
        "usuario" => $usuario,
        "nombres" => $nombres
    ], JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(["success" => false, "message" => "Contraseña incorrecta"]);
}

$stmt->close();
$conexion->close();
?>