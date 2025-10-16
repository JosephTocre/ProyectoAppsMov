<?php
include 'conexion.php';

$accion = $_POST['accion'];

if ($accion == "registrar") {
    $id_usuario = $_POST['id_usuario'];
    $id_meditacion = $_POST['id_meditacion'];
    $fecha = date("Y-m-d");

    $query = "INSERT INTO progreso (id_usuario, id_meditacion, fecha, completado) VALUES ('$id_usuario', '$id_meditacion', '$fecha', true)";
    if (mysqli_query($conexion, $query)) {
        echo "success";
    } else {
        echo "error";
    }
}

if ($accion == "consultar") {
    $id_usuario = $_POST['id_usuario'];
    $query = "SELECT p.*, m.titulo, c.nombre_categoria 
              FROM progreso p
              JOIN meditaciones m ON p.id_meditacion = m.id_meditacion
              JOIN categorias c ON m.id_categoria = c.id_categoria
              WHERE p.id_usuario = '$id_usuario'";
    $resultado = mysqli_query($conexion, $query);

    $progreso = array();

    while ($fila = mysqli_fetch_assoc($resultado)) {
        $progreso[] = $fila;
    }

    echo json_encode($progreso);
}

mysqli_close($conexion);
?>
