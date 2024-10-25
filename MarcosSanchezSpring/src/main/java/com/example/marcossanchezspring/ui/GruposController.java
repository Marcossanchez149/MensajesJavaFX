package com.example.marcossanchezspring.ui;

import com.example.marcossanchezspring.common.Constantes;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.Mensaje;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Component
public class GruposController {

    private final AdministracionUsuarios administracionUsuarios;

    private final AdministracionGrupo administracionGrupo;


    private Usuario usuarioIniciadoSesion;

    public GruposController(AdministracionUsuarios administracionUsuarios, AdministracionGrupo administracionGrupo) {
        this.administracionUsuarios = administracionUsuarios;
        this.administracionGrupo = administracionGrupo;
    }

    private ObservableList<Grupo> listaGrupos = FXCollections.observableArrayList();
    @FXML
    private ListView areaMensajes;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernamelbox;
    @FXML
    private TextField emailbox;
    @FXML
    private PasswordField passwordbox;
    @FXML
    private TextField nombreGrupo;
    @FXML
    private TextField contrasenaGrupo;
    @FXML
    private Pane paneGrupos;
    @FXML
    private Pane inocioSesionPane;
    @FXML
    private Pane mensajesPane;
    @FXML
    private Pane crearYunirGurpo;
    @FXML
    private TextField nombreGrupoUnirse;
    @FXML
    private TextField contrasenaGrupoUnirse;
    @FXML
    private TableView<Grupo> tablaGrupos;
    @FXML
    private TableColumn<Grupo, String> nombreColumna;
    @FXML
    private TextField enviarMensaje;
    @FXML
    private TextField nombreGrupoprivado;
    @FXML
    private TextField usuariosGruposprivado;
    @FXML
    public Button sendmessagebutton;
    @FXML
    public Button botonCrearGrupo;
    @FXML
    public Button botonCrearGrupoprivado;

