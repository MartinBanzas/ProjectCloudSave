$(document).ready(function () {
    $(".review-button").click(function () {
        // Obtener el valor real del atributo data-game-id
        var gameId = $(this).data("game-id");

        // Asignar gameId al campo oculto en el formulario
        $("#reviewModal #gameId").val(gameId);

        // Mostrar el modal correspondiente
        $("#reviewModal").modal("show");
    });
});

