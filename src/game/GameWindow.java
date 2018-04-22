package game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindow {
	
	private Group root = new Group();
	private int width, height;
	private Canvas canvas;
	private Stage stage = new Stage();
	private Player player;
	private ScoreCounter scorecounter;
	private FpsCounter fpscounter;
	private Collisions collisions;
	private BoundingBox bounds;
	private double vgravity = 0.9;
	private double hgravity = 0.3;
	private List<Enemy> enemylist;
	private List<RainDrop> rainlist;
	private List<DestroyEffect> delist;
	private List<Turbine> turbinelist;
	private List<Spike> spikelist;
	private List<Icicle> iciclelist;
	private Timeline tl;
	
	public GameWindow(){
		this.width = 450;
		this.height = 300;
		this.canvas = new Canvas(width, height);
		this.canvas.setOnMouseClicked((a) -> tl.play());
	}
	
	private void initList(){
		enemylist = new ArrayList<Enemy>();
		rainlist = new ArrayList<RainDrop>();
		delist = new ArrayList<DestroyEffect>();
		turbinelist = new ArrayList<Turbine>();
		spikelist = new ArrayList<Spike>();
		iciclelist = new ArrayList<Icicle>();
	}
	
	public void startGame(){
		this.root.getChildren().add(canvas);
	    this.stage.setTitle("Jump 'n' Run");
	    this.stage.setScene(new Scene(root, width, height, Color.GREY));
	    restartGame();
	    this.stage.show();
	    animationFrame();
	}
	
	private void createPlayer(){
		player = new Player(canvas);
	}
	
	private void createCollisions() {
		collisions = new Collisions();
	}
	
	private void createBoundingBox() {
		bounds = new BoundingBox(canvas);
	}
	
	private void createScore(){
		scorecounter = new ScoreCounter(canvas, 0, 0, 20);
	}
	
	private void createFpsCounter() {
		fpscounter = new FpsCounter(canvas, this.width, 0, 20);
		fpscounter.startCounting();
	}
	
	private void createEnemies(){
		LifeForm previousenemy = null;
		double distance = 0;
		
		if(enemylist.size() > 0 && spikelist.size() <= 0){
			previousenemy = enemylist.get(enemylist.size()-1);
			distance = this.height - previousenemy.x;
		}else if(spikelist.size() > 0 && enemylist.size() <= 0) {
			previousenemy = spikelist.get(spikelist.size()-1);
			distance = this.height - previousenemy.x;
		}else if(spikelist.size() > 0 && enemylist.size() > 0){
			if(enemylist.get(enemylist.size()-1).x > spikelist.get(spikelist.size()-1).x)
				previousenemy = enemylist.get(enemylist.size()-1);
			else
				previousenemy = spikelist.get(spikelist.size()-1);
			
			distance = this.height - previousenemy.x;
		}
		
		if(previousenemy == null || distance > 30) {
			double num = getRandomNumber(0, 100);
			if(num > scorecounter.getScore()) {
				Enemy e = new Enemy(canvas);
				enemylist.add(e);
			}else {
				Spike s = new Spike(canvas);
				spikelist.add(s);
			}
		}
	}
	
	private void createRain(){
		if(rainlist.size() > 500) return;
		double x = getRandomNumber(this.width+this.width/2, 0);
		double y = -getRandomNumber(this.height/2, 0);
		double ampspeed = getRandomNumber(2, 0);
		RainDrop r = new RainDrop(canvas, x, y, ampspeed);
		rainlist.add(r);
	}
	
	private void createIcicle() {
		if(this.iciclelist.size() > 3) return;
		double x = getRandomNumber(this.width, 0);
		double y = 0;
		double timer = getRandomNumber(2000, 8000);
		Icicle ice = new Icicle(canvas, x, y, timer);
		iciclelist.add(ice);
	}
	
	private void createTurbine() {
		if(turbinelist.size() > 1) return;
		double w = 30;
		double h = 100;
		double diff = 30;
		double x = 0;
		double y = this.height-(h/2);
		Turbine eg = new Turbine(canvas, x, y, w, h, diff);
		turbinelist.add(eg);
	}
	
	private double getRandomNumber(double min, double max){
		Random rn = new Random();
		double num = (min + (max - min) * rn.nextDouble());
		return num;
	}
	
	private void animationFrame(){
	final Duration dur = Duration.millis(1000/60);
	final Timeline timeline = new Timeline();
	 timeline.setCycleCount(Animation.INDEFINITE);
	 timeline.setAutoReverse(false);
	 timeline.getKeyFrames().add(new KeyFrame(dur,
		new EventHandler<ActionEvent>() {
		   @Override
		   public void handle(ActionEvent args0) {
			  clearCanvas();
			  checkOnFocus();
		      handlePlayer();
		      handleEnemies();
		      handleRain();
		      handleIcicle();
		      handleDestroyEffect();
		      handleTurbine();
		      handleCollision();
		      handleScore();
		      handleFps();
		   }
		})
	 );
	 tl = timeline;
	 timeline.play();
	}
	
	private void checkOnFocus() {
		if(!this.canvas.isFocused()) {
			tl.pause();
		}
	}
	
	private void restartGame() {
		initList();
		
		createPlayer();
	    createScore();
	    createEnemies();
	    createCollisions();
	    createFpsCounter();
	    createBoundingBox();
	}
	
	private void clearCanvas(){
		this.canvas.getGraphicsContext2D().clearRect(0,0,this.width, this.height);
	}
	
	private void handleTurbine() {
		createTurbine();
		for(int ii = 0; ii < turbinelist.size(); ii++) {
			Turbine eg = turbinelist.get(ii);
			eg.drawTurbine();
			eg.handleWind();
		}
	}
	
	private void handleDestroyEffect() {
		for(int ii = 0; ii < delist.size(); ii++){
			DestroyEffect de = delist.get(ii);
			de.drawObjectOval();
			de.moveDestroyEffect();
			de.applyMovement();
			
			handleOutOfBounds(de);
		}
	}
	
	private void handleIcicle() {
		if(this.scorecounter.getScore() > 20)
			createIcicle();
		for(int ii = 0; ii < iciclelist.size(); ii++){
			Icicle ice = iciclelist.get(ii);
			ice.drawIcicle();
			ice.moveIcicle();
			ice.applyMovement();
			ice.verticalGravity(vgravity);
			ice.horizontalGravity(hgravity);
			
			handleOutOfBounds(ice);
		}
	}
	
	private void handleRain(){
		if(this.scorecounter.getScore() < 20)
			createRain();
		for(int ii = 0; ii < rainlist.size(); ii++){
			RainDrop rain = rainlist.get(ii);
			rain.drawObjectRect();
			rain.moveRainDrop();
			rain.applyMovement();
			rain.verticalGravity(vgravity);
			rain.horizontalGravity(hgravity);
			
			handleOutOfBounds(rain);
		}
	}
	
	private void handleEnemies(){
		createEnemies();
		for(int ii = 0; ii < enemylist.size(); ii++){
			Enemy enemy = enemylist.get(ii);
			enemy.drawObjectRect();
			enemy.moveEnemy();
			enemy.applyMovement();
			enemy.horizontalGravity(hgravity);
			enemy.verticalGravity(vgravity);
			
			handleOutOfBounds(enemy);
		}
		for(int ii = 0; ii < spikelist.size(); ii++) {
			Spike spike = spikelist.get(ii);
			spike.drawSpike();
			spike.moveSpike();
			spike.applyMovement();
			spike.horizontalGravity(hgravity);
			spike.verticalGravity(vgravity);
			
			handleOutOfBounds(spike);
		}
	}
	
	private void handlePlayer(){
		player.drawObjectRect();
	    player.movePlayer();
	    player.applyMovement();
	    player.drawJumpEffect();
	    player.verticalGravity(vgravity);
	    player.horizontalGravity(hgravity);
	    
	    handleOutOfBounds(player);
	}
	
	private void handleCollision(){
		handleRainCollision();
		handleEnemyCollision();
		handleTurbineCollision();
		handleIcicleCollision();
	}
	
	private void handleIcicleCollision() {
		ArrayList<Icicle> removeicicle = new ArrayList<>();
		for(Icicle ice : iciclelist) {
			iceEnemyCollision(ice, removeicicle);
			iceSpikeCollision(ice, removeicicle);
			icePlayerCollision(ice);
			iceTurbineCollision(ice, removeicicle);
		}
		for(Icicle ice : removeicicle) {
			iciclelist.remove(ice);
		}
	}
	
	private void iceTurbineCollision(Icicle ice, ArrayList<Icicle> removeicicle) {
		for(Turbine t : turbinelist) {
			if(collisions.collisionAABB(t, ice)) {
				collisions.collisionSide(t, ice);
				if(collisions.collisionRectTriangle(t, ice)) {
					removeicicle.add(ice);
				}
			}else {
				ice.resetCollision();
				t.resetCollision();
			}
		}
	}

	private void iceEnemyCollision(Icicle ice, ArrayList<Icicle> removeicicle) {
		ArrayList<Enemy> removeenemy = new ArrayList<>();
		for(Enemy e : enemylist) {
			if(collisions.collisionAABB(e, ice)) {
				collisions.collisionSide(e, ice);
				if(collisions.collisionRectTriangle(e, ice)) {
					removeenemy.add(e);
					removeicicle.add(ice);
				}
			}else {
				ice.resetCollision();
				e.resetCollision();
			}
		}
		for(Enemy e : removeenemy) {
			enemylist.remove(e);
		}
	}

	private void iceSpikeCollision(Icicle ice, ArrayList<Icicle> removeicicle) {
		ArrayList<Spike> removespike = new ArrayList<>();
		for(Spike s : spikelist) {
			if(collisions.collisionAABB(s, ice)) {
				collisions.collisionSide(s, ice);
				if(collisions.collisionRectTriangle(s, ice)) {
					removespike.add(s);
					removeicicle.add(ice);
				}
			}else {
				ice.resetCollision();
				s.resetCollision();
			}
		}
		for(Spike s : removespike) {
			spikelist.remove(s);
		}
	}
	
	private void icePlayerCollision(Icicle ice) {
		if(collisions.collisionAABB(player, ice)) {
			collisions.collisionSide(player, ice);
			if(collisions.collisionRectTriangle(player, ice)) {
				tl.pause();
				restartGame();
			}
		}else {
			ice.resetCollision();
			player.resetCollision();
		}
	}

	private void handleTurbineCollision() {
		Player player1 = player;
		for(Turbine t : turbinelist) {
			if(collisions.collisionAABB(player1, t))
				turbineOnCollision(player1, t);
			else {
				player1.resetCollision();
				t.resetCollision();
			}
		}
	}
	
	private void turbineOnCollision(Player player, Turbine turbine) {
		player.setCollision(turbine);
		collisions.collisionSide(player, turbine);
		player.handleCollision();
	}

	private void handleRainCollision() {
		ArrayList<RainDrop> removerain = new ArrayList<>();
		Player lf1 = player;
		for(RainDrop r : rainlist) {
			if(collisions.collisionAABB(lf1, r)) {
				r.setCollision(player);
				removerain.add(r);
			}
		}
		for(RainDrop r : removerain) {
			rainlist.remove(r);
		}
	}
	
	private void handleEnemyCollision() {
		ArrayList<Enemy> removeenemy = new ArrayList<>();
		Player lf1 = player;
		for(Enemy e : enemylist) {
			if(collisions.collisionAABB(lf1, e))
				enemyOnCollision(lf1, e, removeenemy);
			else {
				lf1.resetCollision();
				e.resetCollision();
			}
		}
		for(Enemy e : removeenemy) {
			enemylist.remove(e);
		}
		for(Spike s : spikelist) {
			if(collisions.collisionAABB(lf1, s)) {
				collisions.collisionSide(lf1, s);
				if(collisions.collisionRectTriangle(lf1, s)) {
					tl.pause();
					restartGame();
				}
			}else {
				lf1.resetCollision();
				s.resetCollision();
			}
		}
	}
	
	private void enemyOnCollision(Player player, Enemy enemy, ArrayList<Enemy> elist) {
		player.setCollision(enemy);
		enemy.setCollision(player);
		collisions.collisionSide(player, enemy);
		player.handleCollision();
		if(!enemy.hasBeenHit() && !enemy.getCollisionTop()){
			scorecounter.changeScore(-1);
			enemy.setBeenHit(true);
		}else if(!enemy.hasBeenHit() && enemy.getCollisionTop()) {
			scorecounter.changeScore(2);
			enemy.setBeenHit(true);
			createDestroyEffect(enemy);
			elist.add(enemy);
		}else if(enemy.hasBeenHit() && enemy.getCollisionTop()) {
			createDestroyEffect(enemy);
			elist.add(enemy);
		}
	}
	
	private void createDestroyEffect(LifeForm enemy) {
		double amount = 20;
		for(int ii = 0; ii < amount; ii++) {
			double x = enemy.x + (enemy.width/2);
			double y = enemy.y + (enemy.height/2);
			double angle = ii * ((360*(Math.PI/180))/amount);
			DestroyEffect de = new DestroyEffect(canvas, x, y, angle);
			delist.add(de);
		}
	}
	
	private void handleOutOfBounds(WorldObject obj){
		obj.setOutOfBounds(bounds.isOutOfBounds(obj));
		if(obj.getOutOfBounds()){
			bounds.outOfBoundsSide(obj);
			if(obj instanceof RainDrop)
				((RainDrop) obj).onOutOfBounds(rainlist);
			else if(obj instanceof Icicle)
				((Icicle) obj).onOutOfBounds(iciclelist);
			else if(obj instanceof DestroyEffect)
				((DestroyEffect) obj).onOutOfBounds(delist);
			else if(obj instanceof Spike)
				((Spike) obj).onOutOfBounds(scorecounter, spikelist);
			else if(obj instanceof Enemy)
				((Enemy) obj).onOutOfBounds(scorecounter, enemylist);	
			else if(obj instanceof Player)
				((Player) obj).onOutOfBounds();
		}else
			bounds.resetOutOfBoundsSide(obj);
	}
	
	private void handleScore(){
		scorecounter.drawScore();
	}
	
	private void handleFps(){
		fpscounter.drawFps();
	}
	
}
