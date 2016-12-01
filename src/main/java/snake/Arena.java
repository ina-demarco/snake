package snake;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Arena extends Application {
	private static final String YOU_WIN = "You WIN!";
	private static final String GAME_OVER = "Game Over";
	private static final String CONFIG_PROPERTIES_FILE = "config.properties";
	private static final String KEY_MAX_FOOD = "maxFood";
	private static final String KEY_Y = "y";
	private static final String KEY_X = "x";
	public int x = 20;
	public int y = 20;
	public int maxFood = 3;
	private GameController gameController;
	private static int scaling = 20;
	private Canvas canvas;

	public Arena() {
		super();
		this.loadProperties();
		this.gameController = new GameController(this);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Snake");

		Group root = new Group();
		canvas = new Canvas(this.x * scaling, this.y * scaling);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		renderFrame(gc);

		root.getChildren().add(canvas);

		Scene scene = new Scene(root);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					gameController.setCurrentDirection(Direction.UP);
					break;
				case DOWN:
					gameController.setCurrentDirection(Direction.DOWN);
					break;
				case LEFT:
					gameController.setCurrentDirection(Direction.LEFT);
					break;
				case RIGHT:
					gameController.setCurrentDirection(Direction.RIGHT);
					break;
				default:
					break;
				}
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		this.startGameLoop();
	}

	public void startGameLoop() throws InterruptedException {
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				try {
					//Thread.sleep(66);
					//TODO speed in properties
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gameController.calculateFrame();
				if (gameController.isGameOver()) {
					displayGameOver(canvas);
					
					this.stop();
				} else if (gameController.isWin()) {
					displayWin(canvas);
					this.stop();
				} else {
					GraphicsContext gc = canvas.getGraphicsContext2D();
					renderFrame(gc);
				}
			}

			
		}.start();

	}

	
	protected void displayGameOver(Canvas canvas2) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.RED);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(new Font(40));
		gc.fillText(GAME_OVER, canvas.getWidth()/2, canvas.getHeight()/2);
		
	}

	private void displayWin(Canvas canvas) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.LIME);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(new Font(40));
		gc.fillText(YOU_WIN, canvas.getWidth()/2, canvas.getHeight()/2);
	}
	
	private void renderFrame(GraphicsContext gc) {
		//clear background
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getHeight(), gc.getCanvas().getHeight());
		this.drawSnake(gc);
		this.drawFood(gc);
	}

	private void drawFood(GraphicsContext gc) {

		gc.setFill(Color.GREEN);
		gc.setLineWidth(5);
		for (Food f : gameController.getFoodList()) {
			gc.fillOval(f.getPositionX() * scaling, f.getPositionY() * scaling, scaling, scaling);

		}
	}

	private void drawSnake(GraphicsContext gc) {
		gc.setLineWidth(5);
		for (Bodypart part : gameController.getBodypartList()) {
			if (part.isHead()) {
				gc.setFill(Color.AQUA);
				// counter clockwise
				int angle = gameController.getCurrentDirection().getAngle();
				// for the head we use an opening angle of 60 degrees
				angle += 30;

				gc.fillArc(part.getPositionX() * scaling, part.getPositionY() * scaling, scaling, scaling, angle, 300,
						ArcType.ROUND);

			} else {
				gc.setFill(Color.BLUE);
				gc.fillOval(part.getPositionX() * scaling, part.getPositionY() * scaling, scaling, scaling);
			}
		}

	}

	private void createProperties() {

		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(CONFIG_PROPERTIES_FILE);

			// set the properties value
			prop.setProperty(KEY_X, "20");
			prop.setProperty(KEY_Y, "20");
			prop.setProperty(KEY_MAX_FOOD, "3");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	private void loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(CONFIG_PROPERTIES_FILE);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			String xValue = prop.getProperty(KEY_X);
			this.x = Integer.parseInt(xValue);
			String yValue = prop.getProperty(KEY_Y);
			this.y = Integer.parseInt(yValue);
			String maxFoodValue = prop.getProperty(KEY_MAX_FOOD);
			this.maxFood = Integer.parseInt(maxFoodValue);

		} catch (IOException ex) {
			this.createProperties();
			System.out.println("Properties error, please try again");
		} catch (NumberFormatException e) {
			System.out.println("wrong values in properties");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
