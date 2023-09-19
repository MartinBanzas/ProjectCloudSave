$(document).ready(function () {
  // Manejar clic en el botón de revisión
  $(".review-button").click(function () {
    // Obtener el ID del juego del botón de revisión
    var gameId = $(this).attr("id").replace("review-add-btn-", "");

    // Actualizar el valor del campo gameId en el formulario del modal
    $("#gameId").val(gameId);
  });
});