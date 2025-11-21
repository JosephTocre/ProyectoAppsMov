<?php
// conexion.php
$servidor = "localhost";
$usuario = "root";
$clave = "1234";
$base_datos = "dbmedita";

$conexion = new mysqli($servidor, $usuario, $clave, $base_datos);

if ($conexion->connect_error) {
    // Respondemos en JSON si hay error de conexión
    header("Content-Type: application/json; charset=UTF-8");
    http_response_code(500);
    echo json_encode([
        "success" => false,
        "message" => "Error de conexión a la base de datos: " . $conexion->connect_error
    ], JSON_UNESCAPED_UNICODE);
    exit;
}

// Opcional: forzar UTF-8
$conexion->set_charset("utf8mb4");
?>