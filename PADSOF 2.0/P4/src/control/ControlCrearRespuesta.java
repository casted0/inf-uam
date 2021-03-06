package control;

import grupo.GrupoEstudio;
import gui.*;

import java.awt.event.*;

import javax.swing.JOptionPane;

import usuario.Estudiante;
import mailUam.MailUam;

/**
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 *         Clase ControlCrearRespuesta
 */
public class ControlCrearRespuesta implements ActionListener {

	private MailUam modelo;
	private Ventana v;

	/**
	 * Constructor de ControlCrearRespuesta
	 * 
	 * @param v
	 *            Modelo de las ventanas
	 * @param modelo
	 *            Aplicacion donde estan los datos
	 */
	public ControlCrearRespuesta(Ventana v, MailUam modelo) {
		this.modelo = modelo;
		this.v = v;
	}

	/**
	 * @return the modelo
	 */
	public MailUam getModelo() {
		return modelo;
	}

	/**
	 * @param modelo
	 *            the modelo to set
	 */
	public void setModelo(MailUam modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the v
	 */
	public Ventana getV() {
		return v;
	}

	/**
	 * @param v
	 *            the v to set
	 */
	public void setV(Ventana v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		System.out.println("Activando controlador: " + getClass());
		GUICrearRespuesta crearRespuesta = v.getCrearRespuesta();
		if (source.equals(crearRespuesta.getBotonMensajes())) {
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		} else if (source.equals(crearRespuesta.getBotonGrupos())) {
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		} else if (source.equals(crearRespuesta.getBotonVerPerfil())) {
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		} else if (source.equals(crearRespuesta.getBotonSalir())) {
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		} else if (source.equals(crearRespuesta.getButtonResponder())) {
			if (crearRespuesta.getTextRespuesta() == null
					|| crearRespuesta.getTextRespuesta().equals("")) {
				JOptionPane.showMessageDialog(crearRespuesta,
						"No se puede crear una respuesta sin contenido",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if (!(getModelo().getLogged() instanceof Estudiante)) {
					JOptionPane.showMessageDialog(crearRespuesta,
							"Para responder debe  de ser un estudiante",
							"Error", JOptionPane.ERROR_MESSAGE);
					getV().cambiarPanelVerGrupo();
					return;

				}
				getModelo().cargarGrupos();
				getModelo().cargarUsuarios();
				GrupoEstudio g = (GrupoEstudio) getModelo().buscarGrupo(
						getV().getListarPreguntas().getGrupo().getNombre());
				crearRespuesta.setPregunta(g.buscarPregunta(crearRespuesta
						.getPregunta().getCuerpo()));
				crearRespuesta.getPregunta().addRespuesta(
						crearRespuesta.getTextRespuestaText(),
						(Estudiante) getModelo().getLogged());
				// System.out.println(((GrupoEstudio)getModelo().buscarGrupo("asd")).getListaPreguntas().get(0).getListaRespuestas());
				getModelo().guardarGrupos();
				getModelo().guardarUsuarios();
				System.out.println(crearRespuesta.getPregunta()
						.getListaRespuestas());
				v.cambiarPanelVerGrupo();
			}
		}

	}

}
