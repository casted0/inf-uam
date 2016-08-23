package control;

import grupo.Grupo;
import gui.*;

import java.awt.Component;
import java.awt.event.*;

import javax.swing.JCheckBox;

import usuario.Usuario;
import mailUam.MailUam;

public class ControlBuscarGrupo implements ActionListener {
	

	private MailUam modelo;
	private Ventana v;
	
	public ControlBuscarGrupo(Ventana v, MailUam modelo) {
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
		System.out.println("Activando controlador: "+getClass());
		GUIBuscarGrupo buscarGrupo = v.getBuscarGrupo();
		if(source.equals(buscarGrupo.getBotonMensajes())){
			GUIMensaje menuMensaje = getV().getMensajes();
			menuMensaje.setValores();
			System.out.println("Cambiando a Mensajes");
			v.cambiarPanelMenuMensajes();
		}else if(source.equals(buscarGrupo.getBotonGrupos())){
			System.out.println("Cambiando a Grupos");
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(buscarGrupo.getBotonVerPrefil())){
			System.out.println("Cambiando a Perfil");
			GUIVerPerfil verPerfil = v.getPerfil();
			verPerfil.setValores(modelo.getLogged());
			v.cambiarPanelPerfil();
		}else if(source.equals(buscarGrupo.getBotonSalir())){
			System.out.println("Cambiando a Salir");
			modelo.logout();
			v.cambiarPanelLogin();
		}else if(source.equals(buscarGrupo.getBotonEntrar())){
			System.out.println("Cambiando a menu Grupos");
			getModelo().cargarGrupos();
			getModelo().cargarUsuarios();
			for(Component c: buscarGrupo.getPanelResultados().getComponents()){
				JCheckBox check= (JCheckBox) c;
				if (check.isSelected()){
					Grupo g= getModelo().buscarGrupo(check.getText().split("]")[1]);
					Usuario u = getModelo().getLogged();
					g.addUsuario(u);
					u.addGrupo(g);
					
				}
			}
			getModelo().guardarUsuarios();
			getModelo().guardarGrupos();
			getModelo().guardarUsuario();

			getModelo().cargarGrupos();
			getModelo().cargarUsuarios();
			GUIMenuGrupo menuGrupo = v.getMenuGrupos();
			menuGrupo.setValores();
			v.cambiarPanelMenuGrupos();
		}else if(source.equals(buscarGrupo.getBotonBuscar())){
			System.out.println("Cambiando a Buscar");
			getModelo().cargarGrupos();
			buscarGrupo.setValores(getModelo().buscarGrupoLista(buscarGrupo.getTextBuscarText()));
			v.cambiarPanelBuscarGrupo();
		}
	}

}
