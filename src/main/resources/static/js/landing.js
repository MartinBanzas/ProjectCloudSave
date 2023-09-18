$(document).ready(function () {
  // Controlador de clic en el botón "Guardar"
  $("#reviewModal").on("click", ".btn-primary", function () {
    // Obtén el valor de la valoración seleccionada
    var rating = $("input[name='rate']:checked").val();

    // Puedes mostrar el valor en la consola para verificar
    console.log("Valoración seleccionada: " + rating);

    // Aquí puedes enviar el valor al controlador del lado del servidor a través de una solicitud AJAX o un formulario
    // Por ejemplo, con una solicitud AJAX usando jQuery:
    $.ajax({
      url: "/guardar_review", // Cambia esto a la URL correcta de tu controlador en el backend
      method: "POST",
      data: {
        rating: rating, // Enviar el valor de la valoración al servidor
        // También puedes incluir otros datos del formulario aquí
      },
      success: function (response) {
        // Manejar la respuesta del servidor si es necesario
        console.log("Respuesta del servidor: " + response);
        // Cierra el modal después de guardar
        $("#reviewModal").modal("hide");
      },
      error: function (error) {
        // Manejar errores si es necesario
        console.error("Error al enviar la solicitud: " + error);
      },
    });
  });
});