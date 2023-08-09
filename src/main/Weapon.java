package main;

public class Weapon {
    public void setAmmoCount(int ammoCount) {
        this.ammoCount = ammoCount;
    }

    private int ammoCount;
    private int range;



    public Weapon(int ammoCount, int range) {
        this.ammoCount = ammoCount;
        this.range = range;
    }

    public int getAmmoCount() {
        return ammoCount;
    }
    public int getRange() {
        return range;
    }

    public void decrementAmmo() {
        ammoCount = getAmmoCount() - 1;
    }

}
