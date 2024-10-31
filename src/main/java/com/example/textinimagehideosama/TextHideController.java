package com.example.textinimagehideosama;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class TextHideController {




    public int bitSel=1;
    public String normalImagepath="";
    public String hideImagepath="";
    @FXML
    public Label redLabel;
    @FXML
    public Label blueLabel;
    @FXML
    public Label greenLabel;
    @FXML
    public Button Bit1Btn;

    @FXML
    public Button Bit2Btn;
    @FXML
    public Button Bit3Btn;
    @FXML
    public TextArea plainText;
    @FXML
    public ImageView normalImage;
    @FXML
    public ImageView hideImage;

    public void uploadTextFile(){
        FileChooser fileChoser=new FileChooser();
        File file=fileChoser.showOpenDialog(new Stage());
        try{
            Scanner scanner =new Scanner(file);
            while(scanner.hasNextLine()){
            plainText.appendText(scanner.nextLine()+"\n");
            }
        }catch(Exception e){
            System.out.println("Erorr open file");
        }
    }

    public void clearBtn(){
        plainText.clear();
        normalImagepath="";
        hideImagepath="";
        normalImage.setImage(null);
        hideImage.setImage(null);
    }
    public void uploadNormalImage(){
        FileChooser fileChoser=new FileChooser();
        FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP Files (*.bmp)", "*.bmp");
        fileChoser.getExtensionFilters().add(bmpFilter);
        File file=fileChoser.showOpenDialog(new Stage());
        try{
            Image img=new Image(file.getPath());
            normalImagepath=file.getPath();
            normalImage.setImage(img);

        }catch(Exception e){
            System.out.println("Erorr open file");
        }
    }
public void uploadHideImage(){
    FileChooser fileChoser=new FileChooser();
    FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP Files (*.bmp)", "*.bmp");
    fileChoser.getExtensionFilters().add(bmpFilter);
    File file=fileChoser.showOpenDialog(new Stage());
    try{
        Image img=new Image(file.getPath());
        hideImagepath=file.getPath();
        hideImage.setImage(img);
        System.out.println(hideImagepath);
    }catch(Exception e){
        System.out.println("Erorr open file");
    }
}
    public void hideText() {
        if (!normalImagepath.isEmpty() && !plainText.getText().isEmpty()) {
            String msgInBinary = convStrToBin(plainText.getText());
            if(bitSel==1){
                hideOneBit(msgInBinary);
            }
            else if(bitSel==2){
                hideTwoBit(msgInBinary);

            }
            else if(bitSel==3){
                hideThreeBit(msgInBinary);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "An error has occurred!", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void restoreText(){
        if (!hideImagepath.isEmpty()) {

            if(bitSel==1){
               restoreOneBit();
            }
            else if(bitSel==2){
                restoreTwoBit();

            }
            else if(bitSel==3){
                restoreThreeBit();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "An error has occurred!", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void restoreOneBit() {
        BufferedImage read;
        StringBuilder msgInBinary = new StringBuilder();
        try{

            read = ImageIO.read(new File(hideImagepath));
            int width = read.getWidth();
            int height = read.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = read.getRGB(x, y);

                    msgInBinary.append((rgb >> 16) & 0x01);
                }
            }
        }
        catch(Exception e){
            System.out.println("Erorr open file");
        }
        String x=convBinToStr(msgInBinary);
        plainText.setText(x);
    }
    private static String convBinToStr(StringBuilder binaryString) {
        StringBuilder result = new StringBuilder();


        for (int i = 0; i < binaryString.length(); i += 8) {

            if (i + 8 > binaryString.length()) {
                break;
            }


            String byteString = binaryString.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);


            char character = (char) charCode;


            if (character == '\0') {
                break;
            }


            result.append(character);
        }

        return result.toString();
    }
    private void restoreTwoBit() {
        BufferedImage read;
        StringBuilder msgInBinary = new StringBuilder();
        try{

            read = ImageIO.read(new File(hideImagepath));
            int width = read.getWidth();
            int height = read.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = read.getRGB(x, y);
                    int lsbRed = (rgb >> 16) & 0x01;
                    int lsbGreen = (rgb >> 8) & 0x01;


                    msgInBinary.append(lsbRed);
                    msgInBinary.append(lsbGreen);
                }
            }
        }
        catch(Exception e){
            System.out.println("Erorr open file");
        }
        String x=convBinToStr(msgInBinary);
        plainText.setText(x);
    }

    private void restoreThreeBit() {
        BufferedImage read;
        StringBuilder msgInBinary = new StringBuilder();
        try{

            read = ImageIO.read(new File(hideImagepath));
            int width = read.getWidth();
            int height = read.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = read.getRGB(x, y);
                    int lsbRed = (rgb >> 16) & 0x01;
                    int lsbGreen = (rgb >> 8) & 0x01;
                    int lsbBlue = rgb & 0x01;
                    msgInBinary.append(lsbRed);
                    msgInBinary.append(lsbGreen);
                    msgInBinary.append(lsbBlue);
                }
            }
        }
        catch(Exception e){
            System.out.println("Erorr open file");
        }
        String x=convBinToStr(msgInBinary);
        plainText.setText(x);
    }


    private void hideOneBit(String msgInBinary) {
        BufferedImage read;
        try{
            read = ImageIO.read(new File(normalImagepath));
            for (int y = 0; y < read.getHeight(); y++) {
                for (int x = 0; x < read.getWidth(); x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    red = red & 0xFE;
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);
                }
            }
            int msgLength = msgInBinary.length();
            int msgIndex = 0;

            for (int y = 0; y < read.getHeight() && msgIndex < msgLength; y++) {
                for (int x = 0; x < read.getWidth() && msgIndex < msgLength; x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        red = (red & 0xFE) | 0x01;
                    } else {
                        red = red & 0xFE;
                    }
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);

                    msgIndex++;
                }
            }


            ImageIO.write(read, "bmp", new File("C:\\Users\\osamaammar\\Desktop\\test\\osama.bmp")); // Change the path accordingly


        }catch(Exception e){

        }
    }
    private void hideTwoBit(String msgInBinary) {
        BufferedImage read;
        try{
            read = ImageIO.read(new File(normalImagepath));
            for (int y = 0; y < read.getHeight(); y++) {
                for (int x = 0; x < read.getWidth(); x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    red = red & 0xFE;
                    green = green & 0xFE;
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);
                }
            }
            int msgLength = msgInBinary.length();
            int msgIndex = 0;

            for (int y = 0; y < read.getHeight() && msgIndex < msgLength; y++) {
                for (int x = 0; x < read.getWidth() && msgIndex < msgLength; x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        red = (red & 0xFE) | 0x01;
                    } else {
                        red = red & 0xFE;
                    }
                    msgIndex++;
                    if(!(msgIndex < msgLength)){
                        break;
                    }
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        green = (green & 0xFE) | 0x01;
                    } else {
                        green = green & 0xFE;
                    }
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);

                    msgIndex++;
                }
            }


            ImageIO.write(read, "bmp", new File("C:\\Users\\osamaammar\\Desktop\\test\\osama.bmp")); // Change the path accordingly


        }catch(Exception e){

        }
    }
    private void hideThreeBit(String msgInBinary) {
        BufferedImage read;
        try{
            read = ImageIO.read(new File(normalImagepath));
            for (int y = 0; y < read.getHeight(); y++) {
                for (int x = 0; x < read.getWidth(); x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    red = red & 0xFE;
                    green = green & 0xFE;
                    blue = blue & 0xFE;
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);
                }
            }
            int msgLength = msgInBinary.length();
            int msgIndex = 0;

            for (int y = 0; y < read.getHeight() && msgIndex < msgLength; y++) {
                for (int x = 0; x < read.getWidth() && msgIndex < msgLength; x++) {
                    int rgb = read.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        red = (red & 0xFE) | 0x01;
                    } else {
                        red = red & 0xFE;
                    }
                    msgIndex++;
                    if(!(msgIndex < msgLength)){
                        break;
                    }
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        green = (green & 0xFE) | 0x01;
                    } else {
                        green = green & 0xFE;
                    }
                    msgIndex++;
                    if(!(msgIndex < msgLength)){
                        break;
                    }
                    if (msgInBinary.charAt(msgIndex) == '1') {
                        blue = (blue & 0xFE) | 0x01;
                    } else {
                        blue = blue & 0xFE;
                    }
                    int newRgb = (red << 16) | (green << 8) | blue;
                    read.setRGB(x, y, newRgb);

                    msgIndex++;
                }
            }


            ImageIO.write(read, "bmp", new File("C:\\Users\\osamaammar\\Desktop\\test\\osama.bmp")); // Change the path accordingly


        }catch(Exception e){

        }
    }
    private String convStrToBin(String text) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            while (binaryChar.length() < 8) {
                binaryChar = "0" + binaryChar;
            }
            binaryString.append(binaryChar);
        }
        return binaryString.toString();
    }

    public void setOneBit(){
        Bit1Btn.getStyleClass().clear();
        Bit2Btn.getStyleClass().clear();
        Bit3Btn.getStyleClass().clear();
        Bit1Btn.getStyleClass().add("bit-btn-active");
        Bit2Btn.getStyleClass().add("bit-btn");
        Bit3Btn.getStyleClass().add("bit-btn");


        redLabel.getStyleClass().clear();
        blueLabel.getStyleClass().clear();
        greenLabel.getStyleClass().clear();


        redLabel.getStyleClass().add("labe-active");
        greenLabel.getStyleClass().add("label-normal");
        blueLabel.getStyleClass().add("label-normal");
        bitSel=1;
    }
    public void setTwoBit(){
        Bit1Btn.getStyleClass().clear();
        Bit2Btn.getStyleClass().clear();
        Bit3Btn.getStyleClass().clear();
        Bit2Btn.getStyleClass().add("bit-btn-active");
        Bit1Btn.getStyleClass().add("bit-btn");
        Bit3Btn.getStyleClass().add("bit-btn");


        redLabel.getStyleClass().clear();
        blueLabel.getStyleClass().clear();
        greenLabel.getStyleClass().clear();


        redLabel.getStyleClass().add("labe-active");
        greenLabel.getStyleClass().add("labe-active");
        blueLabel.getStyleClass().add("label-normal");
        bitSel=2;
    }
    public void setThreeBit(){
        Bit1Btn.getStyleClass().clear();
        Bit2Btn.getStyleClass().clear();
        Bit3Btn.getStyleClass().clear();
        Bit3Btn.getStyleClass().add("bit-btn-active");
        Bit2Btn.getStyleClass().add("bit-btn");
        Bit1Btn.getStyleClass().add("bit-btn");



        redLabel.getStyleClass().clear();
        blueLabel.getStyleClass().clear();
        greenLabel.getStyleClass().clear();


        redLabel.getStyleClass().add("labe-active");
        greenLabel.getStyleClass().add("labe-active");
        blueLabel.getStyleClass().add("labe-active");
        bitSel=3;
    }
}
