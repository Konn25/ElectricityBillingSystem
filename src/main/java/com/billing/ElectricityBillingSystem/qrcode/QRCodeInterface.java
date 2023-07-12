package com.billing.ElectricityBillingSystem.qrcode;

import java.io.File;

public interface QRCodeInterface {

    File generateQRCodeFromTextToSVG(String text);

    byte[] textToByteArray(String text);

    File byteArrayToQRCode(String text);

}
