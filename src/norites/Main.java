package norites;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends BasicGame {
	/* 1. Main クラスまたはオブジェクトに所属するメンバー変数の宣言を書く所 */
	float x = 180, y = 184;
	float usax = 150, usay = 340;
	int right = 1;
	boolean usamuki= true;//trueだと右向き
	Random rnd = new Random();
	byte icount = 0;
	boolean ismove = false;
	static final float SPEED = 0.1f;
	Image[] sprite = new Image[7];
	private Animation noripie,walk,wait;
	
	String path =null;
	Image unko, tinko, enemy,houdai,tama;
	

	TiledMap map = null;
	int map1,map2;
	public Main(String title) {
		/* 2. コンストラクター */
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		/* 3. 初期化ルーチン
		（フォントや画像、サウンド等のデータをファイルから読み込んで
		オブジェクトとして変数名に関連付けたりする）
		当然、ここはループしない */
		SpriteSheet ssheet = new SpriteSheet(new Image("./resource/img/noripyonsp.png"), 64, 64);

		byte i;
		for (i = 0; i < sprite.length; i++) {
			sprite[i] = ssheet.getSubImage(i, 0);
		}
		Image[] pyonning = {sprite[3],sprite[4],sprite[5],sprite[6]};
		Image[] waiting = {sprite[1],sprite[2],sprite[1],sprite[2]};
		int[] duration = {100,100,100,100};

		walk = new Animation(pyonning, duration, false);
		wait = new Animation(waiting, duration, true);

		noripie = wait;
		
		try{
			unko = new Image("./resource/kabe1.png");

			tinko = new Image("./resource/kabe2.png");
			
			enemy = new Image("./resource/usatan.gif");
			
			houdai = new Image("./resource/砲台1.gif");
			
			tama = new Image("./resource/砲弾.gif");
		}catch(Exception e){
		}
		
		path = "./resource/untitled.tmx";
		//System.out.println(path);
		
		try{
			map = new TiledMap(path, false);
		}catch(SlickException e){
			System.out.println("Error Loading Map!");
		
		}
		
		map1 = map.getLayerIndex("タイル・レイヤー1");
		map2 = map.getLayerIndex("collision");

		
	}
	@SuppressWarnings("static-access")
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		/* 4. ゲームの内部状態（変数等）の更新に関するルーチン
		 * 
		（ゲームのロジックや入力に関する本体・メインループ） */
		float px=x,py=y;
		
		
		float move = SPEED * delta;
		Input input = gc.getInput();
		if (input.isKeyDown(input.KEY_LEFT)) {
			x -= move;
			right = -1;
			
		} else if (input.isKeyDown(input.KEY_RIGHT)) {
			x += move;
			right = 1;
		}
		if (input.isKeyDown(input.KEY_UP)) {
			y -= move;
		} else if (input.isKeyDown(input.KEY_DOWN)) {
			y += move;
		}
		int ntx=(int)x/64; //のりぴーのタイル位置
		int nty=(int)y/64; //のりぴーのタイル位置
		int utx=(int)usax/64;//うさたんのタイル位置
		int uty=(int)usay/64;
		
		if(map.getTileId(ntx, nty, map2)==3 ||
				map.getTileId(ntx+1, nty, map2)==3 || 
				map.getTileId(ntx, nty+1, map2)==3 || 
				map.getTileId(ntx+1, nty+1, map2) == 3){
			x=px;
			y=py;
		}
		if(map.getTileId(ntx, nty, map1)==66){
			x=77;
			y=77;
		}
		if(ntx==utx && nty == uty){
			for(;;){
				x=rnd.nextInt(640-64);
				y=rnd.nextInt(400-64);
				if(map.getTileId((int)x/64, (int)y/64, map2)!=3 &&
					map.getTileId((int)x/64+1, (int)y/64, map2)!=3 && 
					map.getTileId((int)x/64, (int)y/64+1, map2)!=3 && 
					map.getTileId((int)x/64+1, (int)y/64+1, map2) != 3)
					break;
			}
		}
		
		
		if (input.isKeyDown(input.KEY_LEFT)||
			input.isKeyDown(input.KEY_RIGHT)||
			input.isKeyDown(input.KEY_UP)||
			input.isKeyDown(input.KEY_DOWN))
		{
			ismove = true;
			noripie = walk;
			noripie.update(delta);
		} else {
			ismove = false;
			noripie = wait;
			noripie.update(delta);
		};
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		/* 5. 画面描画に関するルーチン
		（ゲームの結果出力に関する本体・メインループ） */
		
		
		for(int tx = 0; tx < 10; tx++){
			for(int ty =0; ty<7;ty++){
			//	System.out.println(map.getTileId(tx,ty,map2));
			if(map.getTileId(tx, ty, map1) == 2){
				
				
				g.drawImage(unko,tx*64,ty*64);
			}
			if(map.getTileId(tx, ty, map1) == 3){
				
				
				g.drawImage(tinko,tx*64,ty*64);
			}
			//System.out.println(map.getTileId(tx, ty, map1));
			
			if(map.getTileId(tx, ty, map2) == 4){
				g.drawImage(houdai,tx*64,ty*64);
			}
			
			
			
			
			
			
			
			}
	}
		if(right==1){
			noripie.draw((int)x,(int)y,right*64,64);
		}else if(right == -1){
			noripie.draw((int)x+64,(int)y,right*64,64);
		}
		if(usamuki)
			usax+=0.1;
		
		else
			usax-=0.1;
		//System.out.println(usamuki);
		if(map.getTileId((int)usax/64, (int)usay/64, map2) == 3 && usamuki==false)
			usamuki = true;
		
		else if(map.getTileId((int)usax/64+1, (int)usay/64, map2) == 3 && usamuki==true)
			usamuki = false;
		
			g.drawImage(enemy, usax, usay);
	}

	public static void main(String[] args) throws SlickException {
		/* 6. JVM 側がこの Main クラスを実体化するための、
		いわば着火メソッド。便宜上、このクラスに埋め込まれているだけで、
		ゲームプログラム本体とは基本的に関係がない部分 */
		AppGameContainer app = new AppGameContainer(new Main("骨組"));
		app.setDisplayMode(680, 500, false);
		app.start();
	}
}