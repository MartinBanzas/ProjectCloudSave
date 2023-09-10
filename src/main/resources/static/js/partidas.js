  function openEditModal() {
                var editModal = document.getElementById("editModal");
                editModal.style.display = "block";
            }

            // Función para cerrar el modal de edición
            function closeEditModal() {
                var editModal = document.getElementById("editModal");
                editModal.style.display = "none";
            }


     document.addEventListener("DOMContentLoaded", function() {
        const toggleButton = document.getElementById('togglePartidas');
        const tablaPartidas = document.getElementById('tablaPartidas');



        toggleButton.addEventListener('click', () => {
            if (tablaPartidas.style.display === 'none' || tablaPartidas.style.display === '') {
                tablaPartidas.style.display = 'table';
                toggleButton.textContent = 'Ocultar Partidas';
            } else {
                tablaPartidas.style.display = 'none';
                toggleButton.textContent = 'Mostrar Partidas';
            }
        });
    });

