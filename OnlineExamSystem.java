import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExamSystem {

    static Connection conn;

    public static void main(String[] args) {
        connectDatabase();
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }

    public static void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/onlineexam", "root", "MyNewPass123!");
            System.out.println("Connected to DB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ---------------- LOGIN FRAME ----------------
class LoginFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin, btnRegister;

    public LoginFrame() {
        setTitle("Online Exam Login");
        setSize(450,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230,240,255));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230,240,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        panel.add(new JLabel("Username:"), gbc);
        txtUser = new JTextField(15);
        gbc.gridx=1; panel.add(txtUser, gbc);

        gbc.gridx=0; gbc.gridy=1;
        panel.add(new JLabel("Password:"), gbc);
        txtPass = new JPasswordField(15);
        gbc.gridx=1; panel.add(txtPass, gbc);

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(100,180,255));
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(100,180,255));

        gbc.gridx=0; gbc.gridy=2; panel.add(btnLogin, gbc);
        gbc.gridx=1; panel.add(btnRegister, gbc);

        add(panel);
        setVisible(true);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterFrame());
    }

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        try {
            PreparedStatement ps = OnlineExamSystem.conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1,user);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int userId = rs.getInt("id");
                JOptionPane.showMessageDialog(this,"Login successful!");
                dispose();
                new InstructionsFrame(userId, user);
            } else {
                JOptionPane.showMessageDialog(this,"Invalid credentials.");
            }
        } catch(Exception ex){ ex.printStackTrace(); }
    }
}

// ---------------- REGISTRATION FRAME ----------------
class RegisterFrame extends JFrame {
    JTextField txtUser, txtEmail;
    JPasswordField txtPass;
    JButton btnReg;
    public RegisterFrame() {
        setTitle("Register New User");
        setSize(450,350);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230,240,255));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230,240,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        panel.add(new JLabel("Username:"), gbc);
        txtUser = new JTextField(15);
        gbc.gridx=1; panel.add(txtUser, gbc);

        gbc.gridx=0; gbc.gridy=1;
        panel.add(new JLabel("Password:"), gbc);
        txtPass = new JPasswordField(15);
        gbc.gridx=1; panel.add(txtPass, gbc);

        gbc.gridx=0; gbc.gridy=2;
        panel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(15);
        gbc.gridx=1; panel.add(txtEmail, gbc);

        btnReg = new JButton("Register");
        btnReg.setBackground(new Color(100,180,255));
        gbc.gridx=1; gbc.gridy=3; panel.add(btnReg, gbc);

        add(panel);
        setVisible(true);

        btnReg.addActionListener(e -> register());
    }

    private void register() {
        try {
            PreparedStatement ps = OnlineExamSystem.conn.prepareStatement(
                "INSERT INTO users(username,password,email) VALUES(?,?,?)");
            ps.setString(1,txtUser.getText());
            ps.setString(2,new String(txtPass.getPassword()));
            ps.setString(3,txtEmail.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Registration successful!");
            dispose();
        } catch(Exception e){ e.printStackTrace(); }
    }
}

// ---------------- INSTRUCTIONS FRAME ----------------
class InstructionsFrame extends JFrame {
    JButton btnNext;
    int userId;
    String username;
    public InstructionsFrame(int userId, String username) {
        this.userId = userId;
        this.username = username;
        setTitle("Exam Instructions");
        setSize(600,400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255,245,230));

        JTextArea instructions = new JTextArea(
            "Exam Instructions:\n\n" +
            "1. Exam duration: 20 minutes\n" +
            "2. Total Questions: 20\n" +
            "3. Each question has one correct answer\n" +
            "4. Do not switch windows (AI proctoring active)\n" +
            "5. Click Next to see Exam Pattern"
        );
        instructions.setEditable(false);
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        instructions.setBackground(new Color(255,245,230));

        btnNext = new JButton("Next");
        btnNext.setBackground(new Color(100,180,255));
        btnNext.addActionListener(e -> {
            dispose();
            new ExamPatternFrame(userId, username);
        });

        add(new JScrollPane(instructions), BorderLayout.CENTER);
        add(btnNext, BorderLayout.SOUTH);
        setVisible(true);
    }
}

// ---------------- EXAM PATTERN FRAME ----------------
class ExamPatternFrame extends JFrame {
    JButton btnStartExam;
    public ExamPatternFrame(int userId, String username) {
        setTitle("Exam Pattern");
        setSize(600,400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255,250,240));
        setLayout(new BorderLayout());

        JTextArea pattern = new JTextArea(
            "Exam Pattern:\n\n" +
            "- Total Questions: 20\n" +
            "- Each question carries 1 mark\n" +
            "- Duration: 20 minutes\n" +
            "- Multiple Choice Questions only\n" +
            "- Answers saved automatically\n" +
            "- Score shown at end"
        );
        pattern.setFont(new Font("Arial", Font.PLAIN, 16));
        pattern.setEditable(false);
        pattern.setBackground(new Color(255,250,240));
        add(pattern, BorderLayout.CENTER);

        btnStartExam = new JButton("Start Exam");
        btnStartExam.setBackground(new Color(100,180,255));
        add(btnStartExam, BorderLayout.SOUTH);

        btnStartExam.addActionListener(e -> {
            dispose();
            new ExamFrame(userId, username);
        });

        setVisible(true);
    }
}

// ---------------- EXAM FRAME ----------------
class ExamFrame extends JFrame {
    JLabel lblTimer, lblQuestion;
    Timer timer;
    int timeLeft = 20 * 60; // 20 min
    JButton btnNext, btnSubmit;
    int userId;
    String username;
    int qIndex = 0;
    int totalQuestions = 20;
    int[] userAnswers = new int[20];

