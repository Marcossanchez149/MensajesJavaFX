package org.example.trabajo.UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.example.trabajo.Domain.Modelo.Grupo;
import org.example.trabajo.Domain.Modelo.Mensaje;
import org.example.trabajo.Domain.Modelo.Usuario;

import java.util.List;

public class GruposController {
    private AdministracionUsuarios administracionUsuarios = new AdministracionUsuarios();

    private AdministracionGrupo administracionGrupo = new AdministracionGrupo();

    private Usuario usuarioIniciadoSesion;

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
    private TextField contraseñaGrupo;
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
    private TextField contraseñaGrupoUnirse;
    @FXML
    private TableView<Grupo> tablaGrupos;
    @FXML
    private TableColumn<Grupo, String> nombreColumna;
    @FXML
    private TextField enviarMensaje;

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
    public void onLoginButtonClick(ActionEvent actionEvent) {
        String nombreUsuario = usernamelbox.getText();
        String correoUsuario = emailbox.getText();
        String contrasenia = passwordbox.getText();
        if(nombreUsuario != null && correoUsuario != null && contrasenia != null)
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
    public void enCrearGrupoClick(ActionEvent actionEvent) {
        String nombreGrupoStr = this.nombreGrupo.getText();
        String contraseñaGrupoStr = this.contraseñaGrupo.getText();
        administracionGrupo.crearGrupo(new Grupo(nombreGrupoStr,contraseñaGrupoStr));
        administracionGrupo.añadirUsuarioaGrupo(nombreGrupoStr,contraseñaGrupoStr,usuarioIniciadoSesion);
        actualizarGruposUsuario();
        nombreGrupo.clear();
        contraseñaGrupo.clear();
    }

    @FXML
    public void enUnirseaGrupoClick(ActionEvent actionEvent) {
        String nombrerGrupo = nombreGrupoUnirse.getText();
        String contraseñaGrupo = contraseñaGrupoUnirse.getText();
        if(administracionGrupo.comprobarEntradaGrupo(nombrerGrupo,contraseñaGrupo)){
            administracionGrupo.añadirUsuarioaGrupo(nombrerGrupo,contraseñaGrupo,usuarioIniciadoSesion);
            actualizarGruposUsuario();
            nombreGrupoUnirse.clear();
            contraseñaGrupoUnirse.clear();
        }
    }


    @FXML
    public void enviarMensajeClick(ActionEvent actionEvent) {
        String mensaje = enviarMensaje.getText();
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null && !mensaje.trim().isEmpty()) {
            administracionGrupo.enviarMensaje(grupoSeleccionado, mensaje, usuarioIniciadoSesion);
            enviarMensaje.clear();
            mostrarMensaje();
        }
    }
    public void mostrarMensaje(){
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if(grupoSeleccionado != null){
            List<Mensaje> mensajes = administracionGrupo.obtenerMensajes(grupoSeleccionado);
            areaMensajes.getItems().clear();
            mensajes.forEach(mensaje -> areaMensajes.getItems().add(mensaje.getUsuario().getUserName() + ": " + mensaje.getContenido()));
        }
    }

    public void onCrearCuentaButtonClick(ActionEvent actionEvent) {
        String nombreUsuario = usernamelbox.getText();
        String correoUsuario = emailbox.getText();
        String contrasenia = passwordbox.getText();
        if(nombreUsuario != null && correoUsuario != null && contrasenia != null)
        {
            boolean respuesta = administracionUsuarios.crearUsuario(new Usuario(nombreUsuario, correoUsuario, contrasenia));
            if(respuesta) {
                welcomeText.setText("Se ha creado la cuenta, Bienvenido "+ nombreUsuario);
                paneGrupos.setVisible(true);
                crearYunirGurpo.setVisible(true);
                mensajesPane.setVisible(true);
                inocioSesionPane.setVisible(false);
                actualizarGruposUsuario();
            }else
                welcomeText.setText("No se ha creado la cuenta");
        }
    }
}