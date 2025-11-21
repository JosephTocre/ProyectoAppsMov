<?php
// registro.php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

require_once 'conexion.php';

$response = ["success" => false, "message" => ""];

// --------------------------------------------
// ASEGURAR QUE SEA POST
// --------------------------------------------
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    $response["message"] = "Método no permitido";
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
    exit;
}

// --------------------------------------------
// RECIBIR DATOS (form-data o x-www-form-urlencoded)
// --------------------------------------------
$nombres = $_POST['nombres'] ?? null;
$usuario = $_POST['usuario'] ?? null;
$clave   = $_POST['clave'] ?? null;

// --------------------------------------------
// SI VIENE JSON → LEERLO TAMBIÉN
// --------------------------------------------
if ($nombres === null || $usuario === null || $clave === null) {
    $inputJSON = file_get_contents("php://input");
    $json = json_decode($inputJSON, true);
    if (is_array($json)) {
        $nombres = $json['nombres'] ?? null;
        $usuario = $json['usuario'] ?? null;
        $clave   = $json['clave'] ?? null;
    }
}

// --------------------------------------------
// VALIDAR CAMPOS VACÍOS
// --------------------------------------------
if (empty($nombres) || empty($usuario) || empty($clave)) {
    http_response_code(400);
    $response["message"] = "Faltan datos";
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
    exit;
}

// --------------------------------------------
// VALIDAR QUE USUARIO NO EXISTA
// --------------------------------------------
$stmt = $conexion->prepare("SELECT id_usuario FROM usuarios WHERE usuario = ?");
$stmt->bind_param("s", $usuario);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $response["message"] = "El usuario ya existe";
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
    $stmt->close();
    $conexion->close();
    exit;
}
$stmt->close();

// --------------------------------------------
// INSERTAR USUARIO CON PASSWORD_HASH
// --------------------------------------------
$hash_clave = password_hash($clave, PASSWORD_BCRYPT);
$stmt = $conexion->prepare("INSERT INTO usuarios (nombres, usuario, clave) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $nombres, $usuario, $hash_clave);

if ($stmt->execute()) {
    $response["success"] = true;
    $response["message"] = "Usuario registrado correctamente";
} else {
    $response["message"] = "Error al registrar usuario: " . $stmt->error;
}

echo json_encode($response, JSON_UNESCAPED_UNICODE);

$stmt->close();
$conexion->close();
?>