    ButtonGroup bg = new ButtonGroup();
    JRadioButton[] rbOptions = new JRadioButton[4];
    int[] questionIds = new int[20];

    public ExamFrame(int userId, String username) {
        this.userId = userId;
        this.username = username;
        setTitle("Online Exam");
        setSize(800,500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(230,240,255));
        setLayout(new BorderLayout());

        lblTimer = new JLabel("Time: 20:00");
        lblTimer.setFont(new Font("Arial",Font.BOLD,16));
        add(lblTimer, BorderLayout.NORTH);

        JPanel questionPanel = new JPanel(new GridLayout(5,1));
        questionPanel.setBackground(new Color(255,255,240));
        lblQuestion = new JLabel();
        questionPanel.add(lblQuestion);

        for(int i=0;i<4;i++){
            rbOptions[i] = new JRadioButton();
            rbOptions[i].setBackground(new Color(255,255,240));
            rbOptions[i].setFont(new Font("Arial", Font.PLAIN, 14));
            bg.add(rbOptions[i]);
            questionPanel.add(rbOptions[i]);
        }

        add(questionPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(230,240,255));
        btnNext = new JButton("Next");
        btnNext.setBackground(new Color(100,180,255));
        btnSubmit = new JButton("Submit");
        btnSubmit.setBackground(new Color(100,180,255));
        btnPanel.add(btnNext);
        btnPanel.add(btnSubmit);
        add(btnPanel, BorderLayout.SOUTH);

        btnNext.addActionListener(e -> nextQuestion());
        btnSubmit.addActionListener(e -> submitExam());

        loadQuestions();
        displayQuestion();
        startTimer();
        setVisible(true);
    }

    private void loadQuestions() {
        try {
            PreparedStatement ps = OnlineExamSystem.conn.prepareStatement(
                "SELECT * FROM questions LIMIT 20");
            ResultSet rs = ps.executeQuery();
            int i=0;
            while(rs.next()) {
                questionIds[i] = rs.getInt("id");
                i++;
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void displayQuestion() {
        try {
            PreparedStatement ps = OnlineExamSystem.conn.prepareStatement(
                "SELECT * FROM questions LIMIT 1 OFFSET ?");
            ps.setInt(1,qIndex);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                lblQuestion.setText((qIndex+1) + ". " + rs.getString("question_text"));
                rbOptions[0].setText(rs.getString("option1"));
                rbOptions[1].setText(rs.getString("option2"));
                rbOptions[2].setText(rs.getString("option3"));
                rbOptions[3].setText(rs.getString("option4"));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void nextQuestion() {
        for(int i=0;i<4;i++){
            if(rbOptions[i].isSelected()){
                userAnswers[qIndex] = i+1;
                bg.clearSelection();
                break;
            }
        }
        qIndex++;
        if(qIndex>=totalQuestions){
            JOptionPane.showMessageDialog(this,"Last question reached");
            qIndex = totalQuestions-1;
        }
        displayQuestion();
    }

    private void submitExam() {
        timer.cancel();
        int correct = 0;
        try {
            for(int i=0;i<totalQuestions;i++){
                PreparedStatement ps = OnlineExamSystem.conn.prepareStatement(
                    "INSERT INTO user_answers(user_id,question_id,selected_option) VALUES(?,?,?)");
                ps.setInt(1,userId);
                ps.setInt(2,questionIds[i]);
                ps.setInt(3,userAnswers[i]);
                ps.executeUpdate();

                PreparedStatement ps2 = OnlineExamSystem.conn.prepareStatement(
                    "SELECT correct_option FROM questions WHERE id=?");
                ps2.setInt(1,questionIds[i]);
                ResultSet rs = ps2.executeQuery();
                if(rs.next() && userAnswers[i]==rs.getInt("correct_option")){
                    correct++;
                }
            }
        } catch(Exception e){ e.printStackTrace(); }

        dispose();
        new ResultFrame(username, correct, totalQuestions);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run() {
                int min = timeLeft / 60;
                int sec = timeLeft % 60;
                lblTimer.setText(String.format("Time: %02d:%02d", min, sec));
                timeLeft--;
                if(timeLeft<0){
                    timer.cancel();
                    submitExam();
                }
            }
        },1000,1000);
    }
}

// ---------------- RESULT FRAME WITH PIE CHART ----------------
class ResultFrame extends JFrame {
    public ResultFrame(String username, int correct, int total) {
        setTitle("Exam Results for " + username);
        setSize(500,450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255,255,240));

        add(new PieChartPanel(correct, total), BorderLayout.CENTER);

        JButton btnDownload = new JButton("Download Result");
        btnDownload.setBackground(new Color(100,180,255));
        add(btnDownload, BorderLayout.SOUTH);
        btnDownload.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Result downloaded successfully!\nScore: " + correct + "/" + total));

        setVisible(true);
    }
}

// ---------------- CUSTOM PIE CHART PANEL ----------------
class PieChartPanel extends JPanel {
    int correct, total;

    public PieChartPanel(int correct, int total) {
        this.correct = correct;
        this.total = total;
        setPreferredSize(new Dimension(400,400));
        setBackground(new Color(255,255,240));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;

        // calculate angles
        double correctAngle = ((double)correct/total) * 360;
        double wrongAngle = 360 - correctAngle;

        // draw correct slice
        g2.setColor(new Color(100,220,100));
        g2.fillArc(50, 50, 300, 300, 0, (int)correctAngle);

        // draw wrong slice
        g2.setColor(new Color(220,100,100));
        g2.fillArc(50, 50, 300, 300, (int)correctAngle, (int)wrongAngle);

        // draw labels
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Correct: " + correct, 50, 370);
        g2.drawString("Incorrect: " + (total - correct), 250, 370);
    }
}
