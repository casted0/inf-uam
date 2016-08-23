package control;

import gui.*;

import java.awt.event.*;

import mailUam.MailUam;

public class ControlVerRespuesta implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlVerRespuesta(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	
	
	public MailUam getModelo() {
		return modelo;
	}



	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}



	public Ventana getV() {
		return v;
	}



	public void setV(Ventana v) {
		this.v = v;
	}


	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUIVerRespuesta verRespuesta = getV().getVerRespuesta();
		if (source.equals(verRespuesta.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(verRespuesta.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = getV().getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(verRespuesta.getBotonVerPrefil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(verRespuesta.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}else if(source.equals(verRespuesta.getButtonAtras())){
			v.cambiarPanelListarPregunta();
		}
	}

}
