/**
 * Autocompletado de productos reutilizable.
 *
 * Uso en el JSP:
 *
 *   <input type="text" id="buscarProducto" class="form-control"
 *          placeholder="Escribe el nombre del producto...">
 *   <input type="hidden" id="idProductoSeleccionado" name="cmbProducto">
 *   <div id="sugerenciasProducto" class="list-group"
 *        style="position:absolute; z-index:1000;"></div>
 *
 *   <script src="js/autocompleteProducto.js"></script>
 *   <script>
 *       initAutocompleteProducto({
 *           inputId: "buscarProducto",
 *           hiddenId: "idProductoSeleccionado",
 *           sugerenciasId: "sugerenciasProducto"
 *       });
 *   </script>
 */

function initAutocompleteProducto(config) {

    var input = document.getElementById(config.inputId);
    var hidden = document.getElementById(config.hiddenId);
    var caja = document.getElementById(config.sugerenciasId);

    var timeoutId = null;

    input.addEventListener("input", function () {

        var texto = input.value.trim();

        // Si el usuario sigue escribiendo, ya no hay un producto
        // valido seleccionado todavia
        hidden.value = "";

        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        if (texto.length < 2) {
            caja.innerHTML = "";
            caja.style.display = "none";
            return;
        }

        // Espera 300ms despues de la ultima tecla antes de buscar,
        // para no saturar al servidor con una peticion por cada letra
        timeoutId = setTimeout(function () {
            buscarProductos(texto, caja, input, hidden);
        }, 300);
    });

    // Cierra las sugerencias si el usuario hace clic fuera
    document.addEventListener("click", function (e) {
        if (e.target !== input) {
            caja.style.display = "none";
        }
    });
}

function buscarProductos(texto, caja, input, hidden) {

    fetch("ServletBuscarProducto?texto=" + encodeURIComponent(texto))
        .then(function (resp) {
            return resp.json();
        })
        .then(function (productos) {

            caja.innerHTML = "";

            if (productos.length === 0) {
                caja.innerHTML =
                    '<div class="list-group-item text-muted">' +
                    "Sin resultados" +
                    "</div>";
                caja.style.display = "block";
                return;
            }

            productos.forEach(function (p) {

                var item = document.createElement("button");
                item.type = "button";
                item.className = "list-group-item list-group-item-action";
                item.textContent =
                    p.nombre + " (Stock: " + p.stock + ")";

                item.addEventListener("click", function () {
                    input.value = p.nombre;
                    hidden.value = p.id;
                    caja.innerHTML = "";
                    caja.style.display = "none";
                });

                caja.appendChild(item);
            });

            caja.style.display = "block";
        })
        .catch(function (err) {
            console.error("Error buscando productos:", err);
        });
}