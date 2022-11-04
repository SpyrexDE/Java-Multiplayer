import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

import utils.Vector2D;

import java.awt.event.*;
import java.awt.MouseInfo;

public class InputHandler {
    public static HashMap<Integer, String> keyMap = new HashMap<Integer, String>() {{
        put(KeyEvent.VK_W, "move_up");
        put(KeyEvent.VK_A, "move_left");
        put(KeyEvent.VK_S, "move_down");
        put(KeyEvent.VK_D, "move_right");
        put(KeyEvent.VK_UP, "move_up");
        put(KeyEvent.VK_LEFT, "move_left");
        put(KeyEvent.VK_DOWN, "move_down");
        put(KeyEvent.VK_RIGHT, "move_right");
    }};

    public static HashMap<Integer, String> btnMap = new HashMap<Integer, String>() {{
        put(MouseEvent.BUTTON1, "shoot");
    }};
    
    public static ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

    public static ArrayList<Integer> pressedBtns = new ArrayList<Integer>();

    public static void init(JPanel panel) {
        panel.addKeyListener(new TAdapter());
        panel.addMouseListener(new MAdapter());
    }

    public static Vector2D getMousePos() {
        return new Vector2D(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }

    public static Vector2D getMousePosOnCanvas() {
        return new Vector2D(MouseInfo.getPointerInfo().getLocation().x - GameWindow.currentCanvas.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - GameWindow.currentCanvas.getLocationOnScreen().y);
    }

    private static class MAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int btn = e.getButton();
            
            if (!pressedBtns.contains(btn))
                pressedBtns.add(btn);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int btn = e.getButton();
            
            if (pressedBtns.contains(btn))
                pressedBtns.remove(pressedBtns.indexOf(btn));
        }
    }

    private static class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (!pressedKeys.contains(key))
                pressedKeys.add(key);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (pressedKeys.contains(key))
                pressedKeys.remove(pressedKeys.indexOf(key));
        }
    }

    public static boolean isActionPressed(String action) {
        for (int key : pressedKeys) {
            if (keyMap.containsKey(key)) {
                if (keyMap.get(key).equals(action)) {
                    return true;
                }
            }
        }

        for (int btn : pressedBtns) {
            if (btnMap.containsKey(btn)) {
                if (btnMap.get(btn).equals(action)) {
                    return true;
                }
            }
        }

        return false;	
    }
}
