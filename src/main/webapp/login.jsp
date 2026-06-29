<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Minimarket - Login</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

<link rel="stylesheet"
href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<style>

:root{
    --amarillo:#FFD200;
    --amarillo-hover:#E6BD00;
    --negro:#1A1A1A;
    --gris-oscuro:#2D2D2D;
    --gris-fondo:#F4F4F4;
}

body{
    background:var(--gris-fondo);
    height:100vh;
    display:flex;
    justify-content:center;
    align-items:center;
    font-family:'Segoe UI',sans-serif;
}

.login-card{
    width:450px;
    border:none;
    border-radius:25px;
    overflow:hidden;
    box-shadow:0 10px 35px rgba(0,0,0,.15);
}

.login-header{
    background:var(--negro);
    color:var(--amarillo);
    text-align:center;
    padding:30px;
    border-bottom:5px solid var(--amarillo);
}

.logo{
    font-size:55px;
    color:var(--amarillo);
}

.login-header h2{
    color:#ffffff;
}

.login-header p{
    color:#cccccc;
}

.login-body{
    padding:35px;
    background:#ffffff;
}

.input-group-text{
    background:var(--negro);
    color:var(--amarillo);
    border:1px solid var(--negro);
}

.form-control{
    height:50px;
    border:1px solid #ccc;
}

.form-control:focus{
    box-shadow:0 0 0 3px rgba(255,210,0,.25);
    border-color:var(--amarillo);
}

.btn-login{
    background:var(--amarillo);
    color:var(--negro);
    border:none;
    height:50px;
    font-weight:bold;
    transition:background .2s ease-in-out;
}

.btn-login:hover{
    background:var(--amarillo-hover);
    color:var(--negro);
}

.footer-text{
    font-size:13px;
    color:#6c757d;
}

.alert-danger{
    border-radius:10px;
    font-size:14px;
}

</style>

</head>

<body>

<div class="card login-card">

    <div class="login-header">

        <div class="logo">
            <i class="bi bi-shop"></i>
        </div>

        <h2 class="fw-bold mt-2">
            Minimarket
        </h2>

        <p class="mb-0">
            Sistema de Gestión Comercial
        </p>

    </div>

    <div class="login-body">

        <form action="ServletLogin" method="post">

            <div class="mb-3">

                <label class="form-label fw-semibold">

                    Usuario

                </label>

                <div class="input-group">

                    <span class="input-group-text">

                        <i class="bi bi-person-fill"></i>

                    </span>

                    <input
                        type="text"
                        name="txtUsuario"
                        class="form-control"
                        placeholder="Ingrese su usuario"
                        required>

                </div>

            </div>

            <div class="mb-4">

                <label class="form-label fw-semibold">

                    Contraseña

                </label>

                <div class="input-group">

                    <span class="input-group-text">

                        <i class="bi bi-lock-fill"></i>

                    </span>

                    <input
                        type="password"
                        name="txtClave"
                        class="form-control"
                        placeholder="Ingrese su contraseña"
                        required>

                </div>

            </div>

            <button
                type="submit"
                class="btn btn-login w-100">

                <i class="bi bi-box-arrow-in-right me-2"></i>

                Ingresar al Sistema

            </button>

        </form>

        <%
        String error=request.getParameter("error");

        if(error!=null){
        %>

        <div class="alert alert-danger mt-4">

            <i class="bi bi-exclamation-triangle-fill me-2"></i>

            Usuario o contraseña incorrecta

        </div>

        <%
        }
        %>

        <div class="text-center mt-4 footer-text">

            © 2026 Minimarket - Control de Inventario

        </div>

    </div>

</div>

</body>
</html>