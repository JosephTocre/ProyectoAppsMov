<?php
$servidor = "localhost";
$usuario = "root";
$clave = "1234";
$base_datos = "dbMedita";

$conexion = mysqli_connect($servidor, $usuario, $clave, $base_datos);

if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}
?>
