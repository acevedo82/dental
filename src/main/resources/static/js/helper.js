function obtenerFecha(fecha) {
	var fechaStr = null;
	var startDate = null;
	if(fecha == null || fecha == undefined || fecha == "" )
		startDate = new Date();
	else 
		startDate = new Date(fecha);
	
	var year = startDate.getFullYear();
	var month = startDate.getMonth();
	if(month < 10) {
		month = month+1;
		month = "0" + month;
	}
	var day = startDate.getDate();
	if(day < 10) {
		day = "0" + day;
	}
	var fecha = year + '-' + month + '-' + day;

	var hours = startDate.getHours();
	if(hours < 10) 
		hours = "0" + hours;
	var mins = startDate.getMinutes();
	if(mins < 10) {
		mins = "0"+mins;
	}
	fechaStr = year + '-' + month + '-' + day;
	return fechaStr;
	
}

function obtenerHora(fecha) {
	var fechaStr = null;
	var startDate = null;
	
	if(fecha == null || fecha == undefined || fecha == "" )
		startDate = new Date();
	else 
		startDate = new Date(fecha);				

	var hours = startDate.getHours();
	if(hours < 10) 
		hours = "0" + hours;	
	var mins = startDate.getMinutes();
	if(mins < 10) {
		mins = "0"+mins;
	}
	fechaStr = hours + ':' + mins;
	return fechaStr;	
}