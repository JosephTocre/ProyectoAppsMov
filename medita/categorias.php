<?php
include 'conexion.php';

$query = "SELECT * FROM categorias";
$resultado = mysqli_query($conexion, $query);

$categorias = array();

while ($fila = mysqli_fetch_assoc($resultado)) {
    $categorias[] = $fila;
}

echo json_encode($categorias);

mysqli_close($conexion);
?>
