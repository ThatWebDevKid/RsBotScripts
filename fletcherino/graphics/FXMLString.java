package scripts.fletcherino.graphics;

public class FXMLString {

    public static String get = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<?import javafx.scene.control.Button?>\n" +
            "<?import javafx.scene.control.ComboBox?>\n" +
            "<?import javafx.scene.control.Label?>\n" +
            "<?import javafx.scene.control.ListView?>\n" +
            "<?import javafx.scene.control.Separator?>\n" +
            "<?import javafx.scene.control.TextField?>\n" +
            "<?import javafx.scene.effect.Light.Distant?>\n" +
            "<?import javafx.scene.effect.Lighting?>\n" +
            "<?import javafx.scene.effect.Shadow?>\n" +
            "<?import javafx.scene.layout.AnchorPane?>\n" +
            "<?import javafx.scene.layout.ColumnConstraints?>\n" +
            "<?import javafx.scene.layout.FlowPane?>\n" +
            "<?import javafx.scene.layout.GridPane?>\n" +
            "<?import javafx.scene.layout.RowConstraints?>\n" +
            "<?import javafx.scene.text.Font?>\n" +
            "\n" +
            "<AnchorPane maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" prefHeight=\"418.0\" prefWidth=\"571.0\" xmlns=\"http://javafx.com/javafx/11.0.1\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"scripts.fletcherino.graphics.GUIController\">\n" +
            "   <children>\n" +
            "      <Label layoutX=\"14.0\" layoutY=\"75.0\" prefHeight=\"30.0\" prefWidth=\"176.0\" text=\"What should we do?\">\n" +
            "         <effect>\n" +
            "            <Lighting>\n" +
            "               <bumpInput>\n" +
            "                  <Shadow />\n" +
            "               </bumpInput>\n" +
            "               <light>\n" +
            "                  <Light.Distant />\n" +
            "               </light>\n" +
            "            </Lighting>\n" +
            "         </effect>\n" +
            "      </Label>\n" +
            "      <Label layoutX=\"2.0\" layoutY=\"7.0\" text=\"ThatWebDevKid's\" textFill=\"#f80000\">\n" +
            "         <font>\n" +
            "            <Font name=\"Copperplate Gothic Bold\" size=\"12.0\" />\n" +
            "         </font>\n" +
            "      </Label>\n" +
            "      <Label layoutX=\"165.0\" layoutY=\"7.0\" prefHeight=\"50.0\" prefWidth=\"249.0\" text=\"FLETCHERINO\" textFill=\"#11ca67\">\n" +
            "         <font>\n" +
            "            <Font name=\"Century Gothic Bold Italic\" size=\"39.0\" />\n" +
            "         </font>\n" +
            "      </Label>\n" +
            "      <Label layoutX=\"14.0\" layoutY=\"126.0\" prefHeight=\"30.0\" prefWidth=\"176.0\" text=\"What item to do?\">\n" +
            "         <effect>\n" +
            "            <Lighting>\n" +
            "               <bumpInput>\n" +
            "                  <Shadow />\n" +
            "               </bumpInput>\n" +
            "               <light>\n" +
            "                  <Light.Distant />\n" +
            "               </light>\n" +
            "            </Lighting>\n" +
            "         </effect>\n" +
            "      </Label>\n" +
            "      <Label layoutX=\"15.0\" layoutY=\"175.0\" prefHeight=\"46.0\" prefWidth=\"150.0\" text=\"Level to do it too? (-1 until supplies last)\" wrapText=\"true\">\n" +
            "         <effect>\n" +
            "            <Lighting>\n" +
            "               <bumpInput>\n" +
            "                  <Shadow />\n" +
            "               </bumpInput>\n" +
            "               <light>\n" +
            "                  <Light.Distant />\n" +
            "               </light>\n" +
            "            </Lighting>\n" +
            "         </effect>\n" +
            "      </Label>\n" +
            "      <Label layoutX=\"14.0\" layoutY=\"237.0\" prefHeight=\"108.0\" prefWidth=\"150.0\" text=\"Number of supplies to fletch? (-1 until supplies run out)\" wrapText=\"true\">\n" +
            "         <effect>\n" +
            "            <Lighting>\n" +
            "               <bumpInput>\n" +
            "                  <Shadow />\n" +
            "               </bumpInput>\n" +
            "               <light>\n" +
            "                  <Light.Distant />\n" +
            "               </light>\n" +
            "            </Lighting>\n" +
            "         </effect>\n" +
            "      </Label>\n" +
            "      <TextField fx:id=\"upToLevel\" layoutX=\"174.0\" layoutY=\"185.0\" prefHeight=\"26.0\" prefWidth=\"150.0\" promptText=\"Level to train to\" />\n" +
            "      <Separator layoutX=\"335.0\" layoutY=\"62.0\" orientation=\"VERTICAL\" prefHeight=\"352.0\" prefWidth=\"9.0\" />\n" +
            "      <ListView fx:id=\"tasksList\" layoutX=\"347.0\" layoutY=\"57.0\" prefHeight=\"166.0\" prefWidth=\"212.0\" />\n" +
            "      <ComboBox fx:id=\"thingToDo\" layoutX=\"174.0\" layoutY=\"77.0\" onAction=\"#thingToDoOnChange\" prefWidth=\"150.0\" />\n" +
            "      <ComboBox fx:id=\"itemToDo\" layoutX=\"174.0\" layoutY=\"128.0\" onAction=\"#itemToDoOnChange\" prefWidth=\"150.0\" />\n" +
            "      <Button fx:id=\"startScript\" layoutX=\"22.0\" layoutY=\"366.0\" mnemonicParsing=\"false\" onAction=\"#startScriptPressed\" prefHeight=\"37.0\" prefWidth=\"302.0\" text=\"Let's get FLETCHING!\" textFill=\"#2908ff\">\n" +
            "         <font>\n" +
            "            <Font name=\"Copperplate Gothic Light\" size=\"20.0\" />\n" +
            "         </font>\n" +
            "      </Button>\n" +
            "      <GridPane layoutX=\"90.0\" layoutY=\"-155.0\">\n" +
            "        <columnConstraints>\n" +
            "          <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n" +
            "          <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n" +
            "        </columnConstraints>\n" +
            "        <rowConstraints>\n" +
            "          <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n" +
            "          <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n" +
            "          <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n" +
            "        </rowConstraints>\n" +
            "      </GridPane>\n" +
            "      <FlowPane layoutX=\"352.0\" layoutY=\"229.0\" prefHeight=\"156.0\" prefWidth=\"202.0\">\n" +
            "         <children>\n" +
            "            <Button fx:id=\"addTask\" mnemonicParsing=\"false\" onAction=\"#addTaskPressed\" prefHeight=\"46.0\" prefWidth=\"201.0\" text=\"Add Task\" />\n" +
            "            <Button fx:id=\"removeTask\" mnemonicParsing=\"false\" onAction=\"#removeTaskPressed\" prefHeight=\"42.0\" prefWidth=\"202.0\" text=\"Remove Task\" />\n" +
            "            <Button fx:id=\"saveTasks\" mnemonicParsing=\"false\" onAction=\"#saveTasksPressed\" prefHeight=\"46.0\" prefWidth=\"98.0\" text=\"Save Tasks\" />\n" +
            "            <TextField fx:id=\"nameProfile\" prefHeight=\"26.0\" prefWidth=\"103.0\" promptText=\"Name Profile\" />\n" +
            "            <Button fx:id=\"loadTasks\" mnemonicParsing=\"false\" onAction=\"#loadTasksPressed\" prefHeight=\"46.0\" prefWidth=\"98.0\" text=\"Load Tasks\" />\n" +
            "            <ComboBox fx:id=\"selectProfile\" onAction=\"#selectProfileOnChange\" prefHeight=\"26.0\" prefWidth=\"103.0\" promptText=\"Select Profile\" />\n" +
            "         </children>\n" +
            "      </FlowPane>\n" +
            "      <TextField fx:id=\"suppliesToFletch\" layoutX=\"174.0\" layoutY=\"275.0\" prefHeight=\"26.0\" prefWidth=\"150.0\" promptText=\"Number of supplies to fletch\" />\n" +
            "   </children>\n" +
            "</AnchorPane>\n";

}
