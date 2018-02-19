import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>
{
	private GridPane grid = new GridPane();
	private Scene scene = new Scene(grid);
	private TextField dec = new TextField();
	private TextField bin = new TextField();
	private TextField oct = new TextField();
	private TextField hex = new TextField();
	private RadioButton d = new RadioButton("Dezimalsystem");
	private RadioButton b = new RadioButton("Binaersystem");
	private RadioButton o = new RadioButton("Oktalsystem");
	private RadioButton h = new RadioButton("Hexadezimalsystem");
	private Text tFrom = new Text("Startsystem Waehlen:");
	private Text tTo = new Text("Ausgabe:");
	private ToggleGroup radios = new ToggleGroup();
	private Button confirm = new Button(
			"                            Umwandeln                             ");
	private Key keyHandle = new Key();
	private FileWriter errorWriter;
	private FileWriter logger;
	private Date time;
	private File csv;
	private FileWriter csvWriter;
	private Text info = new Text(
			"Ausgabe wird auch als .txt und .csv gespeichert.");

	public Main()
	{

	}

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception
	{
		File dir = new File("Logs/Errors");
		dir.mkdirs();
		dir = new File("Logs/Output");
		dir.mkdirs();
		this.csv = new File("Logs/Output/Output.csv");
		this.csvWriter = new FileWriter(this.csv, true);
		try (Scanner f = new Scanner(this.csv))
		{
			if (f.hasNext())
			{

			} else
			{
				this.csvWriter.write("Dezimal,Binaer,Oktal,Hexadezimal\n");
				this.csvWriter.flush();
			}
		} catch (IOException e)
		{

		}

		this.errorWriter = new FileWriter("Logs/Errors/errorLog.txt", true);
		this.logger = new FileWriter("Logs/Output/log.txt", true);
		this.d.setToggleGroup(this.radios);
		this.b.setToggleGroup(this.radios);
		this.o.setToggleGroup(this.radios);
		this.h.setToggleGroup(this.radios);
		this.d.setOnAction(this);
		this.b.setOnAction(this);
		this.o.setOnAction(this);
		this.h.setOnAction(this);
		this.grid.add(this.tFrom, 0, 0);
		this.grid.add(this.d, 1, 0);
		this.grid.add(this.b, 2, 0);
		this.grid.add(this.o, 3, 0);
		this.grid.add(this.h, 4, 0);
		this.d.setSelected(true);
		this.dec.setEditable(true);
		this.bin.setEditable(false);
		this.oct.setEditable(false);
		this.hex.setEditable(false);
		this.clearText();

		this.grid.add(this.tTo, 0, 1);
		this.grid.add(this.dec, 1, 1);
		this.grid.add(this.bin, 2, 1);
		this.grid.add(this.oct, 3, 1);
		this.grid.add(this.hex, 4, 1);

		this.dec.setOnKeyPressed(this.keyHandle);
		this.bin.setOnKeyPressed(this.keyHandle);
		this.oct.setOnKeyPressed(this.keyHandle);
		this.hex.setOnKeyPressed(this.keyHandle);
		this.grid.add(this.confirm, 2, 2);
		this.grid.add(this.info, 2, 3);

		this.confirm.setOnAction(this);

		stage.setTitle("Umrechnen von Zahlensystemen");
		stage.setScene(this.scene);
		stage.show();
	}

	@Override
	public void handle(ActionEvent e)
	{
		this.info.setText("");
		if (e.getSource().getClass().getName()
				.equals("javafx.scene.control.RadioButton"))
		{
			RadioButton r = (RadioButton) e.getSource();
			if (r.equals(this.d))
			{

				this.dec.setEditable(true);
				this.bin.setEditable(false);
				this.oct.setEditable(false);
				this.hex.setEditable(false);
				this.clearText();
			} else if (r.equals(this.b))
			{

				this.dec.setEditable(false);
				this.bin.setEditable(true);
				this.oct.setEditable(false);
				this.hex.setEditable(false);
				this.clearText();
			} else if (r.equals(this.o))
			{

				this.dec.setEditable(false);
				this.bin.setEditable(false);
				this.oct.setEditable(true);
				this.hex.setEditable(false);
				this.clearText();
			} else if (r.equals(this.h))
			{

				this.dec.setEditable(false);
				this.bin.setEditable(false);
				this.oct.setEditable(false);
				this.hex.setEditable(true);
				this.clearText();

			}
		} else if (e.getSource().equals(this.confirm))
		{
			setTexts();
		}
	}

	/**
	 * 
	 */

	private void setTexts()
	{
		try
		{

			if (this.radios.getSelectedToggle().equals(this.d))
			{

				this.oct.setText(Integer.toOctalString(Integer
						.parseInt(this.dec.getText())));
				this.bin.setText(Integer.toBinaryString(Integer
						.parseInt(this.dec.getText())));
				this.hex.setText(Integer.toHexString(Integer.parseInt(this.dec
						.getText())));
				this.logger.write("Decimal: " + this.dec.getText()
						+ "\tBinary: " + this.bin.getText() + "\tOctal: "
						+ this.oct.getText() + "\tHexadecimal: "
						+ this.hex.getText() + "\n");
				this.logger.flush();

			} else if (this.radios.getSelectedToggle().equals(this.b))
			{
				this.dec.setText(Integer.toString(Integer.parseInt(
						this.bin.getText(), 2)));
				this.oct.setText(Integer.toOctalString(Integer.parseInt(
						this.bin.getText(), 2)));
				this.hex.setText(Integer.toHexString(Integer.parseInt(
						this.bin.getText(), 2)));
				this.logger.write("Binary: " + this.bin.getText()
						+ "\tDecimal: " + this.dec.getText() + "\tOctal: "
						+ this.oct.getText() + "\tHexadecimal: "
						+ this.hex.getText() + "\n");
				this.logger.flush();
			} else if (this.radios.getSelectedToggle().equals(this.o))
			{
				this.dec.setText(Integer.toString(Integer.parseInt(
						this.oct.getText(), 8)));
				this.bin.setText(Integer.toBinaryString(Integer.parseInt(
						this.oct.getText(), 8)));
				this.hex.setText(Integer.toHexString(Integer.parseInt(
						this.oct.getText(), 8)));
				this.logger.write("Octal: " + this.oct.getText()
						+ "\tDecimal: " + this.dec.getText() + "\tBinary: "
						+ this.bin.getText() + "\tHexadecimal: "
						+ this.hex.getText() + "\n");
				this.logger.flush();
			} else if (this.radios.getSelectedToggle().equals(this.h))
			{
				this.dec.setText(Integer.toString(Integer.parseInt(
						this.hex.getText(), 16)));
				this.oct.setText(Integer.toOctalString(Integer.parseInt(
						this.hex.getText(), 16)));
				this.bin.setText(Integer.toBinaryString(Integer.parseInt(
						this.hex.getText(), 16)));
				this.logger.write("Hexadecimal: " + this.hex.getText()
						+ "\tDecimal: " + this.dec.getText() + "\tBinary: "
						+ this.bin.getText() + "\tOctal: " + this.oct.getText()
						+ "\n");
				this.logger.flush();
			}
			this.time = new Date();
			this.logger.write(this.time.toString() + "\n\n");
			this.logger.flush();
			this.csvWriter.write(this.dec.getText() + "," + this.bin.getText()
					+ "," + this.oct.getText() + "," + this.hex.getText()
					+ "\n");
			this.csvWriter.flush();
		} catch (NumberFormatException e)
		{

			System.out.println("e");
			try
			{
				this.time = new Date();
				String err = new String();
				err += "|Input was no Integer " + e.getMessage() + "|\n";
				int l = err.length();
				String p = new String();
				for (int i = 1; i < l; i++)
				{
					p += "-";
				}
				err = p + "\n" + err;
				String ti = this.time.toString();
				err += "|" + ti;
				for (int i = 0; i < (l - ti.length()) - 3; i++)
				{
					err += " ";
				}
				err += "|\n";

				for (int i = 1; i < l; i++)
				{
					err += "-";
				}
				err += "\n";
				this.errorWriter.write(err);
				this.errorWriter.flush();
				this.info.setText("Falsche Eingabe");
			} catch (IOException e1)
			{
				e1.printStackTrace();

			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void clearText()
	{
		this.dec.setText("0");
		this.bin.setText("0");
		this.oct.setText("0");
		this.hex.setText("0");
	}

	public class Key implements EventHandler<KeyEvent>
	{

		@Override
		public void handle(KeyEvent e)
		{
			if (e.getCode().equals(KeyCode.ENTER))
			{
				setTexts();
			}

		}

	}

}
