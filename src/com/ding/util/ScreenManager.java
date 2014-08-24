package com.ding.util;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import com.ding.Config;

public class ScreenManager {
	
/*
 * Class: ScreenManager
 * *****************************
 * Attributes: 
 * private GraphicsDevice device;
 * *****************************
 * Methods:
 * public DisplayMode getCurrentDisplayMode() {}
 * public void setFullScreen(DisplayMode displayMode) {}
 * public JFrame getFullScreenWindow() {}
 * */
	
	

	private GraphicsDevice device;
	


	public ScreenManager() {
		//Init ScreenManager by getting default screen devices info
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}

	/**
	 * 
	* @Title: getCurrentDisplayMode 
	* @Description:ge current display mode
	* @param @return
	* @return DisplayMode    
	* @throws
	 */
	public DisplayMode getCurrentDisplayMode() {
		//Get the default display mode EG: 1280 X 800 for mac book pro
		return device.getDisplayMode();
	}

	/**
	 * 
	* @Title: getFisrtSupportedDisplayMode 
	* @Description: get the first supported display mode in a list of defined
	*  display mode
	* @param @param modes
	* @param @return
	* @return DisplayMode    
	* @throws
	 */
	public DisplayMode getFisrtSupportedDisplayMode(DisplayMode[] modes){
		DisplayMode supportedModes[] = device.getDisplayModes();
        for (int i = 0; i < modes.length; i++) {
            for (int j = 0; j < supportedModes.length; j++) {
                if (modes[i].equals(supportedModes[j])) {
                    return modes[i];
                }
            }
        }
        return null;
	}
	
	

	/**
	 * 
	* @Title: getSupportedDisplayModes 
	* @Description: get the supported diplay mode
	* @param @return
	* @return DisplayMode[]    
	* @throws
	 */
    public DisplayMode[] getSupportedDisplayModes() {
        return device.getDisplayModes();
    }
    
    
    /**
     * 
    * @Title: setFullScreen 
    * @Description: set to full screen with a display mode
    * @param @param displayMode
    * @return void    
    * @throws
     */
	public void setFullScreen(DisplayMode displayMode) {
		//Create and init a full screen JFrame
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);

		device.setFullScreenWindow(frame);

		if (displayMode != null && device.isDisplayChangeSupported()) {
			try {
				device.setDisplayMode(displayMode);
			} catch (IllegalArgumentException ex) {

			}
			frame.setSize(displayMode.getWidth(), displayMode.getHeight());
			Config.PANEL_WIDTH = displayMode.getWidth();
			Config.PANEL_HEIGHT = displayMode.getHeight();
		}
		try {
			Runnable r = new Runnable() {
				@Override
				public void run() {
					//Create buffer strategy which can speed up image processing 
					if (frame.getBufferStrategy() == null) {
						frame.createBufferStrategy(2);
					}
				}
			};
			new Thread(r).start();
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: getFullScreenWindow 
	* @Description: return a full screen windows
	* @param @return
	* @return JFrame    
	* @throws
	 */
	public JFrame getFullScreenWindow() {
		//Get a full screen JFrame 
		return (JFrame) device.getFullScreenWindow();
	}
	
	public void setFullScreen(JFrame frame, DisplayMode displayMode){
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);

		device.setFullScreenWindow(frame);

		frame.setSize(displayMode.getWidth(), displayMode.getHeight());
		
	}
}