package gui;

import grupo.GrupoEstudio;
import layout.SpringUtilities;
import mensaje.*;

import java.awt.Label;
import java.awt.event.*;

import javax.swing.*;

import mailUam.*;
import mensaje.Pregunta;
/** 
 * @author Antonio Gomez lucas, Mario Valdemaro Garcia Roque
 * 
 * Clase GUIVerRespuesta
 */
public class GUIVerRespuesta extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonAtras;
	private JPanel panel;
	private Pregunta pregunta;
	private JLabel labelPregunta;
	private JButton buttonEstadisticas;
	/**
	 * Constructor de la clase GUIVerRespuesta
	 * @param modelo
	 * 		Aplicacion donde estan todos los datos
	 */
	public GUIVerRespuesta(MailUam modelo) {
		super(modelo);
		buttonAtras = new JButton("Atras");
		panel = new JPanel( new SpringLayout());
		labelPregunta = new JLabel();
		buttonEstadisticas = new JButton("Ver estadisticas");
		
		SpringLayout layout= new SpringLayout();
		JPanel p1= new JPanel(layout);
		p1.add(labelPregunta);
		p1.add(panel);
		JPanel p2 = new JPanel();
		
		p2.add(buttonAtras);
		p2.add(buttonEstadisticas);
		p1.add(p2);
		SpringUtilities.makeCompactGrid(p1, 3, 1,6, 6,6, 6);
		add(p1);
	}
	
	/**
	 * @return the buttonAtras
	 */
	public JButton getButtonAtras() {
		return buttonAtras;
	}

	/**
	 * @param buttonAtras the buttonAtras to set
	 */
	public void setButtonAtras(JButton buttonAtras) {
		this.buttonAtras = buttonAtras;
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * @return the pregunta
	 */
	public Pregunta getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the labelPregunta
	 */
	public JLabel getLabelPregunta() {
		return labelPregunta;
	}

	/**
	 * @param labelPregunta the labelPregunta to set
	 */
	public void setLabelPregunta(JLabel labelPregunta) {
		this.labelPregunta = labelPregunta;
	}
	

	/**
	 * @return the buttonEstadisticas
	 */
	public JButton getButtonEstadisticas() {
		return buttonEstadisticas;
	}

	/**
	 * @param buttonEstadisticas the buttonEstadisticas to set
	 */
	public void setButtonEstadisticas(JButton buttonEstadisticas) {
		this.buttonEstadisticas = buttonEstadisticas;
	}

	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
		buttonAtras.addActionListener(c);
		buttonEstadisticas.addActionListener(c);
	}

	public void setValores(Pregunta pregunta) {
		this.pregunta= pregunta;
		labelPregunta.setText(pregunta.getCuerpo());
		panel.removeAll();
		System.out.println("preguntas"+pregunta.getListaRespuestas());
		for(Respuesta r: pregunta.getListaRespuestas()){
			panel.add(new JLabel("["+r.getRemitente().getCorreo()+"] "+r.getCuerpo()));
		}
		SpringUtilities.makeCompactGrid(panel, pregunta.getListaRespuestas().size(), 1,6, 6,6, 6);
	}

}
