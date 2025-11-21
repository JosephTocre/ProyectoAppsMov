<?php
// categorias.php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
require_once 'conexion.php';

$query = "SELECT id_categoria, nombre_categoria FROM categorias ORDER BY id_categoria";
$resultado = $conexion->query($query);

$categorias = [];

if ($resultado) {
    while ($fila = $resultado->fetch_assoc()) {
        $categorias[] = $fila;
    }
    echo json_encode($categorias, JSON_UNESCAPED_UNICODE);
} else {
    http_response_code(500);
    echo json_encode(["success" => false, "message" => "Error en la consulta: " . $conexion->error]);
}

$conexion->close();
?>
