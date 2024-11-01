package io.eva_01;

public interface Shootable {
    BulletEVA shoot();
    
    void reload();
    
    boolean canShoot();
}