package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.eva_01.Shootable;


public class EnemyShip extends Entity implements Shootable {
	private Texture bulletEnemy;
	private Texture textureEnemy;
	private MovementStrategy movementStrat;
	private int ammo;
	private float shootCooldown;
	private float lastShotTime;
	private boolean hurt;
	private float changeDirectionTime = 1.0f; // Tiempo entre cambios de dirección
	private float timeSinceLastChange = 0;
	private float directionX = -1; // Componente hacia la izquierda
	private float directionY = 0;  // Componente vertical inicial (sin movimiento)
	
    public EnemyShip(float x, float y, float xSpeed, float ySpeed, int health, Texture textureEnemy, MovementStrategy movementStrategy) {
        super(x, y, xSpeed, ySpeed, health, textureEnemy);
        this.ammo = 10;
        this.shootCooldown = 1f;
        this.lastShotTime = 0;
        this.bulletEnemy = bulletEnemy;
        this.movementStrat = movementStrategy;
    }

    @Override
    public void update(float delta) {
        // Obtener las dimensiones actuales de la ventana o pantalla
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Movimiento aleatorio
        timeSinceLastChange += delta;

        if (timeSinceLastChange >= changeDirectionTime) {
            // Generar nueva dirección aleatoria
            directionX = (float) (Math.random() * 2 - 1); // Valor entre -1 y 1
            directionY = (float) (Math.random() * 2 - 1);

            // Normalizar la dirección
            float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            if (length != 0) {
                directionX /= length;
                directionY /= length;
            }

            timeSinceLastChange = 0;
        }

        // Actualizar posición del enemigo
        x += directionX * xSpeed * delta;
        y += directionY * ySpeed * delta;

        // Rebotar en los límites dinámicos de la pantalla
        if (x < 0 || x + texture.getWidth() > screenWidth) {
            directionX = -directionX; // Cambiar dirección horizontal
            x = Math.max(0, Math.min(x, screenWidth - texture.getWidth())); // Ajustar posición
        }

        if (y < 0 || y + texture.getHeight() > screenHeight) {
            directionY = -directionY; // Cambiar dirección vertical
            y = Math.max(0, Math.min(y, screenHeight - texture.getHeight())); // Ajustar posición
        }
    }


    @Override
    public void onDestroy() {
    	GameManager.getInstance().addScore(100); // Agrega 100 puntos al puntaje global
    	if (Math.random() < 0.3) { // 30% de probabilidad
            dropPowerUp();
        }
    }
    
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    	if (x+xSpeed < 0 || x+xSpeed+spr.getWidth() > Gdx.graphics.getWidth())
        	xSpeed*= 0;
        if (y+ySpeed < 0 || y+ySpeed+spr.getHeight() > Gdx.graphics.getHeight())
        	ySpeed*= 0;
    }
    
    private void dropPowerUp() {
        // Elegir un tipo de power-up aleatoriamente
        PowerUp.PowerUpType[] powerUps = PowerUp.PowerUpType.values();
        PowerUp.PowerUpType chosenPowerUp = powerUps[(int) (Math.random() * powerUps.length)];

        // Determinar duración para power-ups temporales (si aplica)
        float duration = 0; // Duración por defecto (no temporal)
        if (chosenPowerUp == PowerUp.PowerUpType.SHIELD || 
            chosenPowerUp == PowerUp.PowerUpType.SPEED_BOOST || 
            chosenPowerUp == PowerUp.PowerUpType.DOUBLE_SHOT) {
            duration = 10f; // Ejemplo: duración de 10 segundos para efectos temporales
        }

        // Crear el power-up en la posición actual del enemigo
        Texture texture;
        switch (chosenPowerUp) {
            case EXTRA_LIFE:
                texture = new Texture("extralife.png");
                break;
            case SHIELD:
                texture = new Texture("shield.png");
                break;
            case SPEED_BOOST:
                texture = new Texture("speedBoost.png");
                break;
            case DOUBLE_SHOT:
                texture = new Texture("doublegun.png");
                break;
            default:
                throw new IllegalStateException("Tipo de power-up desconocido: " + chosenPowerUp);
        }

        PowerUp powerUp = new PowerUp(x, y, texture, chosenPowerUp, duration);

        // Agregar el power-up al mundo del juego
        GameManager.getInstance().addCollectible(powerUp);
    }



    @Override
    public BulletEVA shoot() {
    	if(canShoot()) {
    		lastShotTime = 0;
    		ammo--;
    		return new BulletEVA(spr.getX()+(spr.getWidth() - 30),spr.getY()+((spr.getHeight()/2) + 30),3,0,1,bulletEnemy);
    	}
    	return null;
    }

	@Override
	public void reload() {
		ammo = 20;
	}

	@Override
	public boolean canShoot() {
		return ammo > 0 && lastShotTime >= shootCooldown;
	}
	
	// Getter para health
    public int getHealth() {
        return health;
    }

    // Setter para health
    public void setHealth(int health) {
        if (health >= 0) {
            this.health = health;
        }
    }

	@Override
	public Rectangle getCollisionArea() {
		return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	public void checkCollisionWithPlayer(PlayerShip player) {
	    if (checkCollision(player)) {
	        // Rebote en dirección opuesta
	        directionX = -directionX;
	        directionY = -directionY;

	        // Opcional: Reducir vida al jugador al colisionar
	        player.takeDamage(1);
	    }
	}
	
	public void checkCollisionWithBullet(BulletEVA bullet) {
	    if (checkCollision(bullet)) {
	        takeDamage(bullet.getDamage()); // Aplicar daño según el ataque
	        bullet.onDestroy(); // Destruir la bala al impactar
	    }
	}
	
	 @Override
	    protected void move(float delta) {
	        movementStrat.move(this, delta);
	    }

}
