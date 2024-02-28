package com.example.lotterycopy;

import Kupon1.Kupon1;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class PlayGameController implements Initializable {
        Scene scene;
        Stage stage;
        @FXML
        private Slider startSlider = new Slider();
        int temperature;





        @FXML
        private ImageView im1;
        @FXML
        private ImageView im2;
        @FXML
        private ImageView im3;
            @FXML
            private ImageView im4;
            @FXML
            private ImageView im5;
            @FXML
            private ImageView im6;

            @FXML
            private ImageView im7;
            @FXML
            private ImageView im8;
            @FXML
            private ImageView im9;

        @FXML
        private Text moneyProcess;
        @FXML
        private Text moneyProcess2;
        @FXML
        private Text moneyProcess3;



    @FXML
        private Text money;

        @FXML
        private TextField moneyDisplay;
        @FXML
        private Button addMoney;
        int quantityOfMoney;


        @FXML
        private final Button startSpinning = new Button();
        @FXML
        private Text result;
        @FXML
        private Text couponRaw;
        @FXML
        private ProgressBar progressBar = new ProgressBar();
        List<Kupon1> coupons = new ArrayList<>();
        double progress = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            coupons = loadCouponsToList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        startSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                temperature = (int) startSlider.getValue();

                if (temperature == 1)
                    startGame();
            }
        });

    }


    public void addMoney() {
        int moneyDown = Integer.parseInt(moneyDisplay.getText());

        quantityOfMoney += moneyDown;
        money.setText(quantityOfMoney + ".00$");
        addToHistory("+" + moneyDown + "$");
    }
    @FXML
    protected void goBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View1Coy.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void startGame() {
        Random random = new Random();
        Timer timer = new Timer();
        couponRaw.setText("");


        TimerTask task = new TimerTask() {
            int counter = 4;
            @Override
            public void run() {
                if (counter > 0) {
                    startSlider.setDisable(true);
                    Random random1 = new Random();
                    result.setText("Losuje...");


                    int randNumber = random1.nextInt(0, coupons.size());
                    int randMinus = randNumber - 1;
                    int randPlus = randNumber + 1;

                        if (randNumber == 0)
                            randMinus = coupons.size() - 1;
                        if (randNumber == coupons.size() - 1)
                            randPlus = 0;

                    Kupon1 kUp = coupons.get(randMinus);
                    Kupon1 k1 = coupons.get(randNumber);
                    Kupon1 kDown = coupons.get(randPlus);

     //               setTextFields(kUp, k1, kDown);                                    //Wyswietlanie couponwo
                    increaseProgress();
                    changeImages();

                    System.out.println(counter + " seconds");
                    counter--;
                } else {
                    int uCoupon = random.nextInt(0, coupons.size());
                    Kupon1 userCoupon = coupons.get(uCoupon);

                    if (userCoupon.isWin()) {
                        Image im = new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/Gold.png");

                        quantityOfMoney += 1000;
                        money.setText(quantityOfMoney + ".00$");
                        addToHistory("+1000$");

                        result.setStyle("-fx-fill: #6CC500;");
                        result.setText("WYGRAŁEŚ +1000$!");

                        im1.setImage(im);
                        im2.setImage(im);
                        im3.setImage(im);

                    } else {
                        Image image = new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/dead.png");

                        quantityOfMoney -= 1000;
                        money.setText(quantityOfMoney + ".00$");
                        addToHistory("-1000$");

                        result.setStyle("-fx-fill: #C90707;");
                        result.setText("PRZEGRANA -1000$");


                        im1.setImage(image);
                        im2.setImage(image);
                        im3.setImage(image);
                    }

                    couponRaw.setText(userCoupon.getNumber() + "   " + userCoupon.isWin() + "   " + userCoupon.isCzyWykorzystany());
                    timer.cancel();
                    progress = 0;
                    progressBar.setProgress(progress);
                    startSlider.setDisable(false);
                    startSlider.setValue(0);

                }
            }
        };
        timer.schedule(task, 0, 1000);
    }
    public void changeImages() {
        Random randImage = new Random();
        List<Image> images = new ArrayList<>();
        images.add(new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/Apple.png"));
        images.add(new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/Cherry.png"));
        images.add(new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/Cocktail.png"));
        images.add(new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/money.png"));


        im1.setImage(images.get(randImage.nextInt(0,images.size())));
        im2.setImage(images.get(randImage.nextInt(0,images.size())));
        im3.setImage(images.get(randImage.nextInt(0,images.size())));

        im4.setImage(images.get(randImage.nextInt(0,images.size())));
        im5.setImage(images.get(randImage.nextInt(0,images.size())));
        im6.setImage(images.get(randImage.nextInt(0,images.size())));


        im7.setImage(images.get(randImage.nextInt(0,images.size())));
        im8.setImage(images.get(randImage.nextInt(0,images.size())));
        im9.setImage(images.get(randImage.nextInt(0,images.size())));
    }


    public void increaseProgress() {
        progress += 0.25;
        progressBar.setProgress(progress);
    }
    public List<Kupon1> loadCouponsToList() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));
        List<Kupon1> newList = new ArrayList<>();


        while (sc.hasNextLine()) {
            String tx = sc.nextLine();
            String[] tab1 = tx.split("-");
            String[] tab2 = tx.split("\\*");

            String txx = tab1[2];
            String[] atx = txx.split("    ");


            newList.add(new Kupon1(tab1[0], Boolean.parseBoolean(tab1[1]), Boolean.parseBoolean(atx[0]), LocalDateTime.parse(tab2[1])));
        }

        return newList;
    }
    @FXML
    public void alert(String typeOfAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("WYGRANA!");
        alert.setContentText(typeOfAlert);
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void playSuperGame() {

        if (quantityOfMoney < 1000) {
            alert("Masz za mało gotówki aby zagrać");
        } else {
            Timer timer = new Timer();
            couponRaw.setText("");

            quantityOfMoney -= 1000;
            money.setText(quantityOfMoney + ".00$");
            addToHistory("-1000$");

            TimerTask task = new TimerTask() {
                int counter = 4;
                @Override
                public void run() {
                    if (counter > 0) {
                        startSlider.setDisable(true);
                        result.setText("Losuje...");

                        increaseProgress();
                        changeImages();
                        System.out.println(counter + " seconds");
                        counter--;
                    } else {
                        Random random1 = new Random();

                        int randNumber = random1.nextInt(0, coupons.size());
                        int randMinus = randNumber - 1;
                        int randPlus = randNumber + 1;

                        if (randNumber == 0)
                            randMinus = coupons.size() - 1;
                        if (randNumber == coupons.size() - 1)
                            randPlus = 0;

                        Kupon1 kUp = coupons.get(randMinus);
                        Kupon1 k1 = coupons.get(randNumber);
                        Kupon1 kDown = coupons.get(randPlus);


                        if ((k1.isWin()  &&  !k1.isCzyWykorzystany()  &&
                                (kUp.isWin()  &&  !kUp.isCzyWykorzystany())  &&
                                (kDown.isWin()  &&  !kDown.isCzyWykorzystany()))) {

                            result.setText("WYGRAŁEŚ");
                            Image im = new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/starsWIN.png");

                            im1.setImage(im);
                            im2.setImage(im);
                            im3.setImage(im);

                            im4.setImage(im);
                            im5.setImage(im);
                            im6.setImage(im);

                            im7.setImage(im);
                            im8.setImage(im);
                            im9.setImage(im);

                            quantityOfMoney += 10000;
                            money.setText(quantityOfMoney + ".00$");
                            addToHistory("+10.000$");

                            result.setStyle("-fx-fill: #6CC500;");
                            result.setText("WYGRAŁEŚ +10.000$!!!");


                        } else {
                            Image image = new Image("file:/Users/tomek/Downloads/LotteryCopy/src/main/resources/icons/dead.png");

                            quantityOfMoney -= 1000;
                            money.setText(quantityOfMoney + ".00$");
                            addToHistory("-1000$");

                            result.setStyle("-fx-fill: #C90707;");
                            result.setText("PRZEGRANA -1000$");

                            im1.setImage(image);
                            im2.setImage(image);
                            im3.setImage(image);
                        }

                        couponRaw.setText(k1.getNumber() + "   " + k1.isWin() + "   " + k1.isCzyWykorzystany());
                        timer.cancel();
                        progress = 0;
                        progressBar.setProgress(progress);
                        startSlider.setDisable(false);
                        startSlider.setValue(0);

                    }
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }



    public void addToHistory(String value) {
        if (moneyProcess2.getText().length() > 4) {
            if (moneyProcess2.getText().startsWith("+"))
                moneyProcess3.setStyle("-fx-fill: #6CC500;");
            if (moneyProcess2.getText().startsWith("-"))
                moneyProcess3.setStyle("-fx-fill: #C90707;");

            moneyProcess3.setText(moneyProcess.getText());
        }

        if (moneyProcess.getText().length() > 4) {
            if (moneyProcess.getText().startsWith("+"))
                moneyProcess2.setStyle("-fx-fill: #6CC500;");
            if (moneyProcess.getText().startsWith("-"))
                moneyProcess2.setStyle("-fx-fill: #C90707;");

            moneyProcess2.setText(moneyProcess.getText());
        }

        if (value.startsWith("+")) {
            moneyProcess.setStyle("-fx-fill: #6CC500;");
            moneyProcess.setText(value);

        } else if (value.startsWith("-")) {
            moneyProcess.setStyle("-fx-fill: #C90707;");
            moneyProcess.setText(value);
        }




    }
}
