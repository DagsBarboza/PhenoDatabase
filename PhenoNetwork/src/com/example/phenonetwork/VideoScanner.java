package com.example.phenonetwork;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.elements.RGBDataSink;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.vaadin.ui.CustomComponent;

public class VideoScanner extends CustomComponent{

	private static BufferedImage currentImage = null;
	private static JLabel currentImageLabel = new JLabel();
	private static Pipeline pipe;
	private static Reader reader = new MultiFormatReader();
	private static JFrame frame = new JFrame("Swing Video Test");
	private static DetectedPage sc = new DetectedPage();
	
	public VideoScanner(DetectedPage detectedPage) {
		sc = detectedPage;
	}

	public VideoScanner() {
		// TODO Auto-generated constructor stub
	}

	public void launch() {
		String[] args = {};
		
		args = Gst.init("SwingVideoTest", args);
		pipe = new Pipeline("pipeline");
		ElementFactory.make("videotestsrc", "source");
		final Element videosrc = ElementFactory.make("dshowvideosrc", "source");
		final Element videofilter = ElementFactory.make("capsfilter", "flt");
		final Element videoMpeg = ElementFactory.make("ffmpegcolorspace","ffmpegcsp0");
//		final Element videoZbar = ElementFactory.make("zbar","zbar");
		
		videofilter.setCaps(Caps
				.fromString("video/x-raw-yuv, width=640, height=480"));

		
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				RGBDataSink.Listener imageCaptureListener = new RGBDataSink.Listener() {
					@Override
					public void rgbFrame(boolean isPreRollImage, int width,
							int height, IntBuffer rgb) {
						currentImage = getBufferedImage(width, height);
						copyDataToImage(rgb, currentImage, width, height);

						// one way to display image on JPanel
						ImageIcon icon = new ImageIcon(currentImage);
						currentImageLabel.setIcon(icon);
						
						LuminanceSource source = new BufferedImageLuminanceSource(currentImage);
						BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						
						try {
							Result result = reader.decode(bitmap);
							if (result!=null){
								System.out.println("FOUND>>>>>>>>>>>>>>>>>>>>>>>"+ result.getText());
								frame.dispose();
								pipe.setState(State.NULL);
//								sc.closeFrame();
							}
						} catch (NotFoundException e) {
							System.out.println(".....");
						} catch (ChecksumException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					    
					}

					private BufferedImage getBufferedImage(int width, int height) {
						BufferedImage bufferedImage = new BufferedImage(width,
								height, BufferedImage.TYPE_INT_RGB);
						bufferedImage.setAccelerationPriority(0.0f);
						return bufferedImage;
					}

					private void copyDataToImage(IntBuffer rgb,
							BufferedImage image, int width, int height) {
						int[] pixels = ((DataBufferInt) image.getRaster()
								.getDataBuffer()).getData();
						rgb.get(pixels, 0, width * height);
					}
				};

				final RGBDataSink videosink = new RGBDataSink("rgb",
						imageCaptureListener);

				pipe.addMany(videosrc,videoMpeg, videofilter, videosink);
				Element.linkMany(videosrc, videoMpeg, videofilter, videosink);

				final JButton captureButton = new JButton("Capture");
				captureButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						String outputFileName = "E:\\output.png";
						try {
							ImageIO.write(currentImage, "png", new File(
									outputFileName));
						} catch (IOException e) {
							System.err.println("File write error");
						}
					}
				});

				// Now create a JFrame to display the video output
				
				frame.setLayout(new GridLayout(1, 1));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				currentImageLabel.setPreferredSize(new Dimension(640, 480));
				frame.add(currentImageLabel);
//				frame.add(captureButton);
				frame.pack();
//				frame.setUndecorated(true);
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				frame.setFocusableWindowState(true);
				frame.setLocationRelativeTo(null);
				
				frame.addWindowListener(new WindowAdapter() {
				    @Override
				    public void windowClosing(WindowEvent e) {
				    	pipe.setState(State.NULL);
				    	
				    }
				});
				
				
				frame.addComponentListener(new ComponentListener() {
					
					@Override
					public void componentShown(ComponentEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void componentResized(ComponentEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void componentMoved(ComponentEvent e) {
						frame.setLocationRelativeTo(null);
						
					}
					
					@Override
					public void componentHidden(ComponentEvent e) {
						// TODO Auto-generated method stub
						
					}
				});

				// Start the pipeline processing
				pipe.setState(State.PLAYING);
			}
		});
		
		
		
	}
	
	

}
