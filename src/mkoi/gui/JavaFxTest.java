package mkoi.gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

import mkoi.AKSLogger;
import mkoi.Logger;
import mkoi.SSLogger;
import mkoi.algorithms.AKS;
import mkoi.algorithms.Solovay_Strassen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class JavaFxTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
    	TextArea ta = TextAreaBuilder.create().prefWidth(800).prefHeight(600).wrapText(true).build();
        Console console = new Console(ta);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
        Scene app = new Scene(ta);

        primaryStage.setScene(app);
        primaryStage.show();

        SSLogger ssLogger = new SSLogger(Logger.CONSOLE_MODE);
		AKSLogger aksLogger = new AKSLogger(Logger.CONSOLE_MODE);
		 
		Solovay_Strassen ss = new Solovay_Strassen();
		ss.setListener(ssLogger);
		 
		AKS aks = new AKS();
		aks.setListener(aksLogger);
		
		boolean ans = false;
		
		ans = ss.isPrime(new BigInteger("8"),30);
		System.out.println(ans);
		
		ans = aks.isPrime(new BigInteger("10"));
        System.out.println(ans);
        
        ps.close();
    }
    public static class Console extends OutputStream 
    {

        private TextArea output;
        private int [] bytes;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException 
        {
           // output.appendText(String.valueOf((char) i));
        }
        @Override
        public void write(byte b[], int off, int len) throws IOException 
        {
        	byte [] temp = new byte[len];
        	
            if (b == null) {
                throw new NullPointerException();
            } else if ((off < 0) || (off > b.length) || (len < 0) ||
                       ((off + len) > b.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }
            for (int i = 0 ; i < len ; i++) 
            {
            	temp[i] = b[off+i];
            }
            output.appendText(new String(temp,"UTF-8"));
        }
    }

    
}