    @FXML
    public void initialize() {
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        listaGrupos = FXCollections.observableArrayList();
        tablaGrupos.setItems(listaGrupos);
        tablaGrupos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarMensaje();
            }
        });
    }


    @FXML
    public void onLoginButtonClick() {
        String nombreUsuario = usernamelbox.getText();
        String correoUsuario = emailbox.getText();
        String contrasenia = passwordbox.getText();
        if(!Objects.equals(nombreUsuario, "") && !Objects.equals(correoUsuario, "") && !Objects.equals(contrasenia, ""))
        {
            boolean respuesta = administracionUsuarios.comprobarUsuario(new Usuario(nombreUsuario, correoUsuario, contrasenia));
            if(respuesta) {
                usuarioIniciadoSesion = new Usuario(nombreUsuario, correoUsuario, contrasenia);
                welcomeText.setText("Se ha iniciado sesión, Bienvenido "+ nombreUsuario);
                paneGrupos.setVisible(true);
                crearYunirGurpo.setVisible(true);
                mensajesPane.setVisible(true);
                inocioSesionPane.setVisible(false);
                actualizarGruposUsuario();
            }else
                welcomeText.setText("No se ha iniciado sesion");
        }
    }

    private void actualizarGruposUsuario() {
        listaGrupos.clear();
        List<Grupo> grupos = administracionGrupo.obtenerGruposDeUsuario(usuarioIniciadoSesion);
        listaGrupos.addAll(grupos);
    }

    @FXML
    public void enCrearGrupoClick() {
        String nombreGrupoStr = this.nombreGrupo.getText();
        String contrasenaGrupoStr = this.contrasenaGrupo.getText();
        if (!nombreGrupoStr.isEmpty() && !contrasenaGrupoStr.isEmpty()){
            administracionGrupo.crearGrupo(new Grupo(nombreGrupoStr,contrasenaGrupoStr,false));
            administracionGrupo.anadirUsuarioaGrupo(new Grupo(nombreGrupoStr,contrasenaGrupoStr,false),usuarioIniciadoSesion);
            actualizarGruposUsuario();
            nombreGrupo.clear();
            contrasenaGrupo.clear();
        }
    }

    @FXML
    public void enUnirseaGrupoClick() {
        String nombrerGrupo = nombreGrupoUnirse.getText();
        String contrasenaGrupoAUnirse = contrasenaGrupoUnirse.getText();
        if(administracionGrupo.comprobarEntradaGrupo(new Grupo(nombrerGrupo,contrasenaGrupoAUnirse,false))){
            administracionGrupo.anadirUsuarioaGrupo(new Grupo(nombrerGrupo,contrasenaGrupoAUnirse,false),usuarioIniciadoSesion);
            actualizarGruposUsuario();
            nombreGrupoUnirse.clear();
            contrasenaGrupoUnirse.clear();
        }
    }


    @FXML
    public void enviarMensajeClick() {
        String mensaje = enviarMensaje.getText();
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null && !mensaje.trim().isEmpty()) {
            administracionGrupo.enviarMensaje(grupoSeleccionado, mensaje, usuarioIniciadoSesion);
            enviarMensaje.clear();
            mostrarMensaje();
        }
    }

    @FXML
    public void mostrarMensaje() {
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null) {
            List<Mensaje> mensajes = administracionGrupo.obtenerMensajes(grupoSeleccionado);
            areaMensajes.getItems().clear();
            mensajes.forEach(mensaje -> {
                Usuario usuario = mensaje.getUsuario();
                if (usuario != null) {
                    areaMensajes.getItems().add(usuario.getUserName() + ": " + mensaje.getContenido());
                } else {
                    areaMensajes.getItems().add(Constantes.USUARIODESCONOCIDO + mensaje.getContenido());
                }
            });
        }
    }

    @FXML
    public void onCrearCuentaButtonClick() {
        String nombreUsuario = usernamelbox.getText();
        String correoUsuario = emailbox.getText();
        String contrasenia = passwordbox.getText();
        if(!Objects.equals(nombreUsuario, "") && !Objects.equals(correoUsuario, "") && !Objects.equals(contrasenia, ""))
        {
            if(!administracionUsuarios.comprobarUsuario(new Usuario(nombreUsuario, correoUsuario, contrasenia))) {
                boolean respuesta = administracionUsuarios.crearUsuario(new Usuario(nombreUsuario, correoUsuario, contrasenia));
                if(respuesta) {
                    usuarioIniciadoSesion = new Usuario(nombreUsuario, correoUsuario, contrasenia);
                    welcomeText.setText(Constantes.SEHACREADOLACUNETA+ nombreUsuario);
                    paneGrupos.setVisible(true);
                    crearYunirGurpo.setVisible(true);
                    mensajesPane.setVisible(true);
                    inocioSesionPane.setVisible(false);
                    actualizarGruposUsuario();
                }else {
                    welcomeText.setText(Constantes.NOSEHACREADOLACUENTA);
                }
            }else {
                welcomeText.setText("El usuario ya existe");
            }
        }else {
            welcomeText.setText("Faltan datos");
        }
    }

    @FXML
    public void enCrearGrupoPrivadoClick() {
        String nombreGrupoStr = this.nombreGrupoprivado.getText();
        String nombresUsuariosStr = this.usuariosGruposprivado.getText();
        if(!nombreGrupoStr.isEmpty() && !nombresUsuariosStr.isEmpty()){
                List<Usuario> usuariosGruposPrivados = administracionUsuarios.buscarUsuariosPorNombres(nombresUsuariosStr);
                administracionGrupo.crearGrupo(new Grupo(nombreGrupoStr,usuariosGruposPrivados,true));
                administracionGrupo.anadirUsuarioaGrupo(new Grupo(nombreGrupoStr,usuariosGruposPrivados,true),usuarioIniciadoSesion);
                actualizarGruposUsuario();
                nombreGrupo.clear();
                contrasenaGrupo.clear();
        }
    }
}