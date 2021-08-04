package com.ydw.edge.model.vo;

import java.io.Serializable;

public class ScreenTransfer implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2882089394813051360L;
	/**
     * 屏幕分辨率
     */
    private Integer video;
    /**
     * 横屏还是竖屏
     */
    private Integer screen;

    /**
     * 屏幕宽
     */
    private Integer width ;
    /**
     * 屏幕高
     */
    private Integer heigth;
    
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }


    public ScreenTransfer(Integer video, Integer screen)
    {
        this.screen = 1;
        if ( screen != null) {
            this.screen = screen;
        }

        this.video = 720;
        if ( video != null) {
            this.video = video;
        }
        width = 1280 ;
        heigth = 720 ;
        Transer ();
    }

    ///横竖屏转换
    private void Transer ()
    {
        //480 = 576 * 1024
        //720 = 720 * 1280
        //1080 = 1080 * 1920
        if ( video == 480)
        {
            if ( screen == 1)  ///横屏
            {
                width = 1024;
                heigth = 576;
            }
            else if ( screen == 2)
            {
                heigth = 1024;
                width = 576 ;
            }

        }
        else if ( video == 720)
        {
            if ( screen == 1)  ///横屏
            {
                width = 1280;
                heigth = 720;
            }
            else if ( screen == 2)
            {
                heigth = 1280;
                width = 720 ;
            }

        }
        else if ( video == 1080)
        {
            if ( screen == 1)  ///横屏
            {
                width = 1920;
                heigth = 1080;
            }
            else if ( screen == 2)
            {
                heigth = 1920;
                width = 1080 ;
            }

        }
        return ;
    }
}
