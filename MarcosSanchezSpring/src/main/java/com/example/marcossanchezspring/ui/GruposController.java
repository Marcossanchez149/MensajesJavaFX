package com.example.marcossanchezspring.ui;

import com.example.marcossanchezspring.common.Constantes;
import com.example.marcossanchezspring.domain.errors.ErrorApp;
import com.example.marcossanchezspring.domain.errors.ErrorAppDatos;
import com.example.marcossanchezspring.domain.errors.ErrorAppGrupos;
import com.example.marcossanchezspring.domain.errors.ErrorAppUsuarios;
import com.example.marcossanchezspring.domain.modelo.Grupo;
import com.example.marcossanchezspring.domain.modelo.GrupoPrivado;
import com.example.marcossanchezspring.domain.modelo.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class GruposController {

    private final AdministracionUsuarios administracionUsuarios;
    private  Alert alertError;
    private final AdministracionGrupo administracionGrupo;
    private Usuario usuarioIniciadoSesion;

    public GruposController(AdministracionUsuarios administracionUsuarios,
                            AdministracionGrupo administracionGrupo) {
        this.administracionUsuarios = administracionUsuarios;
        this.administracionGrupo = administracionGrupo;
        Platform.runLater(() -> {
            this.alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error");
        });
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
        tablaGrupos.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> {
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
        if(!Objects.equals(nombreUsuario, "") && !Objects.equals(correoUsuario, "")
                && !Objects.equals(contrasenia, ""))
        {
            administracionUsuarios.comprobarUsuario
                    (new Usuario(nombreUsuario, correoUsuario, contrasenia)).peek(ok ->{
                usuarioIniciadoSesion = new Usuario(nombreUsuario, correoUsuario, contrasenia);
                welcomeText.setText("Se ha iniciado sesión, Bienvenido "+ nombreUsuario);
                paneGrupos.setVisible(true);
                crearYunirGurpo.setVisible(true);
                mensajesPane.setVisible(true);
                inocioSesionPane.setVisible(false);
                actualizarGruposUsuario();
            }).peekLeft(this::mostratError);
        }
    }

    private void actualizarGruposUsuario() {
        listaGrupos.clear();
        administracionGrupo.obtenerGruposDeUsuario(usuarioIniciadoSesion).peek(grupos1 ->
                listaGrupos.addAll(grupos1)).peekLeft(this::mostratError);
    }

    @FXML
    public void enCrearGrupoClick() {
        String nombreGrupoStr = this.nombreGrupo.getText();
        String contrasenaGrupoStr = this.contrasenaGrupo.getText();
        if (!nombreGrupoStr.isEmpty() && !contrasenaGrupoStr.isEmpty()){
            administracionGrupo.crearGrupo(new Grupo
                    (nombreGrupoStr,contrasenaGrupoStr)).peek(ok ->{
                        administracionGrupo.anadirUsuarioaGrupo
                                ((new Grupo(nombreGrupoStr,contrasenaGrupoStr)
                            ),usuarioIniciadoSesion).peek(ok1 -> {
                            actualizarGruposUsuario();
                            nombreGrupo.clear();
                            contrasenaGrupo.clear();
                        }).peekLeft(this::mostratError);
                    }).peekLeft(this::mostratError);
        }
    }

    @FXML
    public void enUnirseaGrupoClick() {
        String nombrerGrupo = nombreGrupoUnirse.getText();
        String contrasenaGrupoAUnirse = contrasenaGrupoUnirse.getText();
        administracionGrupo.comprobarEntradaGrupo(new Grupo(nombrerGrupo,contrasenaGrupoAUnirse)
        ).peek(ok ->{
            administracionGrupo.anadirUsuarioaGrupo(new Grupo(nombrerGrupo,contrasenaGrupoAUnirse)
                    ,usuarioIniciadoSesion).peek(ok1 -> {
                actualizarGruposUsuario();
                nombreGrupoUnirse.clear();
                contrasenaGrupoUnirse.clear();
            }).peekLeft(this::mostratError);
        }).peekLeft(this::mostratError);
    }


    @FXML
    public void enviarMensajeClick() {
        String mensaje = enviarMensaje.getText();
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null && !mensaje.trim().isEmpty()) {
            administracionGrupo.enviarMensaje(grupoSeleccionado, mensaje,
                    usuarioIniciadoSesion).peek(ok -> {
                mostrarMensaje();
                enviarMensaje.clear();
            }).peekLeft(this::mostratError);
        }
    }

    @FXML
    public void mostrarMensaje() {
        Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();
        if (grupoSeleccionado != null) {
            administracionGrupo.obtenerMensajes(grupoSeleccionado).peek(mensajes -> {
                areaMensajes.getItems().clear();
                mensajes.forEach(mensaje -> {
                    Usuario usuario = mensaje.getUsuario();
                    if (usuario != null) {
                        areaMensajes.getItems().add(usuario.getUserName() + ": " + mensaje.getContenido());
                    } else {
                        areaMensajes.getItems().add(Constantes.USUARIODESCONOCIDO + mensaje.getContenido());
                    }
                });
            }).peekLeft(this::mostratError);
        }
    }

    @FXML
    public void onCrearCuentaButtonClick() {
        String nombreUsuario = usernamelbox.getText();
        String correoUsuario = emailbox.getText();
        String contrasenia = passwordbox.getText();
        if(!Objects.equals(nombreUsuario, "") && !Objects.equals(correoUsuario, "")
                && !Objects.equals(contrasenia, ""))
        {
            administracionUsuarios.crearUsuario(new Usuario(nombreUsuario, correoUsuario, contrasenia))
                    .peek(ok1 -> {
                usuarioIniciadoSesion = new Usuario(nombreUsuario, correoUsuario, contrasenia);
                welcomeText.setText(Constantes.SEHACREADOLACUNETA+ nombreUsuario);
                paneGrupos.setVisible(true);
                crearYunirGurpo.setVisible(true);
                mensajesPane.setVisible(true);
                inocioSesionPane.setVisible(false);
                actualizarGruposUsuario();
                }).peekLeft(this::mostratError);
        }else {
            welcomeText.setText("Faltan datos");
        }
    }

    @FXML
    public void enCrearGrupoPrivadoClick() {
        String nombreGrupoStr = this.nombreGrupoprivado.getText();
        String nombresUsuariosStr = this.usuariosGruposprivado.getText();
        if(!nombreGrupoStr.isEmpty() && !nombresUsuariosStr.isEmpty()){
                administracionUsuarios.buscarUsuariosPorNombres(nombresUsuariosStr).
                        peek(usuariosGruposPrivados -> {
                    administracionGrupo.crearGrupoPrivado
                            (new GrupoPrivado(nombreGrupoStr,usuariosGruposPrivados)).peek(ok ->{
                            administracionGrupo.anadirUsuarioaGrupoPrivado(
                                    new GrupoPrivado(nombreGrupoStr,usuariosGruposPrivados)
                                    ,usuarioIniciadoSesion).peek(ok1 -> {
                                actualizarGruposUsuario();
                                nombreGrupo.clear();
                                contrasenaGrupo.clear();
                                }).peekLeft(this::mostratError);
                    }).peekLeft(this::mostratError);
                }).peekLeft(this::mostratError);
        }
    }
    private void mostratError(ErrorApp errorText) {
        String errorMensaje = "";
        switch(errorText)
        {
            case ErrorAppDatos e -> {
                if(e == ErrorAppDatos.TIMEOUT)
                {
                    errorMensaje = "Error en la base de datos";
                } else if (e == ErrorAppDatos.NO_CONNECTION)
                {
                    errorMensaje = "Error al guardar el grupo";
                }
            }
            case ErrorAppGrupos e -> {
                switch(e)
                {
                    case GRUPO_YA_EXISTE -> errorMensaje = "El grupo ya existe";
                    case NO_GROUPS_FOUND -> errorMensaje = "No se han encontrado grupos";
                    case GRUPO_CONTRASENA_INCORRECTA -> errorMensaje = "Contraseña incorrecta";
                    case USUARIO_YA_EN_GRUPO -> errorMensaje = "Usuario ya en grupo";
                    case NOMBRE_NO_VALIDO -> errorMensaje = "Nombre no valido";
                    case NO_MESSAGES_FOUND -> errorMensaje = "No hay mensajes";
                }

            }
            case ErrorAppUsuarios e -> {
                switch(e)
                {
                    case ERROR_GUARDAR_USUARIO -> errorMensaje = "El grupo ya existe";
                    case USUARIO_NO_EXISTE -> errorMensaje = "Usuario no existe";
                    case PASSWORD_INCORRECTA -> errorMensaje = "Contraseña incorrecta";
                    case USUARIO_NO_ENCONTRADO -> errorMensaje = "Usuario no encontrado";
                    case USUARIO_YA_EXISTE -> errorMensaje = "Usuario ya existe";
                }

            }
        }
        alertError.setContentText(errorMensaje);
        alertError.showAndWait();
    }
}