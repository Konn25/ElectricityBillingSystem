package com.billing.ElectricityBillingSystem.qrcode;

import lombok.NoArgsConstructor;

import java.io.File;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;
@NoArgsConstructor
public class QRCodeGenerator implements QRCodeInterface{

    @Override
    public File generateQRCodeFromTextToSVG(String text) {
        return QRCode.from(text).withCharset("UTF-8").to(ImageType.PNG).svg();
    }

    @Override
    public byte[] textToByteArray(String text) {

        return QRCode.from(text).withCharset("UTF-8").stream().toByteArray();
    }

    @Override
    public File byteArrayToQRCode(String text) {

        byte[] bytes = textToByteArray(text);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(bytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        BufferedImage bufferedImage;
        File tempFile;
        try {
            bufferedImage = ImageIO.read(inputStream);
            tempFile = File.createTempFile("testQRCode",".png");
            ImageIO.write(bufferedImage, "png", tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tempFile;
    }


}
