package application;


public class Sensor {
	private String _id;
	private String _tipo;
	private String _fecha;
	private String _hora;
	private double _valor;
	
	// GETTERS Y SETTERS
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		_id = id;
	}
	public String getTipo() {
		return _tipo;
	}
	public void setTipo(String tipo) {
		_tipo = tipo;
	}
	public String getFecha() {
		return _fecha;
	}
	public void setFecha(String fecha) {
		_fecha = fecha;
	}
	public double getValor() {
		return _valor;
	}
	public void setValor(double valor) {
		_valor = valor;
	}
	
	public String getHora() {
		return _hora;
	}
	public void setHora(String hora) {
		_hora = hora;
	}
	public String toString() {
        return "Sensores [id=" + _id + ", Tipo=" + _tipo +", Fecha=" + _fecha + "Hola=" + _hora + ", Valor=" + _valor+ "]";
    }
}