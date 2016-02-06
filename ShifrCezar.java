package practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/* Шифр Цезаря с ключом например 3.
 Написать метод, который принимает имя файла и шифрует содержимое файла 
 шифром Цезаря.
 Реализовать Шифрование (каждый символ смещается по таблице символов на 
 3 элемента вправо А в D) и Дешифрование (каждый символ смещается по таблице символов на 3 
 элемента влево E в B)
 Например: abe15 -> cdh48 */

/**
 * @author Эркин
 *
 */
public class ShifrCezar extends Application {
	private static int key; // Ключ шифрования
	private static ShifrCezar sc;
	private static FileChooser chooser;

	private int countLine = 0;
	private static Stage stage = new Stage();

	public void kodCesar(File file) throws Exception {
		// Считываем данные с файла и подсчитываем кол-во строк countLine
		BufferedReader bf = new BufferedReader(new FileReader(file));
		while (bf.readLine() != null) {
			countLine++;
		}
		bf.close();
		// Создаем массив строк arr с количеством строк countLine
		String[] arr = new String[countLine];
		// Повторно считываем файл
		bf = new BufferedReader(new FileReader(file));
		// Вносим в массив строк arr каждую строку при считывании файла
		int i = 0;
		String line = "";
		while ((line = bf.readLine()) != null) {
			arr[i] = line;
			i++;
		}
		bf.close();
		// Выводим каждый символ в строке, кроме пробела, плюсуем к нему 3 и
		// преобразуем обратно в char
		for (int k = 0; k < countLine; k++) {
			String s = "";
			for (int q = 0; q < arr[k].length(); q++) {
				char c = arr[k].charAt(q);

				char c1 = (char) ((int) (c) + key); // сначала преобразуем
													// char в int для
													// добавления цифры,
													// затем обратно
													// преобразуем в char
				s = s + c1;
			}
			arr[k] = s; // заносим преобразованную строку обратно в массив arr
		}
		// Записываем массив строк arr в файл
		FileWriter fw = new FileWriter(file);
		for (int str = 0; str < countLine; str++) {
			fw.write(arr[str] + "\r\n");
		}
		fw.close();
		countLine = 0;
	}

	public void dekodCezar(File file) throws Exception {
		// Считываем данные с файла и подсчитываем кол-во строк countLine
		BufferedReader bf = new BufferedReader(new FileReader(file));
		while (bf.readLine() != null) {
			countLine++;
		}
		bf.close();
		// Создаем массив строк arr с количеством строк countLine
		String[] arr = new String[countLine];
		// Повторно считываем файл
		bf = new BufferedReader(new FileReader(file));
		// Вносим в массив строк arr каждую строку при считывании файла
		int i = 0;
		String line = "";
		while ((line = bf.readLine()) != null) {
			arr[i] = line;
			i++;
		}
		bf.close();
		// Выводим каждый символ в строке, кроме пробела, отнимаем от него 3 и
		// преобразуем обратно в char
		for (int k = 0; k < countLine; k++) {
			String s = "";
			for (int q = 0; q < arr[k].length(); q++) {
				char c = arr[k].charAt(q);

				char c1 = (char) ((int) (c) - key); // сначала преобразуем
													// char в int для
													// добавления цифры,
													// затем обратно
													// преобразуем в char
				s = s + c1;

			}
			arr[k] = s; // заносим преобразованную строку обратно в массив arr
		}
		// Записываем массив строк arr в файл
		FileWriter fw = new FileWriter(file);
		for (int str = 0; str < countLine; str++) {
			fw.write(arr[str] + "\r\n");
		}
		fw.close();
		countLine = 0;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		sc = new ShifrCezar();
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		this.stage = stage;
		stage.setTitle("ShifrCesar");
		stage.setScene(MyScene());
		stage.setResizable(false);
		stage.show();
	}

	private Scene MyScene() {
		// TODO Auto-generated method stub
		Group gr = new Group();
		HBox textBox = new HBox();
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setMinHeight(100);
		box.setSpacing(50);
		box.setPadding(new Insets(25));
		textBox.setPadding(new Insets(10, 0, 0, 25));
		gr.getChildren().addAll(textBox, box);

		Button kodCez = new Button("Kodding to shifr");
		kodCez.setMinSize(50, 50);
		kodCez.setAlignment(Pos.CENTER_RIGHT);
		Button dekodCez = new Button("Dekodding shifr");
		dekodCez.setMinSize(50, 50);
		dekodCez.setAlignment(Pos.CENTER);

		TextField keyField = new TextField();
		Text textRes = new Text(" Input key");
		textBox.getChildren().add(textRes);
		box.getChildren().addAll(keyField, kodCez, dekodCez);

		kodCez.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!keyField.getText().equals("")) {
					System.out.println(keyField.getText());
					chooser = new FileChooser();
					chooser.setTitle("Open file");
					File file = chooser.showOpenDialog(ShifrCezar.stage);
					sc.setKey(Integer.parseInt(keyField.getText()));
					if (file != null) {
						try {
							sc.kodCesar(file);
							textRes.setText("Completed - shifr");
							keyField.clear();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							textRes.setText("Key is not Number");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					textRes.setText(" Please input key");
				}

			}

		});

		dekodCez.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (!keyField.getText().equals("")) {
					System.out.println(keyField.getText());
					chooser = new FileChooser();
					chooser.setTitle("Open file");
					File file = chooser.showOpenDialog(ShifrCezar.stage);
					sc.setKey(Integer.parseInt(keyField.getText()));
					if (file != null) {
						try {
							sc.dekodCezar(file);
							textRes.setText("Completed - deshifr");
							keyField.clear();
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							textRes.setText("Key is not Number");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					textRes.setText(" Please input key");
				}

			}
		});

		Scene scene = new Scene(gr, 600, 100);

		return scene;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
