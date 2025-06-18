package com.game.project.GameV1.service;

import com.game.project.GameV1.model.Car;

import java.util.Random;

public class BattleService {
    private static final Random random = new Random();

    public String battle(Car car1, Car car2) {
        StringBuilder log = new StringBuilder();

        while (!car1.isDestroyed() && !car2.isDestroyed()) {
            Car attacker = (car1.getHealth() < car2.getHealth()) ? car1 : car2;
            Car defender = (attacker == car1) ? car2 : car1;

            int attackRoll = random.nextInt(6) + 1;
            int defenseRoll = random.nextInt(6) + 1;

            int attackDamage = attacker.getAttackPower() * attackRoll;
            int defensePower = defender.getDefenseStrength() * defenseRoll;
            int netDamage = Math.max(0, attackDamage - defensePower);

            defender.takeDamage(netDamage);

            log.append(String.format(
                    "%s attacks! 🎲 Attack Roll: %d, Defense Roll: %d → %s takes %d damage, Health: %d\n",
                    attacker.getName(), attackRoll, defenseRoll, defender.getName(), netDamage, defender.getHealth()
            ));
        }

        String winner = car1.isDestroyed() ? car2.getName() : car1.getName();
        log.append("🏆 Winner: " + winner);
        return log.toString();
    }
    public int test(int a){
        int b = a*a;
        return b;
    }
}


