package wastedgames.proviant.layouts;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.objects.AbstractUnit;
import wastedgames.proviant.objects.environment.BackgroundGrass;
import wastedgames.proviant.objects.environment.Chamomile;
import wastedgames.proviant.objects.environment.Cloud;
import wastedgames.proviant.objects.environment.Grass;
import wastedgames.proviant.objects.environment.Rose;
import wastedgames.proviant.objects.environment.Stick;
import wastedgames.proviant.objects.environment.Stone;
import wastedgames.proviant.objects.environment.Sun;
import wastedgames.proviant.objects.fauna.Bug;
import wastedgames.proviant.objects.fauna.LadyBug;
import wastedgames.proviant.objects.fauna.Snail;
import wastedgames.proviant.objects.fauna.UnitBase;
import wastedgames.proviant.objects.fauna.Worm;

import static wastedgames.proviant.layouts.GameField.FLOOR_Y;

public class EnvironmentSolver {
    final int BASES_COUNT = 2;

    public void addEnvironment(UnitSolver solver, Vector2 size) {
        Sun sun = new Sun(100, 70);
        solver.addDrawableUnit(sun);
        for (int i = 0; i <= 10; i++) {
            BackgroundGrass backgroundGrass = new BackgroundGrass(256 * i, FLOOR_Y);
            solver.addDrawableUnit(backgroundGrass);
        }
        for (int i = 0; i < 50; i++) {
            Grass grass = new Grass((int) (Math.random() * size.getX()), FLOOR_Y);
            Stick stick = new Stick((int) (Math.random() * size.getX()), FLOOR_Y);
            Stone stone = new Stone((int) (Math.random() * size.getX()), FLOOR_Y);
            solver.addBoth(stick);
            solver.addBoth(stone);
            solver.addDrawableUnit(grass);
            if (i % 4 == 0) {
                Chamomile chamomile = new Chamomile((int) (Math.random() * size.getX()), FLOOR_Y);
                Rose rose = new Rose((int) (Math.random() * size.getX()), FLOOR_Y);
                solver.addDrawableUnit(chamomile);
                solver.addDrawableUnit(rose);
            }
        }
    }

    public void addUnits(UnitSolver solver, Vector2 size) {

        for (int i = 0; i < BASES_COUNT; i++) {
            UnitBase base1 = new UnitBase((int) (Math.random() * size.getX()), FLOOR_Y);
            UnitBase base2 = new UnitBase((int) (Math.random() * size.getX()), FLOOR_Y);
            UnitBase base3 = new UnitBase((int) (Math.random() * size.getX()), FLOOR_Y);
            UnitBase base4 = new UnitBase((int) (Math.random() * size.getX()), FLOOR_Y);
            for (int j = 0; j < 5; j++) {
                Snail s = new Snail((int) base1.getX(), FLOOR_Y);
                Bug b = new Bug((int) (Math.random() * size.getX()), FLOOR_Y);
                LadyBug lb = new LadyBug((int) (Math.random() * size.getX()), FLOOR_Y);
                Worm w = new Worm((int) (Math.random() * size.getX()), FLOOR_Y);
                solver.addBoth(w);
                solver.addBoth(s);
                solver.addBoth(b);
                solver.addBoth(lb);
                base1.addUnit(s);
                base2.addUnit(b);
                base3.addUnit(lb);
                base4.addUnit(w);
            }
            solver.addBoth(base1);
            solver.addBoth(base2);
            solver.addBoth(base3);
            solver.addBoth(base4);
        }
    }

    public void solveClouds(UnitSolver solver) {

    }

    public void checkEnvUnit(AbstractUnit unit, Vector2 screenSize) {
        if (unit instanceof Cloud) {
            if (unit.getX() > screenSize.getX() + unit.getWidth()) {
                unit.destroy();
            }
        }
    }
}
