package com.example.imageprocess.imageprocess;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
public class ProcessAlgo {
    public static void GetSetPixel(File f) throws IOException {
        BufferedImage img=ImageIO.read(f);
        int height=img.getHeight();
        int width=img.getWidth();
        int p=img.getRGB(0, 0);
        int a=(p>>24) &0xFF;
        int r=(p>>16) &0xFF;
        int g=(p>>8) &0xFF;
        int b=p&0xFF;
        a=255;
        r=100;
        g=150;
        b=200;
        p=(a<<24)|(r<<16)|(g<<8)|b;
        img.setRGB(0, 0, p);
        File res=new File(f.getAbsolutePath());
        ImageIO.write(img, "png", res);
    }  
    public static void GrayScale(File f) throws IOException{
        BufferedImage img=ImageIO.read(f);
        int width=img.getWidth();
        int height=img.getHeight();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int p=img.getRGB(x,y);
                int a=(p>>24) &0xFF;
                int r=(p>>16)&0xFF;
                int g=(p>>8) &0xFF;
                int b=p&0xff;
                int avg=(r+g+b)/3;
                p=(a<<24)|(avg<<24)|(avg<<8)|avg;
                img.setRGB(x, y, p);
            }
        }
        ImageIO.write(img, "png", f);
    }
    public static void Negative(File f) throws IOException{
        BufferedImage img=ImageIO.read(f);
        int width=img.getWidth();
        int height=img.getHeight();
        for(int y=0;y<height;y++)
            for(int x=0;x<width;x++){
                int p=img.getRGB(x,y);  
                int a=(p>>24) & 0xFF;
                int r=(p>>16) & 0xff;
                int g=(p>>8) & 0xff;
                int b=(p)&0xff;
                r=255-r;
                g=255-g;
                b=255-b;
                p=(a<<24)|(r<<16)|(g<<8)|b;
                img.setRGB(x, y, p);
            }
        ImageIO.write(img,"png" ,f);
    }
    public static void RedColored(File f) throws IOException{
        BufferedImage img=ImageIO.read(f);
        int width=img.getWidth();
        int height=img.getHeight();
        for(int y=0;y<height;y++)
            for(int x=0;x<width;x++){
                int p=img.getRGB(x, y);
                int a=(p>>24) &0xff;
                int r=(p>>16) & 0xff;
                p=(a<<24)|(r<<16)|0<<8|0;
                img.setRGB(x, y, p);
            }
        ImageIO.write(img, "png", f);
    }
    public static void SepiaImage(File f) throws IOException{
        BufferedImage img=ImageIO.read(f);
        int width=img.getWidth();
        int height=img.getHeight();
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                int p=img.getRGB(x, y);
                int a=(p>>24)&0xff;
                int r=(p>>16)&0xff;
                int g=(p>>8)&0xff;
                int b=p&0xff;
                int newRed=(int)(0.393*r+0.769*g+0.189*b);
                int newGreen=(int)(0.3948*r+0.686*g+0.168*b);
                int newBlue=(int)(0.272*r+0.534*g+0.131*b);
                r=(newRed>255)?255:newRed;
                g=(newGreen>255)?255:newGreen;
                b=(newBlue>255)?255:newBlue;
                p=(a<<24)|(r<<16)|(g<<8)|b;
                img.setRGB(x, y, p);
            }
        }
        ImageIO.write(img, "png", f);
    }
    public static void RandomColorImage(File f) throws IOException{
        int width=400, height=400;
        BufferedImage img=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int y=0;y<width;y++)
            for(int x=0;x<height;x++){
                int a=(int)(Math.random()*256);
                int r=(int)(Math.random()*256);
                int g=(int)(Math.random()*256);
                int b=(int)(Math.random()*256);
                int p=(a<<24)|(r<<16)|(g<<8)|b;
                img.setRGB(x, y, p);
            }
        ImageIO.write(img, "png", f);
    }
    public static void MirrorImage(File f) throws IOException{
        BufferedImage srcImg=ImageIO.read(f);
        int width=srcImg.getWidth();
        int height=srcImg.getHeight();
        BufferedImage destImg=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                int p = srcImg.getRGB(lx, y);
                destImg.setRGB(rx, y, p);
            }
        }
        ImageIO.write(destImg, "png", f);
    }
    public static void Watermark(File f) throws IOException{
        BufferedImage img=ImageIO.read(f);
        BufferedImage tmp=new BufferedImage(img.getWidth(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics=tmp.createGraphics();
        graphics.drawImage(tmp, 0, 0, null);
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 80));
        graphics.setColor(new Color(255, 0, 0, 40));
        String watermark="Watermark generated";
        graphics.drawString(watermark, img.getWidth()/5, img.getHeight()/3);
        graphics.dispose();
        ImageIO.write(tmp, "png", f);
    }

}
