package com.mycompany.degraudabola;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DegrauDaBola implements ActionListener {
    
    // Criando as variáveis: 
    
    JFrame frame;
    JButton calcularBtn, resetarBtn;
    JTextField larguraInput, alturaInput, velocidadeInput;
    double largura, altura, velocidade;
    
    // Construtor:
    
    DegrauDaBola() {
        
        // Criando a janelinha da calculadora: 
        
        frame = new JFrame("Calcular Degrau da Bola"); // Cria a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define que quando eu clicar no X da janela ela fecha
        frame.setSize (450, 320); // Define o tamanho da janela
        frame.setLayout(null); // To dizendo pro programa que eu vou controlar manualmente onde cada coisa fica
        
        // Criando os titulos e input para as variáveis: 
        
        // Largura:
        
        JLabel larguraLabel = createLabel("Largura do degrau (de 5 a 50 cm):", 30, 30, 200, 30);
        larguraInput = createTextField ("", 240, 30, 100, 30);
        
        // Altura:
        
        JLabel alturaLabel = createLabel("Altura do degrau (de 5 a 50 cm):", 30, 80, 200, 30);
        alturaInput = createTextField ("", 240, 80, 100, 30);
        
        // Velocidade: 
        
        JLabel velocidadeLabel = createLabel("Velocidade (0.1 a 10 m/s):", 30, 130, 200, 30);
        velocidadeInput = createTextField ("", 240, 130, 100, 30);
        
        // Criando os botões de calcular e resetar:
        
        calcularBtn = new JButton("Calcular"); // Cria o botão
        calcularBtn.setBounds(100, 200, 100, 40); // Define o tamanho e as coordenadas dele
        calcularBtn.addActionListener(this); // O botão vai saber quando eu clicar e fazer o que precisa
        calcularBtn.setFocusable(false);
        
        resetarBtn = new JButton("Resetar");
        resetarBtn.setBounds(250, 200, 100, 40);
        resetarBtn.addActionListener(this);
        resetarBtn.setFocusable(false);
        
        // Adicionando os elementos ao frame pra mostrar na tela:
        
        frame.add(larguraLabel);
        frame.add(larguraInput);
        frame.add(alturaLabel);
        frame.add(alturaInput);
        frame.add(velocidadeLabel);
        frame.add(velocidadeInput);
        frame.add(calcularBtn);
        frame.add(resetarBtn);

        frame.setVisible(true);
    }
    
    // Criando a função main para rodar o código:
    
    public static void main(String[] args) {
        new DegrauDaBola();
    }
    
    // Executando a função dos botões: 
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularBtn) { // Se o botão clicado foi o de calcular
            
            try {
                
                // Pegando o que foi digitado e transformando em números:
                
                largura = Double.parseDouble(larguraInput.getText());
                altura = Double.parseDouble(alturaInput.getText());
                velocidade = Double.parseDouble(velocidadeInput.getText());
                
                // Fazendo as verificações pros erros:
                
                if (largura < 5 || largura > 50) { 
                    errorMsg("A largura deve ser entre 5 e 50 cm!");
                    return;
                }
                
                if (altura < 5 || altura > 50) {
                    errorMsg("A altura deve ser entre 5 e 50 cm!");
                    return;
                }
                
                if (velocidade < 0.1 || velocidade > 10) {
                    errorMsg("A velocidade deve ser entre 0.1 e 10 m/s!");
                    return;
                }
                
                // Cálculos:
                
                double gravidade = 9.8;
                double larguraM = largura / 100; // Transformando de centímetros pra metros
                double alturaM = altura / 100;
                int degrau = 0;
                double tempoQueda = 0;
                
                for (int n = 1; n <= 1000; n++) { // n é o numero do degrau que estamos testando
                    double x = n * larguraM; // x é a distância total que a bola percorreu até chegar no degrau n;
                    double t = x / velocidade; // t é ,o tempo que a bola leva pra percorrer a distância x
                    double y = 0.5 * gravidade * t * t; // y é quanto a bola já caiu verticalmente no tempo t, utilizamos a fórmula da quedra livre
                    
                    if (y >= n * alturaM) { // Compara a queda da bola y com a altura total do degrau n, se y >= n, a bola caiu o suficiente pra bater
                        degrau = n;
                        tempoQueda = t;
                        break;
                    }
                }
                
                // Exibindo os resultados: 
                
                if (degrau > 0) {
                    String mensagem = String.format(
                        "Degrau atingido: %d\n" + 
                        "Tempo de queda: %.3f segundos",
                        degrau, tempoQueda
                    );
                    JOptionPane.showMessageDialog(frame, mensagem, "Resultado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                        "A bola não atingiu nenhum degrau nos primeiros 1000 degraus.",
                        "Resultado",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                errorMsg("Insira valores numéricos válidos!");
            }
        }
            if (e.getSource() == resetarBtn) {
                larguraInput.setText("");
                alturaInput.setText("");
                velocidadeInput.setText("");
            }
        }
        
    // Métodos auxiliares:
    
    private JTextField createTextField(String label, int x, int y, int w, int h) {
        JTextField textField = new JTextField(label);
        textField.setBounds(x, y, w, h);
        textField.setEditable(true);
        return textField;
    }

    private JLabel createLabel(String label, int x, int y, int w, int h) {
        JLabel labelText = new JLabel(label);
        labelText.setBounds(x, y, w, h);
        return labelText;
    }

    private void errorMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}