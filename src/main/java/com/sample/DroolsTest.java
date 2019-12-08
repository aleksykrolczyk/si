package com.sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class DroolsTest {

    public static final void main(String[] args) {
        try {
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
        	
        	GUI gui = new GUI();
        	kSession.setGlobal("gui", gui);
            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static class Answer{
    	public String question;
    	public String answer;
    	
    	public Answer(String question, String answer) {
    		this.question = question;
    		this.answer = answer;
    	}
    }
    
    public static class GUI extends JFrame{
    	static final long serialVersionUID = 1;
		JTextPane textPane = new JTextPane();
		List<JButton> buttons = new ArrayList<>();
		Integer WIDTH = 600;
		Integer HEIGTH = 600;
		String userAnswer = null;

    	public GUI() {
    		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		this.setSize(WIDTH + 50, HEIGTH);
    		this.setLayout(new FlowLayout());
    		this.textPane.setPreferredSize(new Dimension(WIDTH,50));
    		this.textPane.setEditable(false);
    		
    		this.add(this.textPane, BorderLayout.NORTH);    		
    	}
    	
    	public String display(String question, List<String> answers) {
    		this.textPane.setText(question);
    		this.drawButtons(answers);
    		this.setVisible(true);
    		
    		while(this.userAnswer == null) {
    			try {
    				Thread.sleep(100);
    			}
    			catch(Exception e){
    				System.out.println("pozdro dla mamy i taty");
    			}
    		}
    		
    		System.out.println(this.userAnswer);
    		String answer = this.userAnswer;
    		this.userAnswer = null;
    		    		
    		return answer;
    	}
    	
    	private void drawButtons(List<String> answers) {
    		this.removeButtons();
    		for(String name: answers) {
    			JButton button = new JButton(name);
    			button.setPreferredSize(new Dimension(WIDTH, 100));
    			this.add(button);
    			this.buttons.add(button);
    			button.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				userAnswer = e.getActionCommand();
        			}
        		});
    		}
    		this.revalidate();
    		this.repaint();
    	}
    	
    	private void removeButtons(){
    		for(JButton button: this.buttons) {
    			this.remove(button);
    		}
    	}
    }
}
