package com.juniverse.multimedia;

import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Reproductor de video MP4 con JavaFX: abrir archivo, reproducir, pausar y detener.
 *
 * @author linux
 */
public class Mp4Player extends Application {

    private MediaPlayer mediaPlayer;

    /**
     * Construye la interfaz con los botones Open File, Play, Pause y Stop.
     * @param stage ventana principal
     * @throws Exception si falla la inicialización
     */
    @Override
    public void start(Stage stage) throws Exception {

        Button openButton = new Button("Open File");
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        Button stopButton = new Button("Stop");

        setButtonsDisabled(true, playButton, pauseButton, stopButton);

        HBox controlBox = createHBox(openButton, playButton, pauseButton, stopButton);

        openButton.setOnAction(e -> {
            Optional<Media> media = openFile(stage);
            if (media.isPresent()) {
                if (mediaPlayer != null) {
                    mediaPlayer.dispose(); // libera el video anterior; si no, seguía sonando
                }
                mediaPlayer = new MediaPlayer(media.get());
                setButtonsDisabled(false, playButton, pauseButton, stopButton);
                Scene scene = getScene(new MediaView(mediaPlayer), controlBox);
                stage.setScene(scene);
            }
        });
        playButton.setOnAction(e -> playVideo());
        pauseButton.setOnAction(e -> pauseVideo());
        stopButton.setOnAction(e -> {
            stopVideo();
            setButtonsDisabled(true, playButton, pauseButton, stopButton);
        });

        Scene scene = getScene(new MediaView(), controlBox);
        stage.setTitle("MP4 Player");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    /** Reproduce el video cargado (si hay uno). */
    private void playVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /** Pausa el video. */
    private void pauseVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /** Detiene el video y lo regresa al inicio. */
    private void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    /** Crea una fila centrada con los nodos indicados. */
    private HBox createHBox(Node... nodes) {
        HBox hBox = new HBox(10, nodes);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    /** Crea una columna centrada con fondo negro. */
    private VBox createVBox(Node... nodes) {
        VBox vBox = new VBox(10, nodes);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.fill(Color.BLACK));
        return vBox;
    }

/**
 * Activa o desactiva los nodos.
 * @param disabled {@code true} para desactivarlos
 * @param nodes nodos afectados
 */
private void setButtonsDisabled(boolean disabled, Node... nodes) {
        for (Node node : nodes) {
node.setDisable(disabled);
        }
    }

    /** Crea la escena de 800x600 con el video arriba y los controles abajo. */
    private Scene getScene(MediaView mediaView, HBox controlBox) {
        VBox parent = createVBox(mediaView, controlBox);
        return new Scene(parent, 800, 600);
    }

    /**
     * Muestra el selector de archivos para elegir un MP4.
     * @param stage ventana propietaria
     * @return el medio elegido, o vacío si se canceló
     */
    private Optional<Media> openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media To Play");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4")
        );
        File file = fileChooser.showOpenDialog(stage);
        Optional<Media> optMedia = Optional.empty();
        if (file == null) {
            return optMedia;
        }
        String mp4Path = file.toURI().toString();
        return Optional.of(new Media(mp4Path));
    }

    /** Libera el reproductor al cerrar la aplicación. */
    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
    }

    /**
     * Punto de entrada.
     * @param args no se usan
     */
    public static void main(String[] args) {
        launch(args);
    }

}
