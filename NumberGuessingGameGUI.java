//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int targetNumber;
    private int attemptsLeft;
    private int totalRounds = 0;
    private int roundsWon = 0;

    private final int MAX_ATTEMPTS = 10;
    private final int LOWER_BOUND = 1;
    private final int UPPER_BOUND = 100;

    private JLabel promptLabel;
    private JTextField inputField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGuessingGameGUI() {
        setTitle("🎮 Number Guessing Game");
        setSize(400, 300);
        setLayout(new GridLayout(7, 1, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        promptLabel = new JLabel("Guess a number between " + LOWER_BOUND + " and " + UPPER_BOUND, SwingConstants.CENTER);
        promptLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        inputField = new JTextField();
        guessButton = new JButton("Guess");
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        attemptsLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);

        add(promptLabel);
        add(inputField);
        add(guessButton);
        add(feedbackLabel);
        add(attemptsLabel);
        add(scoreLabel);

        startNewRound();

        guessButton.addActionListener(e -> handleGuess());

        setVisible(true);
    }

    private void startNewRound() {
        Random random = new Random();
        targetNumber = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1) + LOWER_BOUND;
        attemptsLeft = MAX_ATTEMPTS;
        totalRounds++;

        feedbackLabel.setText("New round started!");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        inputField.setText("");
    }

    private void handleGuess() {
        String input = inputField.getText();
        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("❗ Please enter a valid number.");
            return;
        }

        if (guess < LOWER_BOUND || guess > UPPER_BOUND) {
            feedbackLabel.setText("⛔ Guess must be between " + LOWER_BOUND + " and " + UPPER_BOUND);
            return;
        }

        attemptsLeft--;

        if (guess == targetNumber) {
            feedbackLabel.setText("✅ You guessed the right answer! 🎉 The number was " + targetNumber);
            roundsWon++;

            // Add delay before showing next round prompt
            Timer delayTimer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ((Timer) evt.getSource()).stop(); // Stop timer after one run
                    showNextRoundPrompt();
                }
            });
            delayTimer.setRepeats(false); // Run only once
            delayTimer.start();
            return;
        }
        else if (guess < targetNumber) {
            feedbackLabel.setText("⬆️ Too low!");
        } else {
            feedbackLabel.setText("⬇️ Too high!");
        }

        attemptsLabel.setText("Attempts left: " + attemptsLeft);

        if (attemptsLeft == 0 && guess != targetNumber) {
            feedbackLabel.setText("❌ Out of attempts! The number was " + targetNumber);
            showNextRoundPrompt();
        }

        scoreLabel.setText("Score: " + (roundsWon * 10));
        inputField.setText("");
    }


    private void showNextRoundPrompt() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to play another round?", "Next Round",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            startNewRound();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Thanks for playing!\nRounds Played: " + totalRounds +
                            "\nRounds Won: " + roundsWon +
                            "\nFinal Score: " + (roundsWon * 10));
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGameGUI::new);
    }
}

