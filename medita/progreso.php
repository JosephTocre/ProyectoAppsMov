<?php
// progreso.php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

require_once 'conexion.php';

$accion = $_POST['accion'] ?? '';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(["success" => false, "message" => "Método no permitido"]);
    exit;
}

if ($accion === 'registrar') {
    $id_usuario = $_POST['id_usuario'] ?? '';
    $id_meditacion = $_POST['id_meditacion'] ?? '';
    $fecha = date("Y-m-d");

    if ($id_usuario === '' || $id_meditacion === '') {
        http_response_code(400);
        echo json_encode(["success" => false, "message" => "Faltan datos."]);
        exit;
    }

    $stmt = $conexion->prepare("INSERT INTO progreso (id_usuario, id_meditacion, fecha, completado) VALUES (?, ?, ?, ?)");
    $completado = 1; // true
    $stmt->bind_param("iisi", $id_usuario, $id_meditacion, $fecha, $completado);

    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Progreso registrado."]);
    } else {
        http_response_code(500);
        echo json_encode(["success" => false, "message" => "Error al registrar progreso: " . $stmt->error]);
    }

    $stmt->close();
} elseif ($accion === 'consultar') {
    $id_usuario = $_POST['id_usuario'] ?? '';

    if ($id_usuario === '') {
        http_response_code(400);
        echo json_encode(["success" => false, "message" => "Faltan datos."]);
        exit;
    }

    $stmt = $conexion->prepare(
        "SELECT p.id_progreso, p.fecha, p.completado, m.id_meditacion, m.titulo, c.nombre_categoria
         FROM progreso p
         JOIN meditaciones m ON p.id_meditacion = m.id_meditacion
         JOIN categorias c ON m.id_categoria = c.id_categoria
         WHERE p.id_usuario = ?
         ORDER BY p.fecha DESC"
    );
    $stmt->bind_param("i", $id_usuario);

    if ($stmt->execute()) {
        $res = $stmt->get_result();
        $progreso = [];
        while ($fila = $res->fetch_assoc()) {
            $progreso[] = $fila;
        }
        echo json_encode($progreso, JSON_UNESCAPED_UNICODE);
    } else {
        http_response_code(500);
        echo json_encode(["success" => false, "message" => "Error en la consulta: " . $stmt->error]);
    }

    $stmt->close();
} else {
    http_response_code(400);
    echo json_encode(["success" => false, "message" => "Acción no válida."]);
}

$conexion->close();
?>