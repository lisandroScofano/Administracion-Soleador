function eliminarHorario(id){
	$("#horario" + id).remove();
}

function agregarHorario() {
	var dias = $("#dias").val();
	var entrada = $("#entrada").val();
	var salida = $("#salida").val();

	var indice = $('#horarios tr').length;

	$("#horarios")
			.append(
					"<tr id='horario" + indice + "'><td class='pl-0'>"
							+ dias
							+ "</td><td>"
							+ entrada
							+ "</td><td>"
							+ salida
							+ "</td><td class='text-right'>"
							+ "<a idHorario='horario" + indice + "' onclick='eliminarHorario(" + indice + ")'><i class='la la-trash'></i></a></td>"
							+ "<input type='hidden' name='lugarModel.horariosModel[" + indice + "].dias' value='" + dias +"'/>"
							+ "<input type='hidden' name='lugarModel.horariosModel[" + indice + "].entrada' value='" + entrada +"'/>"
							+ "<input type='hidden' name='lugarModel.horariosModel[" + indice + "].salida' value='" + salida +"'/>"
							+ "</tr>");
}