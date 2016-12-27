package com.example.soul.flashcard;

import android.app.Activity;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by soul on 27/12/16.
 */

public class FileParser extends Thread {
    private ParcelFileDescriptor file;
    private InterfaceFlashProvider fp;
    protected Context context;

    public FileParser(ParcelFileDescriptor _file, Context c){
        file = _file;
        fp = new InterfaceFlashProvider(c);
        context = c;
    }

    public void run(){
        //XmlPullParserFactory pullParserFactory;
        try {
            Log.d("start:","start parsing...******************************************");
            InputStream is = new ParcelFileDescriptor.AutoCloseInputStream(file);
            //context.getAssets().o
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc =  builder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList list = doc.getElementsByTagName("carte");

            for (int i=0; i < list.getLength(); i++){
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    NodeList childs = node.getChildNodes();
                    Carte carte = new Carte();
                    for (int j=0; j < childs.getLength(); j++){
                        Node node2 = childs.item(j);
                        if (node2.getNodeType() == Node.ELEMENT_NODE) {
                            String text = node2.getTextContent();
                            String nom = node2.getNodeName();
                            carte.set(nom,text);
                            //Log.d("nom: ",text);
                        }
                    }
                    fp.insertCarte(carte);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
