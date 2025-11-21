<?php
// verificar_usuarios.php
header("Content-Type: application/json; charset=UTF-8");

require_once 'conexion.php';

$sql = "SELECT id_usuario, usuario, nombres, clave FROM usuarios";
$result = $conexion->query($sql);

if ($result->num_rows > 0) {
    $users = [];
    while($row = $result->fetch_assoc()) {
        $users[] = [
            'id' => $row['id_usuario'],
            'usuario' => $row['usuario'],
            'nombres' => $row['nombres'],
            'clave_length' => strlen($row['clave'])
        ];
    }
    $response = [
        "success" => true,
        "message" => "Se encontraron " . $result->num_rows . " usuarios",
        "usuarios" => $users
    ];
} else {
    $response = [
        "success" => false,
        "message" => "❌ No hay usuarios en la base de datos"
    ];
}

echo json_encode($response, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
$conexion->close();
?>