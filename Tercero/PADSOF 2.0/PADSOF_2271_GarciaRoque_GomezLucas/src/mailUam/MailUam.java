package mailUam;

import grupo.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import usuario.Estudiante;
import usuario.Profesor;
import usuario.Usuario;
import usuario.Visitante;

public class MailUam {
	private static final String ficheroAlumnos = "ALUMNOS.TXT";
	private static final String ficheroProfesores = "PROFESORES.TXT";
	public static final String ficheroId = "cont.obj";
	public static final String nmAplicacion = "aplicacion";
	public static final String nmUsuario = "usuario";
	public static final String nmGrupo = "grupo";
	public static final String nmDatos = "datos.obj";

	public static final String barraLinuxMAC = "/";
	public static final String barraWindows = "\\";
	public static int cargarUsuario=0;
	public static int guardarUsuario=0;
	public static int cargarGrupo=0;
	public static int guardarGrupo=0;
	public String barra;
	private ArrayList<Usuario> listaUsuarios;
	private ArrayList<Grupo> listaGrupos;
	private Usuario logged;

	/**
	 * Constructor mailUam
	 */
	public MailUam() {
		listaGrupos = new ArrayList<>();
		listaUsuarios = new ArrayList<>();
		if (getOS().equals("Windows 7")) {
			barra = barraWindows;
		} else {
			barra = barraLinuxMAC;
		}
		if (!existeDir(nmAplicacion)) {
			cargarDatos();
			crearDirectorios();
		} else {
			cargarGrupos();
			cargarUsuarios();
		}
		logged = null;
	}

	// GETTER Y SETTER

	/**
	 * @return the listaUsuarios
	 */
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	/**
	 * @param listaUsuarios
	 *            the listaUsuarios to set
	 */
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	/**
	 * @return the listaGrupos
	 */
	public ArrayList<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	/**
	 * Devuelve una lista con todos los grupos y subgrupos del sistema
	 * @return La lista de grupos del sistema
	 */
	public ArrayList<Grupo> getListaTodosGrupos() {
		ArrayList<Grupo> lista = new ArrayList<>();
		for (Grupo g : listaGrupos) {
			if (!g.isGrupoColaborativo()) {
				lista.add(g);
				for(Grupo sg :g.getSubGrupos()){
					lista.add(sg);
					lista.addAll(sg.getTodosSubGrupos());
				}
			}
		}
		return lista;
	}

