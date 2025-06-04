document.addEventListener('DOMContentLoaded', () => {
    const btnEnviar = document.getElementById('btn-enviar-juego');

    btnEnviar.addEventListener('click', function (e) {
        const fechaLanzamiento = document.getElementById('fechaLanzamiento');
        const llegadaAlMercado = document.getElementById('llegadaAlMercado');

        if (fechaLanzamiento && llegadaAlMercado) {
            if (new Date(fechaLanzamiento.value) > new Date(llegadaAlMercado.value)) {
                e.preventDefault();
                alert("La fecha de lanzamiento no puede ser posterior a la fecha de llegada a tienda.");
                fechaLanzamiento.focus();
            }
        }
    });
});