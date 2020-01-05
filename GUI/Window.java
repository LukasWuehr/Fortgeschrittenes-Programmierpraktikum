package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
public class Window extends JFrame implements ActionListener{
    JTextField tf; JLabel l; JButton b;
    Window(){
        JButton []buttons = new JButton[5];
        for (JButton button:buttons) {
            button= new JButton("Click");
            button.setSize(100,50);
            add(button);
        }

        setSize(400,400);
        setLayout(new GridLayout(2,3));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) {
        try{
            String host=tf.getText();
            String ip=java.net.InetAddress.getByName(host).getHostAddress();
            l.setText("IP of "+host+" is: "+ip);
        }catch(Exception ex){System.out.println(ex);}
    }
    public static void main(String[] args) {
        new Window();
        new LoginWindow();
        new CardLayoutExample();
    } }

 class LoginWindow {
    public LoginWindow() {
        JFrame f=new JFrame("Games: Login");
        JRadioButton radioButton = new JRadioButton("Button");
        radioButton.setMnemonic(4);
        radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        radioButton.setAlignmentY(Component.TOP_ALIGNMENT);
        radioButton.setSize(50,50);
        //radioButton.setBounds(50,200,200,200);
        JLabel label = new JLabel();
        label.setBounds(20,150, 200,50);
        JPasswordField value = new JPasswordField();
        value.setBounds(100,75,100,30);
        JLabel l1=new JLabel("Username:");
        l1.setBounds(20,20, 80,30);
        JLabel l2=new JLabel("Password:");
        l2.setBounds(20,75, 80,30);
        JButton b = new JButton("Login");
        b.setBounds(100,120, 80,30);
        JTextField text = new JTextField();
        text.setBounds(100,20, 100,30);
        l1.setFont(new Font( "Calibri",Font.PLAIN, 22));

        f.add(value); f.add(l1); f.add(label); f.add(l2); f.add(b); f.add(text);f.add(radioButton);
        for (int i = 0; i <3; i++) {
            f.add(new JButton());
        }
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Username " + text.getText();
                data += ", Password: "
                        + new String(value.getPassword());
                label.setText(data);
            }
        });
    }
}

class CardLayoutExample extends JFrame implements ActionListener{
    CardLayout card;
    JButton b1,b2,b3;
    Container c;
    CardLayoutExample(){
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        c=getContentPane();
        card=new CardLayout(40,30);
//create CardLayout object with 40 hor space and 30 ver space
        c.setLayout(card);

        b1=new JButton("Apple");
        b2=new JButton("Boy");
        b3=new JButton("Cat");
        b3.setBackground(Color.GRAY);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        c.add("a",b1);c.add("b",b2);c.add("c",b3);

    }
    public void actionPerformed(ActionEvent e) {
        card.next(c);
    }
}
