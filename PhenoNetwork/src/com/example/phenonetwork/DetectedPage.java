package com.example.phenonetwork;


public class DetectedPage{

	public DetectedPage(){
		
		VideoScanner vs = new VideoScanner();
		vs.launch();
		
		
		
	}

	public void closeFrame(DetectedPage sc) {
		System.out.println("Now Closing......");
	}
}
