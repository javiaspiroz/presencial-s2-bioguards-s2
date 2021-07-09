package application;

import controlador.ControladorBBDD;


public class Sistema {
	
	private static Sistema _unico;
	private Usuario _usuarioLogueado;


	/**
	 * Constructor de la clase. Nota: Es privado porque se instancia al inicio de la
	 * aplicacion y solo puede haber un objeto Sistema unico en todo el contexto de
	 * la aplicacion.
	 */
	private Sistema() {
	}

	/**
	 * Metodo para acceder al objeto unico de sistema.
	 * @return objeto unico de sistema
	 */
	public static Sistema getUnico() {
		if (_unico == null) {
			_unico = new Sistema();
		}
		return _unico;
	}

	public void inicializar() {
		// Se crea la conexion a BBDD directamente al inicializar el sistema. 
		// TODO: si el reultado es false sacar mensaje de error y salir de la aplicacion. 
		//ControladorBBDD.inicializarBBDD();
	}

	public boolean loginUsuario(String login, String password) {
		_usuarioLogueado = ControladorBBDD.getUsuario(login, password);
		return _usuarioLogueado!=null;
	}

	public void logoutUsuario() {
		_usuarioLogueado = null;
	}


	public Usuario getUsuarioLogueado() {
		return _usuarioLogueado;
	}

	public void setfUsuarioLogueado(Usuario fUsuarioLogeado) {
		this._usuarioLogueado = fUsuarioLogeado;
	}
}
