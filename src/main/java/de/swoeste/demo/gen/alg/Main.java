package de.swoeste.demo.gen.alg;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame {

    // Ideen
    // - Travelling Salesman
    // - Evtl. Java FX nutzen?

    public Main() {
        super("SimpleTest");
    }

    @Override
    public void init(final GameContainer container) throws SlickException {
    }

    @Override
    public void update(final GameContainer container, final int delta) throws SlickException {
    }

    @Override
    public void render(final GameContainer container, final Graphics g) throws SlickException {
        g.drawString("Hello, Slick world!", 0, 100);
    }

    public static void main(final String[] args) {
        try {
            final String lwjglLoc = "E:\\Dev\\m2_repository\\org\\lwjgl\\lwjgl\\lwjgl-platform\\2.9.3\\lwjgl-platform-2.9.3-natives-windows";
            final String jinputLoc = "E:\\Dev\\m2_repository\\net\\java\\jinput\\jinput-platform\\2.0.5\\jinput-platform-2.0.5-natives-windows";
            System.setProperty("org.lwjgl.librarypath", new File(lwjglLoc).getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath", new File(jinputLoc).getAbsolutePath());

            AppGameContainer app = new AppGameContainer(new Main());
            app.setDisplayMode(500, 500, false);
            app.setTargetFrameRate(60);
            app.setVSync(true);
            app.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}