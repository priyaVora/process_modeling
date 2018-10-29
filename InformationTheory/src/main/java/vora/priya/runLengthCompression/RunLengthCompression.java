package vora.priya.runLengthCompression;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class RunLengthCompression {
	private static Map<Integer, Integer> compressionMap;

	public static void main(String[] args) {
		String encodingValue = runLengthEncoding("Hi I am Priyaaaaaaaaaa. My Sister is PPriyanki.".getBytes());
		String decodingValue = runLengthDecoding(encodingValue);

		System.out.println("\n\n");
		compressionMap = new HashMap<>();
		System.out.println(" ");
		byte[] imageArray = null;
		try {
			imageArray = extractBytes("/home/priya/Pictures/openstreetmap.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String decodedImage = runLengthEncoding(imageArray);
		runLengthDecoding(decodedImage);

	}

	public static byte[] extractBytes(String ImageName) throws IOException {
		// open image
		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
		return (data.getData());
	}

//	public String getRunLength(){
//	    StringBuffer dest = new StringBuffer();        
//	    for(int i =0; i < imageByteArray.length; i++){
//	        int runlength = 1;
//	        while(i+1 < imageByteArray.length && imageByteArray[i] == imageByteArray[i+1]){
//	            runlength++;
//	            i++;
//
//	        }     
//
//
//	        dest.append(runlength);  
//
//	        dest.append(imageByteArray[i]);
//
//	    }
//	    return dest.toString();
//	}

	public static String runLengthEncoding(byte[] bs) {
		String encodedString = "";
		for (int i = 0, count = 1; i < bs.length; i++) {
			if (i + 1 < bs.length && bs[i] == bs[i + 1]) {
				count++;
			} else {
				encodedString = encodedString.concat(Integer.toString(count)).concat("$").concat(bs[i] + "%%");
				count = 1;
			}
		}
		System.out.println("------------");
		String encodedStringDelimited = encodedString.replace("$", "");
		encodedStringDelimited = encodedStringDelimited.replaceAll("%%", "");
		System.out.println("Encoded Value: " + encodedStringDelimited);
		return encodedString;
	}

	public static String runLengthDecoding(String text) {
		String decodedString = "";
		String decodedValue = "";
		char[] listOfCharacters = text.toCharArray();

		char frequency = 0;
		char nextChar = 0;
		String totalFrequency = "";

		for (int i = 0; i < listOfCharacters.length; i++) {
			frequency = listOfCharacters[i];
			if (frequency != '%') {
				totalFrequency += frequency;
				if ((i + 1) != listOfCharacters.length) {
					nextChar = listOfCharacters[i + 1];
					//i++;
					if (nextChar != '$') {
						totalFrequency += nextChar;
					}
					if (nextChar == '$') {
						String eachByteNumber = "";
						i++;
						nextChar = listOfCharacters[i + 1];
						i++;

						eachByteNumber += nextChar;
						while (nextChar != '%') {
							for (int j = 0; j < Integer.parseInt(frequency + ""); j++) {
								decodedValue += nextChar;
								i++;
								nextChar = listOfCharacters[i];
								eachByteNumber += nextChar;
							}
						}
						totalFrequency = "";
						if (nextChar == '%') {
							decodedValue += nextChar;
							eachByteNumber = eachByteNumber.replace("%", "");
							String strAsciiTab = Character.toString((char) Integer.parseInt(eachByteNumber));
							int count = 1;
							decodedString += strAsciiTab;

							if (Integer.parseInt(frequency + "") > 1) {
								decodedString += strAsciiTab;
								count++;
							}
							eachByteNumber = "";

						}
					}
				}
			}
		}
		System.out.println("Decoded Value: " + decodedString);
		return decodedValue;
	}
}
