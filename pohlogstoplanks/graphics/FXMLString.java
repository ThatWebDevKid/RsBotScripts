package scripts.pohlogstoplanks.graphics;

public class FXMLString {

    public static String get = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<?import javafx.scene.control.Button?>\n" +
            "<?import javafx.scene.control.ComboBox?>\n" +
            "<?import javafx.scene.control.Label?>\n" +
            "<?import javafx.scene.layout.AnchorPane?>\n" +
            "\n" +
            "<AnchorPane maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" prefHeight=\"178.0\" prefWidth=\"502.0\" xmlns=\"http://javafx.com/javafx/11.0.1\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"scripts.pohlogstoplanks.graphics.GUIController\">\n" +
            "   <children>\n" +
            "      <Label layoutX=\"14.0\" layoutY=\"14.0\" prefHeight=\"32.0\" prefWidth=\"194.0\" text=\"Plank Type\" />\n" +
            "      <Label layoutX=\"14.0\" layoutY=\"89.0\" prefHeight=\"32.0\" prefWidth=\"175.0\" text=\"Banking Location\" />\n" +
            "      <ComboBox fx:id=\"plankType\" layoutX=\"326.0\" layoutY=\"14.0\" prefHeight=\"26.0\" prefWidth=\"175.0\" />\n" +
            "      <ComboBox fx:id=\"bankingLocation\" layoutX=\"325.0\" layoutY=\"92.0\" prefHeight=\"26.0\" prefWidth=\"175.0\" />\n" +
            "      <Button fx:id=\"startButton\" layoutX=\"117.0\" layoutY=\"132.0\" mnemonicParsing=\"false\" onAction=\"#onStart\" prefHeight=\"32.0\" prefWidth=\"267.0\" text=\"Start\" textFill=\"#fa0303\" />\n" +
            "   </children>\n" +
            "</AnchorPane>";

}
