package com.example.clientesolicitudhttp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

import java.io.*;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class HelloController {
    @FXML
    private ComboBox<String> cbMetodoPeticion;
    @FXML
    private TextField lbUrl;
    @FXML
    private Label lbRespuesta;
    @FXML
    private Label lbTipoContenido;
    @FXML
    private RadioButton rbPretty;
    @FXML
    private RadioButton rbRaw;
    @FXML
    private Tab tabCabecera;
    @FXML
    private Tab tabCuerpo;
    @FXML
    private WebView wbBody;
    @FXML
    private WebView wbCabecera;
    private ToggleGroup toggleGroup;

    //Variables globales
    public HttpClient cliente;
    public String respuestaFINAL = null;
    public String contentType = null;
    public String urlConsulta = null;

    @FXML
    void btnConsultar(ActionEvent event) {
        buscarUrl();
    }

    @FXML
    void btnGuardar(ActionEvent event) {
        if (contentType != null) {
            if (contentType.startsWith("text/html")) {
                guardarArchivoHtml();
            } else if (contentType.startsWith("image/")) {
                guardarArchivoImagen();
            } else if (contentType.startsWith("application/pdf")) {
                guardarArchivoPdf();
            } else if (contentType.startsWith("application/json")) {
                guardarArchivoJson();
            }
        }
    }

    public void llenarCombo(){
        ObservableList<String> tipos = FXCollections.observableArrayList("GET", "HEAD", "OPTIONS");
        cbMetodoPeticion.setItems(tipos);
    }

    public void crearGrupoRb() {
        toggleGroup = new ToggleGroup();
        rbPretty.setToggleGroup(toggleGroup);
        rbRaw.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            }
        });
    }

    public void buscarUrl(){
        String url = lbUrl.getText().trim();
        //Validaciones del comboBox y textField
        if(!url.isEmpty()){
            String opcionCbSeleccionada = cbMetodoPeticion.getValue();
            if(opcionCbSeleccionada != null && !opcionCbSeleccionada.isEmpty()){
                //Creacion del request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .method(opcionCbSeleccionada, HttpRequest.BodyPublishers.noBody())
                        .build();

                Task<HttpResponse<String>> task = new Task<HttpResponse<String>>() {
                    @Override
                    protected HttpResponse<String> call() throws Exception {
                        return cliente.send(request, BodyHandlers.ofString());
                    }
                };
                //Si la solicitud fue correcta
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        //Sacar la respuesta de la petición
                        HttpResponse<String> response = task.getValue();
                        int statusCode = response.statusCode();
                        String responseBody = response.body();
                        respuestaFINAL = response.body();
                        String statusMessage = statusCode + " - " + HttpMensajes.getMessage(statusCode);
                        lbRespuesta.setText(statusMessage);
                        lbTipoContenido.setText(response.headers().firstValue("Content-Type").orElse("Desconocido"));
                        System.out.println("Consulta completada. La consulta HTTP se ha completado correctamente.");

                        //Mostrar la respuesta
                        TextArea headerTextArea = new TextArea();
                        headerTextArea.setText(response.headers().map().toString());

                        TextArea bodyTextArea = new TextArea();
                        bodyTextArea.setText(responseBody);

                        //Identificar si es pretty o raw
                        if (rbPretty.isSelected()) {
                            wbCabecera.getEngine().loadContent(response.headers().map().toString());
                            wbBody.getEngine().loadContent(responseBody);
                        } else if (rbRaw.isSelected()) {
                            tabCabecera.setContent(headerTextArea);
                            tabCuerpo.setContent(bodyTextArea);
                        }

                    }
                });
                //Si al solicitud no fue correcta
                task.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        mostrarAlerta("ERROR", "Error en la consulta HTTP: " + t.getSource().getException().getMessage(), Alert.AlertType.ERROR);
                    }
                });

                new Thread(task).start();

            }else{
                mostrarAlerta("ERROR", "No se ha seleccionado un método", Alert.AlertType.ERROR);
            }
        }
        else{
            mostrarAlerta("ERROR", "La URL no es válida", Alert.AlertType.ERROR);
        }
    }

    public void guardarArchivoHtml(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo HTML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos HTML", "*.html"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(respuestaFINAL);
                bufferedWriter.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se seleccionó ninguna ubicación para guardar el archivo.");
        }
    }

    public void guardarArchivoImagen(){
        try{
            URL imageUrl = new URL(urlConsulta);
            InputStream inputStream = imageUrl.openStream();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = null;

            //Verificar la extensión de la imagen
            if (contentType.startsWith("image/png")) {
                extFilter = new FileChooser.ExtensionFilter("Imagen PNG (*.png)", "*.png");
            } else if (contentType.startsWith("image/jpeg")) {
                extFilter = new FileChooser.ExtensionFilter("Imagen JPEG (*.jpg)", "*.jpg");
            } else if (contentType.startsWith("image/gif")) {
                extFilter = new FileChooser.ExtensionFilter("Imagen GIF (*.gif)", "*.gif");
            }

            if (extFilter != null) {
                fileChooser.getExtensionFilters().add(extFilter);
            } else {
                // Por si la imgen está en un formato no compatible
                extFilter = new FileChooser.ExtensionFilter("Imagen (*.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.gif");
                fileChooser.getExtensionFilters().add(extFilter);
            }

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void guardarArchivoPdf() {
        try {
            URL url = new URL(urlConsulta);
            InputStream inputStream = url.openStream();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarArchivoJson() {
        try {
            URL url = new URL(urlConsulta);
            InputStream inputStream = url.openStream();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos JSON (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}