	/**
	 * @param listaGrupos
	 *            the listaGrupos to set
	 */
	public void setListaGrupos(ArrayList<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	/**
	 * @param barra
	 *            the barra to set
	 */
	public void setBarra(String barra) {
		this.barra = barra;
	}

	/**
	 * @return the logged
	 */
	public Usuario getLogged() {
		return logged;
	}

	/**
	 * @param logged
	 *            the logged to set
	 */
	public void setLogged(Usuario logged) {
		this.logged = logged;
	}

	// METODOS NUEVOS

	/**
	 * Login de un usuario
	 * 
	 * @param correo correo del usuario
	 * @param password password del usuario
	 * @return true si se ha podido loguear
	 */
	public Usuario login(String correo, String password) {
		if (existeDir(nmAplicacion + barra + nmUsuario + barra + correo)
				&& !correo.equals("") && !password.equals("") && logged == null) {
			Usuario usr = buscarUsuario(correo);
			if (usr.getPassword().equals(password) && !usr.isVisitante()) {
				setLogged(usr);
				return usr;
			}
		}
		return null;
	}

	/**
	 * Login de visitante
	 * 
	 * @param correo el texto por el que se identificara al usuario
	 * @return true si se ha podido loguear
	 */
	public Usuario loginVisitante(String correo) {
		cargarUsuarios();
		if (existeDir(nmAplicacion + barra + nmUsuario + barra + correo)
				&& !correo.equals("") && logged == null) {
			Usuario usr = buscarUsuario(correo);
			if (usr.isVisitante()) {
				setLogged(usr);
				return usr;
			}
			return null;
		}
		File f = new File(nmAplicacion + barra + nmUsuario + barra + correo);
		f.mkdir();
		Usuario usu = new Visitante("", "", correo, "");
		addUsuario(usu);
		guardarUsuario(usu);
		setLogged(usu);
		return usu;
	}

	/**
	 * Se desconecta el usuario del sistema
	 */
	public void logout() {
		if (logged != null)
			logged.setLastLogin(Calendar.getInstance());
		setLogged(null);
	}

	/**
	 * Crea un grupo en la aplicacion
	 * 
	 * @param nombre
	 *            nombre del grupo
	 * @return true si ha conseguido crear el grupo, false si no
	 */
	public boolean crearGrupoDir(String nombre) {
		return new File(nmAplicacion + barra + nmGrupo + barra + nombre)
				.mkdir();
	}

	/**
	 * Guarda todos los usuarios que haya en el sistema
	 * @return si los usuarios se han guardado correctamente
	 */
	public boolean guardarUsuarios() {

		System.out.println("Guardando Usuario:"+guardarUsuario++);
		for (Usuario usuario : listaUsuarios)
			if (!guardarUsuario(usuario))
				return false;
		return true;
	}

	/**
	 * Buscar varios usuario que se puedan identificar con una cadena parecida
	 * a la dada
	 * 
	 * @param nombre identifiacaion del usuario
	 * @return lista de usuarios con el nombre identico
	 */
	public ArrayList<Usuario> buscarUsuarios(String nombre) {
		System.out.println("correo es:"+nombre);
		int i=0;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		for (Usuario u : listaUsuarios) {
			if (StringSimilarity.similarity(u.getCorreo(),nombre)>=0.5) {
				System.out.println(i);
				usuarios.add(u);
			}

			i++;
		}
		System.out.println(i);
		return usuarios;
	}

	/**
	 * Busca un usuario que tenga un cierto correo
	 * @param correo correo del usuario a buscar
	 * @return Usuario que tenga el correo identico
	 */
	public Usuario buscarUsuario(String correo) {
		for (Usuario u : listaUsuarios) {
			if (u.getCorreo().equals(correo)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Busca grupos que tengan una identificacion similar a una cadena dada
	 * 
	 * @param nombreGrupo el idientificador del grupo
	 * @return lista de grupos con el nombre nombreGrupo
	 */
	public ArrayList<Grupo> buscarGrupoLista(String nombreGrupo) {
		ArrayList<Grupo> grupo = new ArrayList<Grupo>();
		cargarGrupos();
		for (Grupo g : listaGrupos) {
			if (StringSimilarity.similarity(nombreGrupo, g.getNombre()) >= 0.5
					|| g.getNombre().equalsIgnoreCase(nombreGrupo)) {
				grupo.add(g);
			}
			grupo.addAll(g.buscarGrupoLista(nombreGrupo));
		}
		return grupo;
	}

	/**
	 * Busca un grupo pasandole un nombre para el grupo
	 * @param nombreGrupo nombre del grupo a buscar
	 * @return lista de grupos con el nombre nombreGrupo
	 */
	public Grupo buscarGrupo(String nombreGrupo) {
		cargarGrupos();
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombreGrupo)) {
				return g;
			}
			if (!g.isGrupoColaborativo()) {
				Grupo sg = g.buscarGrupo(nombreGrupo);
				if (sg != null) {
					return sg;
				}
			}
		}
		return null;
	}

	/**
	 * Sirve para cargar los datos de los usuario de la aplicacion
	 * @return true si los carga, false si no
	 */
	public boolean cargarDatos() {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			
			archivo = new File(ficheroAlumnos);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

		
			String linea;
			String[] ss, ss2, ss3;
			String correo, password, nombre, apellido;
			while ((linea = br.readLine()) != null) {
				
				if (linea.length() > 1) {
					ss = linea.split(":");
					ss2 = ss[0].split("@");
					ss3 = ss2[0].replace(".", " ").split(" ");
					correo = ss[0];
					password = ss[1];
					nombre = ss3[0];
					apellido = ss3[1];
					this.listaUsuarios.add(new Estudiante(nombre, apellido,
							correo, password));
				}
			}

			
			archivo = new File(ficheroProfesores);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			while ((linea = br.readLine()) != null) {
				
				if (linea.length() > 1) {
					ss = linea.split(":");
					ss2 = ss[0].split("@");
					ss3 = ss2[0].replace(".", " ").split(" ");
					correo = ss[0];
					password = ss[1];
					nombre = ss3[0];
					apellido = ss3[1];
					this.listaUsuarios.add(new Profesor(nombre, apellido,
							correo, password));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * Devuelve el sistema operativo con el que se esta trabajando
	 * 
	 * @return el sistema operativo del ordenador actual
	 */
	public static String getOS() {
		return System.getProperty("os.name");
	}

	/**
	 * Crea los directorios de aplicacion, usuarios y grupos
	 * 
	 * @return true si los puede crear, false si no ha podido
	 */
	public boolean crearDirectorios() {
		File f = new File(nmAplicacion);
		f.mkdir();
		File fg = new File(nmAplicacion + barra + nmGrupo);
		fg.mkdir();
		File fu = new File(nmAplicacion + barra + nmUsuario);
		fu.mkdir();
		for (Usuario u : listaUsuarios) {
			File fusr = new File(fu.getPath() + barra + u.getCorreo());
			fusr.mkdir();
			guardarUsuario(u);
		}
		for (Grupo g : listaGrupos) {
			File fgr = new File(fg.getPath() + barra + g.getNombre());
			fgr.mkdir();
			guardarGrupo(g);
		}
		return true;

	}

	/**
	 * @return la barra que utiliza el sistema operativo
	 */
	public String getBarra() {
		return barra;
	}

	/**
	 * @return la barra que utiliza el sistema operativo
	 */
	public static String getBarraSup() {
		if (MailUam.getOS().equals("Windows 7")) {
			return MailUam.barraWindows;
		} else {
			return MailUam.barraLinuxMAC;
		}
	}

	/**
	 * anade un grupo a la aplicacion
	 * 
	 * @param grupo grupo que se anade
	 * @return true si ha podido false si no
	 */
	public boolean addGrupo(Grupo grupo) {
		return listaGrupos.add(grupo);
	}

	/**
	 * Anade un usuario a la aplicacion
	 * 
	 * @param u usuario anadido
	 * @return true si lo anade, false si no
	 */
	public boolean addUsuario(Usuario u) {
		return listaUsuarios.add(u);
	}

	

	/**
	 * Comprueba si existe un directorio
	 * 
	 * @param dir directorio que comprueba si existe
	 * @return true si existe el directorio, false si no
	 */
	public boolean existeDir(String dir) {
		if (dir == null)
			return false;
		File folder = new File(dir);
		if (folder.exists())
			return true;
		return false;
	}


	/**
	 * Imprime los grupos que coincidan en un directorio
	 * 
	 * @param grupo nombre del grupo
	 * @throws IOException excepcion
	 */
	public void buscarGrupoDir(String grupo) throws IOException {
		Path dir = Paths.get(nmAplicacion + barra + nmGrupo);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*"
				+ grupo + "*")) {
			for (Path file : stream) {
				System.out.println(file);
			}
		}
	}

	/**
	 * carga un usuario a la aplicacion
	 * 
	 * @param correo del usuario para cargar
	 * @return Usuario que quiere cargar.
	 */
	public Usuario cargarUsuario(String correo) {
		Usuario usr = null;
		if (correo == null)
			return null;
		try {
			
			FileInputStream fis = new FileInputStream(nmAplicacion + barra
					+ nmUsuario + barra + correo + barra + nmDatos);
			ObjectInputStream ois = new ObjectInputStream(fis);
			usr = (Usuario) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("ERROR AL CARGAR");
			return null;
		} finally {
			if(listaUsuarios.contains(usr)){
				listaUsuarios.remove(usr);
				System.out.println("se esta borrando");
			}
			this.listaUsuarios.add(usr);
		}
		
		return usr;
	}

	/**
	 * Guarda un usuario en la apliacion
	 * @return true si lo guarda bien false no
	 */
	public boolean guardarUsuario() {
		FileOutputStream fos;
		if (logged == null)
			return false;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmUsuario + barra
					+ logged.getCorreo() + barra + nmDatos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(logged);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		for(Grupo g:logged.getListaGrupos())
			for(Grupo g2: listaGrupos){
				if(g2.getNombre().equals(g.getNombre())){
					g2= g;
				}
			}
		return true;
	}

	/**
	 * Guardad un usuario en la app
	 * @param usuario usuari a guardar
	 * @return true si lo guarda false si no
	 */
	public boolean guardarUsuario(Usuario usuario) {
		FileOutputStream fos;
		if (usuario == null)
			return false;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmUsuario + barra
					+ usuario.getCorreo() + barra + nmDatos);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(usuario);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * Carga un grupo
	 * 
	 * @param nombre del grupo que quiere cargar
	 * @return Grupo que ha cargado
	 */
	public Grupo cargarGrupo(String nombre) {
		Grupo grupo = null;
		if (nombre == null)
			return grupo;

		try {
			FileInputStream fis = new FileInputStream(nmAplicacion + barra
					+ nmGrupo + barra + nombre + barra + nmDatos);
			ObjectInputStream ois = new ObjectInputStream(fis);

			grupo = (Grupo) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			return grupo;
		} finally {
			if (buscarListaGrupos(nombre)) {
				if (!borrarGrupo(nombre))
					return grupo;
			}
			listaGrupos.add(grupo);
		}
		return grupo;
	}

	/**
	 * Borra un grupo del array listaGrupo
	 * 
	 * @param nombre nombre del gripo a borrar
	 * @return truen si lo borra false si no
	 */
	public boolean borrarGrupo(String nombre) { 
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombre))
				return listaGrupos.remove(g);
		}
		return false;
	}

	

	/**
	 * Guarda un grupo en el fichero
	 * 
	 * @param grupo grupo que se guarda
	 * @return true si li guarda false si no
	 */
	public boolean guardarGrupo(Grupo grupo) {
		if (grupo == null)
			return false;
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(nmAplicacion + barra + nmGrupo + barra
					+ grupo.getNombre() + barra + nmDatos);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(grupo);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Guarda los grupos de la aplicacion
	 */
	public void guardarGrupos() {
		System.out.println("Guardando Grupo:"+guardarGrupo++);
		for (Grupo g : listaGrupos)
			guardarGrupo(g);
	}

	/**
	 * Carga los grupos de la aplicacion
	 */
	public void cargarGrupos() {
		System.out.println("cargando Grupo:"+cargarGrupo++);
		listaGrupos.clear();
		File f = new File(nmAplicacion + barra + nmGrupo);
		for (String g : f.list()) {
			cargarGrupo(g);
		}
	}

	/**
	 * Carga los usuarios de la aplicacion
	 */
	public void cargarUsuarios() {
		System.out.println("cargando Usuario:"+cargarUsuario++);
		listaUsuarios.clear();
		File f = new File(nmAplicacion + barra + nmUsuario);
		for (String u : f.list()) {
			cargarUsuario(u);
		}
	}
	/**
	 * Busca si existe el usuario
	 * 
	 * @param nombre identifiacion del usuario
	 * @return true si existe, false si no 
	 */
	public boolean buscarListaGrupos(String nombre) {
		for (Grupo g : listaGrupos) {
			if (g.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String imp = "";
		if (listaUsuarios.size() > 0) {
			for (Usuario u : listaUsuarios) {
				imp += u.getNombre() + " ,";
			}
			imp += "\n";
		}
		if (listaGrupos.size() > 0) {
			for (Grupo g : listaGrupos) {
				imp += g.toString() + "\n";
			}
		}
		return imp;
	}

	/**
	 * Vuele a cargar el usuario logged 
	 */
	public void actualizarLogged() {
		String correo= logged.getCorreo();
		String password= logged.getPassword();
		if(logged== null){
			return;
		}
		if(logged.isVisitante()){
			logged=null;
			loginVisitante(correo);
		}
		else{
			logged=null;
			login(correo, password);
		}
		
		return;
		
	}

}