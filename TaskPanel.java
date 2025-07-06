import java.awt.*;
import java.awt.event.*;

public class TaskPanel extends Panel {
  private Checkbox Checkbox;
  private Button deleteButton;

  public TaskPanel(String text, boolean checked, Frame frame, Panel centerPanel) {
    //Create a horizontal panel for each task
    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

    //Create a checkbox with the task text
    Checkbox = new Checkbox(text, checked);
    Checkbox.setFont(new Font("MS Gothic", Font.PLAIN, 14));

    //Create a delete button
    deleteButton = new Button("çÌèú");
    deleteButton.setFont(new Font("MS Gothic", Font.PLAIN, 14));

    //Add components to the task panel
    add(Checkbox);
    add(deleteButton);

    //Action to remove the task panel when delet button is clicked
    deleteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        centerPanel.remove(TaskPanel.this);
        frame.validate();
        frame.repaint();
      }
    });
  }

  public String getTaskData() {
      return (Checkbox.getState() ? "1" : "0") + "\t" + Checkbox.getLabel();
  }
}
