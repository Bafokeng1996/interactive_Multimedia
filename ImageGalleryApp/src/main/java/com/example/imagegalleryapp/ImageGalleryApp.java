package com.example.imagegalleryapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImageGalleryApp extends Application {

    private final String[] imagePaths = {
            "/pics/all.jfif",
            "/pics/company.jfif",
            "/pics/css.jfif",
            "/pics/Java programming language icon.jfif",
            "/pics/mysql.jpg",
            "/pics/programming.jfif",
            "/pics/programming3.jfif",
            "/pics/NodeJS logo.jfif",
            "/pics/97d713e2-4d62-4197-a459-ecab99a5105f.jfif",
            "/pics/231 Web Development - Websites, Applications and Software.jfif",
            "/pics/AI Logo [Adobe Illustrator].jfif",
            "/pics/Iconos Logos Microsoft Office Word, Excel, Power Point en PNG y Vector.jfif",
            "/pics/New Microsoft Visual Studio Code Logo Sticker Sticker by msft-stickers.jfif",
            "/pics/window_requestAnimationFrame().jfif",
    };

    private BorderPane root;
    private GridPane thumbnailGrid;
    private ImageView fullImageView;
    private Label imageLabel;
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Gallery");

        root = new BorderPane();
        thumbnailGrid = createThumbnailGrid();
        fullImageView = new ImageView();
        imageLabel = new Label();

        // Message pane
        VBox messagePane = new VBox();
        Label messageLabel = new Label("Click on any image to display");
        messageLabel.setStyle("-fx-font-size: 18;");
        messagePane.getChildren().add(messageLabel);
        messagePane.setAlignment(Pos.CENTER);
        root.setCenter(messagePane);

        root.setLeft(thumbnailGrid);
        root.setTop(imageLabel);
        BorderPane.setAlignment(imageLabel, Pos.CENTER);

        HBox navigationControls = createNavigationControls();
        root.setBottom(navigationControls);
        BorderPane.setAlignment(navigationControls, Pos.CENTER);
        BorderPane.setMargin(navigationControls, new Insets(10));

        Scene scene = new Scene(root, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void showFullImage(Image image, ImageView clickedThumbnail) {
        fullImageView.setImage(image);
        fullImageView.setFitWidth(380);
        fullImageView.setFitHeight(360);

        root.setCenter(fullImageView); // Set the fullImageView as the center of the BorderPane

        for (javafx.scene.Node node : thumbnailGrid.getChildren()) {
            if (node instanceof ImageView) {
                node.setOpacity(1.0);
            }
        }

        clickedThumbnail.setOpacity(0.4);

        currentIndex = thumbnailGrid.getChildren().indexOf(clickedThumbnail);

        String imageName = imagePaths[currentIndex].substring(imagePaths[currentIndex].lastIndexOf("/") + 1);
        imageLabel.setText(imageName);
        imageLabel.setTextFill(root.getStyle().contains("-fx-background-color: black") ? javafx.scene.paint.Color.WHITE : javafx.scene.paint.Color.BLACK);
    }

    private GridPane createThumbnailGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int col = 0;
        int row = 0;
        for (String imagePath : imagePaths) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.getStyleClass().add("thumbnail");
            imageView.setOnMouseClicked(event -> showFullImage(image, imageView));

            gridPane.add(imageView, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        return gridPane;
    }

    private HBox createNavigationControls() {
        HBox navigationControls = new HBox();
        navigationControls.setAlignment(Pos.CENTER);
        navigationControls.setSpacing(20);

        NavigationButton prevButton = new NavigationButton("Previous");
        NavigationButton nextButton = new NavigationButton("Next");

        prevButton.setOnAction(event -> navigate(-1));
        nextButton.setOnAction(event -> navigate(1));

        navigationControls.getChildren().addAll(prevButton, nextButton);

        NavigationButton changeThemeButton = new NavigationButton("Change Theme");
        changeThemeButton.setOnAction(event -> changeTheme());
        navigationControls.getChildren().add(changeThemeButton);

        return navigationControls;
    }

    private void navigate(int direction) {
        int newIndex = (currentIndex + direction + imagePaths.length) % imagePaths.length;
        Image newImage = new Image(getClass().getResourceAsStream(imagePaths[newIndex]));
        showFullImage(newImage, (ImageView) thumbnailGrid.getChildren().get(newIndex));
    }

    private void changeTheme() {
        String currentColor = root.getStyle().contains("-fx-background-color: black") ? "black" : "white";
        String newColor = currentColor.equals("black") ? "white" : "black";
        root.setStyle("-fx-background-color: " + newColor + ";");

        imageLabel.setTextFill(root.getStyle().contains("-fx-background-color: black") ? javafx.scene.paint.Color.WHITE : javafx.scene.paint.Color.BLACK);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
