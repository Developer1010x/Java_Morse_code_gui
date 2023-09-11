import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class MorseCodeConverter extends JFrame {

    private JTextField inputTextField;
    private JTextArea outputTextArea;
    private JButton toMorseButton;
    private JButton toEnglishButton;
    private JButton copyOutputButton;
    private JButton pasteButton; // Added Paste Text button

    private Map<Character, String> morseCodeMap;

    public MorseCodeConverter() {
        setTitle("Morse Code Converter");
        setSize(600, 400); // Increased window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        morseCodeMap = createMorseCodeMap();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputTextField = new JTextField(30); // Adjust the number of columns for the input field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        mainPanel.add(inputTextField, gbc);

        outputTextArea = new JTextArea(10, 20); // Increased text box size
        outputTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        toMorseButton = new JButton("To Morse");
        toEnglishButton = new JButton("To English");
        copyOutputButton = new JButton("Copy Output");
        pasteButton = new JButton("Paste Text"); // Added Paste Text button

        toMorseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
                String morse = convertToMorse(input);
                outputTextArea.setText(morse);
            }
        });
      
            JButton clearOutputButton = new JButton("Clear Output");
        
            clearOutputButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    outputTextArea.setText("");
                }
            });
        
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.insets = new Insets(10, 10, 10, 10);
            mainPanel.add(clearOutputButton, gbc);
        
            add(mainPanel);
        
            
        

        toEnglishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
                String english = convertToEnglish(input);
                outputTextArea.setText(english);
            }
        });
        JButton clearInputButton = new JButton("Clear Input");

        clearInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputTextField.setText("");
            }
        });


        
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(clearInputButton, gbc);
    
        add(mainPanel);

        copyOutputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(outputTextArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable contents = clipboard.getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String pastedText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        SwingUtilities.invokeLater(() -> inputTextField.setText(pastedText));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10);
        mainPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toMorseButton);
        buttonPanel.add(toEnglishButton);
        buttonPanel.add(copyOutputButton);
        buttonPanel.add(pasteButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        // Bind Shift + Enter to convert to Morse
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        inputTextField.getInputMap(JComponent.WHEN_FOCUSED).put(shiftEnter, "convertToMorse");
        inputTextField.getActionMap().put("convertToMorse", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText().toUpperCase();
                String morse = convertToMorse(input);
                outputTextArea.setText(morse);
            }
        });

        // Bind Enter to convert to English
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        inputTextField.getInputMap(JComponent.WHEN_FOCUSED).put(enter, "convertToEnglish");
        inputTextField.getActionMap().put("convertToEnglish", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
                String english = convertToEnglish(input);
                outputTextArea.setText(english);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

   private Map<Character, String> createMorseCodeMap() {
        Map<Character, String> map = new HashMap<>();
        map.put('A', ".-");
        map.put('B', "-...");
        map.put('C', "-.-.");
        map.put('D', "-..");
        map.put('E', ".");
        map.put('F', "..-.");
        map.put('G', "--.");
        map.put('H', "....");
        map.put('I', "..");
        map.put('J', ".---");
        map.put('K', "-.-");
        map.put('L', ".-..");
        map.put('M', "--");
        map.put('N', "-.");
        map.put('O', "---");
        map.put('P', ".--.");
        map.put('Q', "--.-");
        map.put('R', ".-.");
        map.put('S', "...");
        map.put('T', "-");
        map.put('U', "..-");
        map.put('V', "...-");
        map.put('W', ".--");
        map.put('X', "-..-");
        map.put('Y', "-.--");
        map.put('Z', "--..");
        map.put('0', "-----");
        map.put('1', ".----");
        map.put('2', "..---");
        map.put('3', "...--");
        map.put('4', "....-");
        map.put('5', ".....");
        map.put('6', "-....");
        map.put('7', "--...");
        map.put('8', "---..");
        map.put('9', "----.");
        map.put(' ', "/");
        map.put('!', "-.-.--");
        map.put('"', ".-..-.");
        map.put('#', "...-..-");
        map.put('$', "...-..-.");
        map.put('%', "------.");
        map.put('&', ".-...");
        map.put('\'', ".----.");
        map.put('(', "-.--.");
        map.put(')', "-.--.-");
        map.put('*', "-..-");
        map.put('+', ".-.-.");
        map.put(',', "--..--");
        map.put('-', "-....-");
        map.put('.', ".-.-.-");
        map.put('/', "-..-.");
        map.put(':', "---...");
        map.put(';', "-.-.-.");
        map.put('<', "-.--.");
        map.put('=', "-...-");
        map.put('>', ".-.-.");
        map.put('?', "..--..");
        map.put('@', ".--.-.");
        map.put('[', "-.--.");
        map.put('\\', "-..-.");
        map.put(']', "-.--.-");
        map.put('^', "--...-");
        map.put('_', "..--.-");
        map.put('`', "....--");
        map.put('{', "-.--.");
        map.put('|', ".-..-");
        map.put('}', "-.--.-");
        map.put('~', ".-.-");
        map.put('€', "..-..-");
        map.put('£', ".-..-");
        map.put('¥', "-.--.-");
        map.put('₹', ".-..-.");
        map.put('a', ".-");
        map.put('b', "-...");
        map.put('c', "-.-.");
        map.put('d', "-..");
        map.put('e', ".");
        map.put('f', "..-.");
        map.put('g', "--.");
        map.put('h', "....");
        map.put('i', "..");
        map.put('j', ".---");
        map.put('k', "-.-");
        map.put('l', ".-..");
        map.put('m', "--");
        map.put('n', "-.");
        map.put('o', "---");
        map.put('p', ".--.");
        map.put('q', "--.-");
        map.put('r', ".-.");
        map.put('s', "...");
        map.put('t', "-");
        map.put('u', "..-");
        map.put('v', "...-");
        map.put('w', ".--");
        map.put('x', "-..-");
        map.put('y', "-.--");
        map.put('z', "--..");
        // Add more symbols as needed
    
        return map;
    }
    
private String convertToMorse(String text) {
    StringBuilder morseBuilder = new StringBuilder();
    for (char c : text.toCharArray()) {
        if (c == ' ') {
            morseBuilder.append(" / ");
        } else {
            String morseChar = morseCodeMap.get(c);
            if (morseChar != null) {
                morseBuilder.append(morseChar).append(" ");
            }
        }
    }
    return morseBuilder.toString();
}


private String convertToEnglish(String morse) {
    StringBuilder englishBuilder = new StringBuilder();
    String[] words = morse.split("   ");
    for (String word : words) {
        String[] letters = word.split(" ");
        for (String letter : letters) {
            boolean found = false;
            for (Map.Entry<Character, String> entry : morseCodeMap.entrySet()) {
                if (entry.getValue().equals(letter)) {
                    englishBuilder.append(entry.getKey());
                    found = true;
                    break;
                }
            }
            if (!found) {
                englishBuilder.append(letter); // Append the original Morse if not found
            }
        }
        englishBuilder.append(" ");
    }
    return englishBuilder.toString();
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MorseCodeConverter::new);
    }
}
