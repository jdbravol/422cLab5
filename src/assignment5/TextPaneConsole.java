package assignment5;

import java.io.IOException;
import java.io.OutputStream;

import javafx.scene.control.TextArea;

public class TextPaneConsole extends OutputStream {
        private TextArea console;

        public TextPaneConsole(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            /*Platform.runLater(() ->*/ console.appendText(valueOf)/*)*/;
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
}
