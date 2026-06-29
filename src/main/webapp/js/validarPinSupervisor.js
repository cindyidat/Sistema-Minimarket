/**
 * Modal reutilizable para solicitar autorizacion de un supervisor
 * (Administrador o Jefe Inventario) mediante usuario + PIN, sin
 * cerrar la sesion del usuario actual.
 *
 * Uso:
 *   solicitarPinSupervisor(function (nombreSupervisor) {
 *       // Aqui va el codigo que se ejecuta SOLO si el PIN fue valido.
 *       // Por ejemplo: enviar el formulario, hacer el fetch, etc.
 *   });
 */

function solicitarPinSupervisor(callbackSiAutorizado) {

    Swal.fire({
        title: "Autorizacion de Supervisor",
        html:
            '<input type="text" id="swalUsuarioSupervisor" ' +
            'class="swal2-input" placeholder="Usuario del supervisor">' +
            '<input type="password" id="swalPinSupervisor" ' +
            'class="swal2-input" placeholder="PIN" maxlength="4">',
        confirmButtonText: "Validar",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        preConfirm: function () {

            var usuarioSupervisor =
                    document.getElementById("swalUsuarioSupervisor").value.trim();

            var pin =
                    document.getElementById("swalPinSupervisor").value.trim();

            if (usuarioSupervisor === "" || pin === "") {
                Swal.showValidationMessage(
                        "Ingresa el usuario y el PIN del supervisor.");
                return false;
            }

            return fetch("ServletValidarPin", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "usuarioSupervisor=" + encodeURIComponent(usuarioSupervisor) +
                      "&pin=" + encodeURIComponent(pin)
            })
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {

                if (!data.autorizado) {
                    Swal.showValidationMessage(
                            "Usuario o PIN incorrectos, o no tiene permisos de supervisor.");
                    return false;
                }

                return data.nombreSupervisor;
            })
            .catch(function () {
                Swal.showValidationMessage(
                        "Error de conexion. Intentalo de nuevo.");
                return false;
            });
        }
    }).then(function (resultado) {

        if (resultado.isConfirmed) {
            callbackSiAutorizado(resultado.value);
        }
    });
}