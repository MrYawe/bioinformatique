package view;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * Created by saetlan on 15/03/17.
 */
class ConsolePanel extends JPanel {
    //////////////////////////////////////////
    //     INSTANCE MEMBER DECLARATION      //
    //////////////////////////////////////////

    private JTextPane jtpOutput;
    private JScrollPane jspOutput;

    private StyledDocument document;
    private int recent_used_id=0;

    ConsolePanel() {
        super();

        //////////////////////////////////////////
        //             INSTANTIATION            //
        //////////////////////////////////////////
        jtpOutput = new JTextPane();
        jspOutput = new JScrollPane(jtpOutput);

        document = jtpOutput.getStyledDocument();

        //////////////////////////////////////////
        //                 LAYOUT               //
        //////////////////////////////////////////

        this.setLayout(new BorderLayout());
        this.add(jspOutput, BorderLayout.CENTER);

        //////////////////////////////////////////
        //               PROPERTIES             //
        //////////////////////////////////////////

        jtpOutput.setEditable(false);
        jtpOutput.setFont(new Font("Courrier New", Font.PLAIN, 14));

        jspOutput.setMinimumSize(new Dimension(200,100));
        jspOutput.setPreferredSize(new Dimension(600,300));

    }

    //Function used to scroll the output to the top
    public void scrollTop() {
        jtpOutput.setCaretPosition(0);
    }

    //Function used to scroll the output to the bottom
    public void scrollBottom() {
        jtpOutput.setCaretPosition(jtpOutput.getDocument().getLength());
    }

    //Function used to write a line in whote on the output without returning to the next line.
    public void print(String s) {
        print(s, Color.BLACK);
    }

    //Function used to write a line with the specified Color on the output without returning to the next line.
    public void print(String s, Color c) {
        Style style = jtpOutput.addStyle("Style", null);
        StyleConstants.setForeground(style, c);

        try {
            document.insertString(document.getLength(), s, style);
        } catch (Exception err) {
            System.out.println("[Exception>ConsolePanel>print]: while printing the output following error has been encountered: \n"+err.getMessage());
            println("Exception -> " + err.getMessage(), Color.RED);
        }
        scrollBottom();
    }

    //Function used to write a line in black on the output with a new line
    public void println(String s){
        println(s, Color.BLACK);
    }

    //Function used to write a line with the specified Color on the output with a new line
    public void println(String s, Color c) {
        print(s +  "\n", c);
    }

    //Function used to clear the output
    public void clear() {
        try {
            document.remove(0, document.getLength());
        } catch (Exception err) {
            System.out.println("[Exception>ConsolePanel>clear]: while clearing the output the following error has been encountered: \n"+err.getMessage());
            println("Exception -> " + err.getMessage(), Color.RED);
        }
    }
}