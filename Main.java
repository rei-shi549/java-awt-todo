import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main{
  public static void main(String[] args){
    String saveFileName = "tasks.txt";
    Frame frame = new Frame("ToDo App");
    frame.setSize(400, 300);
    frame.setLayout(new BorderLayout());

    //Create a shared font (MS Gothic, size 14)
    Font jpFont = new Font("MS Gothic", Font.PLAIN,14);

    // Top: Input area
    Panel topPanel = new Panel(new FlowLayout());

    TextField taskInput = new TextField(20);
    taskInput.setFont(jpFont);

    Button addButton = new Button("’Ç‰Á");
    addButton.setFont(jpFont);

    topPanel.add(taskInput);
    topPanel.add(addButton);
    frame.add(topPanel, BorderLayout.NORTH);

    //Center: Task list area
    final Panel centerPanel = new Panel(new GridLayout(0, 1));
    frame.add(centerPanel, BorderLayout.CENTER);

    //Load tasks from file
    try(BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
      String line;
      while((line = reader.readLine()) != null) {

        String[] parts = line.split("\t",2);
        if(parts.length == 2) {
          boolean checked = parts[0].equals("1");
          String taskText = parts.length > 1 ? parts[1] : "";
          TaskPanel taskPanel = new TaskPanel(taskText, checked, frame, centerPanel);
          centerPanel.add(taskPanel);
        }
      } 
    } catch(IOException ex) {
      ex.printStackTrace();
    }

    //Add button click event
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        //Get text from input field
        String taskText = taskInput.getText().trim();

        if(!taskText.isEmpty()){
          TaskPanel taskPanel = new TaskPanel(taskText, false, frame, centerPanel);
          centerPanel.add(taskPanel);

          //Update the layout to reflect changes
          frame.validate();

          //Clear the input field
          taskInput.setText("");
        }
      }
    });

    taskInput.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          addButton.dispatchEvent(new ActionEvent(addButton,ActionEvent.ACTION_PERFORMED,""));
        }
      }
    });

    //Add window closing behavior
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileName))) {
                for (Component comp : centerPanel.getComponents()) {
                    if (comp instanceof TaskPanel) {
                        TaskPanel tp = (TaskPanel) comp;
                        writer.write(tp.getTaskData());
                        writer.newLine();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            frame.dispose();
        }
    });

    frame.setVisible(true);
    taskInput.requestFocus();
  }
}