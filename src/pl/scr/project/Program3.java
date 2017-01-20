//package pl.scr.project;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.Random;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import javafx.application.Application;
//import javafx.concurrent.Task;
//import javafx.embed.swing.SwingFXUtils;
//import javafx.geometry.Pos;
//import javafx.scene.CacheHint;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
///**
// * Performance charts using JFreeChart
// * 
// * @author Hubert
// *
// */
//public class Program3 extends Application {
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			StackPane p = new StackPane();
//			primaryStage.setTitle("Chart Application");
//			Label loader = new Label("Loading...");
//			loader.setGraphic(new ImageView(new Image("https://media.giphy.com/media/FmcNeI0PnsAKs/giphy.gif")));
//			loader.setFont(new javafx.scene.text.Font(35));
//			p.setStyle("-fx-background: #FFFFFF;");
//			p.getChildren().add(loader);
//			StackPane.setAlignment(loader, Pos.CENTER);
//
//			Scene scene = new Scene(p, 600, 600);
//			primaryStage.setScene(scene);
//			primaryStage.setMaximized(true);
//
//			Task<ArrayList<ImageView>> loadInitial = new Task<ArrayList<ImageView>>() {
//				@Override
//				public ArrayList<ImageView> call() {
//					ArrayList<ImageView> images = new ArrayList<ImageView>();
//
//					for (int i = 0; i < 10; i++) {
//						XYSeries data = new XYSeries(1);
//						XYSeries data2 = new XYSeries(2);
//						XYSeries data3 = new XYSeries(3);
//
//						System.out.println("Calcuating values for graph" + (i + 1));
//						for (int j = 0; j < 5000; j += i + 2) {
//							data.add(j, 0);
//							data.add(j, 1);
//							data.add(j + 1, 1);
//							data.add(j + 1, 0);
//						}
//
//						for (int j = 0; j < 5000; j += i + 1) {
//							data2.add(j, 0);
//							data2.add(j, 1.5);
//							data2.add(j + 0.1, 1.5);
//							data2.add(j + 0.1, 0);
//						}
//
//						for (int j = 2; j < 5000; j += i + 1) {
//							data3.add(j, 0);
//							data3.add(j, 1.2);
//							data3.add(j + 0.1, 1.2);
//							data3.add(j + 0.1, 0);
//						}
//						System.out.println("Finished values for graph" + (i + 1));
//
//						XYSeriesCollection dataset = new XYSeriesCollection();
//						dataset.addSeries(data);
//						dataset.addSeries(data2);
//						dataset.addSeries(data3);
//
//						JFreeChart chart = ChartFactory.createXYAreaChart("", "", "", dataset, PlotOrientation.VERTICAL,
//								false, false, false);
//						chart.setBackgroundPaint(Color.WHITE);
//						chart.setBorderVisible(false);
//						chart.setAntiAlias(true);
//						chart.setBorderPaint(Color.WHITE);
//
//						XYPlot plot = (XYPlot) chart.getPlot();
//						plot.setBackgroundPaint(Color.white);
//						plot.setRangeGridlinePaint(Color.black);
//						plot.setDomainGridlinePaint(Color.black);
//						plot.setOutlineVisible(false);
//
//						Random random = new Random();
//						plot.getRenderer().setSeriesPaint(0,
//								new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//
//						ValueAxis range = plot.getRangeAxis();
//						range.setLowerMargin(0);
//						range.setUpperMargin(0);
//						range.setVisible(false);
//						ValueAxis domainAxis = plot.getDomainAxis();
//						domainAxis.setLowerMargin(0);
//						domainAxis.setUpperMargin(0);
//
//						double maxX = 0;
//
//						for (Object temp : dataset.getSeries()) {
//							double max = ((XYSeries) temp).getMaxX();
//							if (maxX < max)
//								maxX = max;
//						}
//
//						Long L = Math.round(maxX);
//						int maxVal = Integer.valueOf(L.intValue());
//						int width = maxVal * 20; // Works out length to nice
//													// scale.
//													// If you want all values to
//													// be the same use 40,000
//													// (commented out)
//
//						if (width > 250000)
//							width = 250000; // Lags out after this
//
//						System.out.println("Buffering graph" + (i + 1));
//						BufferedImage capture = chart.createBufferedImage(width, 70);
//						// BufferedImage capture =
//						// chart.createBufferedImage(40000, 50);
//						System.out.println("Finished buffering graph" + (i + 1));
//						ImageView imageView = new ImageView();
//						Image chartImg = SwingFXUtils.toFXImage(capture, null);
//						imageView.setImage(chartImg);
//						imageView.setCache(true);
//						imageView.setCacheHint(CacheHint.SPEED);
//
//						images.add(imageView);
//					}
//
//					System.out.println("Finished all processes. Loading graphs");
//					return images;
//				}
//			};
//
//			loadInitial.setOnSucceeded(e -> {
//				VBox images = new VBox();
//				images.setSpacing(0);
//				ArrayList<ImageView> result = loadInitial.getValue();
//				for (ImageView image : result) {
//					images.getChildren().add(image);
//				}
//
//				ScrollPane scrollPane = new ScrollPane(images);
//				scrollPane.setStyle("-fx-background: #FFFFFF;");
//
//				scene.setRoot(scrollPane);
//			});
//
//			new Thread(loadInitial).start();
//
//			primaryStage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}